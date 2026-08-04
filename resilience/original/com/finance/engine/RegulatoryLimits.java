/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

final class RegulatoryLimits {
    private RegulatoryLimits() {
    }

    static String check(String segment, long notionalCents) {
        switch (segment) {
            case "EQUITY": {
                if (notionalCents <= 500000000L) break;
                return "REG_CVM_476_LIMIT";
            }
            case "FX": {
                if (notionalCents <= 200000000L) break;
                return "REG_BACEN_FX_CAP";
            }
            case "CRYPTO": {
                if (notionalCents <= 50000000L) break;
                return "REG_CRYPTO_MTF_CAP";
            }
            case "FIXED_INCOME": {
                if (notionalCents <= 1000000000L) break;
                return "REG_TESOURO_LIMIT";
            }
        }
        return "OK";
    }
}

