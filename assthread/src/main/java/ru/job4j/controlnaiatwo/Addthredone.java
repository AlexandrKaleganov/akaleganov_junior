package ru.job4j.controlnaiatwo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * поток добавляет единицы в базу
 */
public class Addthredone extends Thread {
    private final Database database;
    private final CyclicBarrier barrier;

    public Addthredone(Database database, CyclicBarrier barrier) {
        this.database = database;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            database.addtoStringint(1);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                break;
            }
        }
    }
}
