/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.TransactionEngine$Order;
import com.finance.engine.a;
import com.finance.engine.c;
import com.finance.engine.d;
import java.util.HashMap;
import java.util.Map;

public final class b {
    private final Map<String, Long> a = new HashMap<String, Long>();

    public void a(TransactionEngine$Order[] transactionEngine$OrderArray, long l) {
        this.a.clear();
        for (TransactionEngine$Order transactionEngine$Order : transactionEngine$OrderArray) {
            this.a.putIfAbsent(transactionEngine$Order.accountKey, l);
        }
    }

    public d a(TransactionEngine$Order[] transactionEngine$OrderArray) {
        d d2 = new d();
        for (TransactionEngine$Order transactionEngine$Order : transactionEngine$OrderArray) {
            c c2 = this.a(transactionEngine$Order);
            if (c2.a) {
                ++d2.a;
                d2.c += c2.c;
                d2.d += c2.d;
                b.a(d2.f, c2.f);
                b.a(d2.g, c2.e);
                continue;
            }
            ++d2.b;
            b.a(d2.e, c2.b);
        }
        return d2;
    }

    private static void a(Map<String, Long> map, String string) {
        if (string == null) {
            string = "NULL";
        }
        map.merge(string, 1L, Long::sum);
    }

    public c a(TransactionEngine$Order transactionEngine$Order) {
        c c2 = new c();
        String string = this.b(transactionEngine$Order);
        if (!"OK".equals(string)) {
            c2.a = false;
            c2.b = string;
            return c2;
        }
        c2.e = this.c(transactionEngine$Order);
        if ("BLOCKED".equals(c2.e)) {
            c2.a = false;
            c2.b = "RISK_BLOCKED";
            return c2;
        }
        long l = transactionEngine$Order.quantity * transactionEngine$Order.priceCents;
        c2.c = this.a(transactionEngine$Order, l, c2.e);
        c2.d = "BUY".equals(transactionEngine$Order.side) ? l + c2.c : l - c2.c;
        String string2 = this.a(transactionEngine$Order, c2.d);
        if (!"OK".equals(string2)) {
            c2.a = false;
            c2.b = string2;
            return c2;
        }
        c2.f = this.d(transactionEngine$Order);
        c2.a = true;
        return c2;
    }

    private String b(TransactionEngine$Order transactionEngine$Order) {
        String string;
        if (transactionEngine$Order.txnId == null || transactionEngine$Order.txnId.isEmpty()) {
            return "INVALID_TXN_ID";
        }
        if (!transactionEngine$Order.accountKey.startsWith("ACCT-")) {
            return "INVALID_ACCOUNT";
        }
        if (transactionEngine$Order.quantity <= 0L) {
            return "INVALID_QUANTITY";
        }
        if (transactionEngine$Order.priceCents <= 0L) {
            return "INVALID_PRICE";
        }
        if (!"BUY".equals(transactionEngine$Order.side) && !"SELL".equals(transactionEngine$Order.side)) {
            return "INVALID_SIDE";
        }
        switch (transactionEngine$Order.segment) {
            case "EQUITY": {
                if (transactionEngine$Order.quantity % 100L == 0L || transactionEngine$Order.quantity <= 99L) break;
                return "ODD_LOT";
            }
            case "FX": {
                if (transactionEngine$Order.priceCents >= 50L) break;
                return "FX_MIN_PRICE";
            }
            case "CRYPTO": {
                if (transactionEngine$Order.quantity <= 500L) break;
                return "CRYPTO_LIMIT";
            }
            case "FIXED_INCOME": {
                if (transactionEngine$Order.assetCode.startsWith("NTN")) break;
                return "FI_ASSET_MISMATCH";
            }
            default: {
                return "UNKNOWN_SEGMENT";
            }
        }
        if (!"OK".equals(string = com.finance.engine.a.a(transactionEngine$Order.segment, transactionEngine$Order.quantity * transactionEngine$Order.priceCents))) {
            return string;
        }
        return "OK";
    }

    private String c(TransactionEngine$Order transactionEngine$Order) {
        long l = transactionEngine$Order.quantity * transactionEngine$Order.priceCents;
        int n = 0;
        n = l > 100000000L ? (n += 40) : (l > 10000000L ? (n += 20) : (n += 5));
        switch (transactionEngine$Order.segment) {
            case "CRYPTO": {
                n += 30;
                break;
            }
            case "FX": {
                n += 15;
                break;
            }
            case "EQUITY": {
                n += 10;
                break;
            }
            case "FIXED_INCOME": {
                n += 2;
                break;
            }
            default: {
                n += 25;
            }
        }
        if ("SELL".equals(transactionEngine$Order.side)) {
            n += 5;
        }
        if (transactionEngine$Order.assetCode.equals("BTC")) {
            n += 10;
        }
        if (n >= 70) {
            return "BLOCKED";
        }
        if (n >= 45) {
            return "HIGH";
        }
        if (n >= 25) {
            return "MEDIUM";
        }
        return "LOW";
    }

    private long a(TransactionEngine$Order transactionEngine$Order, long l, String string) {
        long l2 = switch (transactionEngine$Order.segment) {
            case "EQUITY" -> 30L;
            case "FX" -> 15L;
            case "FIXED_INCOME" -> 8L;
            case "CRYPTO" -> 50L;
            default -> 25L;
        };
        switch (string) {
            case "HIGH": {
                l2 += 10L;
                break;
            }
            case "MEDIUM": {
                l2 += 5L;
                break;
            }
        }
        long l3 = l * l2 / 10000L;
        long l4 = 100L;
        return Math.max(l3, l4);
    }

    private String a(TransactionEngine$Order transactionEngine$Order, long l) {
        if (!"BUY".equals(transactionEngine$Order.side)) {
            return "OK";
        }
        Long l2 = this.a.get(transactionEngine$Order.accountKey);
        if (l2 == null) {
            return "ACCOUNT_NOT_FOUND";
        }
        if (l2 < l) {
            return "INSUFFICIENT_FUNDS";
        }
        this.a.put(transactionEngine$Order.accountKey, l2 - l);
        return "OK";
    }

    private String d(TransactionEngine$Order transactionEngine$Order) {
        switch (transactionEngine$Order.segment) {
            case "EQUITY": {
                return "B3";
            }
            case "FX": {
                return transactionEngine$Order.priceCents > 10000L ? "FX_DESK" : "FX_AUTO";
            }
            case "FIXED_INCOME": {
                return "TESOURO";
            }
            case "CRYPTO": {
                return transactionEngine$Order.quantity > 100L ? "OTC" : "EXCHANGE";
            }
        }
        return "INTERNAL";
    }
}

