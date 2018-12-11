package ru.job4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * будет содержать хешкарту с месяцами
 * пришлось добавить этот класс т.к. Windows 10  не хочет через консоль работать с
 * рускоязычными месяцами (хотя при этом в среде всё работало)
 * решил переделать на числа заодно на этот класс повесить полную
 * ответственность за парсинг даты
 */
public class Month {
    private final HashMap<String, String> montMap = new HashMap<String, String>();

    public Month() {
        this.montMap.put("янв", "01");
        this.montMap.put("фев", "02");
        this.montMap.put("мар", "03");
        this.montMap.put("апр", "04");
        this.montMap.put("май", "05");
        this.montMap.put("июн", "06");
        this.montMap.put("июл", "07");
        this.montMap.put("авг", "08");
        this.montMap.put("сен", "09");
        this.montMap.put("окт", "10");
        this.montMap.put("ноя", "11");
        this.montMap.put("дек", "12");
    }

    /**
     * метод получает только текст даты а дальше переделывает его в нужный формат
     * 1. если встречаются слова сегодня или вчера то получаетс дату сегодняшнюю или вчерашнюю и прибавляет время
     * 2 проверяеткаков формат числа оно должно быть не меньше двух едениц
     * дело в том что я мог поставить формат "d MMM yy, HH:mm" и в idea всё работает но когд я компилирую файл и
     * пробую запуск через командную строку то летят экцепшены не нравится что дата из одного символа
     * пришлось добавить лишнюю строчку .map(e -> e.length() == 1 ? "0" + e : e)
     *
     * @param date
     * @return
     */

    public LocalDateTime getParserDate(String date) {
        LocalDateTime dateTime = null;
        if (date.contains("сегодня") || date.contains("вчера")) {
            ArrayList<Integer> time = new ArrayList<>();
            Arrays.stream(date.split("[,:]"))
                    .filter(e -> !e.contains("сегодня") && !e.contains("вчера"))
                    .map(e -> e.replace(" ", ""))
                    .mapToInt(Integer::parseInt).forEach(time::add);
            dateTime = LocalDate.now().atTime(time.get(0), time.get(1));

            if (date.contains("вчера")) {
                dateTime = dateTime.minusDays(1);
            }
        } else {
            StringBuilder dateContainer = new StringBuilder();
            Arrays.stream(date.split("[\\s*,]"))
                    .filter(e -> e.length() > 0)
                    .map(e -> e.length() == 1 ? "0" + e : e)
                    .map(e -> e.length() == 3 ? this.montMap.get(e) : e)
                    .forEach(e -> dateContainer.append(e + " "));
            dateTime = LocalDateTime.parse(dateContainer.toString().trim(), DateTimeFormatter.ofPattern("dd MM yy HH:mm").withLocale(new Locale("ru")));
        }
        return dateTime;
    }
}
