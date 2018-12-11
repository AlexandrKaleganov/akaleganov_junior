package ru.job4j.forum.zadacha;

/**
 * простые задачки для форума
 */
public class Repitength {
    /**
     * вычислить самую длинную не повторяющуюся последовательность
     *
     * @param stroka
     */
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
                    resSize++;
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

    /**
     * Найти в данном массиве кол-во интервалов убывания
     *
     * @param stroka
     * @return
     */
    public int interMinim(int[] stroka) {
        int k = 0;
        int count = 0;
        int newPosition = 0;
        for (int i = 1; i < stroka.length; i++) {
            for (int j = newPosition; j < i; j++) {
                if (stroka[j] > stroka[i]) {
                    k++;
                } else if (stroka[j] <= stroka[i]) {
                    if (k > 0) {
                        count++;
                        k = 0;
                    }
                    newPosition = i;
                }
                if (i == stroka.length - 1 && j == i - 1 && stroka[j] > stroka[i]) {
                    count++;
                }
            }
        }
        return count;
    }
}