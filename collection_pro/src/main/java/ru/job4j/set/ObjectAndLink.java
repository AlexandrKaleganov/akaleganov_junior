package ru.job4j.set;

/**
 * сдержит непосредственно сам объект и ссылку пустую
 * если что можно будет попробовать реализавать так хеш таблицу
 *
 * @param <E>
 */
public class ObjectAndLink<E> {
    private ObjectAndLink newObject;
    private E data;

    ObjectAndLink(E e) {
        this.data = e;
    }

    public E getData() {
        return data;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    /**
     * представим себе вариант когда у нас уже есть элемент в массиве
     * с таким же хеш кодом  типо одинаковые элементы а хеш мапа может хранить
     * одинаковые элементы
     * и тогда мы возьмём hashMap[i].getLinkObjct.add(new ObjectAndLink<E> (e));
     *
     * @param newObject
     */
    public void add(ObjectAndLink<E> newObject) {
        this.newObject = newObject;
    }



    public ObjectAndLink getNewObject() {
        return newObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjectAndLink<?> that = (ObjectAndLink<?>) o;

        return data.equals(that.data);
    }
}
