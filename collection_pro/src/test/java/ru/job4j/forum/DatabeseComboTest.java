package ru.job4j.forum;

import org.junit.Test;
import ru.job4j.forum.combomass.DatabeseCombo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class DatabeseComboTest {

    @Test
    public void cobo() {
        char[] strBox = {'a', 'b', 'c', 'd', 'f'};
        DatabeseCombo compbo = new DatabeseCombo(strBox);
        ArrayList<char[]> res = compbo.getRsl();
        res.forEach(el -> {
            for (int i = 0; i < el.length; i++) {
                System.out.print(el[i] + " ");
            }
            System.out.println("");
        });
    }

    @Test
    public void testic() {
        char[] strBox = {'a', 'b', 'c', 'd', 'f'};
        Deque<Integer> indexes = new LinkedList<>();
        Combo makeoneReturn = new Combo();
        makeoneReturn.makeOneReturn(strBox, indexes, 3);
        Combo makeoneNONOReturn = new Combo();
        makeoneNONOReturn.makeTwoNonReturn(strBox, indexes, 3);
        //тестируем разницы никакой
        assertThat(makeoneReturn.rsl, is(makeoneNONOReturn.rsl));

    }

    private static class Combo {
        private String rsl;

        public String getRsl() {
            return rsl;
        }

        //вот сделал два метода с ретурном и без
        private void makeOneReturn(char[] arr, Deque<Integer> indexes, int expectedSize) {
            if (indexes.size() == expectedSize) {
                for (Integer i : indexes) {
                    System.out.print(arr[i] + " ");
                    rsl = rsl + arr[i];
                }
                System.out.println();
                return;
            }
            for (int i = 0; i < arr.length; i++) {
                if (!indexes.contains(i)) {
                    indexes.addLast(i);
                    makeOneReturn(arr, indexes, expectedSize);
                    indexes.removeLast();
                }
            }
        }
        private void makeTwoNonReturn(char[] arr, Deque<Integer> indexes, int expectedSize) {

            if (indexes.size() == expectedSize) {
                for (Integer i : indexes) {
                    System.out.print(arr[i] + " ");
                    rsl = rsl + arr[i];
                }
                System.out.println();
            }
            for (int i = 0; i < arr.length; i++) {
                if (!indexes.contains(i)) {
                    indexes.addLast(i);
                    makeTwoNonReturn(arr, indexes, expectedSize);
                    indexes.removeLast();
                }
            }
        }

    }
}