package ru.job4j.list;
/**
 * реализация очереде на базе двусвязанного списка
 *
 */

import java.util.Iterator;

public class SimpleListQueue<E> extends SimpleLinkedList<E> {

    public void push(E e) {
        addLast(e);
    }

    public E poll() {
        return deleteFirst();
    }

    @Override
    public Iterator<E> iterator() {
        return super.iterator();
    }
}
