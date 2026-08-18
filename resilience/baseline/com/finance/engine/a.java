/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

final class a {
    private a() {
    }

    static String a(String string, long l) {
        switch (string) {
            case "EQUITY": {
                if (l <= 500000000L) break;
                return "REG_CVM_476_LIMIT";
            }
            case "FX": {
                if (l <= 200000000L) break;
                return "REG_BACEN_FX_CAP";
            }
            case "CRYPTO": {
                if (l <= 50000000L) break;
                return "REG_CRYPTO_MTF_CAP";
            }
            case "FIXED_INCOME": {
                if (l <= 1000000000L) break;
                return "REG_TESOURO_LIMIT";
            }
        }
        return "OK";
    }
}

