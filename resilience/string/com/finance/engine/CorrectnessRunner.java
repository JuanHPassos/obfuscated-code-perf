/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.A;
import com.finance.engine.TransactionEngine$Order;
import com.finance.engine.l;
import com.finance.perf.OrderFactory;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.apache.commons.math3.stat.correlation.Covariance;

public final class CorrectnessRunner {
    private static final String D;

    public static void main(String[] a) {
        int[] nArray;
        for (int n : nArray = new int[]{5000, 20000, 50000}) {
            TransactionEngine$Order[] transactionEngine$OrderArray = OrderFactory.deterministicOrders(n);
            l l2 = new l();
            l2.C(transactionEngine$OrderArray, 1000000000L);
            A a2 = l2.C(transactionEngine$OrderArray);
            System.out.println(D + n + Covariance.C("-") + String.valueOf(a2));
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
                char[] cArray3 = Interval.C("\u001eMpz{").toCharArray();
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
                    char c = cArray4[n5];
                    cArray4[n5] = (char)(c ^ (switch (n3 % 7) {
                        case 0 -> 85;
                        case 1 -> 120;
                        case 2 -> 50;
                        case 3 -> 67;
                        case 4 -> 94;
                        case 5 -> 73;
                        default -> 19;
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
        D = new String(cArray).intern();
    }

    private /* synthetic */ CorrectnessRunner() {
        CorrectnessRunner a;
    }
}

