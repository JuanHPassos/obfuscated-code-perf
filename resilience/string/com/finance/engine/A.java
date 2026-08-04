/*
 * Decompiled with CFR 0.152.
 */
package com.finance.engine;

import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.math3.geometry.euclidean.twod.Segment;
import org.apache.commons.math3.geometry.euclidean.twod.hull.AklToussaintHeuristic;

public final class A {
    public long a;
    public final Map<String, Long> C = new TreeMap<String, Long>();
    public final Map<String, Long> m = new TreeMap<String, Long>();
    private static final String[] f;
    public long c;
    public long G;
    public long K;
    private static final String[] A;
    public final Map<String, Long> D = new TreeMap<String, Long>();

    public String toString() {
        A a;
        long l2 = a.C();
        String string = String.valueOf(a.D);
        String string2 = String.valueOf(a.m);
        String string3 = String.valueOf(a.C);
        long l3 = a.G;
        long l4 = a.K;
        long l5 = a.a;
        long l6 = a.c;
        return com.finance.engine.A.C(28204, 12880) + l6 + com.finance.engine.A.C(28200, -2729) + l5 + com.finance.engine.A.C(28207, -1622) + l4 + com.finance.engine.A.C(28201, -6628) + l3 + com.finance.engine.A.C(28203, -14532) + string3 + com.finance.engine.A.C(28202, 2781) + string2 + com.finance.engine.A.C(28206, -2462) + string + com.finance.engine.A.C(28205, 22745) + l2;
    }

    public A() {
        A a;
    }

    public long C() {
        A a;
        long l2 = 1125899906842597L;
        l2 = 31L * l2 + a.c;
        l2 = 31L * l2 + a.a;
        l2 = 31L * l2 + a.K;
        l2 = 31L * l2 + a.G;
        l2 = com.finance.engine.A.C(l2, a.C);
        l2 = com.finance.engine.A.C(l2, a.m);
        l2 = com.finance.engine.A.C(l2, a.D);
        return l2;
    }

