package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * итератор будет принимать  список чисел и итерация будет идти только по чётным числам
 * также создал второй массив с тремя значениями  второй массив у нас является всего лишь счётчиком
 * просто так удобней  хранить значения
 * hashNextlenght длинна хешнекста
 * value счётчик  элементов подходящщих по условию
 * index счётчик хода
 */


public class EvennumbersIterator implements Iterator<Integer> {
    private final int[] arr;
    private int hashNextlenght;
    private int value;
    private int index;

    EvennumbersIterator(int[] arr) {
        this.arr = arr;
        valid(0, false);
    }

    @Override
    public boolean hasNext() {
        return hashNextlenght > value;
    }

    @Override
    public Integer next() {
        Integer res = null;
        if (valid(index, true) && index < arr.length) {
            res = this.arr[index++];
            value++;
        }
        if (res == null) {
            throw new NoSuchElementException();
        }
        return res;
    }

    /**
     * refactorcode
     */
    public boolean valid(int i, boolean stop) {
        boolean result = false;
        for (; i < arr.length; i++) {
            if (arr[i] % 2 == 0) {
                result = true;
                if (stop) {
                    index = i;
                    break;
                } else {
                    hashNextlenght++;
                }
            }
        }
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}