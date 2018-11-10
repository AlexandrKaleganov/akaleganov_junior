package ru.job4j.forum;

import org.junit.Test;

import java.util.*;

public class Usertest {

    private static class User {
        private final String name;

        @SuppressWarnings("SameParameterValue")
        private User(String name) {
            this.name = name;
        }
    }

    @Test

    public void wherEqualsObjectUsertest() {
        User vasia = new User("Vasia");
        User vasia2 = new User("Vasia");
        String kolia = "1";
        String kolia2 = "1";
        // вот тут будет фальш, т.к. идёт сравнение ссылок
        System.out.println("(vasia == vasia2):" + (vasia == vasia2));
        System.out.println("(kolia == kolia2):" + (kolia == kolia2));
        TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });
        set.add(1);
        set.add(2);
        set.add(1);
        System.out.println(set);
    }
}
