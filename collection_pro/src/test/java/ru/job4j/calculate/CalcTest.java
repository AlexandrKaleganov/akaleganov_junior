package ru.job4j.calculate;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;

import static org.hamcrest.Matchers.is;

public class CalcTest {
    /**
     * тесты решения поражают))
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    @Test
    public void canBeEqualTo24() throws InterruptedException, BrokenBarrierException {
        Integer[] strBox = {4, 1, 8, 7};
        Integer[] strBox1 = {4, 1, 8, 3};
        Integer[] strBoxfalse = {1, 1, 1, 1};
        Integer[] strTestRandom = {20, 20, 40, 15, 4, 1};
        Assert.assertThat(new Calc(24.0).canBeEqualTo24(strBox1), is(true));
        Assert.assertThat(new Calc(24.0).canBeEqualTo24(strBox), is(true));
        Assert.assertThat(new Calc(24.0).canBeEqualTo24(strBoxfalse), is(false));
        Assert.assertThat(new Calc(100.0).canBeEqualTo24(strTestRandom), is(true));
    }
}