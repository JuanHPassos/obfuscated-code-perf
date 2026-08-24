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
    private static final String[] i;
    private static final String[] j;

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
        long l = this.a();
        String string = String.valueOf(this.g);
        String string2 = String.valueOf(this.f);
        String string3 = String.valueOf(this.e);
        long l2 = this.d;
        long l3 = this.c;
        long l4 = this.b;
        long l5 = this.a;
        return com.finance.engine.d.a(4456, 3970) + l5 + com.finance.engine.d.a(4458, -8936) + l4 + com.finance.engine.d.a(4460, -18317) + l3 + com.finance.engine.d.a(4463, -30460) + l2 + com.finance.engine.d.a(4459, -17932) + string3 + com.finance.engine.d.a(4461, -7095) + string2 + com.finance.engine.d.a(4462, 3483) + string + com.finance.engine.d.a(4457, -15418) + l;
    }

    /*
     * Unable to fully structure code
     */
    static {
        block20: {
            block19: {
                var5 = new String[8];
                var3_1 = 0;
                var2_2 = "(e\\\u00ec\u0090\u00d0O`\u001c\u00e0\b|\u00b9\u00d6w\u00c1\u00a3eV\t\u00f4;\u00a8\u001b\u00dd\u00d7@t\u0097\u00063\";D\u00f5\u00a1\u0006l\u00f6\u00d8\u0087\u0083\u00d9\u0011B\u00c9\u001b\u00a17\u00ab\u0005\u00e18\u00be\u008e-e\u00d3#\u0094\u0088";
                var4_3 = "(e\\\u00ec\u0090\u00d0O`\u001c\u00e0\b|\u00b9\u00d6w\u00c1\u00a3eV\t\u00f4;\u00a8\u001b\u00dd\u00d7@t\u0097\u00063\";D\u00f5\u00a1\u0006l\u00f6\u00d8\u0087\u0083\u00d9\u0011B\u00c9\u001b\u00a17\u00ab\u0005\u00e18\u00be\u008e-e\u00d3#\u0094\u0088".length();
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
                    var2_2 = "1\u00f8u\u0087\u00fao\u00b4y\u0016\u00c3\u0007\u00a1\u00c0m\u00d8\u00a6\u00d4\u00d2";
                    var4_3 = "1\u00f8u\u0087\u00fao\u00b4y\u0016\u00c3\u0007\u00a1\u00c0m\u00d8\u00a6\u00d4\u00d2".length();
                    var1_4 = 10;
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
                            v13 = 120;
                            break;
                        }
                        case 1: {
                            v13 = 34;
                            break;
                        }
                        case 2: {
                            v13 = 23;
                            break;
                        }
                        case 3: {
                            v13 = 82;
                            break;
                        }
                        case 4: {
                            v13 = 85;
                            break;
                        }
                        case 5: {
                            v13 = 67;
                            break;
                        }
                        default: {
                            v13 = 74;
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
        com.finance.engine.d.i = var5;
        com.finance.engine.d.j = new String[8];
    }

    private static String a(int n, int n2) {
        int n3 = (n ^ 0x116A) & 0xFFFF;
        if (j[n3] == null) {
            int n4;
            char[] cArray = i[n3].toCharArray();
            int n5 = switch (cArray[0] & 0xFF) {
                case 0 -> 253;
                case 1 -> 19;
                case 2 -> 224;
                case 3 -> 177;
                case 4 -> 208;
                case 5 -> 105;
                case 6 -> 2;
                case 7 -> 225;
                case 8 -> 64;
                case 9 -> 137;
                case 10 -> 114;
                case 11 -> 180;
                case 12 -> 98;
                case 13 -> 62;
                case 14 -> 141;
                case 15 -> 175;
                case 16 -> 8;
                case 17 -> 250;
                case 18 -> 127;
                case 19 -> 11;
                case 20 -> 103;
                case 21 -> 51;
                case 22 -> 228;
                case 23 -> 192;
                case 24 -> 207;
                case 25 -> 217;
                case 26 -> 201;
                case 27 -> 67;
                case 28 -> 213;
                case 29 -> 164;
                case 30 -> 162;
                case 31 -> 249;
                case 32 -> 230;
                case 33 -> 172;
                case 34 -> 243;
                case 35 -> 56;
                case 36 -> 7;
                case 37 -> 232;
                case 38 -> 238;
                case 39 -> 22;
                case 40 -> 29;
                case 41 -> 39;
                case 42 -> 246;
                case 43 -> 227;
                case 44 -> 102;
                case 45 -> 220;
                case 46 -> 189;
                case 47 -> 13;
                case 48 -> 21;
                case 49 -> 112;
                case 50 -> 214;
                case 51 -> 179;
                case 52 -> 215;
                case 53 -> 44;
                case 54 -> 3;
                case 55 -> 237;
                case 56 -> 71;
                case 57 -> 134;
                case 58 -> 234;
                case 59 -> 181;
                case 60 -> 146;
                case 61 -> 38;
                case 62 -> 186;
                case 63 -> 26;
                case 64 -> 57;
                case 65 -> 218;
                case 66 -> 236;
                case 67 -> 58;
                case 68 -> 92;
                case 69 -> 160;
                case 70 -> 244;
                case 71 -> 198;
                case 72 -> 81;
                case 73 -> 10;
                case 74 -> 121;
                case 75 -> 91;
                case 76 -> 163;
                case 77 -> 63;
                case 78 -> 119;
                case 79 -> 87;
                case 80 -> 168;
                case 81 -> 4;
                case 82 -> 77;
                case 83 -> 45;
                case 84 -> 86;
                case 85 -> 239;
                case 86 -> 33;
                case 87 -> 219;
                case 88 -> 202;
                case 89 -> 156;
                case 90 -> 47;
                case 91 -> 184;
                case 92 -> 197;
                case 93 -> 96;
                case 94 -> 32;
                case 95 -> 49;
                case 96 -> 196;
                case 97 -> 66;
                case 98 -> 167;
                case 99 -> 195;
                case 100 -> 74;
                case 101 -> 223;
                case 102 -> 140;
                case 103 -> 15;
                case 104 -> 174;
                case 105 -> 150;
                case 106 -> 106;
                case 107 -> 205;
                case 108 -> 210;
                case 109 -> 182;
                case 110 -> 48;
                case 111 -> 73;
                case 112 -> 41;
                case 113 -> 79;
                case 114 -> 89;
                case 115 -> 104;
                case 116 -> 171;
                case 117 -> 193;
                case 118 -> 216;
                case 119 -> 206;
                case 120 -> 70;
                case 121 -> 157;
                case 122 -> 200;
                case 123 -> 126;
                case 124 -> 31;
                case 125 -> 97;
                case 126 -> 53;
                case 127 -> 122;
                case 128 -> 254;
                case 129 -> 255;
                case 130 -> 37;
                case 131 -> 245;
                case 132 -> 108;
                case 133 -> 113;
                case 134 -> 72;
                case 135 -> 123;
                case 136 -> 129;
                case 137 -> 9;
                case 138 -> 151;
                case 139 -> 5;
                case 140 -> 149;
                case 141 -> 170;
                case 142 -> 247;
                case 143 -> 128;
                case 144 -> 6;
                case 145 -> 1;
                case 146 -> 94;
                case 147 -> 60;
                case 148 -> 16;
                case 149 -> 24;
                case 150 -> 35;
                case 151 -> 0;
                case 152 -> 187;
                case 153 -> 20;
                case 154 -> 124;
                case 155 -> 204;
                case 156 -> 54;
                case 157 -> 14;
                case 158 -> 132;
                case 159 -> 130;
                case 160 -> 90;
                case 161 -> 42;
                case 162 -> 43;
                case 163 -> 17;
                case 164 -> 169;
                case 165 -> 139;
                case 166 -> 209;
                case 167 -> 242;
                case 168 -> 34;
                case 169 -> 28;
                case 170 -> 46;
                case 171 -> 110;
                case 172 -> 111;
                case 173 -> 75;
                case 174 -> 133;
                case 175 -> 231;
                case 176 -> 109;
                case 177 -> 118;
                case 178 -> 23;
                case 179 -> 136;
                case 180 -> 165;
                case 181 -> 221;
                case 182 -> 222;
                case 183 -> 135;
                case 184 -> 95;
                case 185 -> 161;
                case 186 -> 82;
                case 187 -> 120;
                case 188 -> 59;
                case 189 -> 65;
                case 190 -> 143;
                case 191 -> 166;
                case 192 -> 27;
                case 193 -> 142;
                case 194 -> 93;
                case 195 -> 18;
                case 196 -> 144;
                case 197 -> 154;
                case 198 -> 248;
                case 199 -> 199;
                case 200 -> 83;
                case 201 -> 100;
                case 202 -> 233;
                case 203 -> 158;
                case 204 -> 191;
                case 205 -> 173;
                case 206 -> 159;
                case 207 -> 145;
                case 208 -> 229;
                case 209 -> 101;
                case 210 -> 190;
                case 211 -> 188;
                case 212 -> 50;
                case 213 -> 131;
                case 214 -> 194;
                case 215 -> 36;
                case 216 -> 69;
                case 217 -> 80;
                case 218 -> 55;
                case 219 -> 99;
                case 220 -> 61;
                case 221 -> 176;
                case 222 -> 88;
                case 223 -> 251;
                case 224 -> 25;
                case 225 -> 147;
                case 226 -> 84;
                case 227 -> 178;
                case 228 -> 125;
                case 229 -> 211;
                case 230 -> 241;
                case 231 -> 115;
                case 232 -> 212;
                case 233 -> 68;
                case 234 -> 152;
                case 235 -> 148;
                case 236 -> 183;
                case 237 -> 226;
                case 238 -> 185;
                case 239 -> 40;
                case 240 -> 155;
                case 241 -> 30;
                case 242 -> 203;
                case 243 -> 252;
                case 244 -> 52;
                case 245 -> 85;
                case 246 -> 240;
                case 247 -> 12;
                case 248 -> 138;
                case 249 -> 76;
                case 250 -> 78;
                case 251 -> 117;
                case 252 -> 116;
                case 253 -> 153;
                case 254 -> 235;
                default -> 107;
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
            com.finance.engine.d.j[n3] = new String(cArray).intern();
        }
        return j[n3];
    }
}

