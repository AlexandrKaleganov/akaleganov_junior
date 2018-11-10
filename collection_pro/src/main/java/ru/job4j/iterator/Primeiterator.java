package ru.job4j.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Создать итератор возвращающий только простые натуральные числа.
 * Простым является натуральное число больше 1,
 * которое делится без остатка только на 1 и на себя.  И БОЛЬШЕ НИНАЧТО НЕ ДЕЛЯТСЯ БЕЗ ОСТАТКА
 * в условии я старался проверить - если элемент делится без остатка на любое число коме 1 или самого себя - значит
 * значение фальш и число составное иначе оно простое, я не проверял можно ли делить числа на самого себя без остатка
 * или на 1 без остатка, т.к. интовые числа всегда будут делиться на 1 без остатка и сами на себя без остатка
 * второй массив будет счётчиком
 * hashNextlenght длинна хешнекста
 * value счётчик  элементов подходящщих по условию
 * index счётчик хода
 */

public class Primeiterator implements Iterator<Integer> {
    final private int[] arr;
    private int index;

    /**
     * конструктор
     *
     * @param arr
     */
    public Primeiterator(int[] arr) {
        this.arr = arr;

    }


    @Override
    public boolean hasNext() {

        return valid(index) && index < arr.length;
    }

    @Override
    public Integer next() {
        Integer res = null;
        if (hasNext()) {
            res = arr[index++];
        }
        if (res == null) {
            throw new NoSuchElementException();
        }
        return res;
    }

    /**
     * упростим код вынесем цикл в отдельный метод
     *
     * @return
     */
    private boolean valid(int i) {

        boolean result = true;
        for (; i < arr.length; i++) {
            if (arr[i] > 1) {
                for (int j = 2; j < arr[i]; j++) {
                    if (arr[i] % j == 0) {
                        result = false;
                        break;
                    } else {
                        result = true;
                    }
                }
                if (result) {
                    index = i;
                    break;
                }
            }
        }
        return result;
    }
}
