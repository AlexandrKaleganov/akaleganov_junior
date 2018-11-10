package ru.job4j.mapmain;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * попробуем сделать свою хеш мапу, создадим два статичных класса
 * 1 класс кей - он будет содержать объект ключа, ссылку на следующий ключ при коллизиях
 * и ссылку на Статичный класс Data который будет содержать объек, который соответствует данному ключу
 * по нашей бизнес логике ключ дожен будет ссылкаться только на один объект.
 *
 * @param <K>
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class HashMapMain<K, E> implements Iterable<K> {
    private Key[] mapaHash;  //наша хеш таблица, которая содержит список ключ  - значение
    private int size;   //переменная будет увеличиваться на 1 при добавлении элемента и уменьшаться на 1  при удалении

    public HashMapMain() {
        this.mapaHash = new Key[10];
    }

    /**
     * добавление элемента в хешмап при обычном добавлении возвращяет true
     * если изменяем знаение то возвращает true
     *
     * @param key
     * @param data
     * @return
     */
    public boolean put(K key, E data) {
        Data<E> element = new Data<E>(data);
        boolean result = false;
        Key<K> newKey = new Key<K>(key, element);
        int backet = hashFunction(newKey.hashCode);
        if (!(this.mapaHash[backet] == null)) {
            boolean exit = false;
            Key temp = this.mapaHash[backet];
            while (!exit) {
                if (temp.hashCode == newKey.hashCode && temp.key.equals(newKey.key)) {
                    result = true;
                    temp.linkData = newKey.linkData;
                    exit = true;
                } else if (temp.nextKey == null) {
                    temp.nextKey = newKey;
                    result = true;
                    size++;
                    exit = true;
                } else {
                    temp = temp.nextKey;
                }
            }
        } else {
            this.mapaHash[backet] = newKey;
            result = true;
            size++;
        }
        if (this.isResurse()) {
            this.resetMap();
        }
        return result;
    }

    /**
     * если элемент не найдет то вернёт значение null
     * в методе две повторяющиеся строки но решил что нет необходимости делать
     * оельный метод из за двух строк
     *
     * @param key
     * @return
     */
    public E get(K key) {
        E element = null;
        int backed = hashFunction(key.hashCode());
        if (!(this.mapaHash[backed] == null)) {
            Key temp = this.mapaHash[backed];
            if (temp.hashCode == key.hashCode() && temp.key.equals(key)) {
                element = (E) temp.linkData.data;
            } else {
                boolean exit = false;
                while (!exit) {
                    if (!(temp.nextKey == null)) {
                        temp = temp.nextKey;
                        if (temp.hashCode == key.hashCode() && temp.key.equals(key)) {
                            element = (E) temp.linkData.data;
                            exit = true;
                        }
                    } else {
                        exit = true;
                    }
                }
            }
        }
        return element;
    }

    /**
     * удаляем эелемент если елемент найден в массиве то удаляет и возвращает тру
     * если не находит элемент то возвращает фальш
     *
     * @param key
     * @return
     */
    public boolean delete(K key) {
        int backet = hashFunction(key.hashCode());
        boolean res = false;
        if (!(this.mapaHash[backet] == null)) {
            if (this.mapaHash[backet].hashCode == key.hashCode() && this.mapaHash[backet].key.equals(key)) {
                this.mapaHash[backet] = this.mapaHash[backet].nextKey;
                res = true;
                size--;
            } else if (!(this.mapaHash[backet].nextKey == null)) {
                Key temp = this.mapaHash[backet].nextKey;
                Key tempTwo = this.mapaHash[backet];
                boolean exit = false;
                while (!exit) {
                    if (temp.hashCode == key.hashCode() && temp.key.equals(key)) {
                        tempTwo.nextKey = temp.nextKey;
                        size--;
                        res = true;
                        exit = true;
                    } else if (!(temp.nextKey == null)) {
                        tempTwo = temp;
                        temp = temp.nextKey;
                    } else {
                        exit = true;
                    }
                }
            }
        }
        return res;
    }

    /**
     * олучить текущее количество элементов в хештаблице
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * реализую ВЫВОД хеш мапы для того чтобы посмотреть сколько у нас получается коллизий
     * прям наглядно
     *
     * @return
     */
    @Override
    public String toString() {
        String result = "";
        Key iterator;
        for (int i = 0; i < this.mapaHash.length; i++) {
            if (!(this.mapaHash[i] == null)) {
                iterator = this.mapaHash[i];
                result = result + i + ") " + iterator.toString() + "-->" + iterator.linkData.toString();
                boolean exit = false;
                while (!exit) {
                    if (!(iterator.nextKey == null)) {
                        iterator = iterator.nextKey;
                        result = result + "----" + iterator.toString() + "-->" + iterator.linkData.toString();
                    } else {
                        result = result + "\n";
                        exit = true;
                    }
                }
            } else {
                result = result + i + ") " + "-null-" + "\n";
            }
        }
        return result;
    }

    /**
     * хешфункция простейшая остаток от деленина длинну внутреннего массива,
     * иногда хеш код переваливается за пределы мыксимального значения int и тогда
     * хеш получаеся со знаком минус - по этому будем проверять этот момент и если что умножать на -1
     * чтобы не вывалиться за пределы массива
     *
     * @param hashCode
     * @return
     */
    private int hashFunction(int hashCode) {
        int res = hashCode % mapaHash.length;
        if (res < 0) {
            res = res * -1;
        }
        return res;
    }

    /**
     * если размер больше 0 то хешмап не пустая и вернётся тру, иначе фальш
     *
     * @return
     */
    public boolean isEmpty() {
        return size > 0;
    }

    /**
     * если размер больше либо равен 75% то размер массива увеличится в 10 раз;
     *
     * @return
     */
    private boolean isResurse() {
        return size * 100 / this.mapaHash.length >= 75;
    }

    /**
     * расширение хеш таблицы
     */
    private void resetMap() {
        size = 0;
        Key[] temOne = new Key[this.mapaHash.length * 10];
        Key[] tempTwo = this.mapaHash;
        this.mapaHash = temOne;
        for (Key tempKey : tempTwo) {
            if (!(tempKey == null)) {
                this.put((K) tempKey.key, (E) tempKey.linkData.data);
                boolean exit = false;
                while (!exit) {
                    if (!(tempKey.nextKey == null)) {
                        tempKey = tempKey.nextKey;
                        this.put((K) tempKey.key, (E) tempKey.linkData.data);
                    } else {
                        exit = true;
                    }
                }
            }
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new Iter<K>();
    }

    private class Iter<K> implements Iterator<K> {
        private int index = 0;
        int i = 0;
        Key temp = null;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public K next() {
            Key result = null;
            if (hasNext()) {
                boolean exit = false;
                while (!exit) {
                    if (mapaHash[i] != null && temp == null) {
                        result = mapaHash[i];
                        temp = mapaHash[i].nextKey;
                        index++;
                        exit = true;
                    } else if (!(temp == null)) {
                        result = temp;
                        exit = true;
                        index++;
                    } else if (mapaHash[i] == null) {
                        i++;
                    }
                    if (!(temp == null) && !(temp.nextKey == null)) {
                        temp = temp.nextKey;
                    }
                    if (temp == null) {
                        i++;
                    }
                }
            } else {
                throw new NoSuchElementException();
            }
            return (K) result.key;
        }
    }

    /**
     * класс буде хранить  объект ключа, хеш ключа,
     * в случае коллизий ссылку на следующий ключ
     * и  ссылку на обект с элементом
     * список будет односвязанный малость сэкономим память
     *
     * @param <K>
     */
    private static class Key<K> {
        private K key;
        private Key nextKey;
        private Data linkData;
        private final int hashCode;

        Key(K key, Data linkData) {
            this.key = key;
            this.hashCode = key.hashCode();
            this.linkData = linkData;
        }

        @Override
        public String toString() {
            return key.toString();
        }

        public K getKey() {
            return key;
        }
    }

    /**
     * объекты этого класса будут содерать только объект
     *
     * @param <E>
     */
    private static class Data<E> {
        private E data;

        Data(E date) {
            this.data = date;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
