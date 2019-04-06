package ru.job4j.calculate;

import org.junit.Test;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;

import static org.junit.Assert.*;

public class CalcTest {

    @Test
    public void canBeEqualTo24() throws InterruptedException, BrokenBarrierException {
        Integer[] strBox = {10, 10, 10, 10, 10};
        new Calc(50.0).canBeEqualTo24(strBox);
//        HashMap<String>
    }
}