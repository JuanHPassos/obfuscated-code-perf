#!/usr/bin/env python3
"""
T8 - Analise estatistica do overhead de ofuscacao a partir dos JSON do JMH.

Le results/jmh-<arch>-<perfil>.json e, para cada (arquitetura, tamanho, perfil):
- agrega as amostras brutas (rawData) por FORK -> media por fork. O fork e a
  unidade de independencia correta: iteracoes dentro de um mesmo fork sao
  autocorrelacionadas (mesma JVM, aquecimento, JIT), entao tratar cada
  iteracao como amostra independente subestima a variancia (pseudo-replicacao);
- media e IC 95% (t de Student sobre as medias por fork);
- overhead relativo (%) vs. 'original', com IC 95% por bootstrap da razao;
- teste de significancia vs. 'original': Shapiro-Wilk decide entre t de Welch
  (normal) e Mann-Whitney U (nao-normal), quando ha amostras suficientes;
- correcao de multiplas comparacoes (Holm-Bonferroni) por (arch, size);
- tamanho de efeito (Cliff's delta), vetorizado.

Pipeline (main): load() -> analyze() -> summary.csv + plot().

Colunas de analysis/summary.csv:
  arch                arquitetura extraida do nome do arquivo (ex.: x64, arm64).
  size                parametro 'size' do benchmark (nº de ordens).
  profile             perfil de ofuscacao (original|baseline|flow|string|flowstring).
  mean, ci_lo, ci_hi  tempo medio por fork e IC 95% (t de Student).
  unit                unidade do JMH (ex.: us/op).
  overhead_%_vs_original overhead percentual da media vs. 'original'.
  overhead_ci_lo/hi   IC 95% do overhead via bootstrap da razao de medias.
  test                teste aplicado vs. 'original' (welch-t|mann-whitney|n/a).
  p_value             p-valor bruto do teste.
  p_value_holm        p-valor ajustado por Holm-Bonferroni na familia (arch, size).
  significant_5%      True se p_value_holm < 0.05.
  cliffs_delta        tamanho de efeito nao-parametrico em [-1, 1].
  n_forks             nº de forks (amostras) usados na inferencia.

Alem da comparacao "perfil vs. original" dentro de cada (arch, size), o script
tambem testa se o overhead de um perfil DIFERE entre pares de arquiteturas
(ex.: ARM vs. x86_64) -- ver analyze_arch_interaction(). Isso e necessario
porque "significativo em x86_64, nao-significativo em aarch64" (o que a
tabela summary.csv por si so sugere) NAO implica que a diferenca entre as
duas arquiteturas seja, ela mesma, significativa (Gelman & Stern, 2006).
O contraste e feito via bootstrap da diferenca de overheads, sem assumir
normalidade/homocedasticidade -- a mesma cautela estatistica do restante do
script.

Colunas de analysis/arch_interaction.csv:
  size                        parametro 'size' do benchmark.
  profile                     perfil de ofuscacao (!= original).
  arch_a, arch_b              par de arquiteturas comparado (arch_a < arch_b).
  overhead_diff_%_(b_minus_a) overhead_%(arch_b) - overhead_%(arch_a), ambos
                              vs. o 'original' da respectiva arquitetura.
  diff_ci_lo/hi               IC 95% da diferenca acima, via bootstrap.
  p_value                     p-valor bootstrap (2x a proporcao de amostras
                              que cruzam zero).
  p_value_holm                p-valor ajustado por Holm-Bonferroni na familia
                              (size, arch_a, arch_b), atraves dos perfis.
  significant_arch_diff_5%    True se p_value_holm < 0.05: o overhead desse
                              perfil difere entre as duas arquiteturas.
  n_forks_a, n_forks_b        nº de forks usados de cada lado.

Saidas:
  analysis/summary.csv
  analysis/arch_interaction.csv
  analysis/overhead-<arch>-size<N>.png  (barras com error bars, por arch+size)
  analysis/overhead-vs-size.png         (overhead x size, 1 linha/arch, facetado por perfil)

Requisitos: numpy, scipy, pandas, matplotlib
  pip install numpy scipy pandas matplotlib
"""

from __future__ import annotations

import glob
import json
import os
import re

import numpy as np
import pandas as pd

