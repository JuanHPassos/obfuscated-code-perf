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
 * Gera o "golden file": roda o motor com ordens determinísticas em 3 tamanhos (5000/20000/50000) 
 * e imprime o Summary. É comparado (diff) entre o jar original e cada jar ofuscado para provar 
 * que a ofuscação não mudou o comportamento. Está tecnicamente dentro do pacote ofuscado, mas 
 * seu nome e entry point são preservados via <keep-names> no config do Allatori, então continua 
 * invocável via java -cp ... com.finance.engine.CorrectnessRunner.
 *      
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