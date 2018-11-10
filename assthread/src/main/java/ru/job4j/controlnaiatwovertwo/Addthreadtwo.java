package ru.job4j.controlnaiatwovertwo;

import java.util.concurrent.CountDownLatch;

/**
 * поток добавляет двойки в базу
 */
public class Addthreadtwo extends Thread {
    private final CountDownLatch barrier;
    private final Database database;

    public Addthreadtwo(Database stringDatabase, CountDownLatch barrier) {
        this.database = stringDatabase;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            barrier.await();
            for (int i = 0; i < 10; i++) {
                database.addtoStringint(2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
