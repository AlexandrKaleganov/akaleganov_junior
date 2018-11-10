package ru.job4j.forum;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;


public class Stroki {

    public void stringToList(String stroka, int leng) {
        Arrays.stream(stroka.split(" ")).flatMap(t -> Arrays.stream(t.split(","))).filter(t -> t.length() < leng).forEach(System.out::println);
    }
//Есть строка String st = "Hel-l,o", как вывести -1 если встретиться какой-либо символ или цифра в любом месте строки? Подскажите пожалуйста
    public void alphabet(String st) {
        Arrays.asList(st.split("")).stream()
                .filter(k -> {
                    char f = k.charAt(0);
                    return !((f >= 'A' && f <= 'Z') || (f >= 'a' && f <= 'z') || (f >= 'а' && f <= 'я') || (f >= 'А' && f <= 'Я'));
                })
                .forEach(k -> System.out.println("-1"));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку");
        String s = scanner.nextLine();
        System.out.println("Введите макс длинну слова");
        int len = scanner.nextInt();
        new Stroki().stringToList(s, len);
    }
}
