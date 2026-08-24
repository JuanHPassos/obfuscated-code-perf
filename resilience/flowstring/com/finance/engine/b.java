/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.TransactionEngine$Order;
import com.finance.engine.a;
import com.finance.engine.c;
import com.finance.engine.d;
import java.util.HashMap;
import java.util.Map;

public final class b {
    private final Map<String, Long> a = new HashMap<String, Long>();
    public static int b;
    private static final String[] c;
    private static final String[] d;

    public void a(TransactionEngine$Order[] transactionEngine$OrderArray, long l) {
        this.a.clear();
        for (TransactionEngine$Order transactionEngine$Order : transactionEngine$OrderArray) {
            this.a.putIfAbsent(transactionEngine$Order.accountKey, l);
        }
    }

    public d a(TransactionEngine$Order[] transactionEngine$OrderArray) {
        d d2 = new d();
        for (TransactionEngine$Order transactionEngine$Order : transactionEngine$OrderArray) {
            c c2 = this.a(transactionEngine$Order);
            if (c2.a) {
                ++d2.a;
                d2.c += c2.c;
                d2.d += c2.d;
                com.finance.engine.b.a(d2.f, c2.f);
                com.finance.engine.b.a(d2.g, c2.e);
                continue;
            }
            ++d2.b;
            com.finance.engine.b.a(d2.e, c2.b);
        }
        return d2;
    }

    private static void a(Map<String, Long> map, String string) {
        if (string == null) {
            string = com.finance.engine.b.a(7594, 14485);
        }
        map.merge(string, 1L, Long::sum);
    }

    public c a(TransactionEngine$Order transactionEngine$Order) {
        c c2 = new c();
        String string = this.b(transactionEngine$Order);
        if (!com.finance.engine.b.a(7601, -26871).equals(string)) {
            c2.a = false;
            c2.b = string;
            return c2;
        }
        c2.e = this.c(transactionEngine$Order);
        if (com.finance.engine.b.a(7593, 10142).equals(c2.e)) {
            c2.a = false;
            c2.b = com.finance.engine.b.a(7614, -3544);
            return c2;
        }
        long l = transactionEngine$Order.quantity * transactionEngine$Order.priceCents;
        c2.c = this.a(transactionEngine$Order, l, c2.e);
        c2.d = com.finance.engine.b.a(7615, 12424).equals(transactionEngine$Order.side) ? l + c2.c : l - c2.c;
        String string2 = this.a(transactionEngine$Order, c2.d);
        if (!com.finance.engine.b.a(7558, -3207).equals(string2)) {
            c2.a = false;
            c2.b = string2;
            return c2;
        }
        c2.f = this.d(transactionEngine$Order);
        c2.a = true;
        return c2;
    }

