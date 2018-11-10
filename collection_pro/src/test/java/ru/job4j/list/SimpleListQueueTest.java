package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleListQueueTest {

    SimpleListQueue<Integer> stack;
    Iterator<Integer> iterator;

    @Before
    public void singUp() {
        stack = new SimpleListQueue<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        iterator = stack.iterator();
    }


    @Test (expected = NoSuchElementException.class)
    public void whenpollTest() {
        assertThat(stack.getSize(), is(4));
        assertThat(stack.poll(), is(1));
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(3));
        assertThat(stack.getSize(), is(1));
        assertThat(stack.poll(), is(4));
        stack.poll();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenIteratorTest() {
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(4));
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }
}