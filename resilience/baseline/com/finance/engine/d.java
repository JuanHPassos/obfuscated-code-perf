/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import java.util.Map;
import java.util.TreeMap;

public final class d {
    public long a;
    public long b;
    public long c;
    public long d;
    public final Map<String, Long> e = new TreeMap<String, Long>();
    public final Map<String, Long> f = new TreeMap<String, Long>();
    public final Map<String, Long> g = new TreeMap<String, Long>();

    public long a() {
        long l = 1125899906842597L;
        l = 31L * l + this.a;
        l = 31L * l + this.b;
        l = 31L * l + this.c;
        l = 31L * l + this.d;
        l = com.finance.engine.d.a(l, this.e);
        l = com.finance.engine.d.a(l, this.f);
        l = com.finance.engine.d.a(l, this.g);
        return l;
    }

    private static long a(long l, Map<String, Long> map) {
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            for (int i = 0; i < entry.getKey().length(); ++i) {
                l = 31L * l + (long)entry.getKey().charAt(i);
            }
            l = 31L * l + entry.getValue();
        }
        return l;
    }

    public String toString() {
        return "accepted=" + this.a + " rejected=" + this.b + " totalFee=" + this.c + " totalNetSettled=" + this.d + " reject=" + String.valueOf(this.e) + " venue=" + String.valueOf(this.f) + " risk=" + String.valueOf(this.g) + " hash=" + this.a();
    }
}

