package ru.job4j.calculate;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class CalcTest {

    @Test
    public void canBeEqualTo24() {
        Integer[] strBox = {4, 1, 8, 7};
        new Calc().canBeEqualTo24(strBox);
    }
}