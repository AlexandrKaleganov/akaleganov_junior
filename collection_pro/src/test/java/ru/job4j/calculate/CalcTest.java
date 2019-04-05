package ru.job4j.calculate;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class CalcTest {

    @Test
    public void canBeEqualTo24() throws InterruptedException{
        Integer[] strBox = {4, 1, 8, 7};
        new Calc(24.0).canBeEqualTo24(strBox);
    }
}