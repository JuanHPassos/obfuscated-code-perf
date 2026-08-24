#!/usr/bin/env python3
"""
T8c - Grafico enxuto (2 paineis) da apuracao de resiliencia por perfil.

Le resilience/evaluation_scores.csv (apuracao manual da rubrica docs/Check.md
sobre as respostas de LLM em resilience/responses/<modelo>/<perfil>/evaluation.md)
e plota, por perfil de ofuscacao:
  - painel esquerdo:  nota total (%)          -- recall geral da rubrica
  - painel direito:   nota da categoria E (%) -- alvo direto do string encryption

Os dois paineis contam historias diferentes: a nota total cai sobretudo por
falha do CFR em decompilar metodos inteiros sob 'flow'/'flowstring' (efeito do
control-flow obfuscation), enquanto a categoria E so cai quando 'string' esta
ativo -- por isso small multiples em vez de empilhar/misturar as duas metricas
num unico eixo (ver dataviz skill: duas escalas/medidas -> paineis, nunca
eixo duplo).

Cor por modelo (identidade), ordem fixa de paleta categorica (slots 3-4 do
tema padrao, ver dataviz skill / references/palette.md) -- os slots 1-2
(azul/laranja) ja tem outro significado no projeto (arquitetura, em
scripts/07_analyze.py), entao usar os proximos evita confundir as duas
comparacoes quando as figuras aparecem lado a lado no relatorio.

N=1 por celula (modelo x perfil): o protocolo (scripts/prompt-template.md)
pede N=5-10 repeticoes antes de tratar isso como conclusivo -- o grafico
marca isso explicitamente.

Saida: analysis/resilience_scores.png

Requisitos: pandas, matplotlib
"""

from __future__ import annotations

import os

import pandas as pd

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

IN_CSV = "resilience/evaluation_scores.csv"
OUT_DIR = "analysis"
PROFILES_ORDER = ["original", "baseline", "flow", "string", "flowstring"]

# Paleta categorica, slots 3-4 (ver references/palette.md) -- ordem fixa, nunca ciclada.
MODEL_COLORS = {
    "claudesonnet5": "#1baf7a",
    "geminipro3.1": "#eda100",
}
MODEL_LABELS = {
    "claudesonnet5": "Claude Sonnet 5",
    "geminipro3.1": "Gemini Pro 3.1",
}


def plot(df: pd.DataFrame) -> None:
    profiles = [p for p in PROFILES_ORDER if p in df["perfil"].unique()]
    models = [m for m in MODEL_COLORS if m in df["modelo"].unique()]

    fig, axes = plt.subplots(1, 2, figsize=(7.5, 3.1), sharey=True)
    panels = [
        ("pct_total", "Nota total (%)"),
        ("pct_E", "Categoria E — sigilo regulatorio (%)"),
    ]

    n = len(models)
    width = 0.8 / n
    xpos = range(len(profiles))

    for ax, (col, title) in zip(axes, panels):
        for i, model in enumerate(models):
            sub = df[df["modelo"] == model].set_index("perfil").reindex(profiles)
            offset = (i - (n - 1) / 2) * width
            bars = ax.bar(
                [x + offset for x in xpos],
                sub[col],
                width=width,
                color=MODEL_COLORS[model],
                label=MODEL_LABELS[model],
                zorder=3,
            )
            ax.bar_label(bars, fmt="%.0f", padding=2, fontsize=7, color="#52514e")

        ax.set_title(title, color="#0b0b0b", fontsize=10)
        ax.set_xticks(list(xpos))
        ax.set_xticklabels(profiles, rotation=15, fontsize=8.5)
        ax.set_ylim(0, 108)
        ax.set_yticks([0, 25, 50, 75, 100])
        ax.grid(axis="y", color="#e1e0d9", linewidth=0.8, zorder=0)
        ax.set_axisbelow(True)
        ax.tick_params(colors="#898781")
        for spine in ("top", "right"):
            ax.spines[spine].set_visible(False)

    axes[0].set_ylabel("% da rubrica (51 itens)", color="#52514e", fontsize=9)

    handles, labels = axes[0].get_legend_handles_labels()
    fig.legend(handles, labels, loc="upper center", ncol=n, frameon=False, bbox_to_anchor=(0.5, 1.06))
    fig.suptitle("Resiliencia a decompilacao por perfil de ofuscacao", y=1.18, fontsize=11, color="#0b0b0b")
    fig.text(
        0.5, -0.02,
        "N=1 por celula (modelo x perfil) — apuracao preliminar; protocolo pede N=5–10 repeticoes.",
        ha="center", va="top", fontsize=7.5, color="#898781", style="italic",
    )
    fig.tight_layout()

    os.makedirs(OUT_DIR, exist_ok=True)
    out = os.path.join(OUT_DIR, "resilience_scores.png")
    fig.savefig(out, dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f" grafico: {out}")


def main() -> None:
    df = pd.read_csv(IN_CSV)
    plot(df)


if __name__ == "__main__":
    main()
