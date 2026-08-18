#!/usr/bin/env python3
"""
Checagem cruzada via ANOVA fatorial - metodo classico de avaliacao de
desempenho (cf. Jain, "The Art of Computer Systems Performance Analysis") -
como contraponto a abordagem bootstrap/Mann-Whitney de scripts/07_analyze.py.

Le os mesmos results/jmh-<arch>-<perfil>.json (agregando rawData por fork,
igual a 07_analyze.py: fork e a unidade de independencia) e roda duas
analises classicas:

1) Por (arch, size): ANOVA one-way sobre 'profile' (5 niveis) + post-hoc
   Tukey HSD, do qual reportamos apenas os pares contra 'original'. Reporta
   tambem Levene (homogeneidade de variancia) e Shapiro-Wilk dos residuos
   (normalidade) -- as duas premissas da ANOVA classica -- para sinalizar
   quando o resultado merece desconfianca (nesse caso, a alternativa classica
   e Welch-ANOVA + Games-Howell).

2) Por size: ANOVA two-way (arch x profile), reportando o termo de interacao
   C(arch):C(profile). Essa e a mesma pergunta que analyze_arch_interaction()
   em 07_analyze.py responde via bootstrap ("o overhead de um perfil difere
   entre arquiteturas?"), aqui respondida diretamente pelo modelo linear.

Este script e uma checagem, nao substitui 07_analyze.py: se as duas
abordagens concordarem qualitativamente (mesmos perfis/interacoes
significativos), reforca confianca no resultado da analise principal;
divergencias apontam sensibilidade a violacoes de normalidade/homocedasticidade.

Saidas: analysis/anova_summary.csv, analysis/anova_interaction.csv

Requisitos adicionais (alem dos de 07_analyze.py): statsmodels
  pip install statsmodels
"""

from __future__ import annotations

import glob
import json
import os
import re

import numpy as np
import pandas as pd
from scipy import stats
from statsmodels.formula.api import ols
from statsmodels.stats.anova import anova_lm
from statsmodels.stats.multicomp import pairwise_tukeyhsd

RESULTS_DIR = "results"
OUT_DIR = "analysis"
PROFILES_ORDER = ["original", "baseline", "flow", "string", "flowstring"]
FNAME_RE = re.compile(r"jmh-(?P<arch>[^-]+)-(?P<profile>[a-z]+)\.json$")
ALPHA = 0.05


def fork_means(primary: dict) -> list[float]:
    """Reduz o rawData de um benchmark a uma media por fork (ver 07_analyze.py)."""
    raw = primary.get("rawData")
    means: list[float] = []
    if raw:
        for fork in raw:
            vals = [float(x) for x in fork]
            if vals:
                means.append(float(np.mean(vals)))
    if not means:
        means = [float(primary["score"])]
    return means


