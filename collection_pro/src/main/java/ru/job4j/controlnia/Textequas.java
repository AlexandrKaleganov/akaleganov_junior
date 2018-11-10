package ru.job4j.controlnia;


import java.util.*;

/**
 * класс содержит различные методы сравнения двух переменных типа стринг, если они равны по длинне
 * и сравнивает на то что они состоят из одних и тех же символов
 * в первом варианте я кинул сразу в хешсет стринги разбив сплитом и сравнил содержимое,
 * во втором варианте я кинулэлементы в чар аррей отсортировал и сравнил в цикле но зная как работет метод сорт
 * временая сложность получится большая
 * также есть третий вариант который я  предложил его при устном опросе
 * взть каждую букву перого и сторого слова и пройтись по ним в одном цикле,складывая хэши каждого символа
 * по окончанию цикла сравнить хешкоды
 *
 */
public class Textequas {

    /**
     * сделал в  хешсете, т.е. там всё упорядочивается по хеш коду.
     *
     * @param one
     * @param two
     * @return
     */
    public boolean textEqualsOne(String one, String two) {
        boolean rsl = true;
        String[] oneString = one.split("");
        String[] twoString = two.split("");
        HashMap<String, Integer> oneMass = new HashMap<String, Integer>();
        HashMap<String, Integer> twoMass = new HashMap<String, Integer>();
        for (int i = 0; i < oneString.length; i++) {
            textEqualsOneRefactor(oneMass, oneString[i]);
            textEqualsOneRefactor(twoMass, twoString[i]);
        }

        if (!oneMass.entrySet().containsAll(twoMass.entrySet())) {
            rsl = false;
        }
        return rsl;
    }

    private void textEqualsOneRefactor(HashMap<String, Integer> map, String i) {
        if (map.containsKey(i)) {
            map.put(i, map.get(i) + 1);
        } else {
            map.put(i, 1);
        }
    }


    /**
     * Зная как работает метод сорт думаю временная сложность будет больше
     *
     * @param one
     * @param two
     * @return
     */
    public boolean textEqualsTwo(String one, String two) {
        boolean rsl = true;
        char[] oneMass = one.toCharArray();
        char[] twoMass = two.toCharArray();
        Arrays.sort(oneMass);
        Arrays.sort(twoMass);
        for (int i = 0; i < oneMass.length; i++) {
            if (!(oneMass[i] == twoMass[i])) {
                rsl = false;
            }
        }
        return rsl;
    }

    /**
     * этот метод тоже будет быстро работать
     *
     * @param one
     * @param two
     * @return
     */
    public boolean textEqualsTry(String one, String two) {
        boolean rsl = true;
        int oneRes = 0;
        int twoRes = 0;
        for (int i = 0; i < one.length(); i++) {
            oneRes += one.substring(i, i + 1).hashCode();
            twoRes += two.substring(i, i + 1).hashCode();
        }
        if (oneRes != twoRes) {
            rsl = false;
        }
        return rsl;
    }

}