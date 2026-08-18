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
    public static int b;

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
                com.finance.engine.b.a(d2.f, c2.f);
                com.finance.engine.b.a(d2.g, c2.e);
                continue;
            }
            ++d2.b;
            com.finance.engine.b.a(d2.e, c2.b);
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

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private String b(TransactionEngine$Order var1_1) {
        block50: {
            block49: {
                block48: {
                    block47: {
                        block46: {
                            block44: {
                                block45: {
                                    block42: {
                                        block43: {
                                            var2_2 = com.finance.engine.b.b;
                                            v0 = var1_1.txnId;
                                            if (var2_2 != 0) break block42;
                                            if (v0 == null) break block43;
                                            v1 = var1_1.txnId.isEmpty();
                                            if (var2_2 != 0) break block44;
                                            if (v1 == 0) break block45;
                                        }
                                        v0 = "INVALID_TXN_ID";
                                    }
                                    return v0;
                                }
                                v1 = var1_1.accountKey.startsWith("ACCT-");
                            }
                            if (var2_2 == 0) {
                                if (v1 == 0) {
                                    return "INVALID_ACCOUNT";
                                }
                                cfr_temp_0 = var1_1.quantity - 0L;
                                v1 = cfr_temp_0 == 0L ? 0 : (cfr_temp_0 < 0L ? -1 : 1);
                            }
                            if (var2_2 == 0) {
                                if (v1 <= 0) {
                                    return "INVALID_QUANTITY";
                                }
                                cfr_temp_1 = var1_1.priceCents - 0L;
                                v1 = cfr_temp_1 == 0L ? 0 : (cfr_temp_1 < 0L ? -1 : 1);
                            }
                            if (var2_2 != 0) break block46;
                            if (v1 <= 0) {
                                return "INVALID_PRICE";
                            }
                            v2 = "BUY";
                            if (var2_2 != 0) break block47;
                            v1 = (int)v2.equals(var1_1.side);
                        }
                        if (v1 == 0) {
                            v2 = "SELL";
                            if (var2_2 == 0) {
                                if (!v2.equals(var1_1.side)) {
                                    return "INVALID_SIDE";
                                } else {
                                    ** GOTO lbl-1000
                                }
                            }
                        } else lbl-1000:
                        // 3 sources

                        {
                            v2 = var1_1.segment;
                        }
                    }
                    var3_3 = v2;
                    var4_4 = -1;
                    v3 = var3_3.hashCode();
                    if (var2_2 != 0) break block48;
                    switch (v3) {
                        case 2052821701: {
                            v3 = (int)var3_3.equals("EQUITY");
                            if (var2_2 != 0) break;
                            if (v3 == 0) ** GOTO lbl68
                            var4_4 = 0;
                            if (var2_2 == 0) ** GOTO lbl68
                        }
                        case 2258: {
                            v3 = (int)var3_3.equals("FX");
                            if (var2_2 != 0) break;
                            if (v3 == 0) ** GOTO lbl68
                            var4_4 = 1;
                            if (var2_2 == 0) ** GOTO lbl68
                        }
                        case 1996612801: {
                            v3 = (int)var3_3.equals("CRYPTO");
                            if (var2_2 != 0) break;
                            if (v3 == 0) ** GOTO lbl68
                            var4_4 = 2;
                            if (var2_2 == 0) ** GOTO lbl68
                        }
                        case 968326388: {
                            v3 = (int)var3_3.equals("FIXED_INCOME");
                            if (var2_2 != 0) break;
                            if (v3 != 0) {
                                var4_4 = 3;
                            }
                        }
lbl68:
                        // 10 sources

                        default: {
                            v3 = var4_4;
                        }
                    }
                }
                if (var2_2 != 0) ** GOTO lbl76
                switch (v3) {
                    case 0: {
                        cfr_temp_2 = var1_1.quantity % 100L - 0L;
                        v3 = cfr_temp_2 == 0L ? 0 : (cfr_temp_2 < 0L ? -1 : 1);
lbl76:
                        // 2 sources

                        if (var2_2 == 0) {
                            if (v3 == 0) break;
                            cfr_temp_3 = var1_1.quantity - 99L;
                            v3 = cfr_temp_3 == 0L ? 0 : (cfr_temp_3 < 0L ? -1 : 1);
                            if (var2_2 == 0) {
                                if (v3 <= 0) break;
                                return "ODD_LOT";
                            }
                        }
                        break block49;
                    }
                    case 1: {
                        cfr_temp_4 = var1_1.priceCents - 50L;
                        v3 = cfr_temp_4 == 0L ? 0 : (cfr_temp_4 < 0L ? -1 : 1);
                        if (var2_2 == 0) {
                            if (v3 >= 0) break;
                            return "FX_MIN_PRICE";
                        }
                        break block49;
                    }
                    case 2: {
                        cfr_temp_5 = var1_1.quantity - 500L;
                        v3 = cfr_temp_5 == 0L ? 0 : (cfr_temp_5 < 0L ? -1 : 1);
                        if (var2_2 == 0) {
                            if (v3 <= 0) break;
                            return "CRYPTO_LIMIT";
                        }
                        break block49;
                    }
                    case 3: {
                        v3 = (int)var1_1.assetCode.startsWith("NTN");
                        if (var2_2 == 0) {
                            if (v3 != 0) break;
                            return "FI_ASSET_MISMATCH";
                        }
                        break block49;
                    }
                    default: {
                        return "UNKNOWN_SEGMENT";
                    }
                }
                var3_3 = com.finance.engine.a.a(var1_1.segment, var1_1.quantity * var1_1.priceCents);
                v4 = "OK";
                if (var2_2 != 0) break block50;
                v3 = (int)v4.equals(var3_3);
            }
            if (v3 == 0) {
                return var3_3;
            }
            v4 = "OK";
        }
        return v4;
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

