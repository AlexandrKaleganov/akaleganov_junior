package ru.job4j.forum;

/**
 * подсчёт количества элементов массива которые больше правого элемента
 */
public class ForumPodschetmass {

    public int getResult(int[] mass) {
        int result = 0;                            //одын
        for (int i = 1; i < mass.length; i++) {    //два
            for (int j = i - 1; j >= 0; j--) {     //тры
                if (mass[j] > mass[i]) {           //четыры
                    result++;
                }
            }
        }
        return result;
    }

    public String stringParsremuw(String stroka, String symbol, String newsymbol) {
        String result = "";
        boolean isSwitch = false;
        for (int i = 0; i < stroka.length(); i++) {
            if (!isSwitch && stroka.charAt(i) == symbol.charAt(0)) {
                isSwitch = true;
                result += stroka.charAt(i);
            } else if (isSwitch && stroka.charAt(i) == symbol.charAt(0)) {
                result += newsymbol;
            } else {
                result += stroka.charAt(i);
            }
        }
        return result;
    }
}
