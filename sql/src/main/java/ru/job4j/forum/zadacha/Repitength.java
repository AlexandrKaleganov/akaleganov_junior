package ru.job4j.forum.zadacha;


import java.util.HashSet;

/**
 * простые задачки для форума
 * вычислить самую длинную не повторяющуюся последовательность
 */
public class Repitength {

    public int sizemass(int[] stroka) {
        int size = 0;
        int position = 0;
        HashSet<Integer> seti = new HashSet<>();
        for (int i = 0; i < stroka.length; i++) {
            if (!seti.contains(stroka[i])) {
                seti.add(stroka[i]);
            } else {
                if (size <= seti.size()) {
                    size = seti.size();
                    position = i;
                    seti.clear();
                    seti.add(stroka[i]);
                }
            }
        }

        if (size == 1) {
            System.out.println("Последовательности нету");
        } else {
            System.out.println("Индекс " + position + "размер " + size);
        }
        return position;
    }
}