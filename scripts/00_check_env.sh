#!/usr/bin/env bash
#
# T0 - Verificacao de ambiente. Confere ferramentas e imprime versoes que
# devem ser fixadas em versions.lock para reprodutibilidade.
#
set -uo pipefail

echo "== Ambiente =="
echo "arch      : $(uname -m)"
echo "os        : $(uname -sro 2>/dev/null || uname -s)"

need() { command -v "$1" >/dev/null 2>&1 && echo "ok    $1 -> $($1 $2 2>&1 | head -n1)" || echo "FALTA $1"; }

need java "-version"
need mvn "-v"
need python3 "--version"

echo
echo "== Variaveis esperadas =="
echo "ALLATORI_JAR = ${ALLATORI_JAR:-<nao definido>}"
echo "CFR_JAR      = ${CFR_JAR:-<nao definido>}"

echo
echo "== Opcionais de medicao =="
for t in /usr/bin/time taskset perf vcgencmd cpufreq-set; do
  command -v "$t" >/dev/null 2>&1 && echo "ok    $t" || echo "--    $t (ausente)"
done

echo
echo "Dica (x86, requer sudo): sudo cpupower frequency-set -g performance"
echo "Dica (ARM/Pi): garanta cooler ativo; runs com throttling sao descartadas."