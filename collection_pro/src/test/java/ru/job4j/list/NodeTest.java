package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class NodeTest {
    private Node<Integer> first;
    private Node<Integer> two;
    private Node<Integer> third;
    private Node<Integer> four;

    @Before
    public void singUP() {
        first = new Node<Integer>(1);
        two = new Node<Integer>(2);
        third = new Node<Integer>(3);
        four = new Node<Integer>(4);
    }

    @Test
    public void whenLincCikleTestOne() {
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;
        assertThat(first.hasCycle(first), is(true));
    }

    @Test
    public void whenLincCikleTestTwo() {
        first.next = two;
        two.next = third;
        third.next = two;
        four.next = first;
        assertThat(first.hasCycle(first), is(true));

    }

    @Test
    public void whenLincCikleTestTreeFalse() {
        first.next = two;
        two.next = third;
        third.next = four;
        assertThat(first.hasCycle(first), is(false));
    }

    @Test
    public void whenLincCikleTestTreeFalseTwo() {
        assertThat(first.hasCycle(first), is(false));
    }
}