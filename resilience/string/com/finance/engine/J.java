/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresFactory;
import org.apache.commons.math3.genetics.ChromosomePair;

final class J {
    private static final String[] A;
    private static final String[] D;

    private /* synthetic */ J() {
        J a;
    }

    /*
     * Unable to fully structure code
     */
    static {
        block20: {
            block19: {
                var5 = new String[9];
                var3_1 = 0;
                var2_2 = ChromosomePair.C("\u00f9\u001d>\u0017\u001dJ\b\u00d4\u00c744\u0014[f\u00b6\u00b7&\u008e\u00b6\u00d0+\u000ee\u0080\u001f\u0005B\u00e9\u0004\u00e8\u0086gg\u00fa\u00c0\u0011\u001fnn\u00f3\u0088{\u00dfV.\u00b5S4\u00a9t\u00b1\u0099\u000b\u00fe\u00d2q\u0096\u00bc\u000bg7a\u0080Q\u0007wB\u00f3\u00acz\u0090z_\u001bN\u00ba\"X\u00c1m2\u0094^\u000f");
                var4_3 = var2_2.length();
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
                    var2_2 = LeastSquaresFactory.C("\u008c\u00190\u00b9\u009a\u00ab\u0097\u00113\u00b5T\u00f0\u00e9X[\u000f\u0017\u009b\u0081");
                    var4_3 = var2_2.length();
                    var1_4 = 16;
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
                            v13 = 41;
                            break;
                        }
                        case 1: {
                            v13 = 121;
                            break;
                        }
                        case 2: {
                            v13 = 108;
                            break;
                        }
                        case 3: {
                            v13 = 107;
                            break;
                        }
                        case 4: {
                            v13 = 75;
                            break;
                        }
                        case 5: {
                            v13 = 104;
                            break;
                        }
                        default: {
                            v13 = 83;
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
        J.A = var5;
        J.D = new String[9];
    }

    /*
     * Exception decompiling
     */
    static String C(String a, long a) {
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

    private static /* synthetic */ String C(int a, int a2) {
        int n = (a ^ 0x78D4) & 0xFFFF;
        if (D[n] == null) {
            int n2;
            char[] cArray = A[n].toCharArray();
            int n3 = switch (cArray[0] & 0xFF) {
                case 0 -> 99;
                case 1 -> 39;
                case 2 -> 254;
                case 3 -> 52;
                case 4 -> 127;
                case 5 -> 232;
                case 6 -> 23;
                case 7 -> 108;
                case 8 -> 248;
                case 9 -> 192;
                case 10 -> 30;
                case 11 -> 243;
                case 12 -> 249;
                case 13 -> 31;
                case 14 -> 185;
                case 15 -> 42;
                case 16 -> 118;
                case 17 -> 135;
                case 18 -> 84;
                case 19 -> 91;
                case 20 -> 95;
                case 21 -> 187;
                case 22 -> 174;
                case 23 -> 212;
                case 24 -> 205;
                case 25 -> 219;
                case 26 -> 63;
                case 27 -> 66;
                case 28 -> 151;
                case 29 -> 119;
                case 30 -> 5;
                case 31 -> 130;
                case 32 -> 228;
                case 33 -> 252;
                case 34 -> 253;
                case 35 -> 21;
                case 36 -> 4;
                case 37 -> 198;
                case 38 -> 53;
                case 39 -> 72;
                case 40 -> 182;
                case 41 -> 175;
                case 42 -> 45;
                case 43 -> 26;
                case 44 -> 114;
                case 45 -> 3;
                case 46 -> 12;
                case 47 -> 10;
                case 48 -> 235;
                case 49 -> 176;
                case 50 -> 65;
                case 51 -> 246;
                case 52 -> 148;
                case 53 -> 227;
                case 54 -> 18;
                case 55 -> 250;
                case 56 -> 145;
                case 57 -> 195;
                case 58 -> 38;
                case 59 -> 94;
                case 60 -> 109;
                case 61 -> 96;
                case 62 -> 233;
                case 63 -> 158;
                case 64 -> 89;
                case 65 -> 35;
                case 66 -> 25;
                case 67 -> 255;
                case 68 -> 245;
                case 69 -> 207;
                case 70 -> 142;
                case 71 -> 220;
                case 72 -> 170;
                case 73 -> 14;
                case 74 -> 161;
                case 75 -> 242;
                case 76 -> 162;
                case 77 -> 134;
                case 78 -> 163;
                case 79 -> 241;
                case 80 -> 222;
                case 81 -> 221;
                case 82 -> 16;
                case 83 -> 61;
                case 84 -> 111;
                case 85 -> 50;
                case 86 -> 159;
                case 87 -> 37;
                case 88 -> 124;
                case 89 -> 60;
                case 90 -> 169;
                case 91 -> 173;
                case 92 -> 106;
                case 93 -> 87;
                case 94 -> 6;
                case 95 -> 75;
                case 96 -> 181;
                case 97 -> 85;
                case 98 -> 13;
                case 99 -> 247;
                case 100 -> 172;
                case 101 -> 2;
                case 102 -> 47;
                case 103 -> 199;
                case 104 -> 81;
                case 105 -> 167;
                case 106 -> 97;
                case 107 -> 150;
                case 108 -> 71;
                case 109 -> 223;
                case 110 -> 147;
                case 111 -> 141;
                case 112 -> 224;
                case 113 -> 225;
                case 114 -> 104;
                case 115 -> 11;
                case 116 -> 90;
                case 117 -> 41;
                case 118 -> 156;
                case 119 -> 105;
                case 120 -> 183;
                case 121 -> 209;
                case 122 -> 216;
                case 123 -> 98;
                case 124 -> 101;
                case 125 -> 154;
                case 126 -> 240;
                case 127 -> 48;
                case 128 -> 1;
                case 129 -> 244;
                case 130 -> 113;
                case 131 -> 77;
                case 132 -> 79;
                case 133 -> 160;
                case 134 -> 24;
                case 135 -> 82;
                case 136 -> 22;
                case 137 -> 188;
                case 138 -> 236;
                case 139 -> 191;
                case 140 -> 189;
                case 141 -> 44;
                case 142 -> 40;
                case 143 -> 62;
                case 144 -> 0;
                case 145 -> 122;
                case 146 -> 214;
                case 147 -> 202;
                case 148 -> 120;
                case 149 -> 215;
                case 150 -> 15;
                case 151 -> 186;
                case 152 -> 121;
                case 153 -> 83;
                case 154 -> 17;
                case 155 -> 218;
                case 156 -> 92;
                case 157 -> 70;
                case 158 -> 80;
                case 159 -> 29;
                case 160 -> 27;
                case 161 -> 137;
                case 162 -> 86;
                case 163 -> 64;
                case 164 -> 58;
                case 165 -> 203;
                case 166 -> 166;
                case 167 -> 217;
                case 168 -> 157;
                case 169 -> 171;
                case 170 -> 208;
                case 171 -> 49;
                case 172 -> 107;
                case 173 -> 178;
                case 174 -> 194;
                case 175 -> 201;
                case 176 -> 131;
                case 177 -> 110;
                case 178 -> 165;
                case 179 -> 100;
                case 180 -> 200;
                case 181 -> 115;
                case 182 -> 9;
                case 183 -> 139;
                case 184 -> 143;
                case 185 -> 28;
                case 186 -> 133;
                case 187 -> 55;
                case 188 -> 204;
                case 189 -> 73;
                case 190 -> 46;
                case 191 -> 32;
                case 192 -> 129;
                case 193 -> 136;
                case 194 -> 132;
                case 195 -> 19;
                case 196 -> 128;
                case 197 -> 146;
                case 198 -> 229;
                case 199 -> 93;
                case 200 -> 8;
                case 201 -> 196;
                case 202 -> 149;
                case 203 -> 36;
                case 204 -> 180;
                case 205 -> 144;
                case 206 -> 33;
                case 207 -> 112;
                case 208 -> 231;
                case 209 -> 34;
                case 210 -> 125;
                case 211 -> 56;
                case 212 -> 126;
                case 213 -> 54;
                case 214 -> 43;
                case 215 -> 197;
                case 216 -> 59;
                case 217 -> 68;
                case 218 -> 238;
                case 219 -> 138;
                case 220 -> 177;
                case 221 -> 179;
                case 222 -> 211;
                case 223 -> 168;
                case 224 -> 164;
                case 225 -> 20;
                case 226 -> 230;
                case 227 -> 155;
                case 228 -> 116;
                case 229 -> 140;
                case 230 -> 234;
                case 231 -> 153;
                case 232 -> 69;
                case 233 -> 102;
                case 234 -> 206;
                case 235 -> 184;
                case 236 -> 239;
                case 237 -> 213;
                case 238 -> 210;
                case 239 -> 88;
                case 240 -> 251;
                case 241 -> 67;
                case 242 -> 7;
                case 243 -> 76;
                case 244 -> 51;
                case 245 -> 193;
                case 246 -> 57;
                case 247 -> 123;
                case 248 -> 152;
                case 249 -> 237;
                case 250 -> 74;
                case 251 -> 190;
                case 252 -> 78;
                case 253 -> 226;
                case 254 -> 103;
                default -> 117;
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
            J.D[n] = new String(cArray).intern();
        }
        return D[n];
    }
}

