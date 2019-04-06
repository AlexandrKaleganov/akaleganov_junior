package ru.job4j.forum.combomass;

import java.util.Deque;
import java.util.LinkedList;

public class Cobomethod {
    public static void main(String[] args) {
        char[] strBox = {'a', 'b', 'c', 'd'};
        Deque<Integer> indexes = new LinkedList<>();
        make(strBox, indexes, 4);
    }

    static void make(char[] arr, Deque<Integer> indexes, int expectedSize) {
        if (indexes.size() == expectedSize) {
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
