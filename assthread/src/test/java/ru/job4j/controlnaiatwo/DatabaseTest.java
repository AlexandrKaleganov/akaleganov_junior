package ru.job4j.controlnaiatwo;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CyclicBarrier;

import static org.hamcrest.core.Is.is;

public class DatabaseTest {
    @Test
    public void onetwoTest() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);
        Database database = new Database(barrier);

        Addthredone oneT = new Addthredone(database, barrier);
        Addthreadtwo twoT = new Addthreadtwo(database, barrier);
        oneT.start();
        twoT.start();
        Thread.sleep(3);
        oneT.interrupt();
        twoT.interrupt();
        Thread.sleep(3);
//        Assert.assertThat(database.getBase(), is("1111111111222222222211111111112222222222"));
    }
}