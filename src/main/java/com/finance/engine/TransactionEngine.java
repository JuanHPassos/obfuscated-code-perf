package com.finance.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Motor de processamento de ordens/transacoes financeiras
 * 
 * Unica classe que deve ser ofuscada pelo Allatori
 * Não contém nada do JMH. 
 * Pode ser reusada em: 
 *  (a) Benchmark
 *  (b) Teste de correcao (golden file)
 *  (c) Decompilação isolada para avaliação de resiliencia com LLM
 *  
 *  Alvos de ofuscação:
 *    - Ramificações de regras de negócio -> Control Flow Obfuscation
 *    - Tabela de limites regulatorios    -> String Encryption
 */

public final class TransactionEngine {

    // ---- Modelo de dados ------------------------------------------------------------

    // Processa uma ordem de compra/venda
    public static final class Order {
        public String txnId;
        public String accountKey;   
        public String assetCode;
        public String side;         // "BUY" / "SELL"
        public long quantity;
        public long priceCents;
        public String segment;      // "EQUITY", "FX", "FIXED_INCOME", "CRYPTO"
    }

    public static final class Result {
        public boolean accepted;
        public String rejectReason;
        public long feeCents;
        public long netAmountCents;
        public String riskTier;
        public String routedVenue;
    }

    /**
     * Resultado agregado de rodar um lote inteiro de ordens (o "placar final").
     *
     * Comparamos o texto gerado por toString() do jar original com o do jar ofuscado.
     * Se a ofuscacao mudou o comportamento do motor por engano -- por exemplo
     * uma ordem que era aceita passou a ser rejeitada -- os numeros abaixo vao
     * ser diferentes entre as duas execucoes, e a diferenca aparece no diff.
     * Por isso os campos precisam ser 100% deterministicos: mesma entrada
     * sempre gera exatamente os mesmos valores.
     */
    public static final class Summary {
        public long acceptedCount;
        public long rejectedCount;
        public long totalFeeCents;
        public long totalNetSettledCents;

        // Cada mapa conta quantas vezes cada "motivo" apareceu: motivo de
        // rejeicao, venue para onde a ordem foi roteada, e tier de risco.
        // Usamos TreeMap (em vez de HashMap) porque ele mantem as chaves
        // sempre ordenadas alfabeticamente. Isso importa porque um HashMap
        // comum pode imprimir os itens em ordens diferentes em execucoes
        // diferentes, o que faria o toString() variar mesmo sem nada ter
        // mudado de verdade -- e quebraria a comparacao do golden file.
        public final Map<String, Long> rejectHistogram = new TreeMap<>();
        public final Map<String, Long> venueHistogram = new TreeMap<>();
        public final Map<String, Long> riskHistogram  = new TreeMap<>();

        /**
         * Resume todo o Summary em um unico numero (um hash, como o hashCode()
         * do Java, so que calculado a mao aqui para garantir que o calculo seja
         * sempre o mesmo, independente da versao do Java usada).
         * Ideia: em vez de comparar visualmente cada campo, basta comparar esse
         * numero entre a execucao original e a ofuscada. Se forem iguais, e um
         * forte indicio de que tudo (contadores + os tres mapas) bateu certinho.
         */
        public long decisionHash() {
            long h = 1125899906842597L;   // valor inicial arbitrario (so pra nao comecar de 0)
            h = 31 * h + acceptedCount;
            h = 31 * h + rejectedCount;
            h = 31 * h + totalFeeCents;
            h = 31 * h + totalNetSettledCents;
            h = mix(h, rejectHistogram);
            h = mix(h, venueHistogram);
            h = mix(h, riskHistogram);
            return h;
        }

        /** Combina no hash `h` cada entrada (chave, contagem) do mapa `m`, uma de cada vez. */
        private static long mix(long h, Map<String, Long> m) {
            for (Map.Entry<String, Long> e : m.entrySet()) {    // TreeMap = ordem estavel
                for (int i = 0; i < e.getKey().length(); i++) {
                    h = 31 * h + e.getKey().charAt(i);
                }
                h = 31 * h + e.getValue();
            }
            return h;
        }

