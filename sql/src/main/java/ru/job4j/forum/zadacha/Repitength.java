package ru.job4j.forum.zadacha;

/**
 * простые задачки для форума
 * вычислить самую длинную не повторяющуюся последовательность
 */
public class Repitength {

    public int sizemass(int[] stroka) {
        int maxPosition = 0;
        int size = 0;
        int tempSize = 0;
        for (int i = 0; i < stroka.length - 1; i++) {
            if (stroka[i] != stroka[i + 1]) {
                tempSize = tempSize + 1;
            } else {
                if (size < tempSize) {
                    size = tempSize;
                tempSize = 0;
                maxPosition = maxPosition + 1;
                }
            }

        }
        if (size < tempSize) {
            size = tempSize;
            maxPosition = maxPosition + 1;
        }
        System.out.printf("Самая длинная последовательность: %s , размер последовательности : %s", maxPosition, size);
        return maxPosition;
    }
}
