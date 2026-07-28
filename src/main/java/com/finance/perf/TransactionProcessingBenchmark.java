package com.finance.perf;

import com.finance.engine.TransactionEngine;
import com.finance.engine.TransactionEngine.Order;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Harness JMH. NAO deve ser ofuscado: o Allatori aponta apenas
 * para com.finance.engine.* (ver allatori/config.xml).
 *
 * Correcoes de metodo em relacao a versao original:
 *   - @Setup(Level.Iteration): reinicia saldos a cada iteracao,
 *     evitando o vies do "saldo drenado" ao longo das 10 iteracoes.
 *   - @Fork(10): mais robustez estatistica entre JVMs.
 *   - Motor separado -> mesmo codigo usado em correcao e decompilacao.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 20, time = 1)
@Fork(value = 10)
public class TransactionProcessingBenchmark {

    private static final long INITIAL_BALANCE_CENTS = 10_000_000_00L;

    private Order[] orders;
    private TransactionEngine engine;

    @Param({"5000", "20000", "50000"})
    private int size;

    @Setup(Level.Trial)
    public void buildOrders() {
        orders = OrderFactory.deterministicOrders(size);
        engine = new TransactionEngine();
    }

    /** Reinicia o estado mutavel (saldos) a cada iteracao de medicao. */
    @Setup(Level.Iteration)
    public void resetState() {
        engine.resetBalances(orders, INITIAL_BALANCE_CENTS);
    }

    @Benchmark
    public void processOrders(Blackhole bh) {
        TransactionEngine.Summary s = engine.run(orders);
        bh.consume(s);
        bh.consume(s.decisionHash());
    }
}