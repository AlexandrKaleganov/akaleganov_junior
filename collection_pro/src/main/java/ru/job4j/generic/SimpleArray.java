package ru.job4j.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * клас будет универсальной обёрткой на массивом
 * содержать методы add(T model),set(int position, T model), delete(int position), get(int position);
 * Так же, будет реализовывать итератор Iterable<T>.
 */
public class SimpleArray<T> implements Iterable<T> {
    private Object[] object;
    private int index = 0;             //индекс элемента

    /**
     * конструктор из видеоурока
     * где сначало указывается размер.
     *
     * @param size
     */
    public SimpleArray(int size) {
        this.object = new Object[size];
    }

    /**
     * конструктор для добавления сразу массива
     *
     * @param arr
     */
    public SimpleArray(T[] arr) {
        this.object = arr;
        this.index = arr.length;
    }

    /**
     * добавлял элемент и возвращал tru при удачном добавлении
     *
     * @param model
     * @return
     */
    public boolean add(T model) {
        rangeCheck(this.index);
        this.object[index++] = model;
        return true;
    }

    /**
     * старался делать как в листе, не только изменял элемент
     * но метод также возвращал старый елемент
     *
     * @param index
     * @param model
     * @return
     */
    @SuppressWarnings("unchecked")
    public T set(int index, T model) {
        rangeCheck(index);
        T oldElement = (T) this.object[index];
        this.object[index] = model;
        return oldElement;
    }

    @SuppressWarnings("unchecked")
    public T getT(int index) {
        rangeCheck(index);
        return (T) this.object[index];
    }

    /**
     * удаляет элемент и сдвигает все елементы в лево на одну позицию, размер массива не меняется,
     * последний элемент становится null, подглядел схему в аррайлисте
     *
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public T delete(int index) {
        rangeCheck(index);
        T oldElement = (T) this.object[index];
        int tempNum = object.length - index - 1;
        if (tempNum > 0) {
            System.arraycopy(object, index + 1, object, index, tempNum);
        }
        this.object[this.object.length - 1] = null;
        return oldElement;
    }

    private void rangeCheck(int index) {                                 //будем проверять не вывалились ли мы за пределы массива
        if (index >= object.length) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * возвращает размер
     *
     * @return
     */
    public int size() {
        return this.object.length;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + object.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleArrayIterator<T>();          //создаёт итератор
    }

    private class SimpleArrayIterator<T> implements Iterator<T> {
        private int nextObject;             //будет использоваться для итерации

        @Override
        public boolean hasNext() {
            return object.length > nextObject;
        }

        @Override
        public T next() {
            if (hasNext()) {
                //noinspection unchecked
                return (T) object[nextObject++];
            } else {
                throw new NoSuchElementException("NoSuchElement");
            }
        }
    }
}
