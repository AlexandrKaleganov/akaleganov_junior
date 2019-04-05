package ru.job4j.calculate;
/**
 * попробуем сделать универсальный класс для подбора выражений
 * реализация будет построена на очереди, одна из них будет блокирующая очередь
 * что -то типо ProductConsumer
 */

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;

class Calc {
    private final Integer arg = 24;

    private final BlockingDeque<LinkedList<String>> data = new LinkedBlockingDeque<>();

    private LinkedList<LinkedList<String>> random_znak = new LinkedList<>();
    private final StringBuilder temp = new StringBuilder();
    private volatile boolean stop = false;

    public boolean canBeEqualTo24(Integer[] nums) {
        //инициализация моей базы всевозможных вариантов символов
        this.make(new String[]{"+", "-", "/", "*"}, new LinkedList<>(), nums.length - 1, this.random_znak, true);
        System.out.println(this.random_znak);

        return true;
    }

    /**
     * метод который будет генерировать всевозможные варианты наборов чисел, они не должны почторяться
     * знаки могут повторяться для переключения генератора используем булеан селектор
     *
     * @param arr
     * @param indexes
     * @param expectedSize
     */
    void make(Object[] arr, Deque<Integer> indexes, int expectedSize, Queue<LinkedList<String>> data, Boolean selector) {
        try {
            while (this.data.size() == 1) {
                wait();
            }
            if (indexes.size() == expectedSize) {
                LinkedList<String> temp = new LinkedList<String>();

                for (Integer i : indexes) {
                    temp.add(String.valueOf(arr[i]));
                }
                if (temp.size() > 1) {
                    data.offer(temp);
                }
                return;
            }
            for (int i = 0; i < arr.length; i++) {
                if (!indexes.contains(i) || selector) {
                    indexes.addLast(i);
                    make(arr, indexes, expectedSize, data, selector);
                    indexes.removeLast();
                }
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    private void calc(LinkedList<String> num) {

        for (int i = 0; i < this.random_znak.size(); i++) {
            Queue<String> tem_znak = new LinkedList<>();
            tem_znak.addAll(random_znak.get(i));
            Queue<String> tem_num = new LinkedList<>();
            tem_num.addAll(num);
            while (num.size() < j && !exit) {
                temp = num.get(j) +
                        j++;
            }
        }
    }

    /**
     * поток будет добавлять 1 в очередь и будет переходить в режим ожидания
     */
    private class Product implements Runnable {
        private final BlockingDeque<LinkedList<String>> data;
        private final Integer[] nums;

        Product(Integer[] nums, BlockingDeque<LinkedList<String>> data) {
            this.data = data;
            this.nums = nums;
        }

        @Override
        public void run() {
            make(nums, new LinkedList<>(), nums.length, this.data, false);
        }
    }

    /**
     * поток будет добавлять 1 элемент но будет ждать у барьера пока не добавитсяпервый элемент
     */
    private class Consumer implements Runnable {
        private BlockingDeque<LinkedList<String>> data;

        Consumer(BlockingDeque<LinkedList<String>> data) {
            this.data = data;

        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    calc(this.data.take());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
