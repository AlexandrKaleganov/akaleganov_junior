package ru.job4j.forum.discriminant;

import java.util.concurrent.CountDownLatch;

public class ThreadPoool {

    //вычисление дискримината
    public static class Discr implements Runnable {
        private Urovnenie ur;
        private CountDownLatch count;
        Discr(Urovnenie ur) {
            this.ur = ur;
            this.count = count;
        }

        @Override
        public void run() {
            ur.setDesc();
        }
    }
    //вычисление первого корня
    public static class Xone implements Runnable {
        private Urovnenie ur;
        private CountDownLatch count;
        Xone(Urovnenie ur, CountDownLatch count) {
            this.ur = ur;
            this.count = count;

        }

        @Override
        public void run() {
            ur.xOne();

        }
    }

    //вычисление второго корня
    public static class Xtwo implements Runnable {
        private Urovnenie ur;
        private CountDownLatch count;
        Xtwo(Urovnenie ur) {
            this.ur = ur;
            this.count = count;
        }

        @Override
        public void run() {
            ur.xTwo();

        }
    }
    public static class Xnull implements Runnable {
        private Urovnenie ur;
        private CountDownLatch count;
        Xnull(Urovnenie ur) {
            this.ur = ur;
            this.count = count;
        }

        @Override
        public void run() {
            ur.xNull();
        }
    }
}
