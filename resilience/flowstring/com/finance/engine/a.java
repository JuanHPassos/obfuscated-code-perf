/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import com.finance.engine.TransactionEngine$Order;
import com.finance.engine.b;

final class a {
    private static final String[] a;
    private static final String[] b;

    private a() {
    }

    /*
     * Unable to fully structure code
     */
    static String a(String var0, long var1_1) {
        block14: {
            var4_2 = var0;
            var3_3 = com.finance.engine.b.b;
            var5_4 = -1;
            v0 = var4_2.hashCode();
            if (var3_3 != 0) break block14;
            switch (v0) {
                case 2052821701: {
                    v0 = (int)var4_2.equals(com.finance.engine.a.a(18828, -14430));
                    if (var3_3 != 0) break;
                    if (v0 == 0) ** GOTO lbl30
                    var5_4 = 0;
                    if (var3_3 == 0) ** GOTO lbl30
                }
                case 2258: {
                    v0 = (int)var4_2.equals(com.finance.engine.a.a(18826, -29881));
                    if (var3_3 != 0) break;
                    if (v0 == 0) ** GOTO lbl30
                    var5_4 = 1;
                    if (var3_3 == 0) ** GOTO lbl30
                }
                case 1996612801: {
                    v0 = (int)var4_2.equals(com.finance.engine.a.a(18825, -10934));
                    if (var3_3 != 0) break;
                    if (v0 == 0) ** GOTO lbl30
                    var5_4 = 2;
                    if (var3_3 == 0) ** GOTO lbl30
                }
                case 968326388: {
                    v0 = (int)var4_2.equals(com.finance.engine.a.a(18830, -31327));
                    if (var3_3 != 0) break;
                    if (v0 != 0) {
                        var5_4 = 3;
                    }
                }
lbl30:
                // 10 sources

                default: {
                    v0 = var5_4;
                }
            }
        }
        if (var3_3 != 0) ** GOTO lbl38
        switch (v0) {
            case 0: {
                cfr_temp_0 = var1_1 - 500000000L;
                v0 = cfr_temp_0 == 0L ? 0 : (cfr_temp_0 < 0L ? -1 : 1);
lbl38:
                // 2 sources

                if (v0 <= 0) break;
                return com.finance.engine.a.a(18831, -20513);
            }
            case 1: {
                if (var1_1 <= 200000000L) break;
                return com.finance.engine.a.a(18824, 31490);
            }
            case 2: {
                if (var1_1 <= 50000000L) break;
                return com.finance.engine.a.a(18818, -8419);
            }
            case 3: {
                if (var1_1 <= 1000000000L) break;
                return com.finance.engine.a.a(18829, 24923);
            }
        }
        v1 = com.finance.engine.a.a(18827, -18491);
        if (TransactionEngine$Order.b != 0) {
            com.finance.engine.b.b = ++var3_3;
        }
        return v1;
    }

