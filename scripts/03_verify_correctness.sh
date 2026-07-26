#!/usr/bin/env bash
#
# T4 - Validacao de correcao funcional.
#
# Executa o CorrectnessRunner (motor puro, sem JMH) contra o JAR original
# e contra cada JAR ofuscado, capturando o Summary DETERMINISTICO em
# arquivos "golden". Em seguida compara cada ofuscado com o original via diff.
#
# Criterio bloqueante: qualquer diferenca reprova o perfil.
#
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

RUNNER="com.finance.engine.CorrectnessRunner"
OUT="correctness"
mkdir -p "$OUT"

PROFILES=(baseline flow string flowstring)

echo "== Gerando golden do ORIGINAL =="
java -cp "target/app-original.jar" "$RUNNER" > "$OUT/golden-original.txt"

fail=0
for p in "${PROFILES[@]}"; do
  jar="target/app-$p.jar"
  if [[ ! -f "$jar" ]]; then
    echo "SKIP $p - $jar nao encontrado (rode T3/02_obfuscate.sh)"
    fail=1
    continue
  fi

  java -cp "$jar" "$RUNNER" > "$OUT/golden-$p.txt"
  if diff -u "$OUT/golden-original.txt" "$OUT/golden-$p.txt" > "$OUT/diff-$p.txt"; then
    echo "OK    $p - saida identica ao original"
    rm -f "$OUT/diff-$p.txt"
  else
    echo "FAIL $p - divergencia funcional (ver $OUT/diff-$p.txt)"
    fail=1
  fi
done

echo
if [[ $fail -eq 0 ]]; then
  echo "CORRECAO: todos os perfis equivalem ao original."
else
  echo "CORRECAO: houve falhas - perfis divergentes devem ser excluidos ou reconfigurados."
fi
exit $fail

# Rodar T4 -> bash scripts/03_verify_correctness.sh 