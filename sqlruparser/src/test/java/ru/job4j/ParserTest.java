package ru.job4j;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void addtoData() {
        try (Parser parser = new Parser(new Config())) {
            Vacancy vacancy = new Vacancy("uurl", "требуется кто -то", "alexander", LocalDateTime.parse("29 июн 18, 11:14", DateTimeFormatter.ofPattern("d MMM yy, HH:mm")), "надо работать");
//            parser.addtoData(vacancy);
            System.out.println(parser.isInfodatatable("url2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}