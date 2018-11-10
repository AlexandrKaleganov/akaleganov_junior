package ru.job4j.forum;

import java.util.ArrayList;
import java.util.function.Function;

//функции: X^3 - 50cos(x) с интервалом -4 и 3. Методом Хорд, касательных и Ньютона.
public class Calculate {
    public ArrayList<Double> totalfank(int start, int end, Function<Integer, Double> fanc) {
        ArrayList<Double> rsl = new ArrayList<>();
        for (int i = start; i < end; i++) {
            rsl.add(fanc.apply(i));
        }
        return rsl;
    }

    public static void main(String[] args) {
        Calculate calculate = new Calculate();
        System.out.println(calculate.totalfank(-4, 3, (x) -> {
            return Math.pow(x, 3) - 50 * Math.cos(x);
        }));
    }
}
