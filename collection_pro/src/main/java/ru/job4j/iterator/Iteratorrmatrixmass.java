package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * итератор для двухмерного массива, делал поздно вечером и меньше переменных сделать не смог
 * метод next как и heshNext сделал универсальный для любого двухмерного массива
 */
public class Iteratorrmatrixmass implements Iterator<Integer> {
    final private int[][] arr;
    private int indexOne = 0;
    private int indexTwo = 0;

    public Iteratorrmatrixmass(int[][] arr) {
        this.arr = arr;
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    @Override
    public boolean hasNext() {
        boolean res = false;

        for (; !res && indexOne < arr.length;) {
            for (int i = indexOne; i < this.arr.length; i++) {
                for (int j = indexTwo; j < this.arr[i].length; j++) {
                    res = true;
                    break;
                }
                if (!res && indexOne < arr.length) {
                    indexOne++;
                    indexTwo = 0;
                }
            }
        }
        return res;
    }

    @Override
    public Integer next() {
        Integer res = null;
        if (hasNext()) {
            res = arr[indexOne][indexTwo++];

        } else {
            throw new NoSuchElementException();
        }
        return res;
    }

}