try:
    from scipy import stats
    HAVE_SCIPY = True
except ImportError:  # analise continua, mas sem p-valor/efeito
    HAVE_SCIPY = False

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

RESULTS_DIR = "results"              # diretorio de entrada com os JSON do JMH
OUT_DIR = "analysis"                 # diretorio de saida (CSV + graficos)
PROFILES_ORDER = ["original", "baseline", "flow", "string", "flowstring"]
FNAME_RE = re.compile(r"jmh-(?P<arch>[^-]+)-(?P<profile>[a-z]+)\.json$")
N_BOOTSTRAP = 10_000                 # reamostragens do IC de overhead
RNG = np.random.default_rng(20260727)  # seed fixa -> resultados reproduziveis

# Paleta categorica (slots 1-2, ordem fixa) -- ver dataviz skill / references/palette.md.
ARCH_COLORS = {"x86_64": "#2a78d6", "aarch64": "#eb6834"}
ARCH_FALLBACK_COLORS = ["#1baf7a", "#eda100", "#e87ba4", "#4a3aa7", "#e34948"]


def fork_means(primary: dict) -> list[float]:
    """Reduz o rawData de um benchmark a uma media por fork.

    rawData e uma lista (por fork) de listas (iteracoes). Reduzimos cada fork a
    sua media, obtendo uma amostra por fork -- a unidade de independencia. Sem
    rawData, usamos o score agregado como amostra unica.

    Args:
        primary: bloco 'primaryMetric' de uma entrada do JSON do JMH.

    Returns:
        Lista com uma media por fork (ou [score] no fallback).
    """
    raw = primary.get("rawData")
    means: list[float] = []
    if raw:
        for fork in raw:
            vals = [float(x) for x in fork]
            if vals:
                means.append(float(np.mean(vals)))
    if not means:  # fallback: usa o score agregado
        means = [float(primary["score"])]
    return means


def load() -> pd.DataFrame:
    """Carrega todos os results/jmh-*.json em um DataFrame long.

    Cada linha corresponde a um (arch, profile, size) e carrega em 'samples' as
    medias por fork. Arquivos ou entradas malformadas sao ignorados com aviso,
    para que um JSON corrompido nao interrompa toda a analise.

    Returns:
        DataFrame com colunas: arch, profile, size, unit, samples.

    Raises:
        SystemExit: se nenhum arquivo valido for encontrado.
    """
    rows = []
    for path in sorted(glob.glob(os.path.join(RESULTS_DIR, "jmh-*.json"))):
        m = FNAME_RE.search(os.path.basename(path))
        if not m:
            continue
        arch, profile = m.group("arch"), m.group("profile")
        try:
            with open(path, encoding="utf-8") as fh:
                data = json.load(fh)
        except (OSError, json.JSONDecodeError) as exc:
            print(f"[aviso] ignorando '{path}': {exc}")
            continue
        for entry in data:
            try:
                primary = entry["primaryMetric"]
                size = int(entry.get("params", {}).get("size", -1))
                rows.append(
                    {
                        "arch": arch,
                        "profile": profile,
                        "size": size,
                        "unit": primary.get("scoreUnit", "us/op"),
                        "samples": fork_means(primary),
                    }
                )
            except (KeyError, TypeError, ValueError) as exc:
                print(f"[aviso] entrada invalida em '{path}': {exc}")
    if not rows:
        raise SystemExit(
            f"Nenhum arquivo jmh-*.json em '{RESULTS_DIR}/'. Rode T6 (04_bench.sh) primeiro."
        )
    return pd.DataFrame(rows)


def ci95(samples: np.ndarray) -> tuple[float, float, float]:
    """Media e IC 95% (t de Student) de um conjunto de amostras.

    Com scipy ausente, usa a aproximacao normal (z=1.96). Com n<2 o intervalo
    degenera para o proprio ponto.

    Returns:
        Tupla (media, limite_inferior, limite_superior).
    """
    n = len(samples)
    mean = float(np.mean(samples))
    if n < 2:
        return mean, mean, mean
    sem = float(np.std(samples, ddof=1) / np.sqrt(n))
    if HAVE_SCIPY:
        h = sem * stats.t.ppf(0.975, n - 1)
    else:
        h = sem * 1.96
    return mean, mean - h, mean + h


