# Análise Experimental de Ofuscação (ZKM) — ARM vs. x86

Microbenchmark em Java 17 (JMH) para medir o **trade-off entre o overhead de desempenho** introduzido pela ofuscação (Zelix KlassMaster) e a **resiliência** da ofuscação contra recuperação da lógica de negócio (decompilação com CFR + avaliação por LLM), comparando as arquiteturas **x86_64** e **ARM (aarch64)**.

O código-alvo é um motor de processamento de transações financeiras (`com.finance.engine.TransactionEngine`) — a única classe efetivamente ofuscada. O harness JMH e a fábrica de ordens (`com.finance.perf.*`) têm sua identidade (nomes de classe/campo/método) preservada para garantir medições justas, mesmo sendo reprocessados pelo ZKM (ver seção "Perfis de ofuscação").

> Este projeto usou o Allatori originalmente, mas a edição Educational dele ignora silenciosamente os dois recursos pagos que o estudo precisa (control-flow obfuscation e string encryption) — confirmado empiricamente por hash MD5 idêntico, classe a classe, entre jars com esses fatores "ligados" ou não no config do Allatori. O Allatori também fazia o renaming, mas isso o ZKM já faz nativamente — então o Allatori saiu do pipeline por completo, e o ZKM passou a ser o único obfuscador sob estudo.

---

## Estrutura do projeto

```text
pom.xml                    # build Maven (uber-jar JMH via shade) -> app-original.jar
run_pipeline.sh            # orquestra build -> ofuscar -> validar correcao
zkm/
  config-baseline.zkm      # perfil: apenas renaming
  config-flow.zkm          # perfil: renaming + control-flow (obfuscateFlow=aggressive)
  config-string.zkm        # perfil: renaming + string encryption (encryptStringLiterals=enhanced)
  config-flowstring.zkm    # perfil: renaming + control-flow + string encryption
  zkmEval/, zkmDocs/       # distribuicao ZKM baixada (nao versionada)
scripts/
  00_check_env.sh          # verifica ferramentas/versoes
  02_obfuscate.sh          # T3  roda o ZKM (gera os 4 JARs ofuscados)
  03_verify_correctness.sh # T4  golden files + diff vs. original
  04_bench.sh              # T6  coleta JMH (+ termia no ARM)
  05_decompile.sh          # T7  decompila o motor com CFR
  07_analyze.py            # T8  estatistica + graficos (summary.csv, arch_interaction.csv)
  prompt-template.md       # T7  prompt padrao para a LLM (+ protocolo de avaliacao)
src/main/java/com/finance/ # motor (engine) + harness (perf)
docs/                      # Rubrica de resiliencia (Check.md), etc.
analysis-data-preparation.ipynb   # limpeza/tipagem de summary.csv e arch_interaction.csv
                                   # do T8 -> analysis/*_prepared.csv
analysis-exploratoria-dados.ipynb # EDA sobre os *_prepared.csv (histogramas, correlacao,
                                   # boxplots por arquitetura, etc.)

```

Pastas de saída (`target/`, `correctness/`, `results/`, `resilience/`, `analysis/`) são criadas pelos próprios scripts - você não precisa criá-las.

---

## Pré-requisitos

| Ferramenta | Uso | Observação |
| --- | --- | --- |
| JDK 17 (ex.: Temurin) | build e execução | **mesma build** no x86 e no ARM |
| Maven | build (T2) | `mvn -v` |
| ZKM (`ZKM.jar`) | renaming + control-flow + string encryption (T3) | avaliação Zelix KlassMaster, guarde em `zkm/zkmEval/` |
| CFR (`cfr.jar`) | decompilação (T7) | opcional, só para resiliência; guarde em `cfr/` |
| Python 3 + libs | análise (T8) | `pip install numpy scipy pandas matplotlib` |

Os scripts são **bash**. No Ubuntu/Raspberry Pi rodam nativamente; no Windows, use WSL ou Git Bash.