        /** Texto final que vira o golden file: e essa string que e comparada entre original e ofuscado. */
        @Override public String toString() {
            return "accepted=" + acceptedCount
                 + " rejected=" + rejectedCount
                 + " totalFee=" + totalFeeCents
                 + " totalNetSettled=" + totalNetSettledCents
                 + " reject=" + rejectHistogram
                 + " venue=" + venueHistogram
                 + " risk=" + riskHistogram
                 + " hash=" + decisionHash();
        }
    }

    // ---- Estado (saldos) -------------------------------------------------------------

    // Conta -> Saldo em centavos (ex: "ACCT-42" -> 1000000 = R$10.000,00)
    private final Map<String, Long> balances = new HashMap<>();

    /**
     * "Zera o banco": limpa todos os saldos e da o mesmo saldo inicial para
     * cada conta que aparece no lote de ordens.
     *
     * Precisa ser chamado antes de cada execucao (run) ou iteracao de
     * benchmark, senao o saldo vai sendo consumido pelas compras aceitas e,
     * de uma rodada para a outra, mais ordens seriam rejeitadas por falta de
     * saldo (INSUFFICIENT_FUNDS) so por causa do acumulo -- nao porque a
     * ordem em si seja invalida. Isso enviesaria tanto o Summary (golden
     * file) quanto os tempos medidos no benchmark.
     */
    public void resetBalances(Order[] orders, long initialCents) {
        balances.clear();
        for (Order o : orders) {
            // putIfAbsent: se a conta ja apareceu antes no loop, mantem o
            // saldo que ja foi setado (nao sobrescreve de novo).
            balances.putIfAbsent(o.accountKey, initialCents);
        }
    }

    // Execução completa: processa todas as ordens e devolve um Summary
    public Summary run(Order[] orders) {
        Summary s = new Summary();
        for (Order o : orders) {
            Result res = pipeline(o);
            if (res.accepted) {
                s.acceptedCount++;
                s.totalFeeCents += res.feeCents;
                s.totalNetSettledCents += res.netAmountCents;
                bump(s.venueHistogram, res.routedVenue);
                bump(s.riskHistogram, res.riskTier);
            } else {
                s.rejectedCount++;
                bump(s.rejectHistogram, res.rejectReason);
            }
        }
        return s;
    }

    private static void bump(Map<String, Long> m, String k) {
        if (k == null) k = "NULL";
        m.merge(k, 1L, Long::sum);
    }

    // ---- Orquestracao (rico em control flow) --------------------------------------------

    // Processamento da Ordem em 5 etapas
    public Result pipeline(Order o) {
        Result res = new Result();

        // Valida campos e regras por segmento (EQUITY, FX, CRYPTO, FIXED_INCOME).
        String validation = validate(o);
        if (!"OK".equals(validation)) {
            res.accepted = false;
            res.rejectReason = validation;
            return res;
        }

        // Calcula score de risco (LOW/MEDIUM/HIGH/BLOCKED) 
        // somando pontos por segmento/lado/ativo.
        res.riskTier = assessRisk(o);
        if ("BLOCKED".equals(res.riskTier)) {
            res.accepted = false;
            res.rejectReason = "RISK_BLOCKED";
            return res;
        }

        // Calcula taxa em bps conforme segmento e risco
        long gross = o.quantity * o.priceCents;
        res.feeCents = computeFee(o, gross, res.riskTier);
        res.netAmountCents = "BUY".equals(o.side) ? gross + res.feeCents
                                                  : gross - res.feeCents;

        // Verifica e debita saldo da conta (estado mutável em HashMap)
        String check = checkBalanceAndReserve(o, res.netAmountCents);
        if (!"OK".equals(check)) {
            res.accepted = false;
            res.rejectReason = check;
            return res;
        }

        // Decide para qual venue a ordem vai (B3, FX_DESK, TESOURO, OTC, etc).
        res.routedVenue = route(o);
        res.accepted = true;
        return res;
    }

