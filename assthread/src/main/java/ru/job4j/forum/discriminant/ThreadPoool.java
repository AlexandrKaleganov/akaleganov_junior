package ru.job4j.forum.discriminant;


public class ThreadPoool {

    //вычисление дискримината
    public static class Discr implements Runnable {
        private Urovnenie ur;
        Discr(Urovnenie ur) {
            this.ur = ur;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(" Поток вычисляющий Дискриминант ");
            System.out.println(Thread.currentThread().getName() + Thread.currentThread().isAlive());
            ur.setDesc();
        }
    }
    //вычисление первого корня
    public static class Xone implements Runnable {
        private Urovnenie ur;
        Xone(Urovnenie ur) {
            this.ur = ur;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(" вычсляющий первый корень ");
            System.out.println(Thread.currentThread().getName() + Thread.currentThread().isAlive());
            ur.xOne();

        }
    }

    //вычисление второго корня
    public static class Xtwo implements Runnable {
        private Urovnenie ur;
        Xtwo(Urovnenie ur) {
            this.ur = ur;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(" вычсляющий второй корень ");
            System.out.println(Thread.currentThread().getName() + Thread.currentThread().isAlive());
            ur.xTwo();

        }
    }
    //вычисление одного корня при X == 0
    public static class Xnull implements Runnable {
        private Urovnenie ur;
        Xnull(Urovnenie ur) {
            this.ur = ur;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(" вычсляющий корень при D = 0 ");
            System.out.println(Thread.currentThread().getName() + Thread.currentThread().isAlive());
            ur.xNull();
        }
    }
}
