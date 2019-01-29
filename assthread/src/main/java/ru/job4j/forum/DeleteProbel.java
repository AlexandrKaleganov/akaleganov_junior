package ru.job4j.forum;

import java.util.Arrays;

public class DeleteProbel {

    public StringBuilder formatStr(String stroka) {
        StringBuilder rsl = new StringBuilder();
        stroka = stroka.substring(0, 1).contains(" ") ? stroka.substring(2) : stroka;
        Arrays.stream(stroka.split(" ")).filter(e -> {
            if (rsl.toString().length() <= 0) {
                rsl.append(e);
                return false;
            } else {
                return true;
            }
        }).forEach(e -> rsl.append(" " + e.substring(1)));
        return rsl;
    }
}
