package ru.job4j.forum.magazin;

public class Demo {


    public static void main(String[] args) {
        Supermarket supermarket = new Supermarket(2000, 2000);
        Buyer buyer = null;
        Seller seller = null;
        for (int i = 0; i < 10; i++) {
            buyer = new Buyer(supermarket, 500, 200);
            seller = new Seller(supermarket, 100, 100);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(buyer.isAlive());
        System.out.println(seller.isAlive());
    }
}