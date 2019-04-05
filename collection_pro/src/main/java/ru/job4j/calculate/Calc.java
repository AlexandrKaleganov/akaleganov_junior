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

    private final BlockingDeque<ArrayList<String>> data = new LinkedBlockingDeque<>();

    private LinkedList<ArrayList<String>> random_znak = new LinkedList<>();
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
    void make(Object[] arr, Deque<Integer> indexes, int expectedSize, Queue<ArrayList<String>> data, Boolean selector) {
        try {
            while (this.data.size() == 1) {
                wait();
            }
            if (indexes.size() == expectedSize) {
                ArrayList<String> temp = new ArrayList<String>();

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

    private void calc(ArrayList<String> num) {
        boolean exit = false;
        String temp;
        Boolean rsl;
        int j = 0;
        for (int i = 0; i < this.random_znak.size(); i++) {
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
        private final BlockingDeque<ArrayList<String>>  data;
        private final Integer[] nums;

        Product(Integer[] nums, BlockingDeque<ArrayList<String>> data) {
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
        private BlockingDeque<ArrayList<String>> data;
        Consumer(BlockingDeque<ArrayList<String>> data) {
            this.data = data;

        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){

            }
        }
    }
//
//    /**
//     * хз что будет делать
//     */
//    private class Th3 implements Runnable {
//        private final CyclicBarrier barrier;
//        private ArrayList<ArrayList<String>> lists;
//        private final StringBuilder bilder;
//
//        Th3(CyclicBarrier cyclicBarrier, ArrayList<ArrayList<String>> lists, StringBuilder bilder) {
//            this.barrier = cyclicBarrier;
//            this.lists = lists;
//            this.bilder = bilder;
//        }
//
//        @Override
//        public void run() {
//            try {
//                for (int i = 0; i < lists.size(); i++) {
//                    for (int i1 = 0; i1 < lists.get(i).size(); i1++) {
//                        barrier.await();
//                        calc(lists.get(i).get(i1));
//                    }
//                }
//            } catch (InterruptedException | BrokenBarrierException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
