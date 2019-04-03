package ru.job4j.calculate;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class CalcTest {

    @Test
    public void canBeEqualTo24() {
        int[] strBox = {4, 1, 8, 7};
        Deque<Integer> indexes = new LinkedList<>();
        new Calc().make(strBox, indexes, 4);
    }
}