def cliffs_delta(a: np.ndarray, b: np.ndarray) -> float:
    """Cliff's delta em [-1,1]; >0 => 'a' tipicamente maior que 'b'. Vetorizado."""
    a = np.asarray(a, float)
    b = np.sort(np.asarray(b, float))
    if len(a) == 0 or len(b) == 0:
        return float("nan")
    # para cada x em a: quantos b < x (gt) e quantos b > x (lt)
    gt = int(np.searchsorted(b, a, side="left").sum())
    lt = int((len(b) - np.searchsorted(b, a, side="right")).sum())
    return (gt - lt) / (len(a) * len(b))


def bootstrap_overhead_ci(profile: np.ndarray, base: np.ndarray) -> tuple[float, float]:
    """IC 95% do overhead (%) = (mean(profile)/mean(base) - 1)*100 via bootstrap."""
    if len(profile) < 2 or len(base) < 2:
        return float("nan"), float("nan")
    pr = RNG.choice(profile, size=(N_BOOTSTRAP, len(profile)), replace=True).mean(axis=1)
    ba = RNG.choice(base, size=(N_BOOTSTRAP, len(base)), replace=True).mean(axis=1)
    ratios = (pr / ba - 1.0) * 100.0
    lo, hi = np.percentile(ratios, [2.5, 97.5])
    return float(lo), float(hi)


def arch_overhead_diff_ci(
    profile_a: np.ndarray, base_a: np.ndarray, profile_b: np.ndarray, base_b: np.ndarray
) -> tuple[float, float, float, float]:
    """Bootstrap da diferenca de overhead (%) entre duas arquiteturas.

    overhead_x(%) = (mean(profile_x)/mean(base_x) - 1) * 100, para x em {a, b}.
    Reamostra os 4 grupos independentemente (nao ha pareamento entre forks de
    arquiteturas diferentes) e devolve a distribuicao de overhead_b - overhead_a.

    Returns:
        Tupla (diff_pontual, ic_lo, ic_hi, p_valor). NaN se dados insuficientes.
    """
    if min(len(profile_a), len(base_a), len(profile_b), len(base_b)) < 2:
        return float("nan"), float("nan"), float("nan"), float("nan")
    pa = RNG.choice(profile_a, size=(N_BOOTSTRAP, len(profile_a)), replace=True).mean(axis=1)
    ba = RNG.choice(base_a, size=(N_BOOTSTRAP, len(base_a)), replace=True).mean(axis=1)
    pb = RNG.choice(profile_b, size=(N_BOOTSTRAP, len(profile_b)), replace=True).mean(axis=1)
    bb = RNG.choice(base_b, size=(N_BOOTSTRAP, len(base_b)), replace=True).mean(axis=1)
    diff = (pb / bb - 1.0) * 100.0 - (pa / ba - 1.0) * 100.0
    point = (
        (float(np.mean(profile_b)) / float(np.mean(base_b)) - 1.0) * 100.0
        - (float(np.mean(profile_a)) / float(np.mean(base_a)) - 1.0) * 100.0
    )
    lo, hi = np.percentile(diff, [2.5, 97.5])
    p = min(2.0 * min(float(np.mean(diff <= 0)), float(np.mean(diff >= 0))), 1.0)
    return point, float(lo), float(hi), p


def holm_bonferroni(pvals: list[float]) -> np.ndarray:
    """Holm-Bonferroni; retorna p ajustados na ordem original (NaN ignorados)."""
    p = np.asarray(pvals, float)
    adj = np.full_like(p, np.nan)
    idx = np.where(~np.isnan(p))[0]
    if len(idx) == 0:
        return adj
    order = idx[np.argsort(p[idx])]
    m = len(order)
    prev = 0.0
    for rank, i in enumerate(order):
        val = min((m - rank) * float(p[i]), 1.0)
        val = max(val, prev)  # garante monotonicidade
        adj[i] = val
        prev = val
    return adj