    /*
     * Unable to fully structure code
     */
    static {
        block20: {
            block19: {
                var5 = new String[9];
                var3_1 = 0;
                var2_2 = "P\u0004\u0002\u00b2\u00ce\u0010\u0000\u0092\u0098\u00b6\u00b6aT\u00b6\u0096\u00a3E\u00d0;\u00c1$:\u0006\u00b40#\u0018\nT\f\u00a8\u00cd\u0004\u00ca\u00df\u00a9\u00b5y\u00ec \u00ad\u00af\u0011,e\u001dH\u0007\u00a9M@\u0000L\u00d2O\u00a4\u00df\u00aa\u00ab\"\u0006\u00a8\u0097j\u0096\u00ae\u00a9";
                var4_3 = "P\u0004\u0002\u00b2\u00ce\u0010\u0000\u0092\u0098\u00b6\u00b6aT\u00b6\u0096\u00a3E\u00d0;\u00c1$:\u0006\u00b40#\u0018\nT\f\u00a8\u00cd\u0004\u00ca\u00df\u00a9\u00b5y\u00ec \u00ad\u00af\u0011,e\u001dH\u0007\u00a9M@\u0000L\u00d2O\u00a4\u00df\u00aa\u00ab\"\u0006\u00a8\u0097j\u0096\u00ae\u00a9".length();
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
                    var2_2 = "\u00b4\u00ab\u000e\u0091r\u0081\b$\u0014\u00b7d\u00f9$\t\u00baq \u0012|\u00a7\u0017\u0010F\u00a6q*q\u0097\u00f9Q\u009dC\u0084\u00dc\u00a2\u00b6";
                    var4_3 = "\u00b4\u00ab\u000e\u0091r\u0081\b$\u0014\u00b7d\u00f9$\t\u00baq \u0012|\u00a7\u0017\u0010F\u00a6q*q\u0097\u00f9Q\u009dC\u0084\u00dc\u00a2\u00b6".length();
                    var1_4 = 17;
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
                            v13 = 63;
                            break;
                        }
                        case 1: {
                            v13 = 49;
                            break;
                        }
                        case 2: {
                            v13 = 32;
                            break;
                        }
                        case 3: {
                            v13 = 112;
                            break;
                        }
                        case 4: {
                            v13 = 76;
                            break;
                        }
                        case 5: {
                            v13 = 76;
                            break;
                        }
                        default: {
                            v13 = 66;
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
        int n3 = (n ^ 0x498A) & 0xFFFF;
        if (b[n3] == null) {
            int n4;
            char[] cArray = a[n3].toCharArray();
            int n5 = switch (cArray[0] & 0xFF) {
                case 0 -> 78;
                case 1 -> 1;
                case 2 -> 147;
                case 3 -> 220;
                case 4 -> 45;
                case 5 -> 83;
                case 6 -> 36;
                case 7 -> 174;
                case 8 -> 237;
                case 9 -> 169;
                case 10 -> 95;
                case 11 -> 245;
                case 12 -> 35;
                case 13 -> 49;
                case 14 -> 85;
                case 15 -> 231;
                case 16 -> 207;
                case 17 -> 144;
                case 18 -> 48;
                case 19 -> 158;
                case 20 -> 212;
                case 21 -> 47;
                case 22 -> 205;
                case 23 -> 215;
                case 24 -> 17;
                case 25 -> 113;
                case 26 -> 204;
                case 27 -> 117;
                case 28 -> 238;
                case 29 -> 203;
                case 30 -> 50;
                case 31 -> 70;
                case 32 -> 162;
                case 33 -> 80;
                case 34 -> 234;
                case 35 -> 229;
                case 36 -> 168;
                case 37 -> 223;
                case 38 -> 153;
                case 39 -> 197;
                case 40 -> 152;
                case 41 -> 60;
                case 42 -> 126;
                case 43 -> 64;
                case 44 -> 239;
                case 45 -> 27;
                case 46 -> 251;
                case 47 -> 177;
                case 48 -> 5;
                case 49 -> 86;
                case 50 -> 125;
                case 51 -> 40;
                case 52 -> 201;
                case 53 -> 33;
                case 54 -> 235;
                case 55 -> 8;
                case 56 -> 91;
                case 57 -> 243;
                case 58 -> 165;
                case 59 -> 32;
                case 60 -> 101;
                case 61 -> 31;
                case 62 -> 72;
                case 63 -> 149;
                case 64 -> 156;
                case 65 -> 255;
                case 66 -> 194;
                case 67 -> 12;
                case 68 -> 102;
                case 69 -> 138;
                case 70 -> 105;
                case 71 -> 127;
                case 72 -> 18;
                case 73 -> 29;
                case 74 -> 179;
                case 75 -> 77;
                case 76 -> 16;
                case 77 -> 34;
                case 78 -> 109;
                case 79 -> 135;
                case 80 -> 163;
                case 81 -> 103;
                case 82 -> 114;
                case 83 -> 181;
                case 84 -> 242;
                case 85 -> 228;
                case 86 -> 122;
                case 87 -> 89;
                case 88 -> 189;
                case 89 -> 225;
                case 90 -> 51;
                case 91 -> 19;
                case 92 -> 58;
                case 93 -> 55;
                case 94 -> 216;
                case 95 -> 63;
                case 96 -> 87;
                case 97 -> 210;
                case 98 -> 222;
                case 99 -> 97;
                case 100 -> 9;
                case 101 -> 230;
                case 102 -> 143;
                case 103 -> 221;
                case 104 -> 54;
                case 105 -> 124;
                case 106 -> 11;
                case 107 -> 76;
                case 108 -> 139;
                case 109 -> 46;
                case 110 -> 253;
                case 111 -> 30;
                case 112 -> 217;
                case 113 -> 133;
                case 114 -> 119;
                case 115 -> 26;
                case 116 -> 65;
                case 117 -> 115;
                case 118 -> 15;
                case 119 -> 224;
                case 120 -> 53;
                case 121 -> 199;
                case 122 -> 116;
                case 123 -> 193;
                case 124 -> 110;
                case 125 -> 166;
                case 126 -> 184;
                case 127 -> 68;
                case 128 -> 94;
                case 129 -> 6;
                case 130 -> 173;
                case 131 -> 71;
                case 132 -> 240;
                case 133 -> 131;
                case 134 -> 121;
                case 135 -> 62;
                case 136 -> 41;
                case 137 -> 123;
                case 138 -> 141;
                case 139 -> 130;
                case 140 -> 37;
                case 141 -> 3;
                case 142 -> 192;
                case 143 -> 160;
                case 144 -> 142;
                case 145 -> 74;
                case 146 -> 42;
                case 147 -> 167;
                case 148 -> 183;
                case 149 -> 182;
                case 150 -> 39;
                case 151 -> 208;
                case 152 -> 79;
                case 153 -> 150;
                case 154 -> 148;
                case 155 -> 214;
                case 156 -> 233;
                case 157 -> 106;
                case 158 -> 22;
                case 159 -> 248;
                case 160 -> 146;
                case 161 -> 28;
                case 162 -> 99;
                case 163 -> 244;
                case 164 -> 61;
                case 165 -> 202;
                case 166 -> 236;
                case 167 -> 104;
                case 168 -> 249;
                case 169 -> 88;
                case 170 -> 198;
                case 171 -> 59;
                case 172 -> 81;
                case 173 -> 118;
                case 174 -> 82;
                case 175 -> 176;
                case 176 -> 66;
                case 177 -> 211;
                case 178 -> 93;
                case 179 -> 107;
                case 180 -> 159;
                case 181 -> 56;
                case 182 -> 206;
                case 183 -> 171;
                case 184 -> 38;
                case 185 -> 92;
                case 186 -> 241;
                case 187 -> 232;
                case 188 -> 196;
                case 189 -> 7;
                case 190 -> 43;
                case 191 -> 129;
                case 192 -> 137;
                case 193 -> 185;
                case 194 -> 96;
                case 195 -> 24;
                case 196 -> 10;
                case 197 -> 100;
                case 198 -> 178;
                case 199 -> 188;
                case 200 -> 252;
                case 201 -> 254;
                case 202 -> 154;
                case 203 -> 4;
                case 204 -> 219;
                case 205 -> 209;
                case 206 -> 145;
                case 207 -> 191;
                case 208 -> 52;
                case 209 -> 57;
                case 210 -> 23;
                case 211 -> 226;
                case 212 -> 132;
                case 213 -> 195;
                case 214 -> 190;
                case 215 -> 112;
                case 216 -> 73;
                case 217 -> 13;
                case 218 -> 84;
                case 219 -> 175;
                case 220 -> 20;
                case 221 -> 108;
                case 222 -> 90;
                case 223 -> 120;
                case 224 -> 180;
                case 225 -> 151;
                case 226 -> 134;
                case 227 -> 2;
                case 228 -> 187;
                case 229 -> 161;
                case 230 -> 155;
                case 231 -> 218;
                case 232 -> 98;
                case 233 -> 21;
                case 234 -> 128;
                case 235 -> 250;
                case 236 -> 111;
                case 237 -> 136;
                case 238 -> 213;
                case 239 -> 75;
                case 240 -> 246;
                case 241 -> 247;
                case 242 -> 164;
                case 243 -> 67;
                case 244 -> 157;
                case 245 -> 172;
                case 246 -> 14;
                case 247 -> 170;
                case 248 -> 44;
                case 249 -> 69;
                case 250 -> 25;
                case 251 -> 200;
                case 252 -> 140;
                case 253 -> 186;
                case 254 -> 0;
                default -> 227;
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

