/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.A;
import com.finance.engine.TransactionEngine$Order;
import com.finance.engine.d;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.geometry.euclidean.threed.SubLine;
import org.apache.commons.math3.stat.inference.OneWayAnova;

public final class l {
    private final Map<String, Long> K = new HashMap<String, Long>();
    private static final String[] A;
    private static final String[] D;

    /*
     * Exception decompiling
     */
    private /* synthetic */ long C(TransactionEngine$Order a, long a, String a) {
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
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static /* synthetic */ String C(int a, int a2) {
        int n = (a ^ 0xFFFF9A3A) & 0xFFFF;
        if (A[n] == null) {
            int n2;
            char[] cArray = D[n].toCharArray();
            int n3 = switch (cArray[0] & 0xFF) {
                case 0 -> 251;
                case 1 -> 63;
                case 2 -> 55;
                case 3 -> 179;
                case 4 -> 0;
                case 5 -> 204;
                case 6 -> 190;
                case 7 -> 30;
                case 8 -> 37;
                case 9 -> 24;
                case 10 -> 213;
                case 11 -> 235;
                case 12 -> 193;
                case 13 -> 189;
                case 14 -> 29;
                case 15 -> 243;
                case 16 -> 121;
                case 17 -> 97;
                case 18 -> 214;
                case 19 -> 156;
                case 20 -> 75;
                case 21 -> 242;
                case 22 -> 103;
                case 23 -> 104;
                case 24 -> 112;
                case 25 -> 181;
                case 26 -> 244;
                case 27 -> 126;
                case 28 -> 196;
                case 29 -> 92;
                case 30 -> 76;
                case 31 -> 118;
                case 32 -> 152;
                case 33 -> 139;
                case 34 -> 61;
                case 35 -> 171;
                case 36 -> 160;
                case 37 -> 11;
                case 38 -> 42;
                case 39 -> 247;
                case 40 -> 15;
                case 41 -> 106;
                case 42 -> 151;
                case 43 -> 117;
                case 44 -> 252;
                case 45 -> 226;
                case 46 -> 246;
                case 47 -> 6;
                case 48 -> 8;
                case 49 -> 174;
                case 50 -> 220;
                case 51 -> 138;
                case 52 -> 51;
                case 53 -> 146;
                case 54 -> 161;
                case 55 -> 169;
                case 56 -> 147;
                case 57 -> 208;
                case 58 -> 155;
                case 59 -> 148;
                case 60 -> 172;
                case 61 -> 13;
                case 62 -> 167;
                case 63 -> 133;
                case 64 -> 255;
                case 65 -> 143;
                case 66 -> 141;
                case 67 -> 21;
                case 68 -> 140;
                case 69 -> 209;
                case 70 -> 52;
                case 71 -> 215;
                case 72 -> 18;
                case 73 -> 87;
                case 74 -> 185;
                case 75 -> 248;
                case 76 -> 175;
                case 77 -> 192;
                case 78 -> 88;
                case 79 -> 203;
                case 80 -> 222;
                case 81 -> 28;
                case 82 -> 27;
                case 83 -> 36;
                case 84 -> 153;
                case 85 -> 62;
                case 86 -> 45;
                case 87 -> 99;
                case 88 -> 84;
                case 89 -> 162;
                case 90 -> 20;
                case 91 -> 188;
                case 92 -> 144;
                case 93 -> 128;
                case 94 -> 197;
                case 95 -> 107;
                case 96 -> 38;
                case 97 -> 44;
                case 98 -> 177;
                case 99 -> 159;
                case 100 -> 228;
                case 101 -> 149;
                case 102 -> 74;
                case 103 -> 101;
                case 104 -> 194;
                case 105 -> 136;
                case 106 -> 5;
                case 107 -> 16;
                case 108 -> 66;
                case 109 -> 79;
                case 110 -> 168;
                case 111 -> 173;
                case 112 -> 254;
                case 113 -> 46;
                case 114 -> 164;
                case 115 -> 67;
                case 116 -> 50;
                case 117 -> 210;
                case 118 -> 178;
                case 119 -> 68;
                case 120 -> 114;
                case 121 -> 119;
                case 122 -> 33;
                case 123 -> 57;
                case 124 -> 89;
                case 125 -> 225;
                case 126 -> 115;
                case 127 -> 142;
                case 128 -> 233;
                case 129 -> 35;
                case 130 -> 31;
                case 131 -> 145;
                case 132 -> 113;
                case 133 -> 102;
                case 134 -> 41;
                case 135 -> 43;
                case 136 -> 239;
                case 137 -> 100;
                case 138 -> 229;
                case 139 -> 105;
                case 140 -> 14;
                case 141 -> 83;
                case 142 -> 200;
                case 143 -> 182;
                case 144 -> 95;
                case 145 -> 7;
                case 146 -> 2;
                case 147 -> 163;
                case 148 -> 137;
                case 149 -> 206;
                case 150 -> 245;
                case 151 -> 180;
                case 152 -> 26;
                case 153 -> 183;
                case 154 -> 237;
                case 155 -> 93;
                case 156 -> 201;
                case 157 -> 69;
                case 158 -> 58;
                case 159 -> 1;
                case 160 -> 122;
                case 161 -> 60;
                case 162 -> 81;
                case 163 -> 186;
                case 164 -> 191;
                case 165 -> 123;
                case 166 -> 70;
                case 167 -> 80;
                case 168 -> 199;
                case 169 -> 39;
                case 170 -> 47;
                case 171 -> 108;
                case 172 -> 64;
                case 173 -> 56;
                case 174 -> 78;
                case 175 -> 131;
                case 176 -> 195;
                case 177 -> 234;
                case 178 -> 91;
                case 179 -> 218;
                case 180 -> 48;
                case 181 -> 40;
                case 182 -> 82;
                case 183 -> 96;
                case 184 -> 129;
                case 185 -> 223;
                case 186 -> 59;
                case 187 -> 86;
                case 188 -> 221;
                case 189 -> 111;
                case 190 -> 110;
                case 191 -> 125;
                case 192 -> 85;
                case 193 -> 202;
                case 194 -> 240;
                case 195 -> 98;
                case 196 -> 238;
                case 197 -> 249;
                case 198 -> 184;
                case 199 -> 12;
                case 200 -> 73;
                case 201 -> 9;
                case 202 -> 77;
                case 203 -> 127;
                case 204 -> 120;
                case 205 -> 211;
                case 206 -> 34;
                case 207 -> 53;
                case 208 -> 253;
                case 209 -> 49;
                case 210 -> 25;
                case 211 -> 4;
                case 212 -> 170;
                case 213 -> 232;
                case 214 -> 250;
                case 215 -> 19;
                case 216 -> 90;
                case 217 -> 3;
                case 218 -> 216;
                case 219 -> 231;
                case 220 -> 109;
                case 221 -> 116;
                case 222 -> 72;
                case 223 -> 22;
                case 224 -> 227;
                case 225 -> 230;
                case 226 -> 157;
                case 227 -> 158;
                case 228 -> 124;
                case 229 -> 135;
                case 230 -> 187;
                case 231 -> 176;
                case 232 -> 207;
                case 233 -> 130;
                case 234 -> 65;
                case 235 -> 17;
                case 236 -> 94;
                case 237 -> 134;
                case 238 -> 71;
                case 239 -> 236;
                case 240 -> 165;
                case 241 -> 212;
                case 242 -> 23;
                case 243 -> 32;
                case 244 -> 150;
                case 245 -> 132;
                case 246 -> 10;
                case 247 -> 54;
                case 248 -> 219;
                case 249 -> 241;
                case 250 -> 205;
                case 251 -> 154;
                case 252 -> 166;
                case 253 -> 224;
                case 254 -> 217;
                default -> 198;
            };
            int n4 = (a2 & 0xFF) - n3;
            if (n4 < 0) {
                n4 += 256;
            }
            if ((n2 = ((a2 & 0xFFFF) >>> 8) - n3) < 0) {
                n2 += 256;
            }
            int n5 = 0;
            while (n5 < cArray.length) {
                int n6 = n5 % 2;
                int n7 = n5;
                char[] cArray2 = cArray;
                char c = cArray[n7];
                if (n6 == 0) {
                    cArray2[n7] = (char)(c ^ n4);
                    n4 = ((n4 >>> 3 | n4 << 5) ^ cArray[n5]) & 0xFF;
                } else {
                    cArray2[n7] = (char)(c ^ n2);
                    n2 = ((n2 >>> 3 | n2 << 5) ^ cArray[n5]) & 0xFF;
                }
                ++n5;
            }
            l.A[n] = new String(cArray).intern();
        }
        return A[n];
    }

    /*
     * Exception decompiling
     */
    private /* synthetic */ String H(TransactionEngine$Order a) {
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
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static /* synthetic */ void C(Map<String, Long> a, String a2) {
        if (a2 == null) {
            a2 = l.C(-26087, -14517);
        }
        a.merge(a2, 1L, Long::sum);
    }

    /*
     * Exception decompiling
     */
    private /* synthetic */ String D(TransactionEngine$Order a) {
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
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
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
                var2_2 = OneWayAnova.C("\u00e9\u00f2X\u00bb\u00f3|.\u00f5\u00ab9\u00efp6T$g\u009f@\u00e9\u00e9\u0089\u00f6Tw3\u00df\u00df\u00d2\u0091\u00b1Tn\u00d9\u001d\u00d1f\u0014\u0089&\u00afR\u00e2\u00fee\u008a\u0010\r\u009f\u00a9\u0012Xo\u00fb#\u0098\u009d\u00a7\u0086\u00a8\u00c3\u0014;\u00da,\u001fsx\u00d3\u000fdb\u00a3\u00ed5\u0092\u0090\u00e4\u00b5\u00d0g\u00f2/\u0019a\u0004Z\u0018m\u009fP\u0014\u00f36K\u009azC\u001d\t\u00d7>\u00bb4n\u0018\u00eb\u00a0\nVA\u0096\u00ee\u00b2\u00f04\u00beA\u0000(\u008f*\u009f\u00cc\u00f1\u0019G\t\u0004\u00bc\r\u00b1VC\u00d4@w\u00c1}\u00b5o\u00f7\u00c5\u0099\u00cc\u0003\u009c\u00b5\u00bb\u00a5\u00d1\u0010a\u00a4\u00e8\u00a0g\u0082x\u00abO\u0017e/Q\u00e5\u0002)\u00ca^d\u00bc\u000b\u00e6\u00fa!g\\*.\u00ab\u00a5\u0003\u00b51\u00abIIx+d\u0098\u009bz~\u0080\u00e3RY\u0007n\u0089r\u00d6\u00b7n\u00c3\u0091k050\u0093X\u009d\u009f\"z\u00d8\u008d\u00eb\"m\u00ce\u0095\u00bcD\u009eM\u00e6g\u0095\u00b8\u0087\u00a7{\u00f7\u008ed'\u009cG\u00e4\u00aa\u009cW\u001ai\u00a3\u00e0\u0089\u0013\u00fe\\\u00c6\u00eb\u00ac(x\u00ce\u0099V<\b\u00e3@e\u00df\u00bbo\u00ced\u00a27`\u00a1\u008dS:\u00fa\u00b1W\u00e9\u008dI\u00e8\u00d8A\u0002\\\u00e6\u00b5\u00af\u00d8\u00f2\u00c8r\u00bb\u0084n\u00c9Ja\u008e\u000b\u0092d\u008dQ\u00c0\u00b1\u00e5\u000eT\u00cf!ZGf\u00ef\f\u00e7\rS\n\u00bf-V[\u00eev\u00a4u\u00cca\u008f'sl\u00cf\u001f\u001e\u001awv\u00b2\u00ddS\u00f2\u00b1B\u0099\u0011R\u00b5\u00cbd\u0080O\u00b3\u009e4\u00d3W\u00b8\u001fP;\u009a\u00fdk");
                var4_3 = var2_2.length();
                var1_4 = 2;
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
                    var2_2 = SubLine.C("\\E\u00d0`5y\u00a2+\u00b6\u00da\u00a7zo\u00ba\u00bd&\u0088");
                    var4_3 = var2_2.length();
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
                            v13 = 4;
                            break;
                        }
                        case 1: {
                            v13 = 74;
                            break;
                        }
                        case 2: {
                            v13 = 11;
                            break;
                        }
                        case 3: {
                            v13 = 88;
                            break;
                        }
                        case 4: {
                            v13 = 95;
                            break;
                        }
                        case 5: {
                            v13 = 48;
                            break;
                        }
                        default: {
                            v13 = 108;
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
        l.D = var5;
        l.A = new String[45];
    }

    private /* synthetic */ String C(TransactionEngine$Order a, long a2) {
        l a3;
        if (!l.C(-26073, 17838).equals(a.side)) {
            return l.C(-26094, -24928);
        }
        Long l2 = a3.K.get(a.accountKey);
        if (l2 == null) {
            return l.C(-26058, -17551);
        }
        if (l2 < a2) {
            return l.C(-26056, -7204);
        }
        a3.K.put(a.accountKey, l2 - a2);
        return l.C(-26094, -24928);
    }

    public A C(TransactionEngine$Order[] a) {
        A a2 = new A();
        for (TransactionEngine$Order transactionEngine$Order : a) {
            l a3;
            d d2 = a3.C(transactionEngine$Order);
            if (d2.A) {
                ++a2.c;
                a2.K += d2.c;
                a2.G += d2.G;
                l.C(a2.m, d2.D);
                l.C(a2.D, d2.f);
                continue;
            }
            ++a2.a;
            l.C(a2.C, d2.K);
        }
        return a2;
    }

    public void C(TransactionEngine$Order[] a, long a2) {
        l a3;
        a3.K.clear();
        for (TransactionEngine$Order transactionEngine$Order : a) {
            a3.K.putIfAbsent(transactionEngine$Order.accountKey, a2);
        }
    }

    /*
     * Exception decompiling
     */
    private /* synthetic */ String C(TransactionEngine$Order a) {
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
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public d C(TransactionEngine$Order a) {
        l a2;
        d d2 = new d();
        String string = a2.H(a);
        if (!l.C(-26074, 29246).equals(string)) {
            d2.A = false;
            d2.K = string;
            return d2;
        }
        d2.f = a2.D(a);
        if (l.C(-26079, 18482).equals(d2.f)) {
            d2.A = false;
            d2.K = l.C(-26072, 11469);
            return d2;
        }
        long l2 = a.quantity * a.priceCents;
        d2.c = a2.C(a, l2, d2.f);
        d2.G = l.C(-26084, 16768).equals(a.side) ? l2 + d2.c : l2 - d2.c;
        String string2 = a2.C(a, d2.G);
        if (!l.C(-26094, -24928).equals(string2)) {
            d2.A = false;
            d2.K = string2;
            return d2;
        }
        d2.D = a2.C(a);
        d2.A = true;
        return d2;
    }

    public l() {
        l a;
    }
}

