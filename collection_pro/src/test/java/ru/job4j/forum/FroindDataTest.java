package ru.job4j.forum;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FroindDataTest {

    @Test
    public void add() {
        FroindData data = new FroindData();
        String one = "Mike Nike";
        String two = "Oleg Petr";
        String fri = "Mike Petr";
        String five = "Ivan Oleg";
        data.add(one);
        data.add(two);
        data.add(fri);
        data.add(five);
        System.out.println(data.get("Ivan"));
        System.out.println(data.get("Mike"));
        System.out.println(data.get("Nike"));
        System.out.println(data.get("Oleg"));
        System.out.println(data.get("Petr"));

        assertThat(data.get("Ivan"), is("Ivan дружит с : Oleg"));
        assertThat(data.get("Mike"), is("Mike дружит с : Nike, Petr"));
        assertThat(data.get("Nike"), is("Nike дружит с : Mike"));
        assertThat(data.get("Oleg"), is("Oleg дружит с : Petr, Ivan"));
        assertThat(data.get("Petr"), is("Petr дружит с : Mike, Oleg"));
    }
}