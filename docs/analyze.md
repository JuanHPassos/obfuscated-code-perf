# T8 — Análise estatística de desempenho (`scripts/07_analyze.py`)

Documenta o script que transforma os resultados brutos do JMH (`results/jmh-*.json`) nas
saídas estatísticas do projeto (`analysis/summary.csv`, `analysis/arch_interaction.csv` e os
gráficos). Ver também o diagrama de fluxo em [`analyze_flow.excalidraw`](analyze_flow.excalidraw).

## Fluxo geral

```
load() ──┬─→ analyze()                 ──→ analysis/summary.csv          ──┬─→ plot()
         │                                                                  └─→ plot_overhead_vs_size()
         └─→ analyze_arch_interaction() ──→ analysis/arch_interaction.csv
```

`load()` é o único ponto de entrada de dados; a partir dela o script segue duas trilhas
estatisticamente independentes — perfil-vs-original (dentro de cada arquitetura) e
arquitetura-vs-arquitetura (para cada perfil) — que respondem perguntas diferentes e por
isso têm suas próprias correções de múltiplas comparações (ver abaixo).

## 1. Ingestão: `load()` e `fork_means()`

`load()` varre `results/jmh-<arch>-<perfil>.json`, extrai `arch` e `perfil` do nome do
arquivo e monta um DataFrame longo com uma linha por `(arch, perfil, size)`.

A decisão metodológica central está em `fork_means()`: o JMH grava `rawData` como uma
matriz **fork × iteração** (no setup atual, 10 forks × 20 iterações). A função reduz cada
fork à sua própria média, produzindo **uma amostra por fork** — não uma por iteração.

- **Por quê**: iterações dentro do mesmo fork rodam na mesma JVM, com o mesmo estado de
  JIT/aquecimento — são autocorrelacionadas. Tratá-las como observações independentes
  infla artificialmente o tamanho da amostra (pseudo-replicação) e deixa os testes de
  significância otimistas demais. O fork, que reinicia a JVM do zero a cada rodada, é a
  unidade real de independência estatística.
- Sem `rawData` no JSON, a função cai para o `score` agregado do próprio JMH como amostra
  única (fallback).

## 2. `analyze()` — perfil vs. `original`, dentro de cada `(arch, size)`

Para cada grupo `(arch, size)`, compara cada perfil de ofuscação (`baseline`, `flow`,
`string`, `flowstring`) contra `original`:

| Função | O que faz |
|---|---|
| `ci95()` | Média e IC 95% via t de Student, sobre as médias-por-fork (não sobre iterações cruas). |
| `bootstrap_overhead_ci()` | IC 95% do overhead percentual `(mean(perfil)/mean(original) - 1) * 100`, via bootstrap (10.000 reamostragens com reposição da razão de médias) — não assume normalidade nem propaga erro analiticamente. |
| `significance()` | Testa se o perfil difere do original. Shapiro-Wilk decide o teste: se ambas as amostras passam no teste de normalidade, usa Welch's t; caso contrário, Mann-Whitney U (não-paramétrico). Com menos de 3 forks, usa Mann-Whitney direto ou desiste (`n/a`) se nem isso for viável. |
| `cliffs_delta()` | Tamanho de efeito não-paramétrico em `[-1, 1]`, baseado só em ordem (compatível com Mann-Whitney). |
| `holm_bonferroni()` | Corrige os p-valores dos 4 perfis testados dentro do mesmo `(arch, size)` — essa é a família de comparações. `significant_5%` usa o p-valor já ajustado, nunca o bruto. |

### Colunas de `analysis/summary.csv`

| Coluna | Significado |
|---|---|
| `arch`, `size`, `profile` | Chave da linha. |
| `mean`, `ci_lo`, `ci_hi` | Tempo médio por fork e IC 95% (t de Student). |
| `unit` | Unidade do JMH (`us/op`). |
| `overhead_%_vs_original` | Overhead percentual da média vs. `original`. |
| `overhead_ci_lo/hi` | IC 95% do overhead, via bootstrap da razão. |
| `test` | Teste aplicado (`welch-t` \| `mann-whitney` \| `n/a`). |
| `p_value` | P-valor bruto. |
| `p_value_holm` | P-valor ajustado por Holm-Bonferroni, dentro da família `(arch, size)`. |
| `significant_5%` | `True` se `p_value_holm < 0.05`. |
| `cliffs_delta` | Tamanho de efeito não-paramétrico. |
| `n_forks` | Número de forks (amostras) usados na inferência. |

