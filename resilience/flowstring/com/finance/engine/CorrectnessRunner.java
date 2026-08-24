/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.TransactionEngine$Order;
import com.finance.engine.b;
import com.finance.engine.d;
import com.finance.perf.OrderFactory;

public final class CorrectnessRunner {
    private static final String a;

    private CorrectnessRunner() {
    }

    public static void main(String[] stringArray) {
        int[] nArray = new int[]{5000, 20000, 50000};
        int n = b.b;
        for (int n2 : nArray) {
            TransactionEngine$Order[] transactionEngine$OrderArray = OrderFactory.deterministicOrders(n2);
            b b2 = new b();
            b2.a(transactionEngine$OrderArray, 1000000000L);
            d d2 = b2.a(transactionEngine$OrderArray);
            System.out.println(a + n2 + " " + String.valueOf(d2));
            if (n == 0) continue;
            int n3 = TransactionEngine$Order.b;
            TransactionEngine$Order.b = ++n3;
            break;
        }
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {
        char[] cArray;
        block12: {
            int n;
            int n2;
            char[] cArray2;
            int n3;
            block11: {
                char[] cArray3 = "\\?B6s".toCharArray();
                n3 = 0;
                int n4 = cArray3.length;
                cArray2 = cArray3;
                n2 = n4;
                if (n4 <= 1) break block11;
                cArray = cArray2;
                n = n2;
                if (n2 <= n3) break block12;
            }
            do {
                char[] cArray4 = cArray2;
                char[] cArray5 = cArray2;
                int n5 = n3;
                while (true) {
                    char c2 = cArray4[n5];
                    cArray4[n5] = (char)(c2 ^ (switch (n3 % 7) {
                        case 0 -> 15;
                        case 1 -> 118;
                        case 2 -> 24;
                        case 3 -> 115;
                        case 4 -> 78;
                        case 5 -> 38;
                        default -> 1;
                    }));
                    ++n3;
                    cArray2 = cArray5;
                    n2 = n2;
                    if (n2 != 0) break;
                    cArray5 = cArray2;
                    n = n2;
                    n5 = n2;
                    cArray4 = cArray2;
                }
                cArray = cArray2;
                n = n2;
            } while (n2 > n3);
        }
        a = new String(cArray).intern();
    }
}

