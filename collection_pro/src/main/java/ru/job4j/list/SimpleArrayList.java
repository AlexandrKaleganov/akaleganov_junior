package ru.job4j.list;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayList<E> implements SimpleContainer<E> {
    private Object[] object;
    private int index = 0;
    private int size = 0;

    /**
     * конструктор созания динамического массива с указанием размера
     */
    public SimpleArrayList(int size) {
        this.object = new Object[size];
    }

    //просто создал конструктор позволяющий копировать нашь динамический массив
    public SimpleArrayList(SimpleArrayList<E> list) {
        this.object = new Object[10];
        for (int i = 0; i < list.getSize(); i++) {
            this.add(list.get(i));
        }
    }

    /**
     * конструктор создания динамического массива без указания массива
     */
    public SimpleArrayList() {
        this.size = 0;
        this.object = new Object[10];
    }

    /**
     * конструктор создания динамического массива с в который сразу передаётся массив
     */
    public SimpleArrayList(E[] arr) {
        this.object = arr;
        this.size = object.length;
        this.index = object.length;
    }

    /**
     * при добавлении элемента проверяем если нашь внутренний массив закончился то мы увеличим
     * его размер в полтора раза
     * <p>
     * этот метод немного изменил, присутствовали грубые ошибки
     */
    @SuppressWarnings("UnusedReturnValue")
    private boolean isSizeTwo(int index) {
        boolean res = true;
        if (index < object.length - 1) {
            res = false;
        } else {
            Object[] temp = new Object[object.length + object.length / 2];
            System.arraycopy(object, 0, temp, 0, object.length);
            this.object = temp;
        }
        return res;
    }

    /**
     * получение размера нашего аррай листа
     *
     * @return size
     */
    public int getSize() {
        if (this.size < this.index) {
            this.size = this.index;
        }
        return this.size;
    }

    /**
     * добавить элемент
     */
    @Override
    public boolean add(E element) {
        isSizeTwo(index);
        this.object[this.index++] = element;
        return true;
    }

    /**
     * вернуть элемент по индексу
     *
     * @return object
     */
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        if (!(index < getSize())) {
            throw new IndexOutOfBoundsException("IndexOutOfBounds");
        } else {
            return (E) this.object[index];
        }
    }

    /**
     * вернёт тру если элемент есть в листе
     *
     * @return boolean
     */
    public boolean contains(E e) {
        return !(indexOf(e) == -1);
    }

    /**
     * возвращает индекс элемента ,
     * если элемент отсутствует то возвращает -1
     */
    public int indexOf(E e) {
        int res = -1;
        for (int i = 0; i < this.object.length; i++) {
            if (this.object[i] == e) {
                res = i;
            }
        }
        return res;
    }

    /**
     * удаляет элемент по индексу и уменьшает рамер массива
     */
    @SuppressWarnings("unchecked")
    public E remove(int i) {
        Object oldElement = this.get(i);
        for (; i < this.getSize() - 1; i++) {
            this.object[i] = this.object[i + 1];
        }
        this.size--;
        return (E) oldElement;
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorArray();
    }

    private class IteratorArray implements Iterator<E> {
        private int cursor = 0;


        @Override
        public boolean hasNext() {
            return getSize() > cursor;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("NoSuchElement");
            } else {
                return (E) object[cursor++];
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }
}
