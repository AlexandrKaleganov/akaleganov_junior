package ru.job4j.forum.discriminant;

public class StartPogramm {

    public static void main(String[] args) throws InterruptedException {
        Urovnenie urovnenie = new Urovnenie(5, 7, 10);
        Thread discThread = new Thread(new ThreadPoool.Discr(urovnenie));
        Thread xOne = new Thread(new ThreadPoool.Xone(urovnenie));
        Thread xTwo = new Thread(new ThreadPoool.Xtwo(urovnenie));
        Thread xNull = new Thread(new ThreadPoool.Xnull(urovnenie));
        discThread.start();
        xOne.start();
        xTwo.start();
        xNull.start();
        discThread.join();
        xOne.join();
        xTwo.join();
        xNull.join();
        if (urovnenie.getDisc() < 0) {
            System.out.println("уровнение не имеет корней");
        }
    }
}
