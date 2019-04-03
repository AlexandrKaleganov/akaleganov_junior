package ru.job4j.calculate;

import java.util.*;

public class Calc {

//    private Queue<String> string = new LinkedList<>();
private ArrayList<Queue<String>> rendom_list = new ArrayList<>();
    public boolean canBeEqualTo24(int[] nums) {
//        int expected = 24;
//        char[] sim = new char[]{'+', '-', '*', '/'};
//        for (int i = 0; i < nums.length; i++) {
//        }
//        System.out.println(nums[0] + sim[0] + nums[1] + sim[0] + nums[2] + sim[0] + nums[1]);
        return true;
    }

    /**
     * метод который будет генерировать всевозможные варианты наборов
     * @param arr
     * @param indexes
     * @param expectedSize
     */
    void make(int[] arr, Deque<Integer> indexes, int expectedSize) {
        if (indexes.size() == expectedSize) {
            Queue<String> temp = new LinkedList<>();
            for (Integer i : indexes) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
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
