package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SynhronSimpleLinkedList<E> {
    @GuardedBy("this")
    private final SimpleLinkedList<E> data;

    public SynhronSimpleLinkedList() {
        this.data = new SimpleLinkedList<E>();

    }

    public synchronized boolean add(E e) {
        return this.data.add(e);
    }

    public synchronized void addLast(E e) {
        this.data.addLast(e);
    }

    public synchronized void addFirst(E e) {
        this.data.addFirst(e);
    }

    public synchronized E get(int i) {
        return this.data.get(i);
    }

    public synchronized int getSize() {
        return this.data.getSize();
    }

    public synchronized E getFirst() {
        return this.data.getFirst();
    }

    public synchronized E getLast() {
        return this.data.getLast();
    }

    public synchronized Iterator<E> iterator() {
        return copy(this.data).iterator();
    }

    //создал конструткор
    private SimpleLinkedList<E> copy(SimpleLinkedList<E> data) {
        return new SimpleLinkedList<>(data);
    }
}
