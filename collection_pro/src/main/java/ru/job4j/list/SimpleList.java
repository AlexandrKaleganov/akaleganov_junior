package ru.job4j.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * однонаправленный список, походу он работает как стек, последним зашёл первый вышел
 * если я всё правильно понял то удаляю элементы с самого начала , т.е. с последнего зашедшего элемента,
 * так понимаю first ссылается на последний добавленный элемент но считается первым на очереди,
 * спасибо за задание было очень позновательно
 *
 * @param <E>
 */
public class SimpleList<E> implements Iterable<E> {
    private int size;
    private Node<E> first;

    public void add(E date) {
        Node<E> newLink = new Node<E>(date);
        newLink.next = this.first;
        this.first = newLink;
        this.size++;
    }

    public E delete() {
        isCheckelement();
        Node<E> temp = first;
        this.first = temp.next;
        size--;
        return temp.element;
    }

    public E remove(int index) {
        Node<E> removeElement = searshNode(index);
        Node<E> temp = searshNode(index - 1);
        temp.next = removeElement.next;
        size--;
        return removeElement.element;

    }


    /**
     * получить элемент по индексу
     *
     * @param index
     * @return
     */
    public E get(int index) {
        return searshNode(index).element;
    }

    /**
     * приватный метод будет возвращать нам ссылку
     * поиск ссылки вынесли в отдельный метод, т.к. один и тот же поиск используется в
     * двух методах get(int index)   и  remove(int index)
     *
     * @param index
     * @return
     */
    private Node<E> searshNode(int index) {
        Node<E> result = this.first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        Node(E element) {
            this.element = element;
        }

    }
    private void isCheckelement() {
        if (!(size > 0)) {
            throw new NoSuchElementException("в контейнере отсутствуют элементы");
        }
    }
    @Override
    public Iterator<E> iterator() {
        return new Itr<E>();
    }

    private class Itr<E> implements Iterator<E> {
        private Node iter =  first;

        @Override
        public boolean hasNext() {
            return !(iter == null);
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            E element = null;
            if (!hasNext()) {
                throw new NoSuchElementException("элементы закончились");
            } else {
                element = (E) iter.element;
                iter = iter.next;
            }
            return element;
        }


        @Override
        public void remove() {
            throw new UnsupportedOperationException("это тут не работает");
        }
    }
}
