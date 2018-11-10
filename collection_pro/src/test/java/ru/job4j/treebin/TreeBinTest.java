package ru.job4j.treebin;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class TreeBinTest {


    @Test
    public void addandfindByLinkrootTest() {
        TreeBin<Integer> treebinary = new TreeBin<Integer>();
        treebinary.add(1);
        treebinary.add(2);
        treebinary.add(3);
        treebinary.add(3);
        treebinary.add(4);
        treebinary.add(6);
        treebinary.add(33);
        treebinary.add(55);
        assertThat(treebinary.findBylinkroot(22).getValue(), Is.is(33));
        assertThat(treebinary.findBylinkroot(40).getValue(), Is.is(55));


    }

    @Test
    public void whenNodeeqLeftTest() {
        Node<Integer> testNode = new Node<>(4);
        assertThat(testNode.eqLeft(4), Is.is(true));
        assertThat(testNode.eqLeft(3), Is.is(true));
        assertThat(testNode.eqLeft(5), Is.is(false));


    }

    @Test(expected = NoSuchElementException.class)
    public void findBy() {
        TreeBin<Integer> treebinary = new TreeBin<Integer>();
        treebinary.add(1);
        treebinary.add(2);
        treebinary.add(3);
        treebinary.add(3);
        treebinary.add(4);
        treebinary.add(6);
        treebinary.add(55);
        assertThat(treebinary.findBy(6).get().getValue(), Is.is(6));
        assertThat(treebinary.findBy(55).get().getValue(), Is.is(55));
        assertThat(treebinary.findBy(1).get().getValue(), Is.is(1));
        treebinary.findBy(14).get();


    }

    @Test(expected = NoSuchElementException.class)
    public void iterator() {
        TreeBin<Integer> treebinary = new TreeBin<Integer>();
        treebinary.add(1);
        treebinary.add(77);
        treebinary.add(55);
        treebinary.add(33);
        treebinary.add(64);
        treebinary.add(85);
        Iterator<Integer> itr = treebinary.iterator();
        assertThat(itr.hasNext(), Is.is(true));
        assertThat(itr.next(), Is.is(1));
        assertThat(itr.next(), Is.is(77));
        assertThat(itr.next(), Is.is(55));
        assertThat(itr.next(), Is.is(85));
        assertThat(itr.next(), Is.is(33));
        assertThat(itr.hasNext(), Is.is(true));
        assertThat(itr.next(), Is.is(64));
        assertThat(itr.hasNext(), Is.is(false));
        itr.next();
    }
}