    private static /* synthetic */ String C(int a, int a2) {
        int n = (a ^ 0x6E28) & 0xFFFF;
        if (f[n] == null) {
            int n2;
            char[] cArray = A[n].toCharArray();
            int n3 = switch (cArray[0] & 0xFF) {
                case 0 -> 246;
                case 1 -> 24;
                case 2 -> 241;
                case 3 -> 237;
                case 4 -> 116;
                case 5 -> 45;
                case 6 -> 18;
                case 7 -> 20;
                case 8 -> 186;
                case 9 -> 142;
                case 10 -> 156;
                case 11 -> 151;
                case 12 -> 173;
                case 13 -> 205;
                case 14 -> 78;
                case 15 -> 39;
                case 16 -> 29;
                case 17 -> 203;
                case 18 -> 109;
                case 19 -> 56;
                case 20 -> 207;
                case 21 -> 71;
                case 22 -> 149;
                case 23 -> 209;
                case 24 -> 239;
                case 25 -> 152;
                case 26 -> 43;
                case 27 -> 129;
                case 28 -> 121;
                case 29 -> 55;
                case 30 -> 15;
                case 31 -> 131;
                case 32 -> 162;
                case 33 -> 168;
                case 34 -> 87;
                case 35 -> 104;
                case 36 -> 88;
                case 37 -> 130;
                case 38 -> 41;
                case 39 -> 225;
                case 40 -> 252;
                case 41 -> 174;
                case 42 -> 204;
                case 43 -> 194;
                case 44 -> 86;
                case 45 -> 114;
                case 46 -> 64;
                case 47 -> 98;
                case 48 -> 85;
                case 49 -> 105;
                case 50 -> 8;
                case 51 -> 146;
                case 52 -> 67;
                case 53 -> 226;
                case 54 -> 228;
                case 55 -> 133;
                case 56 -> 16;
                case 57 -> 141;
                case 58 -> 249;
                case 59 -> 191;
                case 60 -> 222;
                case 61 -> 245;
                case 62 -> 220;
                case 63 -> 231;
                case 64 -> 25;
                case 65 -> 22;
                case 66 -> 199;
                case 67 -> 242;
                case 68 -> 124;
                case 69 -> 77;
                case 70 -> 201;
                case 71 -> 89;
                case 72 -> 171;
                case 73 -> 254;
                case 74 -> 3;
                case 75 -> 253;
                case 76 -> 94;
                case 77 -> 184;
                case 78 -> 40;
                case 79 -> 235;
                case 80 -> 117;
                case 81 -> 35;
                case 82 -> 102;
                case 83 -> 150;
                case 84 -> 200;
                case 85 -> 144;
                case 86 -> 103;
                case 87 -> 148;
                case 88 -> 221;
                case 89 -> 158;
                case 90 -> 90;
                case 91 -> 99;
                case 92 -> 31;
                case 93 -> 73;
                case 94 -> 187;
                case 95 -> 138;
                case 96 -> 34;
                case 97 -> 183;
                case 98 -> 210;
                case 99 -> 11;
                case 100 -> 68;
                case 101 -> 243;
                case 102 -> 100;
                case 103 -> 175;
                case 104 -> 37;
                case 105 -> 14;
                case 106 -> 135;
                case 107 -> 32;
                case 108 -> 79;
                case 109 -> 5;
                case 110 -> 106;
                case 111 -> 134;
                case 112 -> 216;
                case 113 -> 136;
                case 114 -> 170;
                case 115 -> 140;
                case 116 -> 28;
                case 117 -> 176;
                case 118 -> 69;
                case 119 -> 212;
                case 120 -> 6;
                case 121 -> 107;
                case 122 -> 218;
                case 123 -> 192;
                case 124 -> 217;
                case 125 -> 59;
                case 126 -> 202;
                case 127 -> 53;
                case 128 -> 198;
                case 129 -> 166;
                case 130 -> 214;
                case 131 -> 80;
                case 132 -> 188;
                case 133 -> 180;
                case 134 -> 1;
                case 135 -> 62;
                case 136 -> 137;
                case 137 -> 143;
                case 138 -> 47;
                case 139 -> 54;
                case 140 -> 219;
                case 141 -> 111;
                case 142 -> 251;
                case 143 -> 236;
                case 144 -> 119;
                case 145 -> 61;
                case 146 -> 233;
                case 147 -> 72;
                case 148 -> 206;
                case 149 -> 92;
                case 150 -> 21;
                case 151 -> 115;
                case 152 -> 2;
                case 153 -> 145;
                case 154 -> 10;
                case 155 -> 50;
                case 156 -> 181;
                case 157 -> 30;
                case 158 -> 95;
                case 159 -> 82;
                case 160 -> 177;
                case 161 -> 178;
                case 162 -> 48;
                case 163 -> 52;
                case 164 -> 215;
                case 165 -> 93;
                case 166 -> 13;
                case 167 -> 26;
                case 168 -> 161;
                case 169 -> 65;
                case 170 -> 127;
                case 171 -> 113;
                case 172 -> 223;
                case 173 -> 255;
                case 174 -> 157;
                case 175 -> 57;
                case 176 -> 75;
                case 177 -> 213;
                case 178 -> 238;
                case 179 -> 38;
                case 180 -> 230;
                case 181 -> 17;
                case 182 -> 96;
                case 183 -> 160;
                case 184 -> 196;
                case 185 -> 190;
                case 186 -> 42;
                case 187 -> 164;
                case 188 -> 60;
                case 189 -> 23;
                case 190 -> 240;
                case 191 -> 4;
                case 192 -> 49;
                case 193 -> 91;
                case 194 -> 154;
                case 195 -> 155;
                case 196 -> 63;
                case 197 -> 248;
                case 198 -> 195;
                case 199 -> 7;
                case 200 -> 12;
                case 201 -> 185;
                case 202 -> 33;
                case 203 -> 97;
                case 204 -> 232;
                case 205 -> 250;
                case 206 -> 224;
                case 207 -> 83;
                case 208 -> 118;
                case 209 -> 197;
                case 210 -> 182;
                case 211 -> 74;
                case 212 -> 101;
                case 213 -> 227;
                case 214 -> 112;
                case 215 -> 84;
                case 216 -> 58;
                case 217 -> 123;
                case 218 -> 229;
                case 219 -> 0;
                case 220 -> 167;
                case 221 -> 208;
                case 222 -> 125;
                case 223 -> 126;
                case 224 -> 189;
                case 225 -> 234;
                case 226 -> 9;
                case 227 -> 70;
                case 228 -> 172;
                case 229 -> 132;
                case 230 -> 244;
                case 231 -> 36;
                case 232 -> 46;
                case 233 -> 211;
                case 234 -> 108;
                case 235 -> 122;
                case 236 -> 165;
                case 237 -> 120;
                case 238 -> 81;
                case 239 -> 19;
                case 240 -> 153;
                case 241 -> 27;
                case 242 -> 247;
                case 243 -> 169;
                case 244 -> 163;
                case 245 -> 179;
                case 246 -> 110;
                case 247 -> 193;
                case 248 -> 44;
                case 249 -> 139;
                case 250 -> 66;
                case 251 -> 51;
                case 252 -> 128;
                case 253 -> 76;
                case 254 -> 147;
                default -> 159;
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
            com.finance.engine.A.f[n] = new String(cArray).intern();
        }
        return f[n];
    }

    private static /* synthetic */ long C(long a, Map<String, Long> a2) {
        for (Map.Entry<String, Long> entry : a2.entrySet()) {
            for (int i = 0; i < entry.getKey().length(); ++i) {
                a = 31L * a + (long)entry.getKey().charAt(i);
            }
            a = 31L * a + entry.getValue();
        }
        return a;
    }

    /*
     * Unable to fully structure code
     */
    static {
        block20: {
            block19: {
                var5 = new String[8];
                var3_1 = 0;
                var2_2 = Segment.C("\u0004\u00b7#\u00d2l\u00c9\u00eb)\u00ba\u00a0g\u00d5\u0014\u0080\u00ed\u00c4o\u00d3\u0080Ch\u0085\u00e4\u00da\u00d1\u00be\u00e6Rq\u000e\u00c2\u00f1o\u00b5o\u0018~\u0099\t\u0003\u0096\u00fb\u00d2\u008a\u0081J\u00f8\u0097\u00d3\u00ea\u00a17\u00f4\u00023Ea\u00e1\u008bX\u00f0\u00fc");
                var4_3 = var2_2.length();
                var1_4 = 10;
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
                    var2_2 = AklToussaintHeuristic.C("\"\u00f5!\u00dce3SO\u00d9\u008c\u009e\u001a\u00cb_\u00de\u0095\u0007");
                    var4_3 = var2_2.length();
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
                            v13 = 27;
                            break;
                        }
                        case 1: {
                            v13 = 97;
                            break;
                        }
                        case 2: {
                            v13 = 57;
                            break;
                        }
                        case 3: {
                            v13 = 117;
                            break;
                        }
                        case 4: {
                            v13 = 59;
                            break;
                        }
                        case 5: {
                            v13 = 82;
                            break;
                        }
                        default: {
                            v13 = 4;
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
        com.finance.engine.A.A = var5;
        com.finance.engine.A.f = new String[8];
    }
}