    // ---- Etapa 1: validacao ----------------------------------------------------------

    private String validate(Order o) {
        if (o.txnId == null || o.txnId.isEmpty())          return "INVALID_TXN_ID";
        if (!o.accountKey.startsWith("ACCT-"))             return "INVALID_ACCOUNT";
        if (o.quantity <= 0)                               return "INVALID_QUANTITY";
        if (o.priceCents <= 0)                             return "INVALID_PRICE";
        if (!"BUY".equals(o.side) && !"SELL".equals(o.side)) return "INVALID_SIDE";

        switch (o.segment) {
            case "EQUITY":
                if (o.quantity % 100 != 0 && o.quantity > 99) return "ODD_LOT";
                break;
            case "FX":
                if (o.priceCents < 50) return "FX_MIN_PRICE";
                break;
            case "CRYPTO":
                if (o.quantity > 500) return "CRYPTO_LIMIT";
                break;
            case "FIXED_INCOME":
                if (!o.assetCode.startsWith("NTN")) return "FI_ASSET_MISMATCH";
                break;
            default:
                return "UNKNOWN_SEGMENT";
        }

        // Regra de negocio "secreta" -> alvo real de String Encryption.
        // Limites regulatorios de notional por segmento (em centavos).
        String limitVerdict = RegulatoryLimits.check(o.segment, o.quantity * o.priceCents);
        if (!"OK".equals(limitVerdict)) return limitVerdict;

        return "OK";
    }

    // ---- Etapa 2: classificacao de risco ---------------------------------------------

    private String assessRisk(Order o) {
        long notional = o.quantity * o.priceCents;
        int score = 0;

        if (notional > 1_000_000_00L)      score += 40;
        else if (notional > 100_000_00L)   score += 20;
        else                               score += 5;

        switch (o.segment) {
            case "CRYPTO":       score += 30; break;
            case "FX":           score += 15; break;
            case "EQUITY":       score += 10; break;
            case "FIXED_INCOME": score += 2;  break;
            default:             score += 25;
        }

        if ("SELL".equals(o.side)) score += 5;
        if (o.assetCode.equals("BTC")) score += 10;

        if (score >= 70)       return "BLOCKED";
        else if (score >= 45)  return "HIGH";
        else if (score >= 25)  return "MEDIUM";
        else                   return "LOW";
    }

    // ---- Etapa 3: calculo de taxas ---------------------------------------------------

    private long computeFee(Order o, long gross, String riskTier) {
        long bps;
        switch (o.segment) {
            case "EQUITY":       bps = 30; break;
            case "FX":           bps = 15; break;
            case "FIXED_INCOME": bps = 8;  break;
            case "CRYPTO":       bps = 50; break;
            default:             bps = 25;
        }
        switch (riskTier) {
            case "HIGH":   bps += 10; break;
            case "MEDIUM": bps += 5;  break;
            default:       break;
        }
        long fee = (gross * bps) / 10_000L;
        long minFee = 100;     // R$1,00
        return Math.max(fee, minFee);
    }

    // ---- Etapa 4: saldo + reserva ----------------------------------------------------

    private String checkBalanceAndReserve(Order o, long amount) {
        if (!"BUY".equals(o.side)) return "OK";
        Long bal = balances.get(o.accountKey);
        if (bal == null) return "ACCOUNT_NOT_FOUND";
        if (bal < amount) return "INSUFFICIENT_FUNDS";
        balances.put(o.accountKey, bal - amount);
        return "OK";
    }

    // ---- Etapa 5: roteamento ---------------------------------------------------------

    private String route(Order o) {
        switch (o.segment) {
            case "EQUITY":       return "B3";
            case "FX":           return o.priceCents > 10000 ? "FX_DESK" : "FX_AUTO";
            case "FIXED_INCOME": return "TESOURO";
            case "CRYPTO":       return o.quantity > 100 ? "OTC" : "EXCHANGE";
            default:             return "INTERNAL";
        }
    }
}