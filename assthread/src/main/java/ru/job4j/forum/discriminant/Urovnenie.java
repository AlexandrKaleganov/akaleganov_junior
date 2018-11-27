package ru.job4j.forum.discriminant;

import java.util.concurrent.CountDownLatch;

public class Urovnenie {
    private final double a;
    private final double b;
    private final double c;
    private volatile Double disc = null;
    private double x1;
    private double x2;
    private volatile boolean x1Start = false;
    private volatile boolean x2Start = false;
    CountDownLatch count = new CountDownLatch(1);

    Urovnenie(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void setDesc() {
        disc = Math.pow(b, 2) - (4 * a * c);
        System.out.println(disc);
        System.out.println("через 3 секунды запустим остальные два потока вычисления корней");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void xOne() {
        try {
            count.await();
            if (this.disc <= 0) {
                throw new InterruptedException();
            }
            x1 = (b + Math.sqrt(disc)) / (2 * a);
            System.out.println("Корень 1 = " + x1);
        } catch (InterruptedException e) {
            System.out.println("Запрос на прерывание поток " + Thread.currentThread().getName() + " вычисляющий X2 прервал работу в связи с тем, что D = " + this.disc);        }
    }

    public void xTwo() {
        try {
            count.await();
            if (this.disc <= 0) {
                throw new InterruptedException();
            }
            x2 = (b - Math.sqrt(disc)) / (2 * a);
            System.out.println("Корень x2 = " + x2);
        } catch (InterruptedException e) {
            System.out.println("Запрос на прерывание поток " + Thread.currentThread().getName() + " вычисляющий X2 прервал работу в связи с тем, что D = " + this.disc);
    }
    }

    public void xNull() {
        try {
            count.await();
            if (this.disc < 0) {
                throw new InterruptedException();
            }
            x1 = (-1 * b) / (2 * a);
            System.out.println("Корень  = " + x1);
        } catch (InterruptedException e) {
            System.out.println("Запрос на прерывание поток " + Thread.currentThread().getName() + "   прервал работу в связи с тем, что D = " + this.disc );
        }
    }
}