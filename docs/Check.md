# Check.md — Rubrica de Avaliação de Resiliência à Decompilação

Rubrica usada no estágio T7 (`resilience/`) para pontuar quanto de lógica de
negócio uma LLM consegue reconstruir a partir do código **decompilado**
(`.java` gerado pelo CFR a partir de cada jar: `original`, `baseline`,
`flow`, `string`, `flowstring`). É a "régua" citada em
`scripts/prompt-template.md`.

**Quem preenche esta rubrica é você (ou uma segunda chamada de LLM, em uma
conversa separada que nunca viu o prompt de geração) — nunca a mesma
conversa que produziu a análise.** Mostrar este checklist à LLM antes ou
junto do código transforma o teste de extração espontânea de lógica em um
teste de confirmação de itens já dados, invalidando a medida de resiliência.
Ver seção 3 de `scripts/prompt-template.md` para o detalhamento.

A fonte da verdade é `TransactionEngine.java` + `RegulatoryLimits.java`
(único código ofuscado). Todo item abaixo foi extraído diretamente desses
dois arquivos — nada foi inventado.

## Protocolo de aplicação

1. Envie **o mesmo prompt** (`scripts/prompt-template.md`) para cada
   variante decompilada, sem dizer à LLM qual perfil de ofuscação está sendo
   testado (evita viés do avaliador/modelo).
2. Temperatura baixa (~0.2), N=5–10 repetições por modelo/perfil.
3. Para cada resposta, marque cada um dos 51 itens abaixo com:
   - **1** — afirmação correta e específica (valor numérico e/ou código de
     retorno batem com o código-fonte).
   - **0,5** — direção certa mas vaga, incompleta ou com valor levemente
     errado (ex.: "há um limite para EQUITY" sem o número).
   - **0** — item ausente, ou a LLM escreveu explicitamente que não
     conseguiu determinar aquilo a partir do código.
4. **Alucinação**: se a LLM afirmar algo específico e **errado** (não apenas
   vago) — um valor, nome de campo ou código que não existe no código-fonte
   — marque 0 no item correspondente (se houver) **e** registre à parte na
   coluna "Alucinações" da planilha de apuração. Isso vira uma métrica de
   precisão separada da métrica de recall (itens corretos).
5. Some os pontos por categoria e o total. Escala percentual:
   `nota% = (pontos_obtidos / 51) * 100`.
6. Calcule também a **Nota de Sigilo Regulatório** isolada — só a categoria
   E (7 itens, o alvo direto do String Encryption): `pontosE / 7`. Essa é a
   métrica mais diretamente ligada à eficácia do String Encryption; as
   demais categorias (pipeline/validação/risco/taxa/saldo/roteamento) são
   majoritariamente o alvo do Control Flow Obfuscation.

## Planilha de apuração (esqueleto)

Para cada (modelo, perfil, repetição) preencha uma linha:

| modelo | perfil | rep | pontos_totais | pontos_E (sigilo) | alucinações | obs |
|---|---|---|---|---|---|---|

---

## A. Domínio e modelo de dados (6 itens)

1. Identifica o domínio: motor de processamento/roteamento de ordens de
   compra e venda de ativos financeiros (trading engine).
2. `Order` tem um identificador de transação (`txnId`).
3. `Order` tem uma conta (`accountKey`), reconhecidamente com um prefixo
   fixo (`"ACCT-"`).
4. `Order` tem lado da operação (`side`) com dois valores possíveis:
   compra/venda (BUY/SELL).
5. `Order` tem quantidade e preço (`quantity`, `priceCents`) usados para
   calcular um valor financeiro (notional = quantidade × preço).
6. `Order` tem um segmento de mercado (`segment`) com pelo menos os 4
   valores: EQUITY, FX, CRYPTO, FIXED_INCOME.

## B. Estrutura do pipeline (3 itens)

7. Identifica que a ordem é processada em etapas sequenciais com
   curto-circuito: a primeira etapa que rejeita interrompe o processamento.
8. Identifica corretamente a sequência das 5 etapas: validação → risco →
   cálculo de taxa → checagem/reserva de saldo → roteamento.
9. Identifica os dois desfechos possíveis: aceita (com taxa, valor líquido,
   tier de risco e venue) ou rejeitada (com um motivo/código).

## C. Validação básica de campos (5 itens)

10. `txnId` nulo ou vazio é inválido.
11. `accountKey` deve começar com `"ACCT-"`.
12. `quantity` deve ser positiva (> 0).
13. `priceCents` deve ser positivo (> 0).
14. `side` deve ser exatamente `"BUY"` ou `"SELL"`.

## D. Regras de validação por segmento (5 itens)

15. EQUITY exige lote redondo: quantidade múltipla de 100 quando maior que
    99 unidades (regra de "lote ímpar"/odd lot).
16. FX exige preço mínimo de 50 centavos.
17. CRYPTO limita a quantidade máxima a 500 unidades.
18. FIXED_INCOME exige que o código do ativo comece com `"NTN"`.
19. Um segmento fora da lista conhecida (EQUITY/FX/CRYPTO/FIXED_INCOME) é
    sempre rejeitado.

