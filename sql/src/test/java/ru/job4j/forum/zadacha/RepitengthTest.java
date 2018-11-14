package ru.job4j.forum.zadacha;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class RepitengthTest {

    @Test
    public void sizemass() {
        Repitength repitength = new Repitength();
        String stroka = "11223344555556667789";
        Assert.assertThat(repitength.sizemass(stroka), Is.is(5));
    }
}