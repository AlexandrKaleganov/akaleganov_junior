package ru.job4j;

/**
 * сортировка массива, не относится к данной теме
 */
public class Test {
    public static void main(String[] args) {
        String[] mass = new String[]{"Вишня", "1", "Боб", "3", "Яблоко", "22", "0", "Арбуз"};
        sort(mass);
        toString(mass);
    }

    private static void sort(String[] mass) {
        for (int i = mass.length - 1; i >= 1; i--) {
            for (int j = 0; j < i; j++) {
                if (isNumber(mass[i]) && isNumber(mass[j])) {
                    if (Integer.parseInt(mass[i]) > Integer.parseInt(mass[j])) {
                        toSwap(mass, i, j);
                    }
                } else {
                    if (!isNumber(mass[i]) && !isNumber(mass[j])) {
                        if (isGreaterThan(mass[j], mass[i])) {
                            toSwap(mass, i, j);
                        }
                    }
                }
            }
        }
    }

    private static boolean isGreaterThan(String a, String b) {
        return a.compareTo(b) > 0;
    }

    private static boolean isNumber(String s) {
        if (s.length() == 0) {
            return false;
        }

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if ((i != 0 && c == '-') // Строка содержит '-'
                    || (!Character.isDigit(c) && c != '-') // или не цифра и не начинается с '-'
                    || (chars.length == 1 && c == '-')) { // или одиночный '-'
                return false;
            }
        }
        return true;
    }

    private static void toSwap(String[] array, int first, int second) {
        String temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    private static void toString(String[] mass) {
        for (String s : mass) {
            System.out.println(s);
        }
    }
}