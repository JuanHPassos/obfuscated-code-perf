/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

final class a {
    private static final String[] a;
    private static final String[] b;

    private a() {
    }

    /*
     * Exception decompiling
     */
    static String a(String var0, long var1_1) {
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
                var5 = new String[9];
                var3_1 = 0;
                var2_2 = "\u0095\u009c\u009cx\n*,\u00a3\u0097\u00f1\u001b\u001b]~g\u0098-\u0011\u00e6\u0080\u00f2\u00fb\u00d0I\u009c\u00ae='z\b'\u001c(\u00d4\u00c4\u0002\u00b7R\u0012Xn%&*\u00f6\u00d7\u00d2\u00a2>\u008cN\u00a5\u008bq\u00c2\u00aaz\u0002\u009dC\u0006D\u00e7A\u0097\u00e1\u001f\u0010'\u0007\u00ca\u000b\u00d6@s`u\u00cf6w\u00c3\u001e\u00c9\u00c6";
                var4_3 = "\u0095\u009c\u009cx\n*,\u00a3\u0097\u00f1\u001b\u001b]~g\u0098-\u0011\u00e6\u0080\u00f2\u00fb\u00d0I\u009c\u00ae='z\b'\u001c(\u00d4\u00c4\u0002\u00b7R\u0012Xn%&*\u00f6\u00d7\u00d2\u00a2>\u008cN\u00a5\u008bq\u00c2\u00aaz\u0002\u009dC\u0006D\u00e7A\u0097\u00e1\u001f\u0010'\u0007\u00ca\u000b\u00d6@s`u\u00cf6w\u00c3\u001e\u00c9\u00c6".length();
                var1_4 = 17;
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
                    var2_2 = "\u0006\u0018\u00c3\u0012<\u0083\f\u00ea\u00d5\u00fa\u00c6*\u00be iY\u0094\u0014\u0013";
                    var4_3 = "\u0006\u0018\u00c3\u0012<\u0083\f\u00ea\u00d5\u00fa\u00c6*\u00be iY\u0094\u0014\u0013".length();
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
                            v13 = 2;
                            break;
                        }
                        case 1: {
                            v13 = 115;
                            break;
                        }
                        case 2: {
                            v13 = 49;
                            break;
                        }
                        case 3: {
                            v13 = 55;
                            break;
                        }
                        case 4: {
                            v13 = 68;
                            break;
                        }
                        case 5: {
                            v13 = 50;
                            break;
                        }
                        default: {
                            v13 = 104;
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
        com.finance.engine.a.a = var5;
        com.finance.engine.a.b = new String[9];
    }