    /*
     * Unable to fully structure code
     */
    private String b(TransactionEngine$Order var1_1) {
        block50: {
            block49: {
                block48: {
                    block47: {
                        block46: {
                            block44: {
                                block45: {
                                    block42: {
                                        block43: {
                                            var2_2 = com.finance.engine.b.b;
                                            v0 = var1_1.txnId;
                                            if (var2_2 != 0) break block42;
                                            if (v0 == null) break block43;
                                            v1 = var1_1.txnId.isEmpty();
                                            if (var2_2 != 0) break block44;
                                            if (v1 == 0) break block45;
                                        }
                                        v0 = com.finance.engine.b.a(7584, -18118);
                                    }
                                    return v0;
                                }
                                v1 = (int)var1_1.accountKey.startsWith(com.finance.engine.b.a(7557, 32615));
                            }
                            if (var2_2 == 0) {
                                if (v1 == 0) {
                                    return com.finance.engine.b.a(7562, 23689);
                                }
                                cfr_temp_0 = var1_1.quantity - 0L;
                                v1 = cfr_temp_0 == 0L ? 0 : (cfr_temp_0 < 0L ? -1 : 1);
                            }
                            if (var2_2 == 0) {
                                if (v1 <= 0) {
                                    return com.finance.engine.b.a(7589, -28998);
                                }
                                cfr_temp_1 = var1_1.priceCents - 0L;
                                v1 = cfr_temp_1 == 0L ? 0 : (cfr_temp_1 < 0L ? -1 : 1);
                            }
                            if (var2_2 != 0) break block46;
                            if (v1 <= 0) {
                                return com.finance.engine.b.a(7566, 15826);
                            }
                            v2 = com.finance.engine.b.a(7555, 30375);
                            if (var2_2 != 0) break block47;
                            v1 = (int)v2.equals(var1_1.side);
                        }
                        if (v1 == 0) {
                            v2 = com.finance.engine.b.a(7590, 227);
                            if (var2_2 == 0) {
                                if (!v2.equals(var1_1.side)) {
                                    return com.finance.engine.b.a(7596, -18835);
                                } else {
                                    ** GOTO lbl-1000
                                }
                            }
                        } else lbl-1000:
                        // 3 sources

                        {
                            v2 = var1_1.segment;
                        }
                    }
                    var3_3 = v2;
                    var4_4 = -1;
                    v3 = var3_3.hashCode();
                    if (var2_2 != 0) break block48;
                    switch (v3) {
                        case 2052821701: {
                            v3 = (int)var3_3.equals(com.finance.engine.b.a(7611, 26847));
                            if (var2_2 != 0) break;
                            if (v3 == 0) ** GOTO lbl68
                            var4_4 = 0;
                            if (var2_2 == 0) ** GOTO lbl68
                        }
                        case 2258: {
                            v3 = (int)var3_3.equals(com.finance.engine.b.a(7552, -5558));
                            if (var2_2 != 0) break;
                            if (v3 == 0) ** GOTO lbl68
                            var4_4 = 1;
                            if (var2_2 == 0) ** GOTO lbl68
                        }
                        case 1996612801: {
                            v3 = (int)var3_3.equals(com.finance.engine.b.a(7588, -24229));
                            if (var2_2 != 0) break;
                            if (v3 == 0) ** GOTO lbl68
                            var4_4 = 2;
                            if (var2_2 == 0) ** GOTO lbl68
                        }
                        case 968326388: {
                            v3 = (int)var3_3.equals(com.finance.engine.b.a(7600, 2875));
                            if (var2_2 != 0) break;
                            if (v3 != 0) {
                                var4_4 = 3;
                            }
                        }
lbl68:
                        // 10 sources

                        default: {
                            v3 = var4_4;
                        }
                    }
                }
                if (var2_2 != 0) ** GOTO lbl76
                switch (v3) {
                    case 0: {
                        cfr_temp_2 = var1_1.quantity % 100L - 0L;
                        v3 = cfr_temp_2 == 0L ? 0 : (cfr_temp_2 < 0L ? -1 : 1);
lbl76:
                        // 2 sources

                        if (var2_2 == 0) {
                            if (v3 == 0) break;
                            cfr_temp_3 = var1_1.quantity - 99L;
                            v3 = cfr_temp_3 == 0L ? 0 : (cfr_temp_3 < 0L ? -1 : 1);
                            if (var2_2 == 0) {
                                if (v3 <= 0) break;
                                return com.finance.engine.b.a(7586, 20815);
                            }
                        }
                        break block49;
                    }
                    case 1: {
                        cfr_temp_4 = var1_1.priceCents - 50L;
                        v3 = cfr_temp_4 == 0L ? 0 : (cfr_temp_4 < 0L ? -1 : 1);
                        if (var2_2 == 0) {
                            if (v3 >= 0) break;
                            return com.finance.engine.b.a(7613, 20308);
                        }
                        break block49;
                    }
                    case 2: {
                        cfr_temp_5 = var1_1.quantity - 500L;
                        v3 = cfr_temp_5 == 0L ? 0 : (cfr_temp_5 < 0L ? -1 : 1);
                        if (var2_2 == 0) {
                            if (v3 <= 0) break;
                            return com.finance.engine.b.a(7587, 28545);
                        }
                        break block49;
                    }
                    case 3: {
                        v3 = (int)var1_1.assetCode.startsWith(com.finance.engine.b.a(7598, 26968));
                        if (var2_2 == 0) {
                            if (v3 != 0) break;
                            return com.finance.engine.b.a(7567, 12106);
                        }
                        break block49;
                    }
                    default: {
                        return com.finance.engine.b.a(7592, 26744);
                    }
                }
                var3_3 = com.finance.engine.a.a(var1_1.segment, var1_1.quantity * var1_1.priceCents);
                v4 = com.finance.engine.b.a(7558, -3207);
                if (var2_2 != 0) break block50;
                v3 = (int)v4.equals(var3_3);
            }
            if (v3 == 0) {
                return var3_3;
            }
            v4 = com.finance.engine.b.a(7558, -3207);
        }
        return v4;
    }

