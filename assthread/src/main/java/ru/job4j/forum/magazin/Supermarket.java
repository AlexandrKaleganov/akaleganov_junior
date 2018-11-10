package ru.job4j.forum.magazin;

public class Supermarket {


    private int tovar1;
    private int tovar2;
    private final int maxCOUNT = 2000;
    public static boolean status;

    public Supermarket(int n, int m) {
        this.tovar1 = n;
        this.tovar2 = m;
    }

    public synchronized void buyTovar(int n, int m) {
        while (!((tovar1 >= n) && (tovar2 > m))) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        status = true;
        tovar1 -= n;
        tovar2 -= m;
        System.out.println("Купил товар. На данный момент количество товара  1 " + tovar1);
        System.out.println("На данный момент количество товара  2 " + tovar2 + "\n\n");
        status = false;
        notify();
    }


    public synchronized void putTovar(int n, int m) {
        while (!((tovar1 + n) <= maxCOUNT && (tovar2 + m) <= maxCOUNT)) {
            try {
                System.out.println("Не хватает места для товара. Жду " + "\n");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.tovar1 += n;
        this.tovar2 += m;
        System.out.println("Положил товар " + "\n");
        notify();
    }


    public void setTovar1(int tovar1) {
        this.tovar1 = tovar1;
    }


    public void setTovar2(int tovar2) {
        this.tovar2 = tovar2;
    }


}