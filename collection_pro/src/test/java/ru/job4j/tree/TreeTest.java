package ru.job4j.tree;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Alexander Kaleganov (mailto:alexmur07@mail.ru)
 * @version 2.0
 * @since 0.1
 */
public class TreeTest {
    @Test(expected = NoSuchElementException.class)
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<Integer>(1);
        assertThat(tree.add(1, 2), is(true));
        assertThat(tree.add(2, 5), is(true));
        assertThat(tree.add(1, 3), is(true));
        assertThat(tree.add(1, 4), is(true));
        assertThat(tree.add(4, 5), is(false)); //проверка сработала дубль мы не добавим
        assertThat(tree.add(5, 6), is(true));
        assertThat(tree.findBy(6).isPresent(), is(true));
        Iterator<Integer> iterator = tree.iterator();
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.next(), is(4));
        assertThat(iterator.next(), is(5));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(6));
        assertThat(iterator.hasNext(), is(false));
        iterator.next();

    }
    @Test
    public void whenisBinariTest() {
        Tree<Integer> tree = new Tree<Integer>(1);
        assertThat(tree.add(1, 2), is(true));
        assertThat(tree.add(2, 5), is(true));
        assertThat(tree.add(1, 3), is(true));
        assertThat(tree.add(1, 4), is(true));
        assertThat(tree.add(5, 6), is(true));
        assertThat(tree.isBinary(), is(false));
        Tree<Integer> binari = new Tree<>(1);
        binari.add(1, 2);
        binari.add(1, 3);
        binari.add(2, 5);
        binari.add(3, 6);
        binari.add(3, 7);
        assertThat(binari.isBinary(), is(true));



    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }
}
