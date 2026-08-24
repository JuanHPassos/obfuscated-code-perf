/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.TransactionEngine$Order;
import com.finance.engine.b;

final class a {
    private a() {
    }

    /*
     * Unable to fully structure code
     */
    static String a(String var0, long var1_1) {
        block14: {
            var4_2 = var0;
            var3_3 = b.b;
            var5_4 = -1;
            v0 = var4_2.hashCode();
            if (var3_3 != 0) break block14;
            switch (v0) {
                case 2052821701: {
                    v0 = (int)var4_2.equals("EQUITY");
                    if (var3_3 != 0) break;
                    if (v0 == 0) ** GOTO lbl30
                    var5_4 = 0;
                    if (var3_3 == 0) ** GOTO lbl30
                }
                case 2258: {
                    v0 = (int)var4_2.equals("FX");
                    if (var3_3 != 0) break;
                    if (v0 == 0) ** GOTO lbl30
                    var5_4 = 1;
                    if (var3_3 == 0) ** GOTO lbl30
                }
                case 1996612801: {
                    v0 = (int)var4_2.equals("CRYPTO");
                    if (var3_3 != 0) break;
                    if (v0 == 0) ** GOTO lbl30
                    var5_4 = 2;
                    if (var3_3 == 0) ** GOTO lbl30
                }
                case 968326388: {
                    v0 = (int)var4_2.equals("FIXED_INCOME");
                    if (var3_3 != 0) break;
                    if (v0 != 0) {
                        var5_4 = 3;
                    }
                }
lbl30:
                // 10 sources

                default: {
                    v0 = var5_4;
                }
            }
        }
        if (var3_3 != 0) ** GOTO lbl38
        switch (v0) {
            case 0: {
                cfr_temp_0 = var1_1 - 500000000L;
                v0 = cfr_temp_0 == 0L ? 0 : (cfr_temp_0 < 0L ? -1 : 1);
lbl38:
                // 2 sources

                if (v0 <= 0) break;
                return "REG_CVM_476_LIMIT";
            }
            case 1: {
                if (var1_1 <= 200000000L) break;
                return "REG_BACEN_FX_CAP";
            }
            case 2: {
                if (var1_1 <= 50000000L) break;
                return "REG_CRYPTO_MTF_CAP";
            }
            case 3: {
                if (var1_1 <= 1000000000L) break;
                return "REG_TESOURO_LIMIT";
            }
        }
        if (TransactionEngine$Order.b != 0) {
            b.b = ++var3_3;
        }
        return "OK";
    }
}

