package ru.job4j.forum.combomass;

import java.util.*;

/**
 * содержит готовую базу различных комбинаций массива после срабатывания конструктора
 *
 */
public class DatabeseCombo {
    private ArrayList<char[]> rsl = new ArrayList<char[]>();
    public ArrayList<char[]> getRsl() {
        return rsl;
    }

    public DatabeseCombo(char[] strBox) {
        this.comBinator(strBox);
    }
    private void comBinator(char[] strBox) {
        HashMap<Integer, Integer> maptest = new HashMap<>();
        int maks = (int) Math.pow(strBox.length, strBox.length);
        int[] n = new int[strBox.length];
        for (int i = 0; i < strBox.length; i++) {
            n[i] = i;
        }
        Iterator<int[]> combi = new Itr(n);
        while (combi.hasNext()) {
            n = combi.next();
            rsl.add(new char[strBox.length]);
            for (int i = 0; i < strBox.length; i++) {
                rsl.get(rsl.size() - 1)[i] = strBox[n[i]];
            }
        }
    }
}
