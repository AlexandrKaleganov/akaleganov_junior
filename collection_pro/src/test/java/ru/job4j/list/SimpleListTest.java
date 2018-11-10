package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleListTest {
    private SimpleList<Integer> list;

    @Before
    public void beforeTest() {
        list = new SimpleList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(1), is(2));
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(list.getSize(), is(3));
    }

    @Test
    public void whenAddThreeElementsThenUseDeleteResultTwo() {
        assertThat(list.get(0), is(3));
        list.delete();
        assertThat(list.get(0), is(2));
        list.add(3);
        assertThat(list.remove(1), is(2));
        System.out.println(list.get(1));

    }
    @Test
    public void whenElementsRemoveResul() {
        assertThat(list.get(1), is(2));
        assertThat(list.remove(1), is(2));
        assertThat(list.get(1), is(1));
    }

}