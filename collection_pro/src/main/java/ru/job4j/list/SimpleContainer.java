package ru.job4j.list;

import java.util.Iterator;

/**
 * интерфейс коллекшен))
 * @param <E>
 */

public interface SimpleContainer<E> extends Iterable<E> {
    boolean add(E e);
    E get(int index);

    @Override
    Iterator<E> iterator();
}
