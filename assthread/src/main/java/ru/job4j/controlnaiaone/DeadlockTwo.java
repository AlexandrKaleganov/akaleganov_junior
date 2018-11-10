package ru.job4j.controlnaiaone;
/**
 * @autor Alexander Kaleganov
 * @since 16/09/2018
 * @version 1.0
 * 100% блокировка  тут всё просто один ласс два потока и  CountDownLatch объект этого класса
 * объект этого класса снимает блокировку по происшествии определённый событий если событие не произощло то лок не снимется
 * самая лёгкая задача по логике проще чем на wait
 */

import java.util.concurrent.CountDownLatch;

public class DeadlockTwo {
    private final CountDownLatch lock;
    private final String[] vlue;

    public static void main(String[] args) {
        DeadlockTwo deadlockTwo = new DeadlockTwo(1);
        A a = new A(deadlockTwo);
        B b = new B(deadlockTwo);
        a.start();
        b.start();
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            b.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public DeadlockTwo(int i) {
        lock = new CountDownLatch(i);
        this.vlue = new String[2];
    }


    public void writeA() {
        try {
            this.lock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.vlue[0] = Thread.currentThread().getName();
        System.out.println(this.vlue[0]);
        this.lock.countDown();

    }

    public void writeB() {
        try {
            this.lock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.vlue[1] = Thread.currentThread().getName();
        System.out.println(this.vlue[1]);
        this.lock.countDown();
    }

    public String[] getVlue() {
        return vlue;
    }

    private static class A extends Thread {
        private final DeadlockTwo deadlockTwo;

        public A(DeadlockTwo deadlockTwo) {
            this.deadlockTwo = deadlockTwo;
            setName("A");

        }

        @Override
        public void run() {
            System.out.println("поток " + this.getName() + " стартанул");
            this.deadlockTwo.writeA();
            this.deadlockTwo.lock.countDown();
        }
    }

    private static class B extends Thread {
        private final DeadlockTwo deadlockTwo;

        public B(DeadlockTwo deadlockTwo) {
            this.deadlockTwo = deadlockTwo;
            setName("B");
        }

        @Override
        public void run() {
            System.out.println("поток " + this.getName() + " стартанул");
            this.deadlockTwo.writeB();
        }
    }
}
