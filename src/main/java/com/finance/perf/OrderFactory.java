package com.finance.perf;

import com.finance.engine.TransactionEngine.Order;

/** Gera ordens de forma DETERMINISTICA (seed fixa) para comparacao justa. */
public final class OrderFactory {

    private OrderFactory() {}

    public static Order[] deterministicOrders(int size) {
        Order[] orders = new Order[size];
        long r = 0x9E3779B97F4A7C15L;   // semente fixa

        String[] assets   = {"PETR4", "VALE3", "ITUB4", "USD", "EUR", "BTC", "NTNB"};
        String[] segments = {"EQUITY", "EQUITY", "FX", "FIXED_INCOME", "CRYPTO"};
        String[] sides    = {"BUY", "SELL"};

        for (int i = 0; i < size; i++) {
            r ^= r << 13; r ^= r >>> 7; r ^= r << 17;
            long v = r & 0x7FFFFFFFFFFFFFFFL;

            Order o = new Order();
            o.accountKey = "ACCT-" + (v % 500);
            o.assetCode  = assets[(int) (v % assets.length)];
            o.segment    = segments[(int) (v % segments.length)];
            o.side       = sides[(int) (v % 2)];
            o.quantity   = 1 + (v % 1000);
            o.priceCents = 100 + (v % 5_000_00);
            o.txnId      = "TXN-" + Long.toHexString(v) + "-" + i;
            orders[i] = o;
        }
        return orders;
    }
}