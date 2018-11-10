package ru.job4j.set;


import ru.job4j.list.SimpleLinkedList;

import java.util.Iterator;

@SuppressWarnings("WeakerAccess")
public class SimpleLinkedSet<E> {

    private final SimpleLinkedList<E> list;


    SimpleLinkedSet() {
        this.list = new SimpleLinkedList<E>();
    }

    public boolean add(E e) {
        boolean res = false;
        if (!this.list.contains(e)) {
            res = true;
            this.list.add(e);
        }
        return res;
    }


    public Iterator<E> iterator() {
        return this.list.iterator();
    }
}
