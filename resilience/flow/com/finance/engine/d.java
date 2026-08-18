/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.b;
import java.util.Iterator;
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
    public static int h;

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
        Iterator<Map.Entry<String, Long>> iterator = map.entrySet().iterator();
        int n = com.finance.engine.b.b;
        while (iterator.hasNext()) {
            block3: {
                Map.Entry<String, Long> entry = iterator.next();
                for (int i = 0; i < entry.getKey().length(); ++i) {
                    l = 31L * l + (long)entry.getKey().charAt(i);
                    if (n == 0) {
                        if (n == 0) continue;
                    }
                    break block3;
                }
                l = 31L * l + entry.getValue();
            }
            if (n == 0) continue;
        }
        return l;
    }

    public String toString() {
        return "accepted=" + this.a + " rejected=" + this.b + " totalFee=" + this.c + " totalNetSettled=" + this.d + " reject=" + String.valueOf(this.e) + " venue=" + String.valueOf(this.f) + " risk=" + String.valueOf(this.g) + " hash=" + this.a();
    }
}