def significance(orig: np.ndarray, other: np.ndarray) -> tuple[float, str, float]:
    """Compara 'other' vs, 'orig' e devolve (p_valor, teste, cliffs_delta).

    Escolha do teste: Shapiro-Wilk em cada amostra decide entre t de Welch
    (ambas normais) e Mann-Whitney U (caso contrario). Com poucos forks (n<3)
    cai direto para Mann-Whitney, ou 'n/a' quando nem isso e viavel/scipy falta.

    Returns:
        (p_valor, nome_do_teste, cliffs_delta). p_valor pode ser NaN.
    """
    delta = cliffs_delta(other, orig)
    if not HAVE_SCIPY:
        return float("nan"), "n/a", delta
    # amostras pequenas (poucos forks): usa Mann-Whitney se viavel, senao n/a
    if len(orig) < 3 or len(other) < 3:
        if len(orig) >= 1 and len(other) >= 1 and len(orig) + len(other) >= 3:
            try:
                p = stats.mannwhitneyu(other, orig, alternative="two-sided").pvalue
                return float(p), "mann-whitney", delta
            except ValueError:
                pass
        return float("nan"), "n/a", delta
    normal = True
    for s in (orig, other):
        try:
            if stats.shapiro(s).pvalue < 0.05:
                normal = False
        except ValueError:
            normal = False
    if normal:
        p = stats.ttest_ind(other, orig, equal_var=False).pvalue
        test = "welch-t"
    else:
        p = stats.mannwhitneyu(other, orig, alternative="two-sided").pvalue
        test = "mann-whitney"
    return float(p), test, delta


def analyze(df: pd.DataFrame) -> pd.DataFrame:
    """Agrega por (arch, size) e produz o DataFrame de resumo por perfil.

    Para cada grupo calcula media/IC, overhead vs. 'original' (com IC bootstrap),
    teste de significancia e Cliff's delta. Os p-valores dos perfis != original
    formam uma familia por grupo, corrigida via Holm-Bonferroni; 'significant_5%'
    usa o p ajustado.

    Returns:
        DataFrame com uma linha por (arch, size, profile). Veja o cabecalho do
        modulo para a descricao das colunas.
    """
    out = []
    for (arch, size), grp in df.groupby(["arch", "size"]):
        by_profile = {r.profile: np.asarray(r.samples, float) for r in grp.itertuples()}
        base = by_profile.get("original")
        unit = grp.iloc[0]["unit"]
        rows: list[dict] = []
        raw_p: list[float] = []  # p-valores brutos da familia (perfis != original)
        for profile in PROFILES_ORDER:
            if profile not in by_profile:
                continue
            s = by_profile[profile]
            mean, lo, hi = ci95(s)
            overhead = float("nan")
            oh_lo = oh_hi = float("nan")
            p, test, delta = float("nan"), "", float("nan")
            if base is not None and len(base):
                overhead = (mean / float(np.mean(base)) - 1.0) * 100.0
                if profile != "original":
                    p, test, delta = significance(base, s)
                    oh_lo, oh_hi = bootstrap_overhead_ci(s, base)
            rows.append(
                {
                    "arch": arch,
                    "size": size,
                    "profile": profile,
                    "mean": round(mean, 4),
                    "ci_lo": round(lo, 4),
                    "ci_hi": round(hi, 4),
                    "unit": unit,
                    "overhead_%_vs_original": round(overhead, 2),
                    "overhead_ci_lo": None if np.isnan(oh_lo) else round(oh_lo, 2),
                    "overhead_ci_hi": None if np.isnan(oh_hi) else round(oh_hi, 2),
                    "test": test,
                    "p_value": None if np.isnan(p) else round(p, 6),
                    "cliffs_delta": None if np.isnan(delta) else round(delta, 3),
                    "n_forks": len(s),
                }
            )
            raw_p.append(p if profile != "original" else float("nan"))
        adj = holm_bonferroni(raw_p)
        for row, padj in zip(rows, adj):
            row["p_value_holm"] = None if np.isnan(padj) else round(float(padj), 6)
            row["significant_5%"] = (not np.isnan(padj)) and padj < 0.05
            out.append(row)
    return pd.DataFrame(out)


