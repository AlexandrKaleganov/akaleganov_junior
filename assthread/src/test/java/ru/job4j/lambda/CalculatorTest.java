package ru.job4j.lambda;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CalculatorTest {

    @Test
    public void multipleTest() {
        Calculator calc = new Calculator();
        List<Double> buffer = new ArrayList<>();
        calc.multiple(
                0, 2, 1,
                MathUtil::add,
                buffer::add
        );
        Assert.assertThat(buffer, Is.is(Arrays.asList(1D, 2D, 3D)));
    }
}