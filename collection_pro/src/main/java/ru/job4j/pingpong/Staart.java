package ru.job4j.pingpong;


@SuppressWarnings("WeakerAccess")
public class Staart {
    public static void main(String[] args) {
        new WorkerThread().start();

        try {
            Thread.sleep(7500);
        } catch (InterruptedException e) {
            // handle here exception
        }

        System.out.println("Main Thread ending");
    }

}