## 3. `analyze_arch_interaction()` — arquitetura vs. arquitetura

`analyze()` sozinho só permite afirmações do tipo "significativo em x86_64, não em
aarch64" — o que **não** prova que o overhead difere entre as arquiteturas (comparar duas
significâncias independentes não é o mesmo que testar a diferença entre elas; Gelman &
Stern, 2006). Esta função fecha essa lacuna.

Para cada `size` e cada par de arquiteturas presentes nos dados, e para cada perfil
(exceto `original`):

- **`arch_overhead_diff_ci()`**: bootstrapa os 4 grupos envolvidos (perfil e original de
  cada arquitetura) independentemente, calcula a distribuição de
  `overhead_%(arch_b) - overhead_%(arch_a)` e devolve IC 95% e p-valor (2× a proporção de
  reamostras que cruzam zero).
- **`holm_bonferroni()`**: corrige os p-valores dos 4 perfis dentro da família
  `(size, arch_a, arch_b)` — mesmo padrão de `analyze()`, aplicado aqui à comparação entre
  arquiteturas em vez de perfil-vs-original.

### Colunas de `analysis/arch_interaction.csv`

| Coluna | Significado |
|---|---|
| `size`, `profile` | Chave da linha (perfil sempre `!= original`). |
| `arch_a`, `arch_b` | Par de arquiteturas comparado (`arch_a < arch_b` alfabeticamente). |
| `overhead_diff_%_(b_minus_a)` | `overhead_%(arch_b) - overhead_%(arch_a)`. |
| `diff_ci_lo/hi` | IC 95% dessa diferença, via bootstrap. |
| `p_value` / `p_value_holm` | P-valor bootstrap bruto e ajustado (Holm-Bonferroni, família `(size, arch_a, arch_b)`). |
| `significant_arch_diff_5%` | `True` se `p_value_holm < 0.05`: o overhead desse perfil difere entre as duas arquiteturas. |
| `n_forks_a`, `n_forks_b` | Forks usados de cada lado. |

## 4. Visualização: `plot()` e `plot_overhead_vs_size()`

- **`plot()`**: um PNG por `(arch, size)` — barras de tempo médio + IC 95%, uma barra por
  perfil. Saída: `analysis/overhead-<arch>-size<N>.png` (6 arquivos no setup atual).
- **`plot_overhead_vs_size()`**: complementar — um único PNG (`analysis/overhead-vs-size.png`)
  com overhead (%) no eixo Y, `size` no eixo X, uma linha por arquitetura (cor fixa via
  `ARCH_COLORS`, faixa sombreada = IC 95%) facetado em 4 painéis, um por perfil. Pensado
  para revelar tendências — como o efeito de diluição do overhead em lotes maiores — que
  os gráficos de barra isolados por `(arch, size)` não mostram por estarem cada um em seu
  próprio eixo.

## 5. Orquestração: `main()`

Roda `load() → analyze() → analyze_arch_interaction() → plot() → plot_overhead_vs_size()`
em sequência, salva os dois CSVs, imprime as tabelas no console e faz duas checagens de
sanidade ao final: avisa se `n_forks < 5` (poder estatístico baixo) e se `scipy` está
ausente (nesse caso `p_value`/`cliffs_delta` ficam `NaN`, mas o script não quebra).

## Como rodar

```bash
pip install numpy scipy pandas matplotlib
python3 scripts/07_analyze.py
```

Requer pelo menos um `results/jmh-*.json` (gerado por `scripts/04_bench.sh`, T6). Sem
nenhum arquivo válido, o script aborta com `SystemExit` orientando a rodar o T6 primeiro.

## Princípio geral

Em nenhum ponto o script assume normalidade ou homocedasticidade por padrão: todo
intervalo de confiança de razão/diferença é via bootstrap, e a escolha de teste
paramétrico vs. não-paramétrico é sempre decidida por Shapiro-Wilk, nunca fixada a priori.
Toda vez que múltiplas comparações são feitas contra o mesmo controle dentro de um mesmo
contexto, os p-valores dessa família são corrigidos por Holm-Bonferroni antes de qualquer
"significativo" ser reportado.
