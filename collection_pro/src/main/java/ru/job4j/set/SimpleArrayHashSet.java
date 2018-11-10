package ru.job4j.set;

/**
 * может я не понял задания сделал так:
 * в виде ключа у нас будет Bucket он будет хранить хеш ключ, и будет хранить ссылку на
 * объект, массив динамически расширяется, объект ObjectAdLink содержит непосредственно объект
 * добавляемый объект и ссылку которую тоже можно заполнить
 * в самом классе ObjectAndLink поясняется зачем нужна ссылка
 * также хочу обратить внимание что менее трудозатратно когда мы заранее знаем размер хеш сета
 * чтобы он не пересоздавался , это не хеш меп по этому при коллизиях элемент не будет добавлен
 * а в случае если индекс получится одинаковый мы будем использовать метод, который называется линейное пробирование
 *
 * @param <E>
 */
@SuppressWarnings("WeakerAccess")
public class SimpleArrayHashSet<E> {
    private Bucket[] hashSet;
    private int size;       //будем всегда проверять чтобы эта переменная была всегда на 70% меньше длинны массива
    private int index;

    SimpleArrayHashSet() {
        hashSet = new Bucket[10];
    }

    SimpleArrayHashSet(int i) {
        hashSet = new Bucket[i];
    }

    /**
     * в этом методе методе мы первым делом вызовем метод indexFor и высчитаем индекс по хешкоду
     * и запишем индекс в i далее пойдём по циклу и если в нашем первомжэ индексе
     * будет элемнт с точно таким же хешкодом то сразу выйдем из цикла и метод вернёт фальш
     * элемент не будет добавлен, иначе если итый элемент будет равен нулл то мы туда сунем нашь объект
     * если не нул  то пойдём по массиву искать первую попавшиюся свободную ячейку после добавления в массив
     * элемента мы будем запускать метод SizemasTwoLenghtduplex(); который будет проверять плотность заполнения массива
     * и если он заполнен на 70 % то мы пересоздадим массив и новый будет больше в 10 раз
     *
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        boolean res = false;
        int i = indexFor(e);
        if (!contains(e)) {
            Bucket bucket = new Bucket(new ObjectAndLink<E>(e));
            while (!res) {
                if (hashSet[i] == null) {
                    hashSet[i] = bucket;
                    res = true;
                    size++;
                    if (!this.isDensity()) {
                        this.sizemasTwoLenghtduplex();
                    }
                    break;
                }
                i++;
                if (i == this.hashSet.length) {
                    i = 0;
                }
            }
        }
        return res;
    }


    /**
     * в этот метод будет входить искомый элемент
     * по его хешу будет высчитан индекс, и дальше мы в цикле проверим
     * если индекс равен нулл тогда брейк, и результ останется фальшем
     * т.е. элемента нету в массиве и наче сверять будем  хешкоды, и если они не равны пойдм дальше по массиву
     * если последующий элемент будет равен нулу то брейк, элемента точно нету если он чемто заполнен то будем проверять
     * и по кругу но если i опять наткнётся на начальный индекс то брейк.
     *
     * @param e
     * @return
     */
    public boolean contains(E e) {
        boolean res = false;
        int i = indexFor(e);
        while (!res) {
            if (!(hashSet[i] == null) && hashSet[i].getHeshcod() == e.hashCode()) {
                res = true;
                this.index = i;
                break;
            } else if (hashSet[i] == null) {
                break;
            }
            i++;
            if (i == indexFor(e)) {
                break;
            }
            if (i == this.hashSet.length) {
                i = 0;
            }
        }
        return res;

    }

    /**
     * удаляет элемент но размер внутреннего массива не уменьшается, уменьшается только показатель
     * size - текучщее количество элементов в массиве
     *
     * @param e
     * @return
     */
    public boolean remove(E e) {
        boolean res = false;
        if (this.contains(e)) {
            this.hashSet[index] = null;
            size--;
            res = true;
        }
        return res;
    }

    /**
     * в этом методе будет высчитываться по хешу индекс нового элемента
     * индекс будет равен деление на длину массива, и получение остатка
     * остаток и будет индексом, т.е. длина нашего масива всегда будет умножаться на 10
     * начальная длина 10 элементов
     *
     * @return
     */
    private int indexFor(E e) {
        return e.hashCode() % hashSet.length;
    }

    /**
     * метод будет проверять плотность заполненность массива и если он меньше 70% то вернёт тру иначе альш
     *
     * @return
     */
    private boolean isDensity() {
        return size * 100 / hashSet.length < 70;
    }

    /**
     * ут я буду проверять пустой мой хешьсет или нет
     * если он пустой то вернётся фальш
     *
     * @return
     */
    public boolean isEmpty() {
        return !(size == 0);
    }

    /**
     * в этом методе будет происходить пересоздание и заполнение массива
     * в цикле мы пройдёмся по старому массиву будем брать каждый элемент
     * и через наш же метод add будм добавлять элементы в новый массив
     * так будет всё по понятиям
     * конечно при совпадениях индекса время на поиск и добавление будет увеличиваться
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    private void sizemasTwoLenghtduplex() {
        this.size = 0;
        Bucket[] tempOne = this.hashSet;
        this.hashSet = new Bucket[hashSet.length * 10];
        for (int i = 0; i < tempOne.length; i++) {
            if (!(tempOne[i] == null)) {
                this.add((E) tempOne[i].getLinkObjct().getData());
            }
        }
    }

    /**
     * получить размер массива
     *
     * @return
     */
    public int getSize() {
        return size;
    }
}
