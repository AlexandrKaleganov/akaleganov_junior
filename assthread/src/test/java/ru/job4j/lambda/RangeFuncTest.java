package ru.job4j.lambda;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class RangeFuncTest {

    /**
     * линейниая функция
     */
    @Test
    public void fancTestLine() {
        RangeFunc func = new RangeFunc();
        List<Double> result = func.diapason(0, 3, (value) -> 8 / 2 * value + 2);
        Assert.assertThat(result, Is.is(Arrays.asList(2D, 6D, 10D, 14D)));
    }

    /**
     * квадратичная функия
     */
    @Test
    public void fancTestSquare() {
        RangeFunc func = new RangeFunc();
        List<Double> result = func.diapason(0, 3, (value) -> Math.pow(value, 2));
        Assert.assertThat(result, Is.is(Arrays.asList(0D, 1D, 4D, 9D)));
    }

    /**
     * логорифмическая функция
     */
    @Test
    public void funcTestLog() {
        RangeFunc func = new RangeFunc();
        List<Double> result = func.diapason(8, 10, (value) -> Math.log(value) / Math.log(2));
        Assert.assertThat(result.get(0), Is.is(3D));

    }
}