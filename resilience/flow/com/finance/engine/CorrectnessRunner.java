/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.TransactionEngine$Order;
import com.finance.engine.b;
import com.finance.engine.d;
import com.finance.perf.OrderFactory;

public final class CorrectnessRunner {
    private CorrectnessRunner() {
    }

    public static void main(String[] stringArray) {
        int[] nArray;
        int[] nArray2 = nArray = new int[]{5000, 20000, 50000};
        int n = b.b;
        for (int n2 : nArray2) {
            TransactionEngine$Order[] transactionEngine$OrderArray = OrderFactory.deterministicOrders(n2);
            b b2 = new b();
            b2.a(transactionEngine$OrderArray, 1000000000L);
            d d2 = b2.a(transactionEngine$OrderArray);
            System.out.println("SIZE=" + n2 + " " + String.valueOf(d2));
            if (n == 0) continue;
            int n3 = TransactionEngine$Order.b;
            TransactionEngine$Order.b = ++n3;
            break;
        }
    }
}

