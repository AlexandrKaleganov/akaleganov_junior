package ru.job4j.forum.zadacha;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class RepitengthTest {

    @Test
    public void sizemass() {
        Repitength repitength = new Repitength();
        int[] ints = {5, 7, 6, 6, 8, 1, 2, 2, 7, 4, 3}; //индекс 7 размер 4
        int[] ints1 = {5, 7, 7, 4, 3, 8, 2, 2, 7, 1};   //индекс 2  размер 5
        int[] ints2 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};  // 0
        int[] ints3 = {1, 2, 3, 1, 1, 1, 1, 2, 3};          // индекс 6 размер 3
        int[] ints4 = {4, 6, 1, 2, 1, 2, 3, 2};        //индекс 0  размер 4
        repitength.siseres(ints);
        repitength.siseres(ints1);
        repitength.siseres(ints2);
        repitength.siseres(ints3);
        repitength.siseres(ints4);
    }
}