def analyze_arch_interaction(df: pd.DataFrame) -> pd.DataFrame:
    """Testa se o overhead de cada perfil difere entre pares de arquiteturas.

    Para cada size e cada par de arquiteturas presentes, bootstrapa
    overhead_%(arch_b) - overhead_%(arch_a) (ambos vs. o 'original' da
    respectiva arquitetura) para cada perfil != original. Os p-valores dos
    perfis formam uma familia por (size, arch_a, arch_b), corrigida via
    Holm-Bonferroni -- mesmo padrao usado em analyze() para perfil vs.
    original, aplicado aqui a arquitetura vs. arquitetura.

    Returns:
        DataFrame com uma linha por (size, profile, arch_a, arch_b). Vazio se
        houver menos de duas arquiteturas nos dados.
    """
    archs = sorted(df["arch"].unique())
    out = []
    for size, grp_size in df.groupby("size"):
        by_arch = {
            arch: {r.profile: np.asarray(r.samples, float) for r in g.itertuples()}
            for arch, g in grp_size.groupby("arch")
        }
        for i, arch_a in enumerate(archs):
            for arch_b in archs[i + 1 :]:
                rows: list[dict] = []
                raw_p: list[float] = []
                for profile in PROFILES_ORDER:
                    if profile == "original":
                        continue
                    prof_a = by_arch.get(arch_a, {}).get(profile)
                    base_a = by_arch.get(arch_a, {}).get("original")
                    prof_b = by_arch.get(arch_b, {}).get(profile)
                    base_b = by_arch.get(arch_b, {}).get("original")
                    if prof_a is None or base_a is None or prof_b is None or base_b is None:
                        continue
                    diff, lo, hi, p = arch_overhead_diff_ci(prof_a, base_a, prof_b, base_b)
                    rows.append(
                        {
                            "size": size,
                            "profile": profile,
                            "arch_a": arch_a,
                            "arch_b": arch_b,
                            "overhead_diff_%_(b_minus_a)": None if np.isnan(diff) else round(diff, 2),
                            "diff_ci_lo": None if np.isnan(lo) else round(lo, 2),
                            "diff_ci_hi": None if np.isnan(hi) else round(hi, 2),
                            "p_value": None if np.isnan(p) else round(p, 6),
                            "n_forks_a": len(prof_a),
                            "n_forks_b": len(prof_b),
                        }
                    )
                    raw_p.append(p)
                adj = holm_bonferroni(raw_p)
                for row, padj in zip(rows, adj):
                    row["p_value_holm"] = None if np.isnan(padj) else round(float(padj), 6)
                    row["significant_arch_diff_5%"] = (not np.isnan(padj)) and padj < 0.05
                    out.append(row)
    return pd.DataFrame(out)


def plot(summary: pd.DataFrame) -> None:
    """Gera um grafico de barras (tempo medio + IC 95%) por (arch, size).

    Salva um PNG por grupo em OUT_DIR, com os perfis na ordem de PROFILES_ORDER.
    """
    for (arch, size), grp in summary.groupby(["arch", "size"]):
        grp = grp.set_index("profile").reindex(
            [p for p in PROFILES_ORDER if p in grp["profile"].values]
        )
        means = grp["mean"].to_numpy()
        yerr = np.vstack([means - grp["ci_lo"], grp["ci_hi"] - means])
        plt.figure(figsize=(7, 4))
        plt.bar(grp.index, means, yerr=yerr, capsize=5, color="#4C72B0")
        plt.ylabel(f"tempo medio ({grp['unit'].iloc[0]})")
        plt.title(f"Overhead de ofuscacao - {arch}, size={size}")
        plt.xticks(rotation=15)
        plt.tight_layout()
        fig = os.path.join(OUT_DIR, f"overhead-{arch}-size{size}.png")
        plt.savefig(fig, dpi=120)
        plt.close()
        print(f" grafico: {fig}")


