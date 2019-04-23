package ru.job4j.forum.disp;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Dispmethod {
    private final ArrayList<Consumer<Integer>> fanclist;

    public Dispmethod() {
        fanclist = new ArrayList<>();
        fanclist.add(s -> System.out.println("сработал первый метод, переменная " + s + " обработана"));
        fanclist.add(s -> System.out.println("сработал второй метод, переменная " + s + " обработана"));
        fanclist.add(s -> System.out.println("сработал третий метод, переменная " + s + " обработана"));
        fanclist.add(s -> System.out.println("сработал четвёртый метод, переменная " + s + " обработана"));
    }

    public ArrayList<Consumer<Integer>> getFanclist() {
        return fanclist;

    }
}
