#!/usr/bin/env bash
#
# T3 - Ofuscacao (reproduzivel). Gera os 4 JARs ofuscados a partir do
# target/app-original.jar usando cada perfil do Allatori.
#
# Pre-requisitos:
#   - target/app-original.jar (mvn package)
#   - variavel ALLATORI_JAR apontando para o allatori.jar
#
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

: "${ALLATORI_JAR:?defina ALLATORI_JAR=/caminho/para/allatori.jar}"

if [[ ! -f "target/app-original.jar" ]]; then
  echo "target/app-original.jar nao encontrado. Rode: mvn clean package" >&2
  exit 1
fi

PROFILES=(baseline flow string flowstring)
for p in "${PROFILES[@]}"; do
  echo "== Ofuscando perfil: $p =="
  # Caminhos no config sao resolvidos a partir da raiz do projeto (CWD).
  java -jar "$ALLATORI_JAR" "allatori/config-$p.xml"
done

echo "JARs gerados em target/;"
ls -l target/app-*.jar