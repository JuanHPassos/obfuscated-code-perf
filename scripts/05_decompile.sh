#!/usr/bin/env bash
#
# T7 (parte 1) - Decompilacao com CFR do motor (com.finance.engine.*)
# nos 3 estados relevantes para a analise de resiliencia:
#   - original   (baseline de decompilacao, sem ofuscacao)
#   - cada perfil ofuscado
#
# O delta entre "original decompilado" e "ofuscado decompilado" e o efeito
# real da ofuscacao (o que se perde por decompilacao e normal e nao conta).
#
# Pre-requisito: CFR_JAR apontando para o cfr.jar (ex.: cfr/cfr-0.152.jar)
#
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

: "${CFR_JAR:?defina CFR_JAR=/caminho/para/cfr.jar (ex.: export CFR_JAR=$ROOT/cfr/cfr-0.152.jar)}"

OUT="resilience"
FILTER="com.finance.engine.*"

for p in original baseline flow string flowstring; do
  jar="target/app-$p.jar"
  [[ -f "$jar" ]] || { echo "SKIP $p - $jar nao encontrado"; continue; }
  dst="$OUT/$p"
  mkdir -p "$dst"
  echo "== CFR $p =="
  java -jar "$CFR_JAR" "$jar" \
    --outputdir "$dst" \
    --jarfilter "$FILTER" \
    > "$dst/_cfr.log" 2>&1 || echo "  (CFR retornou erro parcial - ver $dst/_cfr.log)"
done

echo
echo "Decompilacao concluida em $OUT/."
echo "Proximo passo (T7 parte 2, manual/semi-automatico):"
echo "  1) Envie cada estado decompilado a LLM usando scripts/prompt-template.txt"
echo "  2) Pontue com a rubrica em docs/Check.md"
echo "  3) Repita N=5..10 por modelo (Claude/Gemini) e agregue media +/- desvio"