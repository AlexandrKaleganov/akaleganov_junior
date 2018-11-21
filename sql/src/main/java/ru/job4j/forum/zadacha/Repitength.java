package ru.job4j.forum.zadacha;

/**
 * простые задачки для форума
 * вычислить самую длинную не повторяющуюся последовательность
 */
public class Repitength {
    public int sizemass(int[] stroka) {
        int sizeRES = 0;
        int positionRES = 0;
        int newposition = 0;
        for (int i = 0; i < stroka.length; i++) {
            for (int j = newposition; j < i; j++) {

                if (stroka[j] == stroka[i]) {
                    if (sizeRES <= stroka.length - newposition) {
                        sizeRES = i - newposition;
                        positionRES = newposition;
                    }
                    newposition = i;

                } else if (i == stroka.length - 1 && stroka.length - newposition >= sizeRES) {
                    sizeRES = stroka.length - newposition;
                    positionRES = newposition;
                }
                ;
            }
        }

        if (sizeRES == 1) {
            System.out.println("Последовательности нету");
        } else {
            System.out.println("Индекс " + positionRES + " размер " + sizeRES);
        }
        return positionRES;
    }
}