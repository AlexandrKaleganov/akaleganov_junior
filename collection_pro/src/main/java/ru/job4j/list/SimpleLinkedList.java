package ru.job4j.list;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * реализация двусвязанного списка
 * пришлось маленько его допилить чтобы на его базе селать стек и очередь
 * добавлены методы удаление из начала списка и удаление из конца списка также
 * добавлены методы добавление в нчало и добавление в конец
 */
public class SimpleLinkedList<E> implements SimpleContainer<E> {

    private int size = 0;
    private int modCount = 0;
    private Node<E> first;
    private Node<E> last;

    /**
     * создадим конструктор по умолчанию
     */
    public SimpleLinkedList() {

    }

    public SimpleLinkedList(SimpleLinkedList<E> linkedList) {
        Node<E> node = linkedList.first;
        while (node != null) {
            this.add(node.date);
            node = node.next;
        }
    }

    /**
     * данный метод добавляет элемент именно в конец списка
     * основан на методе addLs
     */
    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    /**
     * добавление элемента в начало
     */
    public void addFirst(E e) {
        Node<E> newLink = new Node<>(e);
        if (first == null) {
            refPrime(newLink);
        } else {
            newLink.next = first;
            newLink.previos = first;
            first = newLink;
        }
        this.size++;
        modCount++;
    }

    /**
     * добавление элемента в конец
     */
    public void addLast(E e) {
        Node<E> newLink = new Node<>(e);
        if (first == null) {
            refPrime(newLink);
        } else {
            last.next = newLink;
            newLink.previos = last;
            this.last = newLink;
        }
        this.size++;
        modCount++;
    }

    /**
     * рефакторинг добавления самог первого элемента происходит везде одинаково
     * добавляется он в конец или в начало
     */
    private void refPrime(Node<E> newLink) {
        newLink.previos = first;
        first = newLink;
        newLink.next = this.last;
        this.last = newLink;
    }

    /**
     * метод не просто возвращает элемент,
     * а проверяет с какой стороны быстрее добраться до элемента контейнера
     */
    @Override
    public E get(int index) {
        whenIndexofBound(index);
        Node<E> result = null;
        if (isCheck(index)) {
            result = first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = getSize() - 1; i > index; i--) {
                result = last.previos;
            }
        }
        return result.date;
    }


    /**
     * узнать размер нашего двусвязанного списка
     */
    public int getSize() {
        return size;
    }

    /**
     * данный метод проверяет существует ли данный индекс
     * и сотри с какой стороны быстрее искать искомый индекс
     */
    private boolean isCheck(int index) {
        whenIndexofBound(index);
        return index + 1 <= getSize() / 2;
    }

    private static class Node<E> extends SimpleLinkedList {
        E date;
        Node<E> next;
        Node<E> previos;

        Node(E date) {
            this.date = date;
        }

        @Override
        public int hashCode() {
            return date.hashCode();
        }
    }

    /**
     * удаление первого элемента
     */
    public E deleteFirst() {
        isCheckelement();
        Node<E> temp = first;
        if (this.size == 1) {
            this.first = temp.next;
        } else {
            this.first = temp.next;
            first.previos = temp.previos;
        }
        modCount++;
        size--;
        return temp.date;
    }

    /**
     * удаление последнего элемента
     */
    public E deleteLast() {
        isCheckelement();
        Node<E> temp = last;
        if (this.size == 1) {
            this.last = temp.previos;
        } else {
            this.last = last.previos;
            last.next = temp.next;
        }
        modCount++;
        size--;
        return temp.date;
    }

    /**
     * вернуть последний элемент
     */
    public E getLast() {
        return last.date;
    }

    /**
     * вернуть первый элемент
     */
    public E getFirst() {
        return first.date;
    }

    private int getModCount() {
        return modCount;
    }

    /**
     * проверка есть ли в контейнере элементы
     */
    private void isCheckelement() {
        if (!(size > 0)) {
            throw new NoSuchElementException("в контейнере отсутствуют элементы");
        }
    }

    /**
     * проверяет выходим ли мы за рамки существующих элементов
     * и проверяет эсть ли хотябы один элемент
     */
    private void whenIndexofBound(int index) {
        if (size - 1 < index || size == 0) {
            throw new IndexOutOfBoundsException("данного индекса не существует или вы вывалились за пределы массива");
        }
    }

    /**
     * вернёт тру если элемент есть в листе
     */
    public boolean contains(E e) {
        return !(indexOf(e) == -1);
    }

    /**
     * возвращает индекс элемента ,
     * если элемент отсутствует то возвращает -1
     */
    private synchronized int indexOf(E e) {
        int res = -1;
        Node temp = first;
        for (int i = 0; i < size; i++) {
            if (temp.date == e) {
                res = i;
                break;
            }
            temp = temp.next;
        }
        return res;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr<>();
    }

    private class Itr<E> implements Iterator<E> {
        private Node iter = first;
        private int check = getModCount();


        @Override
        public boolean hasNext() {
            testCheck();
            return !(iter == null);
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            testCheck();
            E element = null;
            if (!hasNext()) {
                throw new NoSuchElementException("элементы закончились");
            } else {
                element = (E) iter.date;
                iter = iter.next;
            }
            return element;
        }

        private void testCheck() {
            if (!(check == getModCount())) {
                throw new ConcurrentModificationException("массив был изменён интерация остановлена");
            }
        }
    }
}
