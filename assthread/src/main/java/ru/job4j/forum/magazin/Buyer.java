package ru.job4j.forum.magazin;

public class Buyer extends Thread {

    private int countN;
    private int countM;
    Supermarket supermarket;

    public Buyer(Supermarket supermarket, int countM, int countN) {
        this.countM = countM;
        this.countN = countN;
        this.supermarket = supermarket;
        this.start();
    }

    @Override
    public void run() {
        System.out.println("Пришел покупатель. ");
        supermarket.buyTovar(countM, countN);

    }

}
