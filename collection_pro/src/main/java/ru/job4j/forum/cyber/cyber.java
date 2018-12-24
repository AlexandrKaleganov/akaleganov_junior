package ru.job4j.forum.cyber;

import java.time.Month;
import java.util.Scanner;
import java.util.function.BiPredicate;

public class cyber {
    private static Scanner sc = new Scanner(System.in);

    /**
     * тут уже будем пилить условия
     *
     * @return
     */
    public Month readMonth() {
        Month rsl = null;
        boolean exit = false;
        try {
            while (!exit) {
                Integer s = sc.nextInt();
                rsl = this.relizfank(s, "The word you entered is not \nthe name of the month, try again");
                if (rsl != null) {
                    exit = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }


    private Month relizfank(Integer s, String err) throws Exception {
        Month rsl = null;
        rsl = this.fank(s, err, (l, month) ->
                l == month.getValue()
        );
        return rsl;
    }

    /**
     * метод либо вернёт месяц либо выбросит исключение
     * методы принимает Integer  можно сделать для стринга и
     * в условие проверять какой тип пришёл тот метод и запускаем
     *
     * @param s
     * @param err
     * @param pr
     * @param <S>
     * @return
     * @throws Exception
     */
    private <S> Month fank(S s, String err, BiPredicate<S, Month> pr) throws Exception {
        Month rsl = null;
        boolean test = false;
        for (Month month : Month.values()) {
            if (pr.test(s, month)) {
                rsl = month;
                test = true;
                break;
            }
        }
        if (!test) {
            throw new WrongInputConsoleParametersException(err);
        }
        return rsl;
    }

    /**
     * запуск программы
     *
     * @param args
     */
    public static void main(String[] args) {
        cyber cyber = new cyber();
        System.out.println(cyber.readMonth());
    }
}
