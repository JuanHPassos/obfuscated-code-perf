#!/usr/bin/env bash
#
# T3 - Ofuscacao (reproduzivel). Gera os 4 JARs ofuscados a partir do
# target/app-original.jar usando cada perfil do Allatori.
#
# T3a (pre-passo) - a edicao Educational do Allatori ignora silenciosamente
# "string-encryption" (recurso pago), entao a criptografia de strings de
# com.finance.engine.* e feita antes pelo ZKM (zkm/config-stringenc.zkm),
# so nos 6 .class do motor, gerando target/app-strenc.jar. Os perfis
# string/flowstring partem desse jar em vez de app-original.jar; baseline/flow
# continuam a partir de app-original.jar sem alteracao.
#
# Pre-requisitos:
#   - target/app-original.jar (mvn package)
#   - variavel ALLATORI_JAR apontando para o allatori.jar
#   - variavel ZKM_JAR apontando para o ZKM.jar (ex.: zkm/zkmEval/ZKM.jar)
#
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

: "${ALLATORI_JAR:?defina ALLATORI_JAR=/caminho/para/allatori.jar}"
: "${ZKM_JAR:?defina ZKM_JAR=/caminho/para/ZKM.jar}"

if [[ ! -f "target/app-original.jar" ]]; then
  echo "target/app-original.jar nao encontrado. Rode: mvn clean package" >&2
  exit 1
fi

echo "== T3a: string encryption do motor (ZKM) =="
rm -rf target/zkm-engine-in target/zkm-engine-out
mkdir -p target/zkm-engine-in
unzip -qo target/app-original.jar 'com/finance/engine/*' -d target/zkm-engine-in
java -jar "$ZKM_JAR" -l target/zkm-log.txt zkm/config-stringenc.zkm

cp target/app-original.jar target/app-strenc.jar
(cd target/zkm-engine-out && jar uf "$ROOT/target/app-strenc.jar" com/finance/engine/*.class)

PROFILES=(baseline flow string flowstring)
for p in "${PROFILES[@]}"; do
  echo "== Ofuscando perfil: $p =="
  # Caminhos no config sao resolvidos a partir da raiz do projeto (CWD).
  java -jar "$ALLATORI_JAR" "allatori/config-$p.xml"
done

echo "JARs gerados em target/;"
ls -l target/app-*.jar