### Variáveis de ambiente

```bash
export ZKM_JAR=zkm/zkmEval/ZKM.jar              # necessário para T3
export CFR_JAR=cfr/cfr-0.152.jar                # necessário para T7

```

---

## Execução rápida

Build + ofuscação + validação de correção (T2-T4) em um comando:

```bash
export ZKM_JAR=/caminho/para/ZKM.jar
bash run_pipeline.sh

```

Para incluir a coleta de desempenho no host atual (T6):

```bash
bash run_pipeline.sh --bench

```

> Sempre invoque com `bash <script>` (não depende de `chmod +x`).

---

## Execução passo a passo

### 0. Conferir o ambiente

```bash
bash scripts/00_check_env.sh

```

### T2 - Build do JAR original

```bash
mvn -q clean package
# gera target/app-original.jar

```

### T3 - Ofuscação (4 perfis)

```bash
export ZKM_JAR=/caminho/para/ZKM.jar
bash scripts/02_obfuscate.sh
# gera target/app-baseline.jar, app-flow.jar, app-string.jar, app-flowstring.jar

```

Extrai `com/finance/engine/*` e `com/finance/perf/**` (motor + harness JMH, que
referencia o motor pelo nome) do jar original, roda o ZKM uma vez por perfil
(um `.zkm` cada, ver "Perfis de ofuscação" abaixo) e mescla o resultado de
volta numa cópia de `app-original.jar`. `com.finance.perf.*` é aberto junto
com o motor só para o ZKM atualizar corretamente as chamadas cruzadas ao
renomear o motor — sua própria identidade (nomes/anotações JMH) fica
excluída de renaming/control-flow/string, então continua invocável pelo JMH
normalmente.

### T4 - Validação de correção funcional

```bash
bash scripts/03_verify_correctness.sh
# executa o CorrectnessRunner no original e nos 4 ofuscados,
# compara as saidas (golden files) e falha se houver divergencia

```

### T6 - Coleta de desempenho

```bash
bash scripts/04_bench.sh              # detecta a arquitetura via uname -m
# gera results/jmh-<arch>-<perfil>.json (+ time-*.txt; thermal-*.log no ARM)

```

O script já fixa o clock da CPU no modo `performance` (via `sudo cpupower frequency-set -g performance`) antes de medir, nas duas arquiteturas, e restaura o governor original ao final — não é mais um passo manual. Se `cpupower` não estiver instalado ou o `sudo` falhar (ex.: sem senha configurada em modo não-interativo), o script avisa e segue sem travar o clock — resultados coletados assim tendem a ter mais ruído por variação de frequência (DVFS).
> Se o `cpupower` não estiver instalado: `sudo apt-get install -y linux-cpupower`.

Cada perfil roda com `-f 10 -wi 10 -i 20` (10 forks, 10 iterações de warmup, 20 de medição, 1s cada) — o fork é a unidade de réplica independente usada depois no T8.


### T7 - Resiliência (decompilação + LLM)

```bash
export CFR_JAR=cfr/cfr-0.152.jar
bash scripts/05_decompile.sh
# decompila com.finance.engine.* de cada estado em resilience/<perfil>/

```