def plot_overhead_vs_size(summary: pd.DataFrame) -> None:
    """Overhead (%) vs. size, uma linha por arquitetura, facetado por perfil.

    Complementa plot() (barras isoladas por arch+size): aqui o objetivo e
    visualizar como o overhead evolui com o tamanho do lote e comparar as
    arquiteturas lado a lado no mesmo eixo, para efeitos como diluicao do
    overhead relativo em lotes maiores. Cor fixa por arquitetura (paleta
    categorica, mesma identidade em todos os paineis) via ARCH_COLORS.
    """
    profiles = [p for p in PROFILES_ORDER if p != "original" and p in summary["profile"].values]
    archs = sorted(summary["arch"].unique())
    sizes = sorted(summary["size"].unique())
    if len(archs) < 2 or not profiles:
        return

    ncols = 2
    nrows = -(-len(profiles) // ncols)
    fig, axes = plt.subplots(nrows, ncols, figsize=(10, 3.5 * nrows), sharex=True, sharey=True)
    axes = np.atleast_1d(axes).flatten()

    for ax, profile in zip(axes, profiles):
        sub = summary[summary["profile"] == profile]
        for j, arch in enumerate(archs):
            g = sub[sub["arch"] == arch].set_index("size").reindex(sizes)
            color = ARCH_COLORS.get(arch, ARCH_FALLBACK_COLORS[j % len(ARCH_FALLBACK_COLORS)])
            xpos = range(len(sizes))
            y = g["overhead_%_vs_original"].to_numpy(dtype=float)
            lo = g["overhead_ci_lo"].to_numpy(dtype=float)
            hi = g["overhead_ci_hi"].to_numpy(dtype=float)
            ax.plot(xpos, y, marker="o", markersize=8, linewidth=2, color=color, label=arch)
            if not np.isnan(lo).all():
                ax.fill_between(xpos, lo, hi, color=color, alpha=0.15, linewidth=0)
        ax.axhline(0, color="#c3c2b7", linewidth=1, linestyle="--", zorder=0)
        ax.set_title(profile, color="#0b0b0b")
        ax.set_xticks(range(len(sizes)))
        ax.set_xticklabels([str(s) for s in sizes])
        ax.grid(axis="y", color="#e1e0d9", linewidth=0.8)
        ax.set_axisbelow(True)
        ax.tick_params(colors="#898781")
        for spine in ("top", "right"):
            ax.spines[spine].set_visible(False)

    for ax in axes[len(profiles):]:
        ax.set_visible(False)
    for ax in axes[max(0, len(profiles) - ncols) : len(profiles)]:
        ax.set_xlabel("size (nº de ordens)", color="#52514e")
    for ax in axes[::ncols]:
        ax.set_ylabel("overhead (%) vs. original", color="#52514e")

    handles, labels = axes[0].get_legend_handles_labels()
    fig.legend(handles, labels, loc="upper center", ncol=len(archs), frameon=False, bbox_to_anchor=(0.5, 1.04))
    fig.suptitle("Overhead da ofuscacao por tamanho de lote", y=1.1, color="#0b0b0b")
    fig.tight_layout()
    out = os.path.join(OUT_DIR, "overhead-vs-size.png")
    fig.savefig(out, dpi=120, bbox_inches="tight")
    plt.close(fig)
    print(f" grafico: {out}")


def main() -> None:
    """Orquestra a analise: le os JSON, gera summary.csv, imprime e plota."""
    os.makedirs(OUT_DIR, exist_ok=True)
    df = load()
    summary = analyze(df)
    summary = summary.sort_values(["arch", "size", "profile"])
    csv = os.path.join(OUT_DIR, "summary.csv")
    summary.to_csv(csv, index=False)
    print(f"Resumo salvo em {csv}\n")
    with pd.option_context("display.max_columns", None, "display.width", 160):
        print(summary.to_string(index=False))
    print()

    arch_interaction = analyze_arch_interaction(df)
    if not arch_interaction.empty:
        arch_interaction = arch_interaction.sort_values(["size", "profile", "arch_a", "arch_b"])
        arch_csv = os.path.join(OUT_DIR, "arch_interaction.csv")
        arch_interaction.to_csv(arch_csv, index=False)
        print(f"Comparacao entre arquiteturas salva em {arch_csv}\n")
        with pd.option_context("display.max_columns", None, "display.width", 160):
            print(arch_interaction.to_string(index=False))
        print()
    else:
        print("[aviso] menos de duas arquiteturas em results/: comparacao ARM vs. x86 pulada.\n")

    plot(summary)
    plot_overhead_vs_size(summary)
    min_forks = int(summary["n_forks"].min()) if not summary.empty else 0
    if min_forks < 5:
        print(
            f"\n[aviso] poucos forks (min={min_forks}): baixo poder estatistico. "
            "Aumente 'forks' no JMH para inferencia mais robusta."
        )
    if not HAVE_SCIPY:
        print("\n[aviso] scipy ausente: p-valor/efeito nao calculados. pip install scipy")


if __name__ == "__main__":
    main()