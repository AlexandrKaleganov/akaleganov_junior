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
        if (size <= tempSize  && tempSize > 1) {
            size = tempSize;
            maxPosition = maxPosition + 1;
        }
        if (maxPosition > 0) {
            System.out.println("Самая длинная последовательность: " + maxPosition +" , размер последовательности : " + size);
        } else {
            System.out.println("массив не имеет последовательности");
        }
        return maxPosition;
    }
}