Depois, para cada estado, anexe os arquivos `.java` decompilados (exceto `CorrectnessRunner.java`) à LLM junto com o prompt de `scripts/prompt-template.md`, e pontue as respostas — **você**, não a LLM que gerou a análise — com a rubrica de 51 itens em [docs/Check.md](https://www.google.com/search?q=docs/Check.md) (ver seção 3 do prompt-template para o motivo). Repita N=5-10 vezes por modelo (Claude, Gemini), com temperatura baixa, e agregue média ± desvio. O `original` decompilado é a referência: o delta até o ofuscado é o efeito real da ofuscação.

### T8 - Análise estatística

```bash
pip install numpy scipy pandas matplotlib
python3 scripts/07_analyze.py
# gera analysis/summary.csv, analysis/arch_interaction.csv
# e analysis/overhead-<arch>-size<N>.png

```

Para cada perfil calcula média, IC 95%, overhead % vs. `original`, teste de significância (Welch-t ou Mann-Whitney, escolhido por Shapiro-Wilk) e tamanho de efeito (Cliff's delta), com correção Holm-Bonferroni por família (arch, size).

Além disso, `arch_interaction.csv` testa se o overhead de cada perfil **difere entre ARM e x86_64** (bootstrap da diferença de overheads, com correção Holm-Bonferroni por (size, par de arquiteturas)). Isso é necessário porque "significativo em x86, não-significativo em ARM" na tabela `summary.csv` não implica, por si só, que a diferença entre as arquiteturas seja estatisticamente significativa.

#### EDA em notebook

Os notebooks `analysis-data-preparation.ipynb` e `analysis-exploratoria-dados.ipynb` (raiz do repo) rodam depois: o primeiro limpa/tipa `summary.csv`/`arch_interaction.csv` em `analysis/*_prepared.csv`, o segundo faz a EDA completa (histogramas, correlação de Pearson/Spearman, boxplot por arquitetura etc.) em cima desses `_prepared.csv`. Ambos assumem T8 já rodado — se você re-obfuscar/re-benchmarkar qualquer perfil, rode T8 (e T8b) de novo antes deles.

---

## Rodando no Raspberry Pi (ARM) e mesclando os dados

Os JARs são bytecode portável — **não re-ofusque no Pi**; copie os mesmos artefatos do x86.

1. Transferir JARs e o script de bench para o Pi:
```bash
ssh pi@<IP_DO_PI> 'mkdir -p ~/ideia/target ~/ideia/scripts'
scp target/app-*.jar      pi@<IP_DO_PI>:~/ideia/target/
scp scripts/04_bench.sh   pi@<IP_DO_PI>:~/ideia/scripts/

```


2. No Pi (garanta cooling ativo, o mesmo JDK e acesso a `sudo` para o `cpupower`):
```bash
cd ~/ideia
sed -i 's/\r$//' scripts/04_bench.sh  # normaliza terminacoes de linha se necessario
bash scripts/04_bench.sh              # detecta aarch64; avisa se houver throttling

```


3. Trazer os resultados de volta para a **mesma pasta** `results/` do x86:
```bash
scp pi@<IP_DO_PI>:~/ideia/results/jmh-aarch64-*.json ./results/

```
> Ou dá para usar um pendrive :)


4. Rodar a análise combinada:
```bash
python3 scripts/07_analyze.py

```


O `summary.csv` traz a coluna `arch`, então x86 e ARM aparecem lado a lado.
**Compare o overhead relativo (%)** entre arquiteturas, não os tempos absolutos (CPUs diferentes não são comparáveis diretamente).

---

## Perfis de ofuscação

| Perfil | Renaming | Control flow | String encryption |
| --- | --- | --- | --- |
| `baseline` | ✅ | - | - |
| `flow` | ✅ | ✅ (`obfuscateFlow=aggressive`) | - |
| `string` | ✅ | - | ✅ (`enhanced`) |
| `flowstring` | ✅ | ✅ | ✅ |

Renaming é o pano de fundo em todos os perfis — o mesmo passe do ZKM que faz
control-flow/string encryption já renomeia classes/campos/métodos do motor.
`com.finance.perf.*` (harness JMH, fábrica de ordens) e `org.openjdk.jmh.*`/libs
de terceiros nunca são afetados pelos três fatores: os dois primeiros ficam
com nome/control-flow/string explicitamente excluídos em cada `zkm/config-*.zkm`
(só a chamada para o motor renomeado é atualizada); o resto nem é aberto pelo
ZKM (só `classpath`), então o bytecode fica 100% intocado. Isso isola o
overhead medido: só o motor é afetado pelos três fatores.

---
