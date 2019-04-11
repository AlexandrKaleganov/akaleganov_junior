package ru.job4j.calculate;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;

import static org.hamcrest.Matchers.is;

public class CalcTest {
    /**
     * тесты решения
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    @Test
    public void canBeEqualTo1() throws InterruptedException, BrokenBarrierException {
        Integer[] strBox = {4, 1, 8, 7};
        Assert.assertThat(new Calc(24.0).canBeEqualTo24(strBox), is(true));
    }
    @Test
    public void canBeEqualTo2 () throws InterruptedException, BrokenBarrierException {
        Integer[] strBox1 = {4, 1, 8, 3};
        Assert.assertThat(new Calc(24.0).canBeEqualTo24(strBox1), is(true));
    }
    @Test
    public void canBeEqualTo3() throws InterruptedException, BrokenBarrierException {
        Integer[] strBoxfalse = {1, 1, 1, 1};
        Assert.assertThat(new Calc(24.0).canBeEqualTo24(strBoxfalse), is(false));
    }
    @Test
    public void canBeEqualTo4() throws InterruptedException, BrokenBarrierException {
        Integer[] strTestRandom = {20, 20, 40, 15, 4, 1};
        Assert.assertThat(new Calc(100.0).canBeEqualTo24(strTestRandom), is(true));
    }
}