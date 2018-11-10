package ru.job4j;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MonthTest {
    /**
     * тест парсинга даты
     */
    @Test
    public void getParserDate() {
        Month month = new Month();
        Assert.assertThat(month.getParserDate("5 ноя 18, 20:51").toString(), Is.is("2018-11-05T20:51"));
        Assert.assertThat(month.getParserDate("сегодня, 20:51").toString(), Is.is(LocalDate.now().atTime(20, 51).toString()));
    }
}