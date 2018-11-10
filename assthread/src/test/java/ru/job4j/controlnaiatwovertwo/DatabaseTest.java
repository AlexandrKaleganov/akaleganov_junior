package ru.job4j.controlnaiatwovertwo;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.Matchers.is;

public class DatabaseTest {

    @Test
    public void addtoStringint() throws InterruptedException {
        for (int j = 0; j < 50; j++) {

            Database database = new Database();
            ExecutorService executorService = Executors.newFixedThreadPool(2);

            for (int i = 0; i < 2; i++) {
                CountDownLatch countDownLatch = new CountDownLatch(10);
                executorService.execute(new Addthreadtwo(database, countDownLatch));
                executorService.execute(new Addthredone(database, countDownLatch));
            }

            Thread.sleep(3);
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                Thread.sleep(100);
            }
            Thread.sleep(3);
            Assert.assertThat(database.getBase(), is("1111111111222222222211111111112222222222"));
        }
    }

}