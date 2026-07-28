#!/usr/bin/env bash
#
# T6 - Coleta de desempenho (JMH) para original + 4 perfis ofuscados.
#
# Uso:
#   scripts/04_bench.sh           # detecta arquitetura via uname -m
#   scripts/04_bench.sh x86_64
#   scripts/04_bench.sh aarch64   # ativa monitor termico da Raspberry Pi
#
# Saidas (results/):
#   jmh-<arch>-<perfil>.json   -> metricas JMH (consumidas pela analise T8)
#   time-<arch>-<perfil>.txt   -> RSS/startup via /usr/bin/time -v (se disponivel)
#   thermal-<arch>-<perfil>.log-> temp/clock/throttled (apenas ARM)
#
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

HOST_ARCH="${1:-$(uname -m)}"
RESULTS="results"
mkdir -p "$RESULTS"

# Parametros JMH (batem com as anotacoes do benchmark; explicitos p/ reprodutibilidade)
JMH_ARGS=(-f 10 -wi 10 -i 20 -foe true)

# Trava o governor de CPU em "performance" (evita ruido de DVFS durante a
# medicao) e restaura o valor original ao sair. Nao falha o script se
# cpupower/sysfs nao estiverem disponiveis, so avisa.
GOV_FILE="/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"
ORIG_GOVERNOR=""
if command -v cpupower >/dev/null 2>&1 && [[ -r "$GOV_FILE" ]]; then
  ORIG_GOVERNOR="$(cat "$GOV_FILE")"
  if sudo cpupower frequency-set -g performance >/dev/null 2>&1; then
    echo ">>> Governor de CPU travado em 'performance' (era '$ORIG_GOVERNOR')"
    trap '[[ -n "$ORIG_GOVERNOR" ]] && sudo cpupower frequency-set -g "$ORIG_GOVERNOR" >/dev/null 2>&1' EXIT
  else
    echo "!! Falha ao travar governor em 'performance' (sem sudo?) - resultados podem ter mais ruido de DVFS."
    ORIG_GOVERNOR=""
  fi
else
  echo "!! cpupower/scaling_governor indisponivel - governor de CPU nao foi travado."
fi

is_arm=0
case "$HOST_ARCH" in aarch64|arm*) is_arm=1 ;; esac

# Wrappers opcionais (nao falham se a ferramenta nao existir)
TIME_BIN="" ; [[ -x /usr/bin/time ]] && TIME_BIN="/usr/bin/time -v -o"
PIN=""      ; command -v taskset >/dev/null 2>&1 && PIN="taskset -c 0"

run_one() {
  local profile="$1" jar="$2"
  local tag="${HOST_ARCH}-${profile}"
  if [[ ! -f "$jar" ]]; then
    echo "SKIP $tag - $jar nao encontrado"; return 0
  fi
  echo ">>> Benchmark $tag"

  local thermal_pid=""
  if [[ $is_arm -eq 1 ]] && command -v vcgencmd >/dev/null 2>&1; then
    ( while true; do
        echo "$(date +%s) $(vcgencmd measure_temp) $(vcgencmd measure_clock arm) $(vcgencmd get_throttled)"
        sleep 1
      done ) > "$RESULTS/thermal-$tag.log" &
    thermal_pid=$!
  fi

  local time_prefix=()
  [[ -n "$TIME_BIN" ]] && time_prefix=($TIME_BIN "$RESULTS/time-$tag.txt")

  ${time_prefix[@]:-} $PIN \
    java -jar "$jar" "${JMH_ARGS[@]}" \
    -rf json -rff "$RESULTS/jmh-$tag.json" || true

  if [[ -n "$thermal_pid" ]]; then
    kill "$thermal_pid" 2>/dev/null || true
    # get_throttled != 0x0 indica throttling ocorrido/ativo -> run suspeita
    if grep -qiE 'throttled=0x0*[1-9a-f]' "$RESULTS/thermal-$tag.log"; then
      echo "!! THROTTLING detectado em $tag - invalide e re-execute esta run."
    fi
  fi
}

run_one original   target/app-original.jar
run_one baseline   target/app-baseline.jar
run_one flow       target/app-flow.jar
run_one string     target/app-string.jar
run_one flowstring target/app-flowstring.jar

echo "Resultados em $RESULTS/"