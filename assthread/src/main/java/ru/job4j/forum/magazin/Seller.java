package ru.job4j.forum.magazin;

public class Seller extends Thread {

    private Supermarket supermarket;
    private int n;
    private int m;

    public Seller(Supermarket supermarket, int n, int m) {
        this.m = m;
        this.n = n;
        this.supermarket = supermarket;
        this.start();
    }


    public void run() {
        System.out.println("Пришел продавец. ");
        supermarket.putTovar(n, m);

    }

}