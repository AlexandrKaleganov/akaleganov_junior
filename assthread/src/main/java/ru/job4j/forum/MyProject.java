package ru.job4j.forum;

/**
 * @author Alexander Kaleganov
 * расчитывает среднеарифметическое
 */
public class MyProject {
    public int massToArrArifmeticsr(String[] n) {
        int sum = 0;
        byte[] num = null;
        int size = 0;
        for (String s : n) {
            num = s.getBytes();
            for (int j = 0; j < num.length; j++) {
                sum += num[j] < 0 ? num[j] * -1 : num[j];
                size++;
            }
        }
        return sum / size;
    }
}
