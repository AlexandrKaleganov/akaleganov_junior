package ru.job4j.forum.zadacha;

/**
 * простые задачки для форума
 * вычислить самую длинную не повторяющуюся последовательность
 */
public class Repitength {

    public void siseres(int[] stroka) {
        int resPosition = 0;
        int tempSize = 0;
        int resSize = 0;
        int newPosition = 0;
        for (int i = 1; i < stroka.length; i++) {
            tempSize++;
            for (int j = newPosition; j < i; j++) {
                if (stroka[j] == stroka[i]) {
                    if (resSize <= tempSize) {
                        resSize = tempSize;
                        resPosition = newPosition;
                    }
                    tempSize = 0;
                    newPosition = i;
                } else if (i == stroka.length - 1 && resSize <= tempSize + 1 && j == i - 1) {
                    resSize = tempSize + 1;
                    resPosition = newPosition;
                }
            }
        }

        if (resSize == 1) {
            System.out.println("Последовательности нету");
        } else {
            System.out.println("Индекс " + resPosition + " размер " + resSize);
        }
    }
}