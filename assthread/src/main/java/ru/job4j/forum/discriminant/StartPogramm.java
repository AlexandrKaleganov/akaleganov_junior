package ru.job4j.forum.discriminant;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StartPogramm {
    private volatile Double disc = null;
    private double x1;
    private double x2;
    private volatile boolean x1Start = false;
    private volatile boolean x2Start = false;
    public static void main(String[] args) {
        Urovnenie urovnenie = new Urovnenie(1, 6, 9);
        CountDownLatch count = new CountDownLatch(1);

        ThreadPoool.Discr discThread = new ThreadPoool.Discr(urovnenie);

    }


        public void reshenie() {
            //вычисление ервого корня

    }
}
