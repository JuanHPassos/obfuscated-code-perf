package com.finance.engine;

import com.finance.engine.TransactionEngine.Order;
import com.finance.engine.TransactionEngine.Summary;
import com.finance.perf.OrderFactory;

/**
 * Gera o resumo determinístico (golden file) para o protocolo de correcao.
 *
 * Uso:
 *   java -cp original.jar   com.finance.engine.CorrectnessRunner > golden_original.txt
 *   java -cp obfuscated.jar com.finance.engine.CorrectnessRunner > golden_obfuscated.txt
 *   diff golden_original.txt golden_obfuscated.txt   # deve ser vazio
 *
 * Nota: se o Allatori renomear CorrectnessRunner, mantenha esta classe
 * (e OrderFactory) fora do escopo de renaming OU use o entry point
 * preservado via keep no config.xml.
 */
public final class CorrectnessRunner {

    private CorrectnessRunner() {}

    public static void main(String[] args) {
        int[] sizes = {5000, 20000, 50000};
        for (int size : sizes) {
            Order[] orders = OrderFactory.deterministicOrders(size);
            TransactionEngine engine = new TransactionEngine();
            engine.resetBalances(orders, 10_000_000_00L);
            Summary s = engine.run(orders);
            System.out.println("SIZE=" + size + " " + s);
        }
    }
}