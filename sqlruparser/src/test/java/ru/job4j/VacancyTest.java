package ru.job4j;

import org.junit.Test;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class VacancyTest {

    @Test
    public void vacancyTest() throws ParseException {
//        Vacancy vacancy = new Vacancy("url", "вакансия", "саша", LocalDateTime.parse("29 06 18, 11:14", DateTimeFormatter.ofPattern("dd MMM yy, HH:mm")), "надо работать");
//        System.out.println(vacancy);
        String str = "сегодня, 16:35";
        ArrayList<Integer> time = new ArrayList<>();
//        Arrays.stream(str.split("[,:]")).filter(e -> !e.contains("сегодня")).map(e -> e.replace(" ", "")).mapToInt(Integer::parseInt).forEach(time::add);
        String x = "вася";
        String y = "gtnz";

        if (x.contains(y.substring(0, 1))) {
            System.out.println("ну да содержит");
        }

    }


}