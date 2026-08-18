#!/usr/bin/env bash
#
# T3 - Ofuscacao (reproduzivel). Gera os 4 JARs ofuscados a partir do
# target/app-original.jar. O ZKM e o unico obfuscador do projeto (renaming +
# control-flow + string encryption, tudo num so passe por perfil) - o
# Allatori saiu do pipeline: confirmamos empiricamente (hash MD5 identico,
# classe a classe) que a edicao Educational dele nao fazia control-flow nem
# string encryption (recursos pagos, ignorados em silencio), e o unico
# fator que sobrava - renaming - o ZKM tambem faz nativamente.
#
# com.finance.engine.* (motor) e com.finance.perf.* (OrderFactory,
# TransactionProcessingBenchmark, com/finance/perf/jmh_generated/*, que
# referenciam o motor pelo nome) sao abertos JUNTOS em cada passe, pra o ZKM
# atualizar corretamente as chamadas cruzadas ao renomear o motor -
# renaming precisa de consistencia entre classes, diferente de
# control-flow/string (transformacao interna a cada classe, nao precisa).
# com.finance.perf.* fica com nomes/flow/string excluidos em cada config
# .zkm (so identidade preservada, chamadas pro motor atualizadas). Tudo mais
# (org.openjdk.jmh.*, libs de terceiros) nunca e aberto (so "classpath"),
# bytecode 100% intocado ali.
#
# Pre-requisitos:
#   - target/app-original.jar (mvn package)
#   - variavel ZKM_JAR apontando para o ZKM.jar (ex.: zkm/zkmEval/ZKM.jar)
#
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

: "${ZKM_JAR:?defina ZKM_JAR=/caminho/para/ZKM.jar}"

if [[ ! -f "target/app-original.jar" ]]; then
  echo "target/app-original.jar nao encontrado. Rode: mvn clean package" >&2
  exit 1
fi

echo "== Extraindo classes de entrada (motor + perf) =="
rm -rf target/zkm-input
mkdir -p target/zkm-input
unzip -qo target/app-original.jar 'com/finance/engine/*' 'com/finance/perf/*' -d target/zkm-input

PROFILES=(baseline flow string flowstring)
for p in "${PROFILES[@]}"; do
  echo "== Ofuscando perfil: $p (ZKM: zkm/config-$p.zkm) =="
  outdir="target/zkm-$p-out"
  rm -rf "$outdir"
  java -jar "$ZKM_JAR" -l "target/zkm-log-$p.txt" "zkm/config-$p.zkm"
  cp target/app-original.jar "target/app-$p.jar"
  # Renaming muda os nomes dos .class (ex.: RegulatoryLimits.class -> a.class):
  # "jar uf" so ATUALIZA/ADICIONA entradas, nunca remove - sem apagar as
  # entradas originais primeiro, a copia pre-renaming ficaria morta mas
  # presente (e legivel, sem ofuscacao nenhuma) dentro do jar final. Apaga
  # tudo do escopo aberto antes de mesclar de volta so o que o ZKM gerou.
  zip -q -d "target/app-$p.jar" \
    'com/finance/engine/*.class' \
    'com/finance/perf/*.class' \
    'com/finance/perf/jmh_generated/*.class'
  (cd "$outdir" && jar uf "$ROOT/target/app-$p.jar" \
    com/finance/engine/*.class \
    com/finance/perf/*.class \
    com/finance/perf/jmh_generated/*.class)
done

echo "JARs gerados em target/;"
ls -l target/app-*.jar
