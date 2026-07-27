# Análise Experimental de Ofuscação (Allatori) — ARM vs. x86

Microbenchmark em Java 17 (JMH) para medir o **trade-off entre o overhead de desempenho** introduzido pela ofuscação (Allatori) e a **resiliência** da ofuscação contra recuperação da lógica de negócio (decompilação com CFR + avaliação por LLM), comparando as arquiteturas **x86_64** e **ARM (aarch64)**.

O código-alvo é um motor de processamento de transações financeiras (`com.finance.engine.TransactionEngine`) — a única classe efetivamente ofuscada. O harness JMH e a fábrica de ordens (`com.finance.perf.*`) são preservados para garantir medições justas.

---

## Estrutura do projeto

```text
pom.xml                    # build Maven (uber-jar JMH via shade) -> app-original.jar
run_pipeline.sh            # orquestra build -> ofuscar -> validar correcao
allatori/
  config-baseline.xml      # perfil: apenas renaming
  config-flow.xml          # perfil: renaming + control flow
  config-string.xml        # perfil: renaming + string encryption
  config-flowstring.xml    # perfil: renaming + control flow + string encryption
scripts/
  00_check_env.sh          # verifica ferramentas/versoes
  02_obfuscate.sh          # T3  roda o Allatori (gera os 4 JARs ofuscados)
  03_verify_correctness.sh # T4  golden files + diff vs. original
  04_bench.sh              # T6  coleta JMH (+ termia no ARM)
  05_decompile.sh          # T7  decompila o motor com CFR
  07_analyze.py            # T8  estatistica + graficos
  prompt-template.txt      # T7  prompt padrao para a LLM
src/main/java/com/finance/ # motor (engine) + harness (perf)
docs/                      # Rubrica de resiliencia (Check.md), etc.

```

Pastas de saída (`target/`, `correctness/`, `results/`, `resilience/`, `analysis/`) são criadas pelos próprios scripts - você não precisa criá-las.

---

## Pré-requisitos

| Ferramenta | Uso | Observação |
| --- | --- | --- |
| JDK 17 (ex.: Temurin) | build e execução | **mesma build** no x86 e no ARM |
| Maven | build (T2) | `mvn -v` |
| Allatori (`allatori.jar`) | ofuscação (T3) | da sua licença/distribuição |
| CFR (`cfr.jar`) | decompilação (T7) | opcional, só para resiliência |
| Python 3 + libs | análise (T8) | `pip install numpy scipy pandas matplotlib` |

Os scripts são **bash**. No Ubuntu/Raspberry Pi rodam nativamente; no Windows, use WSL ou Git Bash.

### Variáveis de ambiente

```bash
export ALLATORI_JAR=/caminho/para/allatori.jar  # necessário para T3
export CFR_JAR=/caminho/para/cfr.jar            # necessário para T7

```

---

## Execução rápida

Build + ofuscação + validação de correção (T2-T4) em um comando:

```bash
export ALLATORI_JAR=/caminho/para/allatori.jar
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
export ALLATORI_JAR=/caminho/para/allatori.jar
bash scripts/02_obfuscate.sh
# gera target/app-baseline.jar, app-flow.jar, app-string.jar, app-flowstring.jar

```

### T4 - Validação de correção funcional

```bash
bash scripts/03_verify_correctness.sh
# executa o CorrectnessRunner no original e nos 4 ofuscados,
# compara as saidas (golden files) e falha se houver divergencia

```

### T6 - Coleta de desempenho

**Antes de medir (obrigatório, nas duas arquiteturas - x86 e ARM):** fixe o clock da CPU no modo `performance` para reduzir variância:
```bash
sudo cpupower frequency-set -g performance

```
> Se o `cpupower` não estiver instalado: `sudo apt-get install -y linux-cpupower`.


Depois, colete:
```bash
bash scripts/04_bench.sh              # detecta a arquitetura via uname -m
# gera results/jmh-<arch>-<perfil>.json (+ time-*.txt; thermal-*.log no ARM)

```


### T7 - Resiliência (decompilação + LLM)

```bash
export CFR_JAR=/caminho/para/cfr.jar
bash scripts/05_decompile.sh
# decompila com.finance.engine.* de cada estado em resilience/<perfil>/

```

Depois, para cada estado, envie o código decompilado à LLM usando `scripts/prompt-template.txt` e pontue as respostas com a rubrica de 51 itens em [docs/Check.md](https://www.google.com/search?q=docs/Check.md). Repita N=5-10 vezes por modelo (Claude, Gemini), com temperatura baixa, e agregue média ± desvio. O `original` decompilado é a referência: o delta até o ofuscado é o efeito real da ofuscação.

### T8 - Análise estatística

```bash
pip install numpy scipy pandas matplotlib
python3 scripts/07_analyze.py
# gera analysis/summary.csv e analysis/overhead-<arch>-size<N>.png

```

Para cada perfil calcula média, IC 95%, overhead % vs. `original`, teste de significância (Welch-t ou Mann-Whitney, escolhido por Shapiro-Wilk) e tamanho de efeito (Cliff's delta).

---

## Rodando no Raspberry Pi (ARM) e mesclando os dados

Os JARs são bytecode portável — **não re-ofusque no Pi**; copie os mesmos artefatos do x86.

1. Transferir JARs e o script de bench para o Pi:
```bash
ssh pi@<IP_DO_PI> 'mkdir -p ~/ideia/target ~/ideia/scripts'
scp target/app-*.jar      pi@<IP_DO_PI>:~/ideia/target/
scp scripts/04_bench.sh   pi@<IP_DO_PI>:~/ideia/scripts/

```


2. No Pi (garanta cooling ativo e o mesmo JDK):
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
| `flow` | ✅ | ✅ (`enable` + `extensive=maximum`) | - |
| `string` | ✅ | - | ✅ (`maximum`) |
| `flowstring` | ✅ | ✅ | ✅ |

Renaming é o pano de fundo em todos os perfis. Control flow e string encryption são aplicados **apenas ao motor** (`com.finance.engine.*`) via `apply2class`, isolando o overhead medido.

---
