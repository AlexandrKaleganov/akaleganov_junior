package ru.job4j.threade;

/**
 * дедлок
 * Второе  о чём удалось узнать это взаимная блокировка создаются два класса, А и В ,
 * с методами f o o ( ) и b a r ( ) соответственно, которые приостанавливаются непосредственно
 * перед попыткой вызова метода из другого класса методы объеков используют объекты друг друга
 * но засыпают перед тем как вызвать свои же методыСначала в главном
 * классе Deadl o c k получаются экземпляры классов А и В, а затем запускается второй
 * поток исполнения , в котором устанавливается состояние взаимной блокировки.
 * В методах foo ( ) и b a r ( ) используется метод s l e ep ( ) , чтобы стимулировать появление
 * взаимной блокировки.
 */
@SuppressWarnings("ALL")
public class Dedlock implements Runnable {
    public static void main(String[] args) {
        new Dedlock();
    }

    Thread potok;
    A a = new A();
    B b = new B();

    Dedlock() {
        potok = new Thread(this);
        potok.start();
        a.foo(b); // получаем блокировку потока
    }

    @Override
    public void run() {
        b.bar(a);
        System.out.println("Этой надписи вообще не дождёмся"); //вот
    }

    private static class A {
        public synchronized void foo(B b) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Метод объекта  A  преврван");
            }
            System.out.println("пытаюсь вызвать метот B.last...");
            b.last();
        }

        private synchronized void last() {
            System.out.println("A.last");
        }
    }

    private static class B {
        public synchronized void bar(A a) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Метод объекта  B  преврван");
            }
            System.out.println("пытаюсь вызвать метот A.last...");
            a.last();
        }

        private synchronized void last() {
            System.out.println("B.last");
        }

    }
}
