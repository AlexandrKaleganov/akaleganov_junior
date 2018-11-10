package ru.job4j.controlnaiatwovertwo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Alexander Kaleganov
 * @since 17.09.2018
 * база позволяет делать запись сразу из двух потоков.
 */
public class Database {
    private String base;

    public Database() {
        this.base = "";

    }

    public void addtoStringint(int n) {
            this.base += n;
    }

    public String getBase() {
        return base;
    }
}
