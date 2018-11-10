package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@SuppressWarnings("ALL")
@ThreadSafe
public class SynhronSimpleArrayList<E> implements SimpleContainer<E> {
    @GuardedBy("this")
    private final SimpleArrayList<E> database;

    @SuppressWarnings("SpellCheckingInspection")
    public SynhronSimpleArrayList() {
        this.database = new SimpleArrayList<>();
    }

    public synchronized boolean add(E e) {
        return this.database.add(e);
    }

    public synchronized E remove(int i) {
        return this.database.remove(i);
    }

    public synchronized int getSize() {
        return this.database.getSize();
    }

    @SuppressWarnings("unused")
    public synchronized boolean contains(E e) {
        return this.database.contains(e);
    }

    public synchronized int indexOf(E e) {
        return this.database.indexOf(e);
    }

    public synchronized Iterator<E> iterator() {
        return copy(this.database).iterator();
    }

    public synchronized E get(int i) {
        return this.database.get(i);
    }

    private SimpleArrayList<E> copy(SimpleArrayList<E> database) {
        return new SimpleArrayList<>(database);

    }
}
