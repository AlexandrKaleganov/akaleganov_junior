package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayListTest {
    SimpleArrayList<Integer> simpleArrayList;
    Iterator<Integer> it;

    @Before
    public void setUp() {
        simpleArrayList = new SimpleArrayList<>(3);
        simpleArrayList.add(1);
        simpleArrayList.add(2);
        simpleArrayList.add(3);
        simpleArrayList.add(4);
        it = simpleArrayList.iterator();
    }

    @Test
    public void getSize() {
        SimpleArrayList<String> stroka = new SimpleArrayList<String>();
        assertThat(stroka.getSize(), is(0));
        assertThat(simpleArrayList.getSize(), is(4));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenaddElementandGetElementTest() {
        simpleArrayList.add(50);
        assertThat(simpleArrayList.get(4), is(50));
        simpleArrayList.get(5);
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator() {
            assertThat(it.hasNext(), is(true));
            assertThat(it.next(), is(1));
            assertThat(it.hasNext(), is(true));
            assertThat(it.next(), is(2));
            assertThat(it.hasNext(), is(true));
            assertThat(it.next(), is(3));
            assertThat(it.hasNext(), is(true));
            assertThat(it.next(), is(4));
            assertThat(it.hasNext(), is(false));
            it.next();
    }
}