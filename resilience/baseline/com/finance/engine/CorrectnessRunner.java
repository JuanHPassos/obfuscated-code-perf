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
        for (int n : nArray = new int[]{5000, 20000, 50000}) {
            TransactionEngine$Order[] transactionEngine$OrderArray = OrderFactory.deterministicOrders(n);
            b b2 = new b();
            b2.a(transactionEngine$OrderArray, 1000000000L);
            d d2 = b2.a(transactionEngine$OrderArray);
            System.out.println("SIZE=" + n + " " + String.valueOf(d2));
        }
    }
}

