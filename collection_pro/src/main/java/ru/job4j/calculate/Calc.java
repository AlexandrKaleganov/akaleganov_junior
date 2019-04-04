package ru.job4j.calculate;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Calc {
    private final Integer arg = 24;
    //    private Queue<String> string = new LinkedList<>();
    private ArrayList<ArrayList<String>> random_list = new ArrayList<>();
    private ArrayList<ArrayList<String>> random_znak = new ArrayList<>();
    private final StringBuilder temp = new StringBuilder();
    CyclicBarrier barrier = new CyclicBarrier(2);

    public boolean canBeEqualTo24(Integer[] nums) {
        this.make(nums, new LinkedList<>(), nums.length, this.random_list, false);
        System.out.println(random_list);
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
    void make(Object[] arr, Deque<Integer> indexes, int expectedSize, ArrayList<ArrayList<String>> data, Boolean selector) {
        if (indexes.size() == expectedSize) {
            ArrayList<String> temp = new ArrayList<String>();

            for (Integer i : indexes) {
                temp.add(String.valueOf(arr[i]));
            }
            if (temp.size() > 1) {
                data.add(temp);
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
    }

    private void calc(String e) {
        this.temp.append(e);
    }

    /**
     * поток будет добавлять 1 элемент и будет переходить в режим ожидания
     */
    private class Th1 implements Runnable {
        private final ArrayList<ArrayList<String>> lists;
        private final CyclicBarrier barrier;

        Th1(ArrayList<ArrayList<String>> lists, CyclicBarrier cyclicBarrier) {
            this.barrier = cyclicBarrier;
            this.lists = lists;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < lists.size(); i++) {
                    for (int i1 = 0; i1 < lists.get(i).size(); i1++) {
                        calc(lists.get(i).get(i1));
                        barrier.await();
                    }
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * поток будет добавлять 1 элемент но будет ждать у барьера пока не добавитсяпервый элемент
     */
    private class Th2 implements Runnable {
        private final CyclicBarrier barrier;
        private ArrayList<ArrayList<String>> lists;

        Th2(CyclicBarrier cyclicBarrier, ArrayList<ArrayList<String>> lists) {
            this.barrier = cyclicBarrier;
            this.lists = lists;

        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < lists.size(); i++) {
                    for (int i1 = 0; i1 < lists.get(i).size(); i1++) {
                        barrier.await();
                        calc(lists.get(i).get(i1));
                    }
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * хз что будет делать
     */
    private class Th3 implements Runnable {
        private final CyclicBarrier barrier;
        private ArrayList<ArrayList<String>> lists;
        private final StringBuilder bilder;

        Th3(CyclicBarrier cyclicBarrier, ArrayList<ArrayList<String>> lists, StringBuilder bilder) {
            this.barrier = cyclicBarrier;
            this.lists = lists;
            this.bilder = bilder;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < lists.size(); i++) {
                    for (int i1 = 0; i1 < lists.get(i).size(); i1++) {
                        barrier.await();
                        calc(lists.get(i).get(i1));
                    }
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
