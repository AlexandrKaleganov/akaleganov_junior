package ru.job4j.forum.disp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Consumer;


public class DispmethodTest {

    @Test
    public void getFanclist() {
        Dispmethod dispmethod = new Dispmethod();
        ArrayList<Consumer<Integer>> list = dispmethod.getFanclist();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).accept(i);
        }
    }
}