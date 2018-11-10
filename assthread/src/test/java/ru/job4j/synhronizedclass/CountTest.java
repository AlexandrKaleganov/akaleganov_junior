package ru.job4j.synhronizedclass;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CountTest {
    @SuppressWarnings("WeakerAccess")
    public class ThreadCount extends Thread {
        private final Count count;

        private ThreadCount(final Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }
    @Test
    public void increment() throws InterruptedException {
        //создаём счётчик
        final Count count = new Count();
        //создаём нити
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        //Запускаем нити
        first.start();
        second.start();
        //Заставляем главную нить ждать завершения второстепенных нитей
        first.join();
        second.join();
        assertThat(count.getValue(), is(2));
    }
}