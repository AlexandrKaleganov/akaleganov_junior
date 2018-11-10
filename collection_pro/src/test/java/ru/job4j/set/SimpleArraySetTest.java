package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleArraySetTest {
    private SimpleArraySet<Integer> setarray;

    @Before
    public void singUp() {
        setarray = new SimpleArraySet<Integer>();
    }

    @Test
    public void addSimpleSetTest() {
        assertThat(setarray.add(1), is(true));
        assertThat(setarray.add(1), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenIteratorSimplesetTest() {
        setarray.add(1);
        setarray.add(2);
        setarray.add(3);
        Iterator<Integer> it = setarray.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(false));
        it.next();
    }
}