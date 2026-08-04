/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

final class G {
    private /* synthetic */ G() {
        G a;
    }

    static String l(String a, long a2) {
        switch (a) {
            case "EQUITY": {
                if (a2 <= 500000000L) break;
                return "REG_CVM_476_LIMIT";
            }
            case "FX": {
                if (a2 <= 200000000L) break;
                return "REG_BACEN_FX_CAP";
            }
            case "CRYPTO": {
                if (a2 <= 50000000L) break;
                return "REG_CRYPTO_MTF_CAP";
            }
            case "FIXED_INCOME": {
                if (a2 <= 1000000000L) break;
                return "REG_TESOURO_LIMIT";
            }
        }
        return "OK";
    }
}

