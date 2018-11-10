package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;


import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleArrayTest {
    private Iterator<Integer> it;
    private SimpleArray<Integer> test;

    @Before
    public void setUp() {
        test = new SimpleArray<Integer>(new Integer[]{1, 2, 3});
        it = test.iterator();
    }

    @Test (expected = NoSuchElementException.class)
    public void shouldReturnEvenNumbersSequentially() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void whenaddTest() {
        test.add(12);
        SimpleArray<String> stroka = new SimpleArray<String>(2);
        assertThat(stroka.add("вася"), is(true));
        assertThat(stroka.add("саша"), is(true));
        assertThat(stroka.getT(1), is("саша"));
    }

    @Test
    public void whrnSetelementToUniversalMassiv() {
        assertThat(test.set(2, 4), is(3));
        assertThat(test.getT(2), is(4));
    }

    @Test
    public void whengetTElementTest() {
        assertThat(test.getT(0), is(1));
        assertThat(test.getT(1), is(2));
        assertThat(test.getT(2), is(3));
    }

    @Test
    public void whendeleteElementTest() {
        assertThat(test.delete(0), is(1));
        assertThat(test.getT(0), is(2));
        assertThat(test.getT(1), is(3));
        Integer ekcepted = null;
        assertThat(test.getT(2), is(ekcepted));
    }

}