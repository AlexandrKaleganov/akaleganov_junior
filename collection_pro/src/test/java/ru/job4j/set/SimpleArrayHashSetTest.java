package ru.job4j.set;
/**
 * все тесты проходит
 */

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleArrayHashSetTest {
    private SimpleArrayHashSet<Integer> hashSet;

    @Before
    public void singUp() {
        hashSet = new SimpleArrayHashSet<Integer>();
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);
    }

    @Test
    public void add() {
        assertThat(hashSet.add(4), Is.is(true));
        assertThat(hashSet.add(5), Is.is(true));
        assertThat(hashSet.add(2), Is.is(false));
    }

    @Test
    public void contains() {
        assertThat(hashSet.contains(3), Is.is(true));
        assertThat(hashSet.contains(2), Is.is(true));
        assertThat(hashSet.contains(5), Is.is(false));

    }

    @Test
    public void remove() {
        assertThat(hashSet.contains(1), Is.is(true));
        assertThat(hashSet.remove(1), Is.is(true));
        assertThat(hashSet.contains(1), Is.is(false));
        assertThat(hashSet.contains(2), Is.is(true));
        assertThat(hashSet.remove(2), Is.is(true));
        assertThat(hashSet.contains(2), Is.is(false));
        assertThat(hashSet.isEmpty(), Is.is(true));
        assertThat(hashSet.remove(3), Is.is(true));
        assertThat(hashSet.isEmpty(), Is.is(false));
    }

    /**
     * проверим пересоздание хешсета
     *
     */
    @Test
    public void whenMasstoMAssMAx() {
        hashSet.add(4);
        hashSet.add(5);
        hashSet.add(6);
        hashSet.add(7);
        hashSet.add(8);
        hashSet.add(9);
        hashSet.add(10);
        hashSet.add(11);
        hashSet.add(12);
        assertThat(hashSet.getSize(), Is.is(12));
        assertThat(hashSet.contains(11), Is.is(true));
    }
}