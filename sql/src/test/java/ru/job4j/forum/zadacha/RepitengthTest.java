package ru.job4j.forum.zadacha;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class RepitengthTest {

    @Test
    public void sizemass() {
        Repitength repitength = new Repitength();
        int[] ints = {1, 2, 3, 5, 5, 4, 5, 6, 7, 1, 2, 3};
        Assert.assertThat(repitength.sizemass(ints), Is.is(2));
    }
}