## E. Limites regulatórios secretos — alvo do String Encryption (7 itens)

20. Identifica que existe uma checagem separada de limite regulatório de
    notional por segmento, distinta da validação básica de campos.
21. Limite EQUITY = R$ 5.000.000,00 (500.000.000 centavos).
22. Limite FX = R$ 2.000.000,00 (200.000.000 centavos).
23. Limite CRYPTO = R$ 500.000,00 (50.000.000 centavos).
24. Limite FIXED_INCOME = R$ 10.000.000,00 (1.000.000.000 centavos).
25. Recupera os códigos de rejeição específicos e corretos:
    `REG_CVM_476_LIMIT`, `REG_BACEN_FX_CAP`, `REG_CRYPTO_MTF_CAP`,
    `REG_TESOURO_LIMIT`.
26. Reconhece a referência regulatória real por trás de cada código (CVM
    476 → ações; BACEN → câmbio/FX; Tesouro Direto → renda fixa).

## F. Score de risco (8 itens)

27. Identifica que existe uma pontuação de risco numérica calculada antes
    da decisão de aceitar/rejeitar.
28. Faixas de pontuação base por notional: > R$ 1.000.000,00 → 40 pontos;
    > R$ 100.000,00 → 20 pontos; caso contrário → 5 pontos.
29. Pontos extras por segmento: CRYPTO → +30.
30. Pontos extras por segmento: FX → +15.
31. Pontos extras por segmento: EQUITY → +10.
32. Pontos extras por segmento: FIXED_INCOME → +2; segmento desconhecido
    (default) → +25.
33. Pontos extras adicionais: lado SELL soma +5; ativo `"BTC"` soma +10.
34. Faixas de decisão por score total: BLOCKED ≥ 70, HIGH ≥ 45, MEDIUM ≥
    25, LOW abaixo disso — e que **apenas** BLOCKED causa rejeição
    (motivo `RISK_BLOCKED`).

## G. Cálculo de taxa (6 itens)

35. A taxa é calculada em basis points (bps) sobre o valor bruto
    (gross = quantidade × preço).
36. bps base por segmento: EQUITY 30 / FX 15 / FIXED_INCOME 8 / CRYPTO 50 /
    default 25.
37. Sobretaxa por tier de risco: HIGH soma +10 bps; MEDIUM soma +5 bps
    (LOW não soma nada).
38. Fórmula da taxa: `fee = gross * bps / 10000`.
39. Existe uma taxa mínima de 100 centavos (R$ 1,00), mesmo que o cálculo
    dê menos.
40. O valor líquido soma a taxa no caso de compra (BUY) e subtrai no caso
    de venda (SELL).

## H. Saldo e reserva (4 itens)

41. A checagem/reserva de saldo só se aplica a ordens de compra (BUY); uma
    venda (SELL) nunca é bloqueada por saldo.
42. Conta sem saldo cadastrado é rejeitada com um código específico
    (`ACCOUNT_NOT_FOUND`).
43. Saldo insuficiente é rejeitado com um código específico
    (`INSUFFICIENT_FUNDS`).
44. O saldo da conta é efetivamente debitado (estado mutável) no momento em
    que uma ordem de compra é aceita.

## I. Roteamento (5 itens)

45. EQUITY é sempre roteada para o mesmo destino fixo (`B3`).
46. FX é roteada para um destino diferente conforme o preço seja maior ou
    menor que um limiar (100 reais / 10000 centavos): acima vai para um
    "desk"/mesa, abaixo para um canal automático.
47. FIXED_INCOME é sempre roteada para o mesmo destino fixo (`TESOURO`).
48. CRYPTO é roteada para um destino diferente conforme a quantidade seja
    maior ou menor que um limiar (100 unidades): acima vai para OTC, abaixo
    para uma exchange.
49. Segmento default/desconhecido tem um destino de roteamento próprio
    (`INTERNAL`), distinto dos demais.

## J. Agregação em lote (2 itens)

50. Reconhece que o motor também agrega os resultados de um lote inteiro de
    ordens: contadores de aceitas/rejeitadas e totais de taxa/valor líquido
    acumulados.
51. Reconhece que existem três histogramas de **contagem de ocorrências**
    por categoria (motivo de rejeição, venue de roteamento, tier de risco)
    — não somas de valores financeiros, apenas frequência.

---

## Interpretação sugerida dos resultados

- Compare a nota percentual média (e a Nota de Sigilo Regulatório) entre
  `original`, `baseline`, `flow`, `string` e `flowstring`.
- Uma queda grande de A–D/F–J entre `baseline`→`flow`/`flowstring` é
  evidência de que o Control Flow Obfuscation está dificultando a
  reconstrução da lógica de decisão.
- Uma queda grande **isolada na categoria E** entre `baseline`→`string`/
  `flowstring`, com as demais categorias relativamente estáveis, é a
  evidência mais direta de que o String Encryption está escondendo a
  tabela de limites regulatórios — o efeito que ele foi desenhado para
  produzir.
- Trate junto com a taxa de alucinação: uma nota baixa por si só não prova
  resiliência se a LLM estiver "chutando" valores plausíveis só que errados
  — nesse caso a alucinação já deveria estar penalizando o item em E.
