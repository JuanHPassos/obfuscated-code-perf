/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.RegulatoryLimits;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public final class TransactionEngine {
    private final Map<String, Long> balances = new HashMap<String, Long>();

    public void resetBalances(Order[] orders, long initialCents) {
        this.balances.clear();
        for (Order o : orders) {
            this.balances.putIfAbsent(o.accountKey, initialCents);
        }
    }

    public Summary run(Order[] orders) {
        Summary s = new Summary();
        for (Order o : orders) {
            Result res = this.pipeline(o);
            if (res.accepted) {
                ++s.acceptedCount;
                s.totalFeeCents += res.feeCents;
                s.totalNetSettledCents += res.netAmountCents;
                TransactionEngine.bump(s.venueHistogram, res.routedVenue);
                TransactionEngine.bump(s.riskHistogram, res.riskTier);
                continue;
            }
            ++s.rejectedCount;
            TransactionEngine.bump(s.rejectHistogram, res.rejectReason);
        }
        return s;
    }

    private static void bump(Map<String, Long> m, String k) {
        if (k == null) {
            k = "NULL";
        }
        m.merge(k, 1L, Long::sum);
    }

    public Result pipeline(Order o) {
        Result res = new Result();
        String validation = this.validate(o);
        if (!"OK".equals(validation)) {
            res.accepted = false;
            res.rejectReason = validation;
            return res;
        }
        res.riskTier = this.assessRisk(o);
        if ("BLOCKED".equals(res.riskTier)) {
            res.accepted = false;
            res.rejectReason = "RISK_BLOCKED";
            return res;
        }
        long gross = o.quantity * o.priceCents;
        res.feeCents = this.computeFee(o, gross, res.riskTier);
        res.netAmountCents = "BUY".equals(o.side) ? gross + res.feeCents : gross - res.feeCents;
        String check = this.checkBalanceAndReserve(o, res.netAmountCents);
        if (!"OK".equals(check)) {
            res.accepted = false;
            res.rejectReason = check;
            return res;
        }
        res.routedVenue = this.route(o);
        res.accepted = true;
        return res;
    }

    private String validate(Order o) {
        String limitVerdict;
        if (o.txnId == null || o.txnId.isEmpty()) {
            return "INVALID_TXN_ID";
        }
        if (!o.accountKey.startsWith("ACCT-")) {
            return "INVALID_ACCOUNT";
        }
        if (o.quantity <= 0L) {
            return "INVALID_QUANTITY";
        }
        if (o.priceCents <= 0L) {
            return "INVALID_PRICE";
        }
        if (!"BUY".equals(o.side) && !"SELL".equals(o.side)) {
            return "INVALID_SIDE";
        }
        switch (o.segment) {
            case "EQUITY": {
                if (o.quantity % 100L == 0L || o.quantity <= 99L) break;
                return "ODD_LOT";
            }
            case "FX": {
                if (o.priceCents >= 50L) break;
                return "FX_MIN_PRICE";
            }
            case "CRYPTO": {
                if (o.quantity <= 500L) break;
                return "CRYPTO_LIMIT";
            }
            case "FIXED_INCOME": {
                if (o.assetCode.startsWith("NTN")) break;
                return "FI_ASSET_MISMATCH";
            }
            default: {
                return "UNKNOWN_SEGMENT";
            }
        }
        if (!"OK".equals(limitVerdict = RegulatoryLimits.check(o.segment, o.quantity * o.priceCents))) {
            return limitVerdict;
        }
        return "OK";
    }

    private String assessRisk(Order o) {
        long notional = o.quantity * o.priceCents;
        int score = 0;
        score = notional > 100000000L ? (score += 40) : (notional > 10000000L ? (score += 20) : (score += 5));
        switch (o.segment) {
            case "CRYPTO": {
                score += 30;
                break;
            }
            case "FX": {
                score += 15;
                break;
            }
            case "EQUITY": {
                score += 10;
                break;
            }
            case "FIXED_INCOME": {
                score += 2;
                break;
            }
            default: {
                score += 25;
            }
        }
        if ("SELL".equals(o.side)) {
            score += 5;
        }
        if (o.assetCode.equals("BTC")) {
            score += 10;
        }
        if (score >= 70) {
            return "BLOCKED";
        }
        if (score >= 45) {
            return "HIGH";
        }
        if (score >= 25) {
            return "MEDIUM";
        }
        return "LOW";
    }

    private long computeFee(Order o, long gross, String riskTier) {
        long bps = switch (o.segment) {
            case "EQUITY" -> 30L;
            case "FX" -> 15L;
            case "FIXED_INCOME" -> 8L;
            case "CRYPTO" -> 50L;
            default -> 25L;
        };
        switch (riskTier) {
            case "HIGH": {
                bps += 10L;
                break;
            }
            case "MEDIUM": {
                bps += 5L;
                break;
            }
        }
        long fee = gross * bps / 10000L;
        long minFee = 100L;
        return Math.max(fee, minFee);
    }

    private String checkBalanceAndReserve(Order o, long amount) {
        if (!"BUY".equals(o.side)) {
            return "OK";
        }
        Long bal = this.balances.get(o.accountKey);
        if (bal == null) {
            return "ACCOUNT_NOT_FOUND";
        }
        if (bal < amount) {
            return "INSUFFICIENT_FUNDS";
        }
        this.balances.put(o.accountKey, bal - amount);
        return "OK";
    }

    private String route(Order o) {
        switch (o.segment) {
            case "EQUITY": {
                return "B3";
            }
            case "FX": {
                return o.priceCents > 10000L ? "FX_DESK" : "FX_AUTO";
            }
            case "FIXED_INCOME": {
                return "TESOURO";
            }
            case "CRYPTO": {
                return o.quantity > 100L ? "OTC" : "EXCHANGE";
            }
        }
        return "INTERNAL";
    }

    public static final class Order {
        public String txnId;
        public String accountKey;
        public String assetCode;
        public String side;
        public long quantity;
        public long priceCents;
        public String segment;
    }

    public static final class Summary {
        public long acceptedCount;
        public long rejectedCount;
        public long totalFeeCents;
        public long totalNetSettledCents;
        public final Map<String, Long> rejectHistogram = new TreeMap<String, Long>();
        public final Map<String, Long> venueHistogram = new TreeMap<String, Long>();
        public final Map<String, Long> riskHistogram = new TreeMap<String, Long>();

        public long decisionHash() {
            long h = 1125899906842597L;
            h = 31L * h + this.acceptedCount;
            h = 31L * h + this.rejectedCount;
            h = 31L * h + this.totalFeeCents;
            h = 31L * h + this.totalNetSettledCents;
            h = Summary.mix(h, this.rejectHistogram);
            h = Summary.mix(h, this.venueHistogram);
            h = Summary.mix(h, this.riskHistogram);
            return h;
        }

        private static long mix(long h, Map<String, Long> m) {
            for (Map.Entry<String, Long> e : m.entrySet()) {
                for (int i = 0; i < e.getKey().length(); ++i) {
                    h = 31L * h + (long)e.getKey().charAt(i);
                }
                h = 31L * h + e.getValue();
            }
            return h;
        }

        public String toString() {
            return "accepted=" + this.acceptedCount + " rejected=" + this.rejectedCount + " totalFee=" + this.totalFeeCents + " totalNetSettled=" + this.totalNetSettledCents + " reject=" + String.valueOf(this.rejectHistogram) + " venue=" + String.valueOf(this.venueHistogram) + " risk=" + String.valueOf(this.riskHistogram) + " hash=" + this.decisionHash();
        }
    }

    public static final class Result {
        public boolean accepted;
        public String rejectReason;
        public long feeCents;
        public long netAmountCents;
        public String riskTier;
        public String routedVenue;
    }
}

