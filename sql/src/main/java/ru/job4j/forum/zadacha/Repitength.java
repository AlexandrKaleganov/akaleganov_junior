package ru.job4j.forum.zadacha;

import com.sun.javafx.binding.StringFormatter;

/**
 * простые задачки для форума
 * вычислить самую длинную не повторяющуюся последовательность
 */
public class Repitength {

    public int sizemass(int[] stroka) {
        int maxPosition = 0;
        int size = 1;
        int tempSize = 1;
        int tempPosition = 0;
        for (int i = 0; i < stroka.length - 1; i++) {
            if (stroka[i] != stroka[i + 1]) {
                tempSize = tempSize + 1;
            } else {
                if (size < tempSize) {
                    size = tempSize;
                    tempSize = 1;
                    maxPosition = tempPosition + 1;
                }
                tempPosition++;
            }

        }
        if (size <= tempSize && tempSize > 1) {
            size = tempSize;
            maxPosition = maxPosition + 1;
        }
        if (maxPosition > 0) {
            System.out.println("Самая длинная последовательность: " + maxPosition + " , размер последовательности : " + size);
        } else {
            System.out.println("массив не имеет последовательности");
        }
        return maxPosition;
    }

    public int sizemasstwo(int[] stroka) {
        int maxPosition = 0;
        int size = 0;
        int tempPosition = 0;
        for (int i = 1; i < stroka.length; i++) {
            for (int j = tempPosition; j < i; j++) {
                if (stroka[i] == stroka[j] || i == stroka.length -1) {

                    if (maxPosition <= tempPosition && size <= i - tempPosition) {
                        size = tempPosition == 0 ? i - tempPosition - 1 : i - tempPosition;
                        maxPosition = tempPosition;
                    }
                    tempPosition = i;
                }
            }
        }
        if (size == 1) {
            System.out.println("массив не имеет последовательности");
        } else {
            System.out.println("Индекс: " + maxPosition + " , размер последовательности : " + size);
        }
        return maxPosition;
    }
}
