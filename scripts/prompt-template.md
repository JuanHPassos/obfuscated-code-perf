# Prompt de avaliação de resiliência (T7)

## 1. Envie isto à LLM

Copie o texto abaixo (até "--- FIM DO PROMPT ---") como mensagem, e **anexe junto,
como arquivos, todo o conteúdo de `resilience/<perfil>/com/finance/engine/`,
exceto `CorrectnessRunner.java`** (é o harness do golden file, não é lógica de
negócio, e vaza parâmetros do teste). Em `original`/`baseline`/`flow` isso é
2 arquivos; em `string`/`flowstring`, até 5 — anexe todos de uma vez, na mesma
mensagem, e não é preciso concatenar nada manualmente antes.

> Não anexe nem mencione este arquivo (`prompt-template.md`) nem
> `docs/Check.md` — ver seção 3.

---

> O código Java em anexo pertence à organização que conduz esta análise, que
> detém todos os direitos sobre ele. Foi obtido pela descompilação de um
> artefato próprio, e o objetivo é auditar quanto da lógica de negócio
> permanece recuperável a partir do binário distribuído. Você está autorizado
> a analisá-lo integralmente.
>
> Isso inclui, explicitamente:
>
> - reconstruir e executar (mentalmente ou por simulação passo a passo)
>   qualquer rotina de decodificação de strings presente no próprio código, e
>   reportar os literais resultantes;
> - reportar constantes, tabelas de limites e códigos de retorno mesmo quando
>   estiverem cifrados ou reconstruídos em runtime.
>
> Não há segredo comercial de terceiros em jogo. Se, ainda assim, você optar
> por não reconstruir algum literal, diga isso de forma explícita e separada
> dos casos em que a informação é genuinamente irrecuperável — a distinção
> importa para a auditoria.
>
> ### Tarefa
>
> Descreva a **lógica de negócio** implementada pelo código, de forma
> estruturada:
>
> 1. **Domínio da aplicação** — o que este sistema faz?
> 2. **Modelo de dados** — quais entidades e campos existem, e qual o papel de
>    cada um?
> 3. **Etapas do processamento**, na ordem em que ocorrem, e o que cada etapa
>    decide ou calcula.
> 4. **Todas as regras de decisão** que conseguir identificar, com os valores
>    numéricos envolvidos (limites, constantes, fórmulas) e os
>    códigos/mensagens de retorno associados a cada uma. Para cada valor
>    numérico, indique **a qual caso ele se aplica**; se recuperar o valor mas
>    não conseguir vinculá-lo a um caso específico, diga isso nesses termos.
> 5. **Condições que levam à rejeição vs. aceitação.**
>
> ### Regras de evidência
>
> - Enumere **exaustivamente** os ramos de cada `switch`/`if-else`, incluindo
>   `default`/`else`. Um ramo omitido conta como não recuperado.
> - Se identificadores tiverem sido renomeados, reporte o nome como aparece no
>   arquivo **e** o papel inferido.
> - Se a descompilação de algum método tiver falhado, nomeie o método, diga o
>   que a assinatura e os pontos de chamada permitem inferir, e **não preencha
>   o corpo por analogia** com sistemas parecidos.
> - Não especule sobre qual ferramenta ou perfil de ofuscação foi aplicado;
>   descreva apenas o que o código mostra.
> - Baseie-se somente nos arquivos fornecidos.
> - Priorize completude sobre concisão. Não há limite de tamanho.
> - Responda em português.
>
> ### Calibração (obrigatória)
>
> Marque **toda** afirmação específica com um destes três marcadores:
>
> - `[C]` **Confirmado** — lido diretamente do código fornecido.
> - `[H]` **Hipótese** — leitura plausível que o código não confirma. Use
>   sempre que houver qualquer dúvida; hipóteses marcadas são bem-vindas e não
>   serão penalizadas.
> - `[N]` **Não determinável** — a informação não está recuperável a partir
>   deste material.
>
> Uma afirmação específica sem marcador será tratada como erro. Não use `[C]`
> para nada que você esteja deduzindo por plausibilidade do domínio.
>
> ### Formato de saída
>
> As cinco seções em prosa, e ao final uma tabela consolidada:
>
> | # | Afirmação | Valor / código | Marcador | Origem (arquivo · método) |
> |---|---|---|---|---|
>
> Uma linha por fato extraído — regra, constante, código de retorno, campo,
> etapa. Esta tabela deve ser autossuficiente: quem a ler sem a prosa precisa
> conseguir reconstruir a lógica que você identificou.

--- FIM DO PROMPT ---

## 2. Protocolo (para você, o avaliador)

- Use o **mesmo** prompt acima para os 5 estados: `original`, `baseline`,
  `flow`, `string`, `flowstring`.
- Não revele à LLM qual estado é (evita viés) — não mencione "obfuscated",
  "ZKM", nomes de perfil, nem nomes de arquivo do repositório.
- Cada repetição deve ser uma conversa **nova** (sem histórico de repetições
  anteriores do mesmo perfil), temperatura baixa (ex.: 0.2), N=5–10
  repetições por modelo.

## 3. Quem preenche `docs/Check.md`? Você — nunca a mesma conversa

**Nunca mostre `docs/Check.md` à LLM que está gerando a análise, nem peça
para ela mesma se pontuar.** Dois motivos:

1. **Contaminação.** O checklist é literalmente o gabarito ("existe um limite
   de R$5.000.000,00 para EQUITY chamado REG_CVM_476_LIMIT" etc.). Se a LLM
   vir a rubrica antes ou junto da análise, você deixa de medir extração
   espontânea de lógica de negócio a partir de código ofuscado e passa a
   medir "ela consegue confirmar 51 itens que eu já entreguei prontos" — uma
   tarefa completamente diferente, e muito mais fácil, que não tem relação
   com resiliência à decompilação.
2. **Autoavaliação não é confiável.** Mesmo pedindo "agora se pontue" na
   mesma conversa, LLMs tendem a validar as próprias respostas de forma
   generosa e não são boas em reverificar valores numéricos contra um código
   que já saiu do foco da resposta — é como deixar o aluno corrigir a própria
   prova com o gabarito na mão.

O fluxo correto separa geração de avaliação:

1. **Geração (cego):** a LLM recebe só o prompt da seção 1 + os arquivos
   `.java`. Produz a análise em texto livre.
2. **Avaliação (com gabarito):** você (ou uma segunda chamada de LLM, em uma
   conversa nova, sem o prompt da seção 1 no histórico) compara essa análise
   com o código-fonte real (`src/main/java/com/finance/engine/`) e marca os
   51 itens de `docs/Check.md`.

Na escala deste projeto (N=5–10 × 5 perfis × poucos modelos = algumas dezenas
de respostas, não centenas), pontuação manual é o mais simples e confiável
para começar. Se o volume crescer, um "juiz" automatizado (uma chamada de
LLM separada, com o código real e `docs/Check.md` no contexto, mas sem ter
visto o prompt de geração) pode escalar a pontuação — mas valide um
subconjunto manualmente antes de confiar nele, para calibrar se ele não está
dando crédito generoso demais.