    /*
     * Exception decompiling
     */
    private String c(TransactionEngine$Order var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:404)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.access$600(SwitchStringRewriter.java:53)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:368)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:26)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:201)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    private long a(TransactionEngine$Order var1_1, long var2_2, String var4_3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:404)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.access$600(SwitchStringRewriter.java:53)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:368)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:26)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:201)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private String a(TransactionEngine$Order transactionEngine$Order, long l) {
        if (!com.finance.engine.b.a(7555, 30375).equals(transactionEngine$Order.side)) {
            return com.finance.engine.b.a(7558, -3207);
        }
        Long l2 = this.a.get(transactionEngine$Order.accountKey);
        if (l2 == null) {
            return com.finance.engine.b.a(7597, 8961);
        }
        if (l2 < l) {
            return com.finance.engine.b.a(7607, -7164);
        }
        this.a.put(transactionEngine$Order.accountKey, l2 - l);
        return com.finance.engine.b.a(7558, -3207);
    }

    /*
     * Exception decompiling
     */
    private String d(TransactionEngine$Order var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:404)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.access$600(SwitchStringRewriter.java:53)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:368)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:26)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:201)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Unable to fully structure code
     */
    static {
        block20: {
            block19: {
                var5 = new String[45];
                var3_1 = 0;
                var2_2 = "q\u0002(\u00ad\u0003;\u009c\u00f1\u0006\u009a\u000bRe\u00ed\u00aa\u0010;2\"\u00cc\u0015\u00aa\u001d\u00be\u001cE\u00f5\u0098\u0090\u00e3Lg\u0007m><\u0018\u0086\u00eb\u007f\f>\u0007\u00c6\u00e4\u007f\u009aPm\u00d5V\u00df\u00fb\u000e\u00eb78lV\u00beu<\u0014\u0018^\u008e\u0016\u001f\u0006\u001c=H\u00d9#\u0011\u0003\u009a\u00c5\u00e9\f\u0089\u00de\u0094\u00b2\u0004\u00f7;\u00d0f\u00a1\u00e6V\f\u009e\n\u0096\u00cb\u0083J\u00cf\u00a2D\u00da\u00b9\u00ff\u0011\u0012\u00a8\u001b=\u009c\\\u0006\u0087rx\u00d1w\u00b1+9\u00af\u00ef\u0004%\u00c7\u001c\u0007\u0002\u00fc\u00b5\u000f\u0004uG+\u0004\u00a6V\u00e2<\u00de\u00b5\u00faO\u00cb\u0084\u0007\u00b7j\u00c0\u0087t\u008d\u00d6\u0007\u0091\u00ad\u00cc[t\u008c\u0000\u0012\u00af\u0092\u00b5\u00ccH3\u00f0\u007f\u0015\u00f2\u00c0f\u00d3jO\u00af!m\u0007\u00a7\u00baV\t\u00aa\u000f\u00db\u0004R\u00b6?\u00b2\b3\u00dd!5)\u0016B\u00dd\b\u0086\u0006\r\u0097J\u00eeY\u00cb\f\u00cb\u00ae\u00dc\u00bc\r6\u001a\u00e8B\u00a6b\u00b6\u0002\u0017\u00db\f\u008e\u0099\u00e9T\u00da\u00f9\u009d\u00ae\u0017K\u00c0\u008e\u0003\u00dbn[\u0003;$\u0086\f7\fD\u00d3\u00f4\u00c3\u009f{7\u000f\u00f1E\u0006*Z\u0096\u00a3\u00caJ\u0006<\u00ebL\u0003\u00a3J\u0002K\u00e9\u0003e]\u0096\u0002\u00a2z\u0007]\u00a8\u009d\u00df\u00df\u0086\u00a3\u0004\u0007\u00ce\u00e64\u0005\u000e\u00be\u0098\u00e4\u0094\u0007\u00a5$\u0016\u00df\u00b2w\u00ce\u0003\u00ba\u00a6w\u0002\u00d1u\u0004\u00b6\u00a7\u00a3\u0090\r\u00fb\u00cb:\u00f3\u0016M}B\u0011\u00dd}a\u0099\u0011\u001cn!\u00a0B=\n\u001e\u00d1#\u008bRO\f\u008d\u0089T\u0006B\u00b1I2\u008e@";
                var4_3 = "q\u0002(\u00ad\u0003;\u009c\u00f1\u0006\u009a\u000bRe\u00ed\u00aa\u0010;2\"\u00cc\u0015\u00aa\u001d\u00be\u001cE\u00f5\u0098\u0090\u00e3Lg\u0007m><\u0018\u0086\u00eb\u007f\f>\u0007\u00c6\u00e4\u007f\u009aPm\u00d5V\u00df\u00fb\u000e\u00eb78lV\u00beu<\u0014\u0018^\u008e\u0016\u001f\u0006\u001c=H\u00d9#\u0011\u0003\u009a\u00c5\u00e9\f\u0089\u00de\u0094\u00b2\u0004\u00f7;\u00d0f\u00a1\u00e6V\f\u009e\n\u0096\u00cb\u0083J\u00cf\u00a2D\u00da\u00b9\u00ff\u0011\u0012\u00a8\u001b=\u009c\\\u0006\u0087rx\u00d1w\u00b1+9\u00af\u00ef\u0004%\u00c7\u001c\u0007\u0002\u00fc\u00b5\u000f\u0004uG+\u0004\u00a6V\u00e2<\u00de\u00b5\u00faO\u00cb\u0084\u0007\u00b7j\u00c0\u0087t\u008d\u00d6\u0007\u0091\u00ad\u00cc[t\u008c\u0000\u0012\u00af\u0092\u00b5\u00ccH3\u00f0\u007f\u0015\u00f2\u00c0f\u00d3jO\u00af!m\u0007\u00a7\u00baV\t\u00aa\u000f\u00db\u0004R\u00b6?\u00b2\b3\u00dd!5)\u0016B\u00dd\b\u0086\u0006\r\u0097J\u00eeY\u00cb\f\u00cb\u00ae\u00dc\u00bc\r6\u001a\u00e8B\u00a6b\u00b6\u0002\u0017\u00db\f\u008e\u0099\u00e9T\u00da\u00f9\u009d\u00ae\u0017K\u00c0\u008e\u0003\u00dbn[\u0003;$\u0086\f7\fD\u00d3\u00f4\u00c3\u009f{7\u000f\u00f1E\u0006*Z\u0096\u00a3\u00caJ\u0006<\u00ebL\u0003\u00a3J\u0002K\u00e9\u0003e]\u0096\u0002\u00a2z\u0007]\u00a8\u009d\u00df\u00df\u0086\u00a3\u0004\u0007\u00ce\u00e64\u0005\u000e\u00be\u0098\u00e4\u0094\u0007\u00a5$\u0016\u00df\u00b2w\u00ce\u0003\u00ba\u00a6w\u0002\u00d1u\u0004\u00b6\u00a7\u00a3\u0090\r\u00fb\u00cb:\u00f3\u0016M}B\u0011\u00dd}a\u0099\u0011\u001cn!\u00a0B=\n\u001e\u00d1#\u008bRO\f\u008d\u0089T\u0006B\u00b1I2\u008e@".length();
                var1_4 = 4;
                var0_5 = -1;
lbl7:
                // 2 sources

                while (true) {
                    v0 = ++var0_5;
                    v1 = var2_2.substring(v0, v0 + var1_4);
                    v2 = -1;
                    break block19;
                    break;
                }
lbl12:
                // 1 sources

                while (true) {
                    var5[var3_1++] = v3.intern();
                    if ((var0_5 += var1_4) < var4_3) {
                        var1_4 = var2_2.charAt(var0_5);
                        ** continue;
                    }
                    var2_2 = "m4~n\u00d7\u00f3\u000f==\u00e2-\r\u0096\u001e9l\u00a3\u00eb\u0091\u0010\u00e4}";
                    var4_3 = "m4~n\u00d7\u00f3\u000f==\u00e2-\r\u0096\u001e9l\u00a3\u00eb\u0091\u0010\u00e4}".length();
                    var1_4 = 6;
                    var0_5 = -1;
lbl21:
                    // 2 sources

                    while (true) {
                        v4 = ++var0_5;
                        v1 = var2_2.substring(v4, v4 + var1_4);
                        v2 = 0;
                        break block19;
                        break;
                    }
                    break;
                }
lbl26:
                // 1 sources

                while (true) {
                    var5[var3_1++] = v3.intern();
                    if ((var0_5 += var1_4) < var4_3) {
                        var1_4 = var2_2.charAt(var0_5);
                        ** continue;
                    }
                    break block20;
                    break;
                }
            }
            v5 = v1.toCharArray();
            var6_6 = 0;
            v6 = v5.length;
            v7 = v5;
            v8 = v6;
            if (v6 > 1) ** GOTO lbl76
            do {
                v9 = v7;
                v10 = v7;
                v11 = var6_6;
                while (true) {
                    v12 = v9[v11];
                    switch (var6_6 % 7) {
                        case 0: {
                            v13 = 51;
                            break;
                        }
                        case 1: {
                            v13 = 105;
                            break;
                        }
                        case 2: {
                            v13 = 21;
                            break;
                        }
                        case 3: {
                            v13 = 97;
                            break;
                        }
                        case 4: {
                            v13 = 35;
                            break;
                        }
                        case 5: {
                            v13 = 63;
                            break;
                        }
                        default: {
                            v13 = 90;
                        }
                    }
                    v9[v11] = (char)(v12 ^ v13);
                    ++var6_6;
                    v7 = v10;
                    v8 = v8;
                    if (v8 != 0) break;
                    v10 = v7;
                    v14 = v8;
                    v11 = v8;
                    v9 = v7;
                }
lbl76:
                // 2 sources

                v15 = v7;
                v14 = v8;
            } while (v8 > var6_6);
            v3 = new String(v15);
            switch (v2) {
                default: {
                    ** continue;
                }
                ** case 0:
lbl85:
                // 1 sources

                ** continue;
            }
        }
        com.finance.engine.b.c = var5;
        com.finance.engine.b.d = new String[45];
    }

    private static String a(int n, int n2) {
        int n3 = (n ^ 0x1DA6) & 0xFFFF;
        if (d[n3] == null) {
            int n4;
            char[] cArray = c[n3].toCharArray();
            int n5 = switch (cArray[0] & 0xFF) {
                case 0 -> 67;
                case 1 -> 54;
                case 2 -> 65;
                case 3 -> 147;
                case 4 -> 18;
                case 5 -> 218;
                case 6 -> 104;
                case 7 -> 164;
                case 8 -> 121;
                case 9 -> 228;
                case 10 -> 186;
                case 11 -> 205;
                case 12 -> 9;
                case 13 -> 51;
                case 14 -> 66;
                case 15 -> 149;
                case 16 -> 111;
                case 17 -> 96;
                case 18 -> 141;
                case 19 -> 23;
                case 20 -> 87;
                case 21 -> 1;
                case 22 -> 61;
                case 23 -> 226;
                case 24 -> 236;
                case 25 -> 123;
                case 26 -> 150;
                case 27 -> 44;
                case 28 -> 97;
                case 29 -> 24;
                case 30 -> 93;
                case 31 -> 45;
                case 32 -> 248;
                case 33 -> 161;
                case 34 -> 82;
                case 35 -> 99;
                case 36 -> 158;
                case 37 -> 173;
                case 38 -> 109;
                case 39 -> 100;
                case 40 -> 255;
                case 41 -> 181;
                case 42 -> 60;
                case 43 -> 254;
                case 44 -> 221;
                case 45 -> 214;
                case 46 -> 243;
                case 47 -> 225;
                case 48 -> 107;
                case 49 -> 152;
                case 50 -> 5;
                case 51 -> 56;
                case 52 -> 15;
                case 53 -> 135;
                case 54 -> 240;
                case 55 -> 22;
                case 56 -> 43;
                case 57 -> 2;
                case 58 -> 58;
                case 59 -> 35;
                case 60 -> 202;
                case 61 -> 235;
                case 62 -> 39;
                case 63 -> 118;
                case 64 -> 46;
                case 65 -> 154;
                case 66 -> 210;
                case 67 -> 115;
                case 68 -> 10;
                case 69 -> 172;
                case 70 -> 128;
                case 71 -> 182;
                case 72 -> 153;
                case 73 -> 229;
                case 74 -> 30;
                case 75 -> 105;
                case 76 -> 72;
                case 77 -> 114;
                case 78 -> 95;
                case 79 -> 17;
                case 80 -> 131;
                case 81 -> 48;
                case 82 -> 52;
                case 83 -> 249;
                case 84 -> 199;
                case 85 -> 241;
                case 86 -> 102;
                case 87 -> 188;
                case 88 -> 116;
                case 89 -> 138;
                case 90 -> 120;
                case 91 -> 239;
                case 92 -> 234;
                case 93 -> 197;
                case 94 -> 62;
                case 95 -> 156;
                case 96 -> 165;
                case 97 -> 124;
                case 98 -> 90;
                case 99 -> 68;
                case 100 -> 136;
                case 101 -> 55;
                case 102 -> 230;
                case 103 -> 184;
                case 104 -> 32;
                case 105 -> 50;
                case 106 -> 231;
                case 107 -> 143;
                case 108 -> 223;
                case 109 -> 167;
                case 110 -> 64;
                case 111 -> 168;
                case 112 -> 145;
                case 113 -> 12;
                case 114 -> 36;
                case 115 -> 122;
                case 116 -> 63;
                case 117 -> 212;
                case 118 -> 203;
                case 119 -> 129;
                case 120 -> 174;
                case 121 -> 163;
                case 122 -> 238;
                case 123 -> 162;
                case 124 -> 148;
                case 125 -> 198;
                case 126 -> 84;
                case 127 -> 227;
                case 128 -> 69;
                case 129 -> 92;
                case 130 -> 29;
                case 131 -> 215;
                case 132 -> 216;
                case 133 -> 42;
                case 134 -> 103;
                case 135 -> 209;
                case 136 -> 11;
                case 137 -> 220;
                case 138 -> 83;
                case 139 -> 88;
                case 140 -> 146;
                case 141 -> 219;
                case 142 -> 79;
                case 143 -> 204;
                case 144 -> 34;
                case 145 -> 155;
                case 146 -> 134;
                case 147 -> 179;
                case 148 -> 140;
                case 149 -> 21;
                case 150 -> 38;
                case 151 -> 201;
                case 152 -> 106;
                case 153 -> 94;
                case 154 -> 33;
                case 155 -> 3;
                case 156 -> 47;
                case 157 -> 4;
                case 158 -> 170;
                case 159 -> 183;
                case 160 -> 70;
                case 161 -> 242;
                case 162 -> 127;
                case 163 -> 144;
                case 164 -> 101;
                case 165 -> 98;
                case 166 -> 160;
                case 167 -> 73;
                case 168 -> 0;
                case 169 -> 113;
                case 170 -> 6;
                case 171 -> 108;
                case 172 -> 233;
                case 173 -> 137;
                case 174 -> 117;
                case 175 -> 251;
                case 176 -> 194;
                case 177 -> 89;
                case 178 -> 27;
                case 179 -> 200;
                case 180 -> 191;
                case 181 -> 40;
                case 182 -> 151;
                case 183 -> 217;
                case 184 -> 176;
                case 185 -> 53;
                case 186 -> 224;
                case 187 -> 85;
                case 188 -> 112;
                case 189 -> 57;
                case 190 -> 133;
                case 191 -> 75;
                case 192 -> 25;
                case 193 -> 119;
                case 194 -> 192;
                case 195 -> 59;
                case 196 -> 246;
                case 197 -> 157;
                case 198 -> 74;
                case 199 -> 159;
                case 200 -> 81;
                case 201 -> 132;
                case 202 -> 71;
                case 203 -> 175;
                case 204 -> 49;
                case 205 -> 76;
                case 206 -> 206;
                case 207 -> 232;
                case 208 -> 178;
                case 209 -> 213;
                case 210 -> 190;
                case 211 -> 185;
                case 212 -> 16;
                case 213 -> 7;
                case 214 -> 211;
                case 215 -> 247;
                case 216 -> 169;
                case 217 -> 189;
                case 218 -> 177;
                case 219 -> 20;
                case 220 -> 208;
                case 221 -> 139;
                case 222 -> 237;
                case 223 -> 41;
                case 224 -> 207;
                case 225 -> 195;
                case 226 -> 166;
                case 227 -> 187;
                case 228 -> 80;
                case 229 -> 250;
                case 230 -> 28;
                case 231 -> 180;
                case 232 -> 222;
                case 233 -> 252;
                case 234 -> 31;
                case 235 -> 13;
                case 236 -> 196;
                case 237 -> 19;
                case 238 -> 126;
                case 239 -> 37;
                case 240 -> 130;
                case 241 -> 193;
                case 242 -> 14;
                case 243 -> 91;
                case 244 -> 8;
                case 245 -> 86;
                case 246 -> 78;
                case 247 -> 26;
                case 248 -> 125;
                case 249 -> 245;
                case 250 -> 77;
                case 251 -> 253;
                case 252 -> 110;
                case 253 -> 244;
                case 254 -> 171;
                default -> 142;
            };
            int n6 = (n2 & 0xFF) - n5;
            if (n6 < 0) {
                n6 += 256;
            }
            if ((n4 = ((n2 & 0xFFFF) >>> 8) - n5) < 0) {
                n4 += 256;
            }
            int n7 = 0;
            while (n7 < cArray.length) {
                int n8 = n7 % 2;
                int n9 = n7;
                char[] cArray2 = cArray;
                char c2 = cArray[n9];
                if (n8 == 0) {
                    cArray2[n9] = (char)(c2 ^ n6);
                    n6 = ((n6 >>> 3 | n6 << 5) ^ cArray[n7]) & 0xFF;
                } else {
                    cArray2[n9] = (char)(c2 ^ n4);
                    n4 = ((n4 >>> 3 | n4 << 5) ^ cArray[n7]) & 0xFF;
                }
                ++n7;
            }
            com.finance.engine.b.d[n3] = new String(cArray).intern();
        }
        return d[n3];
    }
}

