package ru.job4j.list;
/**
 * реализация стека на базе двусвязанного списка
 *
 */
import java.util.Iterator;

    public class SimpleStack<E> extends SimpleLinkedList<E> {

    public void push(E e) {
        addFirst(e);
    }

    public E poll() {
        return deleteFirst();
    }

    @Override
    public Iterator<E> iterator() {
        return super.iterator();
    }
}
