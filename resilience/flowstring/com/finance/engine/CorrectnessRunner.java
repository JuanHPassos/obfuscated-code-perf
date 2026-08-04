/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.TransactionEngine;
import com.finance.engine.l;
import com.finance.engine.m;
import com.finance.perf.OrderFactory;

public final class CorrectnessRunner {
    public static void main(String[] a) {
        int[] a2;
        for (int a3 : a2 = new int[]{5000, 20000, 50000}) {
            TransactionEngine.Order[] a4 = OrderFactory.deterministicOrders(a3);
            l a5 = new l();
            a5.l(a4, 1000000000L);
            m a6 = a5.l(a4);
            System.out.println("SIZE=" + a3 + " " + String.valueOf(a6));
        }
    }

    private /* synthetic */ CorrectnessRunner() {
        CorrectnessRunner a;
    }
}

