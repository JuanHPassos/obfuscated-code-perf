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
    private static final String[] h;
    private static final String[] i;

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
        long l = this.a();
        String string = String.valueOf(this.g);
        String string2 = String.valueOf(this.f);
        String string3 = String.valueOf(this.e);
        long l2 = this.d;
        long l3 = this.c;
        long l4 = this.b;
        long l5 = this.a;
        return com.finance.engine.d.a(-11257, -25038) + l5 + com.finance.engine.d.a(-11262, -28028) + l4 + com.finance.engine.d.a(-11263, -9689) + l3 + com.finance.engine.d.a(-11264, 3516) + l2 + com.finance.engine.d.a(-11259, -3466) + string3 + com.finance.engine.d.a(-11260, -19711) + string2 + com.finance.engine.d.a(-11258, 11992) + string + com.finance.engine.d.a(-11261, 27164) + l;
    }

    /*
     * Unable to fully structure code
     */
    static {
        block20: {
            block19: {
                var5 = new String[8];
                var3_1 = 0;
                var2_2 = "9\u00b1\u00b0b\u00ae\u00f1\nt.\u001d\u00d1\u0092\u00e3\u001a\u00e5\u00d1\u009a\nA\u00ce\u00b1\u0015H\u00a9\u00f7B\u0019n\u0011\u00b9(\u00ae\u00c9\u00ab2\u00831\u008fn,\u001d\u00a5\u00c0\u00d8\u0091=\t\u0006\u008e<\u00f9e\u001fB\u008e\u00b0\u0006\u00f4\u00e6\u0001\u00d1\u0092\u0087";
                var4_3 = "9\u00b1\u00b0b\u00ae\u00f1\nt.\u001d\u00d1\u0092\u00e3\u001a\u00e5\u00d1\u009a\nA\u00ce\u00b1\u0015H\u00a9\u00f7B\u0019n\u0011\u00b9(\u00ae\u00c9\u00ab2\u00831\u008fn,\u001d\u00a5\u00c0\u00d8\u0091=\t\u0006\u008e<\u00f9e\u001fB\u008e\u00b0\u0006\u00f4\u00e6\u0001\u00d1\u0092\u0087".length();
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
                    var2_2 = "k\u00ab\u00fea\u00ee\u00f5\u0095\u007f\u0007\u00c7Qk\u00beL\u008c\u009a";
                    var4_3 = "k\u00ab\u00fea\u00ee\u00f5\u0095\u007f\u0007\u00c7Qk\u00beL\u008c\u009a".length();
                    var1_4 = 8;
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
                            v13 = 124;
                            break;
                        }
                        case 1: {
                            v13 = 106;
                            break;
                        }
                        case 2: {
                            v13 = 93;
                            break;
                        }
                        case 3: {
                            v13 = 15;
                            break;
                        }
                        case 4: {
                            v13 = 54;
                            break;
                        }
                        case 5: {
                            v13 = 124;
                            break;
                        }
                        default: {
                            v13 = 51;
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
        com.finance.engine.d.h = var5;
        com.finance.engine.d.i = new String[8];
    }

    private static String a(int n, int n2) {
        int n3 = (n ^ 0xFFFFD403) & 0xFFFF;
        if (i[n3] == null) {
            int n4;
            char[] cArray = h[n3].toCharArray();
            int n5 = switch (cArray[0] & 0xFF) {
                case 0 -> 193;
                case 1 -> 254;
                case 2 -> 42;
                case 3 -> 233;
                case 4 -> 195;
                case 5 -> 35;
                case 6 -> 91;
                case 7 -> 74;
                case 8 -> 92;
                case 9 -> 253;
                case 10 -> 85;
                case 11 -> 98;
                case 12 -> 232;
                case 13 -> 230;
                case 14 -> 104;
                case 15 -> 159;
                case 16 -> 87;
                case 17 -> 168;
                case 18 -> 133;
                case 19 -> 40;
                case 20 -> 70;
                case 21 -> 169;
                case 22 -> 209;
                case 23 -> 63;
                case 24 -> 51;
                case 25 -> 186;
                case 26 -> 250;
                case 27 -> 178;
                case 28 -> 241;
                case 29 -> 245;
                case 30 -> 252;
                case 31 -> 129;
                case 32 -> 84;
                case 33 -> 32;
                case 34 -> 235;
                case 35 -> 105;
                case 36 -> 119;
                case 37 -> 79;
                case 38 -> 194;
                case 39 -> 36;
                case 40 -> 226;
                case 41 -> 212;
                case 42 -> 152;
                case 43 -> 28;
                case 44 -> 217;
                case 45 -> 139;
                case 46 -> 120;
                case 47 -> 4;
                case 48 -> 218;
                case 49 -> 56;
                case 50 -> 89;
                case 51 -> 53;
                case 52 -> 247;
                case 53 -> 93;
                case 54 -> 142;
                case 55 -> 222;
                case 56 -> 174;
                case 57 -> 146;
                case 58 -> 113;
                case 59 -> 69;
                case 60 -> 177;
                case 61 -> 10;
                case 62 -> 153;
                case 63 -> 107;
                case 64 -> 170;
                case 65 -> 205;
                case 66 -> 83;
                case 67 -> 236;
                case 68 -> 175;
                case 69 -> 183;
                case 70 -> 67;
                case 71 -> 151;
                case 72 -> 138;
                case 73 -> 237;
                case 74 -> 181;
                case 75 -> 19;
                case 76 -> 248;
                case 77 -> 21;
                case 78 -> 219;
                case 79 -> 78;
                case 80 -> 136;
                case 81 -> 73;
                case 82 -> 239;
                case 83 -> 99;
                case 84 -> 192;
                case 85 -> 231;
                case 86 -> 2;
                case 87 -> 81;
                case 88 -> 125;
                case 89 -> 60;
                case 90 -> 17;
                case 91 -> 140;
                case 92 -> 8;
                case 93 -> 0;
                case 94 -> 26;
                case 95 -> 16;
                case 96 -> 203;
                case 97 -> 123;
                case 98 -> 208;
                case 99 -> 55;
                case 100 -> 160;
                case 101 -> 224;
                case 102 -> 49;
                case 103 -> 172;
                case 104 -> 144;
                case 105 -> 137;
                case 106 -> 59;
                case 107 -> 64;
                case 108 -> 134;
                case 109 -> 44;
                case 110 -> 211;
                case 111 -> 130;
                case 112 -> 82;
                case 113 -> 207;
                case 114 -> 39;
                case 115 -> 163;
                case 116 -> 150;
                case 117 -> 162;
                case 118 -> 20;
                case 119 -> 240;
                case 120 -> 244;
                case 121 -> 210;
                case 122 -> 23;
                case 123 -> 97;
                case 124 -> 100;
                case 125 -> 66;
                case 126 -> 196;
                case 127 -> 46;
                case 128 -> 191;
                case 129 -> 198;
                case 130 -> 190;
                case 131 -> 199;
                case 132 -> 103;
                case 133 -> 95;
                case 134 -> 38;
                case 135 -> 184;
                case 136 -> 48;
                case 137 -> 108;
                case 138 -> 202;
                case 139 -> 52;
                case 140 -> 71;
                case 141 -> 213;
                case 142 -> 57;
                case 143 -> 227;
                case 144 -> 27;
                case 145 -> 220;
                case 146 -> 243;
                case 147 -> 132;
                case 148 -> 117;
                case 149 -> 145;
                case 150 -> 72;
                case 151 -> 18;
                case 152 -> 61;
                case 153 -> 122;
                case 154 -> 29;
                case 155 -> 156;
                case 156 -> 47;
                case 157 -> 228;
                case 158 -> 43;
                case 159 -> 171;
                case 160 -> 101;
                case 161 -> 1;
                case 162 -> 127;
                case 163 -> 88;
                case 164 -> 96;
                case 165 -> 154;
                case 166 -> 179;
                case 167 -> 173;
                case 168 -> 86;
                case 169 -> 115;
                case 170 -> 106;
                case 171 -> 238;
                case 172 -> 249;
                case 173 -> 80;
                case 174 -> 126;
                case 175 -> 200;
                case 176 -> 206;
                case 177 -> 118;
                case 178 -> 189;
                case 179 -> 251;
                case 180 -> 58;
                case 181 -> 90;
                case 182 -> 13;
                case 183 -> 94;
                case 184 -> 37;
                case 185 -> 111;
                case 186 -> 116;
                case 187 -> 102;
                case 188 -> 188;
                case 189 -> 41;
                case 190 -> 15;
                case 191 -> 30;
                case 192 -> 31;
                case 193 -> 62;
                case 194 -> 109;
                case 195 -> 77;
                case 196 -> 11;
                case 197 -> 215;
                case 198 -> 166;
                case 199 -> 176;
                case 200 -> 50;
                case 201 -> 165;
                case 202 -> 114;
                case 203 -> 161;
                case 204 -> 221;
                case 205 -> 25;
                case 206 -> 12;
                case 207 -> 148;
                case 208 -> 128;
                case 209 -> 185;
                case 210 -> 124;
                case 211 -> 110;
                case 212 -> 65;
                case 213 -> 187;
                case 214 -> 135;
                case 215 -> 7;
                case 216 -> 34;
                case 217 -> 223;
                case 218 -> 24;
                case 219 -> 182;
                case 220 -> 121;
                case 221 -> 229;
                case 222 -> 143;
                case 223 -> 22;
                case 224 -> 234;
                case 225 -> 9;
                case 226 -> 112;
                case 227 -> 14;
                case 228 -> 255;
                case 229 -> 147;
                case 230 -> 149;
                case 231 -> 155;
                case 232 -> 6;
                case 233 -> 180;
                case 234 -> 141;
                case 235 -> 214;
                case 236 -> 225;
                case 237 -> 5;
                case 238 -> 242;
                case 239 -> 164;
                case 240 -> 216;
                case 241 -> 3;
                case 242 -> 45;
                case 243 -> 54;
                case 244 -> 197;
                case 245 -> 157;
                case 246 -> 131;
                case 247 -> 204;
                case 248 -> 167;
                case 249 -> 158;
                case 250 -> 201;
                case 251 -> 33;
                case 252 -> 76;
                case 253 -> 246;
                case 254 -> 75;
                default -> 68;
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
            com.finance.engine.d.i[n3] = new String(cArray).intern();
        }
        return i[n3];
    }
}

