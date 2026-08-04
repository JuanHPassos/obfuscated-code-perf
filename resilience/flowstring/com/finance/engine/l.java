/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.G;
import com.finance.engine.TransactionEngine;
import com.finance.engine.i;
import com.finance.engine.m;
import java.util.HashMap;
import java.util.Map;

public final class l {
    private final Map<String, Long> b = new HashMap<String, Long>();

    public i l(TransactionEngine.Order a) {
        l a2;
        i a3 = new i();
        String a4 = a2.i(a);
        if (!"OK".equals(a4)) {
            a3.d = false;
            a3.J = a4;
            return a3;
        }
        a3.K = a2.l(a);
        if ("BLOCKED".equals(a3.K)) {
            a3.d = false;
            a3.J = "RISK_BLOCKED";
            return a3;
        }
        long a5 = a.quantity * a.priceCents;
        a3.j = a2.l(a, a5, a3.K);
        a3.b = "BUY".equals(a.side) ? a5 + a3.j : a5 - a3.j;
        String a6 = a2.l(a, a3.b);
        if (!"OK".equals(a6)) {
            a3.d = false;
            a3.J = a6;
            return a3;
        }
        a3.M = a2.g(a);
        a3.d = true;
        return a3;
    }

    private /* synthetic */ String i(TransactionEngine.Order a) {
        String a2;
        if (a.txnId == null || a.txnId.isEmpty()) {
            return "INVALID_TXN_ID";
        }
        if (!a.accountKey.startsWith("ACCT-")) {
            return "INVALID_ACCOUNT";
        }
        if (a.quantity <= 0L) {
            return "INVALID_QUANTITY";
        }
        if (a.priceCents <= 0L) {
            return "INVALID_PRICE";
        }
        if (!"BUY".equals(a.side) && !"SELL".equals(a.side)) {
            return "INVALID_SIDE";
        }
        switch (a.segment) {
            case "EQUITY": {
                if (a.quantity % 100L == 0L || a.quantity <= 99L) break;
                return "ODD_LOT";
            }
            case "FX": {
                if (a.priceCents >= 50L) break;
                return "FX_MIN_PRICE";
            }
            case "CRYPTO": {
                if (a.quantity <= 500L) break;
                return "CRYPTO_LIMIT";
            }
            case "FIXED_INCOME": {
                if (a.assetCode.startsWith("NTN")) break;
                return "FI_ASSET_MISMATCH";
            }
            default: {
                return "UNKNOWN_SEGMENT";
            }
        }
        if (!"OK".equals(a2 = G.l(a.segment, a.quantity * a.priceCents))) {
            return a2;
        }
        return "OK";
    }

    public m l(TransactionEngine.Order[] a) {
        m a2 = new m();
        for (TransactionEngine.Order a3 : a) {
            l a4;
            i a5 = a4.l(a3);
            if (a5.d) {
                ++a2.J;
                a2.j += a5.j;
                a2.M += a5.b;
                l.l(a2.K, a5.M);
                l.l(a2.b, a5.K);
                continue;
            }
            ++a2.d;
            l.l(a2.C, a5.J);
        }
        return a2;
    }

    private /* synthetic */ String g(TransactionEngine.Order a) {
        switch (a.segment) {
            case "EQUITY": {
                return "B3";
            }
            case "FX": {
                return a.priceCents > 10000L ? "FX_DESK" : "FX_AUTO";
            }
            case "FIXED_INCOME": {
                return "TESOURO";
            }
            case "CRYPTO": {
                return a.quantity > 100L ? "OTC" : "EXCHANGE";
            }
        }
        return "INTERNAL";
    }

    private /* synthetic */ String l(TransactionEngine.Order a) {
        long a2 = a.quantity * a.priceCents;
        int a3 = 0;
        a3 = a2 > 100000000L ? (a3 += 40) : (a2 > 10000000L ? (a3 += 20) : (a3 += 5));
        switch (a.segment) {
            case "CRYPTO": {
                a3 += 30;
                break;
            }
            case "FX": {
                a3 += 15;
                break;
            }
            case "EQUITY": {
                a3 += 10;
                break;
            }
            case "FIXED_INCOME": {
                a3 += 2;
                break;
            }
            default: {
                a3 += 25;
            }
        }
        if ("SELL".equals(a.side)) {
            a3 += 5;
        }
        if (a.assetCode.equals("BTC")) {
            a3 += 10;
        }
        if (a3 >= 70) {
            return "BLOCKED";
        }
        if (a3 >= 45) {
            return "HIGH";
        }
        if (a3 >= 25) {
            return "MEDIUM";
        }
        return "LOW";
    }

    private static /* synthetic */ void l(Map<String, Long> a, String a2) {
        if (a2 == null) {
            a2 = "NULL";
        }
        a.merge(a2, 1L, Long::sum);
    }

    public l() {
        l a;
    }

    private /* synthetic */ long l(TransactionEngine.Order a, long a2, String a3) {
        long a4 = switch (a.segment) {
            case "EQUITY" -> 30L;
            case "FX" -> 15L;
            case "FIXED_INCOME" -> 8L;
            case "CRYPTO" -> 50L;
            default -> 25L;
        };
        switch (a3) {
            case "HIGH": {
                a4 += 10L;
                break;
            }
            case "MEDIUM": {
                a4 += 5L;
                break;
            }
        }
        long a5 = a2 * a4 / 10000L;
        long a6 = 100L;
        return Math.max(a5, a6);
    }

    private /* synthetic */ String l(TransactionEngine.Order a, long a2) {
        l a3;
        if (!"BUY".equals(a.side)) {
            return "OK";
        }
        Long a4 = a3.b.get(a.accountKey);
        if (a4 == null) {
            return "ACCOUNT_NOT_FOUND";
        }
        if (a4 < a2) {
            return "INSUFFICIENT_FUNDS";
        }
        a3.b.put(a.accountKey, a4 - a2);
        return "OK";
    }

    public void l(TransactionEngine.Order[] a, long a2) {
        l a3;
        a3.b.clear();
        for (TransactionEngine.Order a4 : a) {
            a3.b.putIfAbsent(a4.accountKey, a2);
        }
    }
}

