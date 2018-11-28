package ru.job4j.forum.discriminant;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;


public class UrovnenieTest {
    //старт потоков
    public void startThread(Urovnenie urovnenie) throws InterruptedException {
        Thread discThread = new Thread(new ThreadPoool.Discr(urovnenie));
        Thread xOne = new Thread(new ThreadPoool.Xone(urovnenie));
        Thread xTwo = new Thread(new ThreadPoool.Xtwo(urovnenie));
        Thread xNull = new Thread(new ThreadPoool.Xnull(urovnenie));
        discThread.start();
        xOne.start();
        xTwo.start();
        xNull.start();
        discThread.join();
        xOne.join();
        xTwo.join();
        xNull.join();

    }

    /**
     * уровнение не имеет корней
     *
     * @throws InterruptedException
     */
    @Test
    public void urovnenieTestwhendisMinnull() throws InterruptedException {
        Urovnenie urovnenie = new Urovnenie(4, 6, 10);
        this.startThread(urovnenie);
        Assert.assertThat(urovnenie.getDisc(), Is.is(-124.0));
        Assert.assertThat(urovnenie.getX1(), Is.is(0.0));
        Assert.assertThat(urovnenie.getX2(), Is.is(0.0));
        if (urovnenie.getDisc() < 0) {
            System.out.println("уровнение не имеет корней");
        }
    }

    /**
     * уровнение имеет один корень
     *
     * @throws InterruptedException
     */
    @Test
    public void urovnenieTestwhendisISgnull() throws InterruptedException {
        Urovnenie urovnenie = new Urovnenie(1, 6, 9);
        this.startThread(urovnenie);
        Assert.assertThat(urovnenie.getDisc(), Is.is(0.0));
        Assert.assertThat(urovnenie.getX1(), Is.is(-3.0));
    }

    /**
     * уровнение имеет два корня
     *
     * @throws InterruptedException
     */
    @Test
    public void urovnenieTestwhendisBignull() throws InterruptedException {
        Urovnenie urovnenie = new Urovnenie(1, 3, -4);
        this.startThread(urovnenie);
        Assert.assertThat(urovnenie.getDisc(), Is.is(25.0));
        Assert.assertThat(urovnenie.getX1(), Is.is(-4.0));
        Assert.assertThat(urovnenie.getX2(), Is.is(1.0));

    }
}