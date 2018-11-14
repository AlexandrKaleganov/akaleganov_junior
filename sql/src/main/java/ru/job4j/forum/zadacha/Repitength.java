package ru.job4j.forum.zadacha;

/**
 * простые задачки для форума
 * вычислить самую длинную не повторяющуюся последовательность
 */
public class Repitength {

    public int sizemass(String stroka) {
        int position = 0;
        int maxPosition = 0;
        int size = 0;
        String temp = "";
        String[] mass = stroka.split("");
        for (String mas : mass) {
            if (temp.contains(mas) || temp.length() < 1) {
                temp = temp + mas;
            } else {
                if (temp.length() > size) {
                    size = temp.length();
                    maxPosition = position + 1;
                }
                position = position + 1;
                temp = "";
            }
        }
        System.out.printf("Самая длинная последовательность: %s , размер последовательности : %s", maxPosition, size);
        return maxPosition;
    }
}
