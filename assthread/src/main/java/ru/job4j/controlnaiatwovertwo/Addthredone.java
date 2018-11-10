package ru.job4j.controlnaiatwovertwo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * поток добавляет единицы в базу
 */
public class Addthredone extends Thread {
    private final Database database;
    private final CountDownLatch barrier;

    public Addthredone(Database database, CountDownLatch barrier) {
        this.database = database;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        for (int i = 0; i <  10; i++) {
            database.addtoStringint(1);
            this.barrier.countDown();
        }
    }
}
