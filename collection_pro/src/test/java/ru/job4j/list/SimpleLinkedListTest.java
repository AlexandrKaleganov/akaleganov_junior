package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleLinkedListTest {
    private SimpleLinkedList<Integer> list;
    private Iterator<Integer> it;

    @Before
    public void beforeTest() {
        list = new SimpleLinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        it = list.iterator();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(0), is(1));
        assertThat(list.get(1), is(2));
        assertThat(list.get(2), is(3));
        assertThat(list.get(3), is(4));
        list.get(4);
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        list.add(9);
        assertThat(list.getSize(), is(5));

    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorOne() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void iteratorTwo() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        list.add(8);
        it.next();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenDeleteLast() {
        assertThat(list.deleteLast(), is(4));
        list.get(3);
    }
}