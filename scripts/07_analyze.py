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

Saidas:
  analysis/summary.csv
  analysis/overhead-<arch>-size<N>.png  (barras com error bars)

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
    plot(summary)
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