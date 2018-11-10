package ru.job4j.set;

import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;

@SuppressWarnings("ALL")
public class SimpleArraySet<E> {
    SimpleArrayList<E> list;

    SimpleArraySet(int size) {
        list = new SimpleArrayList<E>(size);
    }

    SimpleArraySet() {
        list = new SimpleArrayList<E>();
    }

    SimpleArraySet(E[] arr) {
        list = new SimpleArrayList<E>(arr);
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
