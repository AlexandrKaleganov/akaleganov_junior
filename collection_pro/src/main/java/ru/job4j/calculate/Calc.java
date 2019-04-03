package ru.job4j.calculate;

import java.util.*;

public class Calc {

    //    private Queue<String> string = new LinkedList<>();
    private ArrayList<Queue<String>> rendom_list = new ArrayList<>();

    public boolean canBeEqualTo24(int[] nums) {
        this.make(nums, new LinkedList<>(), nums.length);
        System.out.println(rendom_list);
        return true;
    }

    /**
     * метод который будет генерировать всевозможные варианты наборов
     *
     * @param arr
     * @param indexes
     * @param expectedSize
     */
    void make(int[] arr, Deque<Integer> indexes, int expectedSize) {
        if (indexes.size() == expectedSize) {
            Queue<String> temp = new LinkedList<>();
            for (Integer i : indexes) {
                ((LinkedList<String>) temp).add(String.valueOf(arr[i]));
            }
            if (temp.size() > 1) {
                this.rendom_list.add(temp);
            }
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (!indexes.contains(i)) {
                indexes.addLast(i);
                make(arr, indexes, expectedSize);
                indexes.removeLast();
            }
        }
    }
}
