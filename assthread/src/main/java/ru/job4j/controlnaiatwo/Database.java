package ru.job4j.controlnaiatwo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Alexander Kaleganov
 * @since 17.09.2018
 * база позволяет делать запись сразу из двух потоков.
 * не выжно склько будт циклов в потоках,  в базу будет сначало писатья едеицы, потом двойки по 10 штук
 * сделал с помощью цкил барьер когда два потока подходят к барьеру всё начинается по новой
 */
public class Database {
    private String base;
    private final CyclicBarrier barrier;

    public Database(CyclicBarrier barrier) {
        this.barrier = barrier;
        this.base = "";

    }

    public void addtoStringint(int n) {
        for (int i = 0; i < 10; i++) {
            this.base += n;

        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getBase() {
        return base;
    }
}
