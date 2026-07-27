#!/usr/bin/env python3
"""
T8 - Analise estatistica do overhead de ofuscacao a partir dos JSON do JMH.

Le results/jmh-<arch>-<perfil>.json, agrega as amostras brutas (rawData) por
(arquitetura, tamanho, perfil), e calcula:
  - media, intervalo de confianca 95% (t de Student sobre as amostras);
  - overhead relativo (%) de cada perfil vs. 'original';
  - teste de significancia vs. 'original': Shapiro-Wilk decide entre
    t de Welch (normal) e Mann-Whitney U (nao-normal);
  - tamanho de efeito (Cliff's delta).

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
from itertools import product

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

RESULTS_DIR = "results"
OUT_DIR = "analysis"
PROFILES_ORDER = ["original", "baseline", "flow", "string", "flowstring"]
FNAME_RE = re.compile(r"jmh-(?P<arch>[^-]+)-(?P<profile>[a-z]+)\.json$")


def flatten_raw(primary: dict) -> list[float]:
    """Achata rawData (lista por fork de listas de iteracoes) em amostras."""
    raw = primary.get("rawData")
    samples: list[float] = []
    if raw:
        for fork in raw:
            samples.extend(float(x) for x in fork)
    if not samples:  # fallback: usa o score agregado
        samples = [float(primary["score"])]
    return samples


def load() -> pd.DataFrame:
    rows = []
    for path in sorted(glob.glob(os.path.join(RESULTS_DIR, "jmh-*.json"))):
        m = FNAME_RE.search(os.path.basename(path))
        if not m:
            continue
        arch, profile = m.group("arch"), m.group("profile")
        with open(path, encoding="utf-8") as fh:
            data = json.load(fh)
        for entry in data:
            size = int(entry.get("params", {}).get("size", -1))
            primary = entry["primaryMetric"]
            rows.append(
                {
                    "arch": arch,
                    "profile": profile,
                    "size": size,
                    "unit": primary.get("scoreUnit", "us/op"),
                    "samples": flatten_raw(primary),
                }
            )
            
    if not rows:
        raise SystemExit(
            f"Nenhum arquivo jmh-*.json em '{RESULTS_DIR}/'. Rode T6 (04_bench.sh) primeiro."
        )
    return pd.DataFrame(rows)


def ci95(samples: np.ndarray) -> tuple[float, float, float]:
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
    """Cliff's delta em [-1,1]; >0 significa 'a' tipicamente maior que 'b'."""
    gt = sum(1 for x, y in product(a, b) if x > y)
    lt = sum(1 for x, y in product(a, b) if x < y)
    return (gt - lt) / (len(a) * len(b))


def significance(orig: np.ndarray, other: np.ndarray) -> tuple[float, str, float]:
    if not HAVE_SCIPY or len(orig) < 3 or len(other) < 3:
        return float("nan"), "n/a", cliffs_delta(other, orig)
    normal = True
    for s in (orig, other):
        try:
            if stats.shapiro(s).pvalue < 0.05:
                normal = False
        except Exception:
            normal = False
    if normal:
        p = stats.ttest_ind(other, orig, equal_var=False).pvalue
        test = "welch-t"
    else:
        p = stats.mannwhitneyu(other, orig, alternative="two-sided").pvalue
        test = "mann-whitney"
    return float(p), test, cliffs_delta(other, orig)


def analyze(df: pd.DataFrame) -> pd.DataFrame:
    out = []
    for (arch, size), grp in df.groupby(["arch", "size"]):
        by_profile = {r.profile: np.asarray(r.samples, float) for r in grp.itertuples()}
        base = by_profile.get("original")
        for profile in PROFILES_ORDER:
            if profile not in by_profile:
                continue
            s = by_profile[profile]
            mean, lo, hi = ci95(s)
            unit = grp.iloc[0]["unit"]
            overhead = float("nan")
            p, test, delta = float("nan"), "", float("nan")
            if base is not None and len(base):
                overhead = (mean / float(np.mean(base)) - 1.0) * 100.0
                if profile != "original":
                    p, test, delta = significance(base, s)
            out.append(
                {
                    "arch": arch,
                    "size": size,
                    "profile": profile,
                    "mean": round(mean, 4),
                    "ci_lo": round(lo, 4),
                    "ci_hi": round(hi, 4),
                    "unit": unit,
                    "overhead_%_vs_original": round(overhead, 2),
                    "test": test,
                    "p_value": None if np.isnan(p) else round(p, 6),
                    "significant_5%": (not np.isnan(p)) and p < 0.05,
                    "cliffs_delta": None if np.isnan(delta) else round(delta, 3),
                    "n_samples": len(s),
                }
            )
    return pd.DataFrame(out)


def plot(summary: pd.DataFrame) -> None:
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
        print(f"  grafico: {fig}")


def main() -> None:
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
    if not HAVE_SCIPY:
        print("\n[aviso] scipy ausente: p-valor/efeito nao calculados. pip install scipy")


if __name__ == "__main__":
    main()