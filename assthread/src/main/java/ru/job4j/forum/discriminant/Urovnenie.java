package ru.job4j.forum.discriminant;

import java.util.concurrent.CountDownLatch;

/**
 * класс уровнение содержит начальные данные и методы для решения уровнения
 */
public class Urovnenie {
    private final double a;
    private final double b;
    private final double c;
    private volatile Double disc = null;
    private double x1;
    private double x2;
    CountDownLatch count = new CountDownLatch(1);

    Urovnenie(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Double getDisc() {
        return disc;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public void setDesc() {
        disc = Math.pow(b, 2) - (4 * a * c);
        System.out.println("Дискриминант = " + disc);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count.countDown();
    }

    //метод вычисления первого корня
    public void xOne() {
        try {
            count.await();
            if (this.disc <= 0) {
                throw new InterruptedException();
            }
            x1 = ((-1 * b) - Math.sqrt(disc)) / (2 * a);
            System.out.println("Корень x1 = " + x1);
        } catch (InterruptedException e) {
            System.out.println("Запрос на прерывание поток " + Thread.currentThread().getName() + " вычисляющий X1 прервал работу в связи с тем, что D = " + this.disc);
        }
    }

    //метод вычисления второго корня
    public void xTwo() {
        try {
            count.await();
            if (this.disc <= 0) {
                throw new InterruptedException();
            }
            x2 = ((-1 * b) + Math.sqrt(disc)) / (2 * a);
            System.out.println("Корень x2 = " + x2);
        } catch (InterruptedException e) {
            System.out.println("Запрос на прерывание поток " + Thread.currentThread().getName() + " вычисляющий X2 прервал работу в связи с тем, что D = " + this.disc);
        }
    }

    //метод вычисления корня при D=0
    public void xNull() {
        try {
            count.await();
            if (this.disc != 0) {
                throw new InterruptedException();
            }
            x1 = (-1 * b) / (2 * a);
            System.out.println("Корень  = " + x1);
        } catch (InterruptedException e) {
            System.out.println("Запрос на прерывание поток " + Thread.currentThread().getName() + "   прервал работу в связи с тем, что D = " + this.disc);
        }
    }
}