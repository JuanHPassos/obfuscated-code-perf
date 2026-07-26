#!/usr/bin/env bash
#
# T5 - Pipeline reproduzivel de ponta a ponta (build -> ofuscar -> corrigir).
# A coleta de desempenho e a resiliencia ficam de fora por padrao
# porque dependem do host (x86/ARM) e de ferramentas externas (CFR/LLM).
#
# Uso:
#   ALLATORI_JAR=/caminho/allatori.jar ./run_pipeline.sh
#   ALLATORI_JAR=... ./run_pipeline.sh --bench    # inclui T6 no host atual
#
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"
cd "$ROOT"

echo "== T2: build (mvn clean package) =="
mvn -q clean package

echo "== T3: ofuscacao =="
bash scripts/02_obfuscate.sh

echo "== T4: validacao de correcao =="
bash scripts/03_verify_correctness.sh

if [[ "${1:-}" == "--bench" ]]; then
  echo "== T6: desempenho ($(uname -m)) =="
  bash scripts/04_bench.sh "$(uname -m)"
fi

echo "Pipeline concluido."