    private static String a(int n, int n2) {
        int n3 = (n ^ 0xFFFFA892) & 0xFFFF;
        if (b[n3] == null) {
            int n4;
            char[] cArray = a[n3].toCharArray();
            int n5 = switch (cArray[0] & 0xFF) {
                case 0 -> 84;
                case 1 -> 244;
                case 2 -> 75;
                case 3 -> 155;
                case 4 -> 0;
                case 5 -> 54;
                case 6 -> 1;
                case 7 -> 147;
                case 8 -> 135;
                case 9 -> 141;
                case 10 -> 134;
                case 11 -> 233;
                case 12 -> 69;
                case 13 -> 126;
                case 14 -> 113;
                case 15 -> 255;
                case 16 -> 86;
                case 17 -> 250;
                case 18 -> 68;
                case 19 -> 234;
                case 20 -> 254;
                case 21 -> 87;
                case 22 -> 229;
                case 23 -> 143;
                case 24 -> 196;
                case 25 -> 129;
                case 26 -> 184;
                case 27 -> 127;
                case 28 -> 32;
                case 29 -> 236;
                case 30 -> 57;
                case 31 -> 5;
                case 32 -> 235;
                case 33 -> 55;
                case 34 -> 167;
                case 35 -> 77;
                case 36 -> 177;
                case 37 -> 85;
                case 38 -> 6;
                case 39 -> 49;
                case 40 -> 210;
                case 41 -> 110;
                case 42 -> 104;
                case 43 -> 249;
                case 44 -> 48;
                case 45 -> 39;
                case 46 -> 117;
                case 47 -> 96;
                case 48 -> 136;
                case 49 -> 157;
                case 50 -> 208;
                case 51 -> 116;
                case 52 -> 112;
                case 53 -> 107;
                case 54 -> 22;
                case 55 -> 73;
                case 56 -> 14;
                case 57 -> 63;
                case 58 -> 27;
                case 59 -> 165;
                case 60 -> 42;
                case 61 -> 221;
                case 62 -> 16;
                case 63 -> 226;
                case 64 -> 9;
                case 65 -> 120;
                case 66 -> 52;
                case 67 -> 115;
                case 68 -> 230;
                case 69 -> 26;
                case 70 -> 239;
                case 71 -> 181;
                case 72 -> 148;
                case 73 -> 38;
                case 74 -> 132;
                case 75 -> 78;
                case 76 -> 199;
                case 77 -> 201;
                case 78 -> 62;
                case 79 -> 24;
                case 80 -> 12;
                case 81 -> 151;
                case 82 -> 114;
                case 83 -> 217;
                case 84 -> 206;
                case 85 -> 4;
                case 86 -> 175;
                case 87 -> 220;
                case 88 -> 158;
                case 89 -> 163;
                case 90 -> 166;
                case 91 -> 238;
                case 92 -> 13;
                case 93 -> 79;
                case 94 -> 82;
                case 95 -> 174;
                case 96 -> 232;
                case 97 -> 19;
                case 98 -> 186;
                case 99 -> 92;
                case 100 -> 200;
                case 101 -> 138;
                case 102 -> 21;
                case 103 -> 10;
                case 104 -> 94;
                case 105 -> 45;
                case 106 -> 162;
                case 107 -> 76;
                case 108 -> 2;
                case 109 -> 97;
                case 110 -> 41;
                case 111 -> 161;
                case 112 -> 216;
                case 113 -> 253;
                case 114 -> 140;
                case 115 -> 35;
                case 116 -> 20;
                case 117 -> 44;
                case 118 -> 190;
                case 119 -> 91;
                case 120 -> 183;
                case 121 -> 101;
                case 122 -> 72;
                case 123 -> 53;
                case 124 -> 98;
                case 125 -> 173;
                case 126 -> 60;
                case 127 -> 118;
                case 128 -> 197;
                case 129 -> 245;
                case 130 -> 7;
                case 131 -> 152;
                case 132 -> 70;
                case 133 -> 93;
                case 134 -> 191;
                case 135 -> 204;
                case 136 -> 246;
                case 137 -> 106;
                case 138 -> 205;
                case 139 -> 122;
                case 140 -> 150;
                case 141 -> 123;
                case 142 -> 180;
                case 143 -> 189;
                case 144 -> 130;
                case 145 -> 224;
                case 146 -> 237;
                case 147 -> 66;
                case 148 -> 71;
                case 149 -> 247;
                case 150 -> 90;
                case 151 -> 194;
                case 152 -> 202;
                case 153 -> 125;
                case 154 -> 15;
                case 155 -> 193;
                case 156 -> 58;
                case 157 -> 215;
                case 158 -> 128;
                case 159 -> 47;
                case 160 -> 242;
                case 161 -> 56;
                case 162 -> 164;
                case 163 -> 40;
                case 164 -> 153;
                case 165 -> 154;
                case 166 -> 33;
                case 167 -> 28;
                case 168 -> 133;
                case 169 -> 46;
                case 170 -> 223;
                case 171 -> 228;
                case 172 -> 25;
                case 173 -> 51;
                case 174 -> 212;
                case 175 -> 65;
                case 176 -> 251;
                case 177 -> 211;
                case 178 -> 145;
                case 179 -> 149;
                case 180 -> 74;
                case 181 -> 176;
                case 182 -> 171;
                case 183 -> 207;
                case 184 -> 105;
                case 185 -> 241;
                case 186 -> 17;
                case 187 -> 3;
                case 188 -> 178;
                case 189 -> 31;
                case 190 -> 137;
                case 191 -> 131;
                case 192 -> 222;
                case 193 -> 30;
                case 194 -> 89;
                case 195 -> 248;
                case 196 -> 159;
                case 197 -> 59;
                case 198 -> 99;
                case 199 -> 231;
                case 200 -> 172;
                case 201 -> 64;
                case 202 -> 23;
                case 203 -> 119;
                case 204 -> 8;
                case 205 -> 139;
                case 206 -> 142;
                case 207 -> 36;
                case 208 -> 83;
                case 209 -> 227;
                case 210 -> 179;
                case 211 -> 37;
                case 212 -> 103;
                case 213 -> 209;
                case 214 -> 18;
                case 215 -> 187;
                case 216 -> 213;
                case 217 -> 111;
                case 218 -> 214;
                case 219 -> 121;
                case 220 -> 198;
                case 221 -> 146;
                case 222 -> 102;
                case 223 -> 34;
                case 224 -> 81;
                case 225 -> 192;
                case 226 -> 29;
                case 227 -> 11;
                case 228 -> 185;
                case 229 -> 43;
                case 230 -> 252;
                case 231 -> 240;
                case 232 -> 182;
                case 233 -> 218;
                case 234 -> 225;
                case 235 -> 203;
                case 236 -> 156;
                case 237 -> 61;
                case 238 -> 169;
                case 239 -> 67;
                case 240 -> 170;
                case 241 -> 160;
                case 242 -> 124;
                case 243 -> 80;
                case 244 -> 109;
                case 245 -> 219;
                case 246 -> 144;
                case 247 -> 168;
                case 248 -> 50;
                case 249 -> 108;
                case 250 -> 100;
                case 251 -> 188;
                case 252 -> 88;
                case 253 -> 95;
                case 254 -> 195;
                default -> 243;
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
            com.finance.engine.a.b[n3] = new String(cArray).intern();
        }
        return b[n3];
    }
}

