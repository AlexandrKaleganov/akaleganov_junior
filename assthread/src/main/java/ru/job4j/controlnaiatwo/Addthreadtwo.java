package ru.job4j.controlnaiatwo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * поток добавляет двойки в базу
 */
public class Addthreadtwo extends Thread {
    private final CyclicBarrier barrier;
    private final Database database;

    public Addthreadtwo(Database stringDatabase, CyclicBarrier barrier) {
        this.database = stringDatabase;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            try {
                barrier.await();
                database.addtoStringint(2);
            } catch (InterruptedException | BrokenBarrierException e) {
                break;
            }
        }
    }

}
