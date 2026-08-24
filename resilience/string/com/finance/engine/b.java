/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.TransactionEngine$Order;
import com.finance.engine.c;
import com.finance.engine.d;
import java.util.HashMap;
import java.util.Map;

public final class b {
    private final Map<String, Long> a = new HashMap<String, Long>();
    private static final String[] b;
    private static final String[] c;

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
            string = com.finance.engine.b.a(-27015, 32464);
        }
        map.merge(string, 1L, Long::sum);
    }

    public c a(TransactionEngine$Order transactionEngine$Order) {
        c c2 = new c();
        String string = this.b(transactionEngine$Order);
        if (!com.finance.engine.b.a(-27059, -3301).equals(string)) {
            c2.a = false;
            c2.b = string;
            return c2;
        }
        c2.e = this.c(transactionEngine$Order);
        if (com.finance.engine.b.a(-27012, 14546).equals(c2.e)) {
            c2.a = false;
            c2.b = com.finance.engine.b.a(-27014, -10005);
            return c2;
        }
        long l = transactionEngine$Order.quantity * transactionEngine$Order.priceCents;
        c2.c = this.a(transactionEngine$Order, l, c2.e);
        c2.d = com.finance.engine.b.a(-27042, -21598).equals(transactionEngine$Order.side) ? l + c2.c : l - c2.c;
        String string2 = this.a(transactionEngine$Order, c2.d);
        if (!com.finance.engine.b.a(-27018, -7746).equals(string2)) {
            c2.a = false;
            c2.b = string2;
            return c2;
        }
        c2.f = this.d(transactionEngine$Order);
        c2.a = true;
        return c2;
    }

    /*
     * Exception decompiling
     */
    private String b(TransactionEngine$Order var1_1) {
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
        if (!com.finance.engine.b.a(-27050, -2638).equals(transactionEngine$Order.side)) {
            return com.finance.engine.b.a(-27018, -7746);
        }
        Long l2 = this.a.get(transactionEngine$Order.accountKey);
        if (l2 == null) {
            return com.finance.engine.b.a(-27054, 9510);
        }
        if (l2 < l) {
            return com.finance.engine.b.a(-27057, -1865);
        }
        this.a.put(transactionEngine$Order.accountKey, l2 - l);
        return com.finance.engine.b.a(-27018, -7746);
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
                var2_2 = "'\u00e4\u0086\u008f\u009a\u00ad\u0003c\u0002\u00e5\u0007x\u00a1X\u00b7\u0086g\u0090\bz\u00ea\u00a1\u00beY\u0011\u0082\u00d1\u0006\u0090\u00b2pED\u00f4\u0007\u0012\u00d3II)qe\u0005\u001c\u0017s\u00bc\u0089\r\u008d{]\u0088\u009aT\"\u008d5\u008d\u0094\u000b\u00d2\u0006\u00dc\u00dc3\u00f2\u00a1.\u0003\u00c5\u00ea1\u0004B\u00f7\u00e7~\u0006\u00c8\u00f9c\u00ba\u0014\u001f\u0003\u00ad\u00b5\u00fd\u0011\u000fs\u0011+\u00bd\u00e8\u00ec\u00fd\u00e0\u009e\u00ee\u00cb r\u00e7K\u009d\u0006\u0087\u00f9\u008a\u00ba)\u001f\u0002\u00f1\u00db\u0012:\u0083\u00ae\u0083K\u00ac^`\u000f\u00b8\u00eeO`\u0081\u00d5\u001d\u00dbV\u0003\u00b6\u00e0\u00c5\u00026%\u0003$m\u0017\u0007pJ6\u00fb\u00a7\u00e1\u0095\u0002F,\u0010ev@)9`V\u000b\u00baZL\u001b\u00d1]\u0088\u007f\u0003*\u009bz\u000f\u0002z\u00ac\u00a8\u00a4P\u00e5\r\u00dc\u008c\u0090\u0014\t\u009a\u00b2\u0007\u00ca\u0006R\u00f6Z$\u001d\u0002\u00c3\r\u0004\u0092\u00ac\u00fd\u0015\u0006\u001d\u00da\u000b2\u00a66\u0004\u00c3P\u00a4\u0003\u0004?\u0004;\u0089\bD\u00fb\u00fcE\u0014\u00c2\\\u00a2\f\u00101\u000e\"7\u0093\u0093\u00b0\u00bc\u0004\u00d0\u0082\f\u008e\n=\u00a6\u0096\u0091\u00a35\u0006\u0081\u009c\u00f4\f|\u00e7\u0083\u00f8\u0086\u00c8\u00a5\u00dbzi\b/\u0007^\u0089T\u0096\u0086\u00d9F\u000f$^\u00ea#\u00d1\u00d1\"\u00e0}7\u00f0\u00a7\u0091\u00ae\u00b3\fh\u00ff\u009c\u00f5\u0014\u00bb\u008a\n:v\bI\u0004\u00a3\u0099e\u00a1\u00117\u0080\u00ed\u0010\u00bb]\u00fb\u00fe \u0096\u00d8\u0084S\u0018\u00e2\u00c4\u0010\fr\u00a9\u00e6\\\u001b\u00fb\u0012\u00adR\u00e7B\u00ad\u0002\u00a5\u0003\u000e\u00ae\u00979\u0015\u0016\u00e7\u00b3\u00fb\u0003I\u00d1\u00c4\u0091\u0098";
                var4_3 = "'\u00e4\u0086\u008f\u009a\u00ad\u0003c\u0002\u00e5\u0007x\u00a1X\u00b7\u0086g\u0090\bz\u00ea\u00a1\u00beY\u0011\u0082\u00d1\u0006\u0090\u00b2pED\u00f4\u0007\u0012\u00d3II)qe\u0005\u001c\u0017s\u00bc\u0089\r\u008d{]\u0088\u009aT\"\u008d5\u008d\u0094\u000b\u00d2\u0006\u00dc\u00dc3\u00f2\u00a1.\u0003\u00c5\u00ea1\u0004B\u00f7\u00e7~\u0006\u00c8\u00f9c\u00ba\u0014\u001f\u0003\u00ad\u00b5\u00fd\u0011\u000fs\u0011+\u00bd\u00e8\u00ec\u00fd\u00e0\u009e\u00ee\u00cb r\u00e7K\u009d\u0006\u0087\u00f9\u008a\u00ba)\u001f\u0002\u00f1\u00db\u0012:\u0083\u00ae\u0083K\u00ac^`\u000f\u00b8\u00eeO`\u0081\u00d5\u001d\u00dbV\u0003\u00b6\u00e0\u00c5\u00026%\u0003$m\u0017\u0007pJ6\u00fb\u00a7\u00e1\u0095\u0002F,\u0010ev@)9`V\u000b\u00baZL\u001b\u00d1]\u0088\u007f\u0003*\u009bz\u000f\u0002z\u00ac\u00a8\u00a4P\u00e5\r\u00dc\u008c\u0090\u0014\t\u009a\u00b2\u0007\u00ca\u0006R\u00f6Z$\u001d\u0002\u00c3\r\u0004\u0092\u00ac\u00fd\u0015\u0006\u001d\u00da\u000b2\u00a66\u0004\u00c3P\u00a4\u0003\u0004?\u0004;\u0089\bD\u00fb\u00fcE\u0014\u00c2\\\u00a2\f\u00101\u000e\"7\u0093\u0093\u00b0\u00bc\u0004\u00d0\u0082\f\u008e\n=\u00a6\u0096\u0091\u00a35\u0006\u0081\u009c\u00f4\f|\u00e7\u0083\u00f8\u0086\u00c8\u00a5\u00dbzi\b/\u0007^\u0089T\u0096\u0086\u00d9F\u000f$^\u00ea#\u00d1\u00d1\"\u00e0}7\u00f0\u00a7\u0091\u00ae\u00b3\fh\u00ff\u009c\u00f5\u0014\u00bb\u008a\n:v\bI\u0004\u00a3\u0099e\u00a1\u00117\u0080\u00ed\u0010\u00bb]\u00fb\u00fe \u0096\u00d8\u0084S\u0018\u00e2\u00c4\u0010\fr\u00a9\u00e6\\\u001b\u00fb\u0012\u00adR\u00e7B\u00ad\u0002\u00a5\u0003\u000e\u00ae\u00979\u0015\u0016\u00e7\u00b3\u00fb\u0003I\u00d1\u00c4\u0091\u0098".length();
                var1_4 = 6;
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
                    var2_2 = "\u00ce\u0010\u00d2=Fh\u0007\u00e2\u00eb\u0095\u0007v\u0007\u0007\u00e7\u007f[\u00e3`\u00ea";
                    var4_3 = "\u00ce\u0010\u00d2=Fh\u0007\u00e2\u00eb\u0095\u0007v\u0007\u0007\u00e7\u007f[\u00e3`\u00ea".length();
                    var1_4 = 12;
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
                            v13 = 53;
                            break;
                        }
                        case 1: {
                            v13 = 74;
                            break;
                        }
                        case 2: {
                            v13 = 124;
                            break;
                        }
                        case 3: {
                            v13 = 104;
                            break;
                        }
                        case 4: {
                            v13 = 110;
                            break;
                        }
                        case 5: {
                            v13 = 104;
                            break;
                        }
                        default: {
                            v13 = 61;
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
        com.finance.engine.b.b = var5;
        com.finance.engine.b.c = new String[45];
    }

    private static String a(int n, int n2) {
        int n3 = (n ^ 0xFFFF965F) & 0xFFFF;
        if (c[n3] == null) {
            int n4;
            char[] cArray = b[n3].toCharArray();
            int n5 = switch (cArray[0] & 0xFF) {
                case 0 -> 208;
                case 1 -> 124;
                case 2 -> 125;
                case 3 -> 207;
                case 4 -> 48;
                case 5 -> 225;
                case 6 -> 129;
                case 7 -> 252;
                case 8 -> 197;
                case 9 -> 250;
                case 10 -> 37;
                case 11 -> 27;
                case 12 -> 178;
                case 13 -> 64;
                case 14 -> 233;
                case 15 -> 113;
                case 16 -> 184;
                case 17 -> 194;
                case 18 -> 205;
                case 19 -> 59;
                case 20 -> 185;
                case 21 -> 200;
                case 22 -> 132;
                case 23 -> 136;
                case 24 -> 112;
                case 25 -> 240;
                case 26 -> 179;
                case 27 -> 58;
                case 28 -> 145;
                case 29 -> 167;
                case 30 -> 108;
                case 31 -> 159;
                case 32 -> 47;
                case 33 -> 231;
                case 34 -> 38;
                case 35 -> 128;
                case 36 -> 115;
                case 37 -> 133;
                case 38 -> 121;
                case 39 -> 149;
                case 40 -> 43;
                case 41 -> 215;
                case 42 -> 14;
                case 43 -> 173;
                case 44 -> 10;
                case 45 -> 131;
                case 46 -> 163;
                case 47 -> 12;
                case 48 -> 2;
                case 49 -> 102;
                case 50 -> 73;
                case 51 -> 23;
                case 52 -> 111;
                case 53 -> 237;
                case 54 -> 110;
                case 55 -> 216;
                case 56 -> 206;
                case 57 -> 126;
                case 58 -> 171;
                case 59 -> 181;
                case 60 -> 193;
                case 61 -> 166;
                case 62 -> 195;
                case 63 -> 123;
                case 64 -> 218;
                case 65 -> 29;
                case 66 -> 118;
                case 67 -> 239;
                case 68 -> 161;
                case 69 -> 18;
                case 70 -> 235;
                case 71 -> 67;
                case 72 -> 210;
                case 73 -> 168;
                case 74 -> 122;
                case 75 -> 36;
                case 76 -> 219;
                case 77 -> 41;
                case 78 -> 182;
                case 79 -> 100;
                case 80 -> 97;
                case 81 -> 85;
                case 82 -> 33;
                case 83 -> 96;
                case 84 -> 19;
                case 85 -> 175;
                case 86 -> 142;
                case 87 -> 80;
                case 88 -> 52;
                case 89 -> 236;
                case 90 -> 211;
                case 91 -> 87;
                case 92 -> 188;
                case 93 -> 220;
                case 94 -> 202;
                case 95 -> 84;
                case 96 -> 201;
                case 97 -> 72;
                case 98 -> 105;
                case 99 -> 106;
                case 100 -> 56;
                case 101 -> 213;
                case 102 -> 203;
                case 103 -> 192;
                case 104 -> 75;
                case 105 -> 199;
                case 106 -> 24;
                case 107 -> 169;
                case 108 -> 186;
                case 109 -> 17;
                case 110 -> 49;
                case 111 -> 83;
                case 112 -> 42;
                case 113 -> 114;
                case 114 -> 65;
                case 115 -> 143;
                case 116 -> 196;
                case 117 -> 98;
                case 118 -> 156;
                case 119 -> 144;
                case 120 -> 15;
                case 121 -> 32;
                case 122 -> 94;
                case 123 -> 116;
                case 124 -> 120;
                case 125 -> 107;
                case 126 -> 35;
                case 127 -> 95;
                case 128 -> 90;
                case 129 -> 92;
                case 130 -> 174;
                case 131 -> 86;
                case 132 -> 21;
                case 133 -> 226;
                case 134 -> 28;
                case 135 -> 255;
                case 136 -> 11;
                case 137 -> 134;
                case 138 -> 224;
                case 139 -> 204;
                case 140 -> 62;
                case 141 -> 183;
                case 142 -> 247;
                case 143 -> 88;
                case 144 -> 223;
                case 145 -> 164;
                case 146 -> 8;
                case 147 -> 198;
                case 148 -> 234;
                case 149 -> 242;
                case 150 -> 248;
                case 151 -> 146;
                case 152 -> 140;
                case 153 -> 162;
                case 154 -> 165;
                case 155 -> 82;
                case 156 -> 150;
                case 157 -> 77;
                case 158 -> 139;
                case 159 -> 155;
                case 160 -> 241;
                case 161 -> 68;
                case 162 -> 148;
                case 163 -> 4;
                case 164 -> 147;
                case 165 -> 51;
                case 166 -> 93;
                case 167 -> 53;
                case 168 -> 158;
                case 169 -> 187;
                case 170 -> 238;
                case 171 -> 154;
                case 172 -> 228;
                case 173 -> 212;
                case 174 -> 34;
                case 175 -> 46;
                case 176 -> 63;
                case 177 -> 99;
                case 178 -> 16;
                case 179 -> 22;
                case 180 -> 180;
                case 181 -> 160;
                case 182 -> 25;
                case 183 -> 3;
                case 184 -> 69;
                case 185 -> 153;
                case 186 -> 91;
                case 187 -> 74;
                case 188 -> 245;
                case 189 -> 243;
                case 190 -> 151;
                case 191 -> 6;
                case 192 -> 104;
                case 193 -> 76;
                case 194 -> 189;
                case 195 -> 54;
                case 196 -> 40;
                case 197 -> 138;
                case 198 -> 60;
                case 199 -> 66;
                case 200 -> 172;
                case 201 -> 251;
                case 202 -> 117;
                case 203 -> 7;
                case 204 -> 61;
                case 205 -> 109;
                case 206 -> 170;
                case 207 -> 30;
                case 208 -> 5;
                case 209 -> 9;
                case 210 -> 246;
                case 211 -> 26;
                case 212 -> 44;
                case 213 -> 232;
                case 214 -> 45;
                case 215 -> 177;
                case 216 -> 103;
                case 217 -> 39;
                case 218 -> 119;
                case 219 -> 79;
                case 220 -> 191;
                case 221 -> 157;
                case 222 -> 244;
                case 223 -> 55;
                case 224 -> 214;
                case 225 -> 127;
                case 226 -> 254;
                case 227 -> 81;
                case 228 -> 137;
                case 229 -> 227;
                case 230 -> 50;
                case 231 -> 152;
                case 232 -> 217;
                case 233 -> 253;
                case 234 -> 57;
                case 235 -> 209;
                case 236 -> 89;
                case 237 -> 176;
                case 238 -> 130;
                case 239 -> 229;
                case 240 -> 0;
                case 241 -> 71;
                case 242 -> 78;
                case 243 -> 13;
                case 244 -> 230;
                case 245 -> 101;
                case 246 -> 222;
                case 247 -> 135;
                case 248 -> 190;
                case 249 -> 221;
                case 250 -> 70;
                case 251 -> 1;
                case 252 -> 31;
                case 253 -> 141;
                case 254 -> 20;
                default -> 249;
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
            com.finance.engine.b.c[n3] = new String(cArray).intern();
        }
        return c[n3];
    }
}

