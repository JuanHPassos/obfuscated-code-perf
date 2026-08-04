/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.TransactionEngine;
import com.finance.perf.OrderFactory;

public final class CorrectnessRunner {
    private CorrectnessRunner() {
    }

    public static void main(String[] args) {
        int[] sizes;
        for (int size : sizes = new int[]{5000, 20000, 50000}) {
            TransactionEngine.Order[] orders = OrderFactory.deterministicOrders(size);
            TransactionEngine engine = new TransactionEngine();
            engine.resetBalances(orders, 1000000000L);
            TransactionEngine.Summary s = engine.run(orders);
            System.out.println("SIZE=" + size + " " + String.valueOf(s));
        }
    }
}