def load_long() -> pd.DataFrame:
    """Carrega os JSON do JMH em formato long: uma linha por (arch, size, profile, fork).

    Returns:
        DataFrame com colunas: arch, size, profile, fork, value.

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
                for fork_idx, val in enumerate(fork_means(primary)):
                    rows.append(
                        {"arch": arch, "size": size, "profile": profile, "fork": fork_idx, "value": val}
                    )
            except (KeyError, TypeError, ValueError) as exc:
                print(f"[aviso] entrada invalida em '{path}': {exc}")
    if not rows:
        raise SystemExit(
            f"Nenhum arquivo jmh-*.json em '{RESULTS_DIR}/'. Rode T6 (04_bench.sh) primeiro."
        )
    return pd.DataFrame(rows)


def oneway_per_group(df: pd.DataFrame) -> pd.DataFrame:
    """ANOVA one-way (profile) por (arch, size) + Tukey HSD contra 'original'.

    Returns:
        DataFrame com uma linha por (arch, size, profile != original), com o
        F/p da ANOVA omnibus do grupo, os p-valores de Levene e Shapiro-Wilk
        (checagem de premissas) e a diferenca de medias + p-valor ajustado do
        Tukey HSD para esse perfil especifico vs. 'original'.
    """
    out = []
    for (arch, size), grp in df.groupby(["arch", "size"]):
        grp = grp[grp["profile"].isin(PROFILES_ORDER)]
        if grp["profile"].nunique() < 2:
            continue

        model = ols("value ~ C(profile)", data=grp).fit()
        aov = anova_lm(model, typ=2)
        f_val = float(aov.loc["C(profile)", "F"])
        p_omnibus = float(aov.loc["C(profile)", "PR(>F)"])

        group_vals = [g["value"].to_numpy() for _, g in grp.groupby("profile")]
        levene_p = float(stats.levene(*group_vals).pvalue)
        shapiro_p = float(stats.shapiro(model.resid).pvalue) if len(model.resid) >= 3 else float("nan")

        tukey = pairwise_tukeyhsd(grp["value"], grp["profile"], alpha=ALPHA)
        tab = tukey.summary()
        tukey_df = pd.DataFrame(tab.data[1:], columns=tab.data[0])

        for _, row in tukey_df.iterrows():
            g1, g2 = row["group1"], row["group2"]
            if "original" not in (g1, g2):
                continue
            profile = g2 if g1 == "original" else g1
            # meandiff da tabela e mean(group2) - mean(group1); normaliza p/ mean(profile) - mean(original)
            meandiff = float(row["meandiff"]) if g1 == "original" else -float(row["meandiff"])
            out.append(
                {
                    "arch": arch,
                    "size": size,
                    "profile": profile,
                    "anova_F": round(f_val, 3),
                    "anova_p": round(p_omnibus, 6),
                    "levene_p": round(levene_p, 4),
                    "shapiro_resid_p": None if np.isnan(shapiro_p) else round(shapiro_p, 4),
                    "tukey_meandiff": round(meandiff, 4),
                    "tukey_p_adj": round(float(row["p-adj"]), 6),
                    "tukey_reject_5%": bool(row["reject"]),
                }
            )
    return pd.DataFrame(out)


def twoway_interaction(df: pd.DataFrame) -> pd.DataFrame:
    """ANOVA two-way (arch x profile) por size: testa a interacao formalmente.

    O termo C(arch):C(profile) responde "o efeito do perfil de ofuscacao
    depende da arquitetura?" -- a mesma pergunta de analyze_arch_interaction()
    em 07_analyze.py, aqui via modelo linear em vez de bootstrap.

    Returns:
        DataFrame com uma linha por size (vazio se houver menos de duas
        arquiteturas nos dados).
    """
    out = []
    for size, grp in df.groupby("size"):
        grp = grp[grp["profile"].isin(PROFILES_ORDER)]
        if grp["arch"].nunique() < 2:
            continue
        model = ols("value ~ C(arch) * C(profile)", data=grp).fit()
        aov = anova_lm(model, typ=2)
        row = aov.loc["C(arch):C(profile)"]
        p_val = float(row["PR(>F)"])
        out.append(
            {
                "size": size,
                "anova_F_interaction": round(float(row["F"]), 3),
                "anova_p_interaction": round(p_val, 6),
                "significant_interaction_5%": p_val < ALPHA,
            }
        )
    return pd.DataFrame(out)


def main() -> None:
    """Orquestra a checagem: carrega os JSON, roda as duas ANOVAs e imprime/salva."""
    os.makedirs(OUT_DIR, exist_ok=True)
    df = load_long()

    summary = oneway_per_group(df)
    summary = summary.sort_values(["arch", "size", "profile"])
    csv = os.path.join(OUT_DIR, "anova_summary.csv")
    summary.to_csv(csv, index=False)
    print(f"Resumo ANOVA salvo em {csv}\n")
    with pd.option_context("display.max_columns", None, "display.width", 160):
        print(summary.to_string(index=False))

    bad_levene = summary.loc[summary["levene_p"] < ALPHA, ["arch", "size"]].drop_duplicates()
    bad_shapiro = summary.loc[
        summary["shapiro_resid_p"].notna() & (summary["shapiro_resid_p"] < ALPHA), ["arch", "size"]
    ].drop_duplicates()
    if not bad_levene.empty:
        print(
            f"\n[aviso] Levene rejeitou homogeneidade de variancia em {len(bad_levene)} grupo(s) "
            "(arch,size) -- considere Welch-ANOVA/Games-Howell nesses casos."
        )
    if not bad_shapiro.empty:
        print(
            f"[aviso] Shapiro-Wilk rejeitou normalidade dos residuos em {len(bad_shapiro)} grupo(s) "
            "(arch,size) -- ANOVA classica pode nao ser confiavel ali."
        )

    interaction = twoway_interaction(df)
    if not interaction.empty:
        interaction = interaction.sort_values("size")
        arch_csv = os.path.join(OUT_DIR, "anova_interaction.csv")
        interaction.to_csv(arch_csv, index=False)
        print(f"\nInteracao arch x profile (por size) salva em {arch_csv}\n")
        with pd.option_context("display.max_columns", None, "display.width", 160):
            print(interaction.to_string(index=False))
    else:
        print("\n[aviso] menos de duas arquiteturas em results/: interacao arch x profile pulada.")


if __name__ == "__main__":
    main()
