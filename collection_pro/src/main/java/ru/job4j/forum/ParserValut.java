package ru.job4j.forum;

import java.util.Arrays;

public class ParserValut {
    public String prser(String stroka) {
        StringBuilder rsl = new StringBuilder();
        Arrays.stream(stroka.split(" ")).forEach(s -> {
            try {
                rsl.append(" -> " + Double.parseDouble(s));
            } catch (NumberFormatException e) {
                rsl.append("\n" + s);
            }
        });
        return rsl.toString();
    }
}