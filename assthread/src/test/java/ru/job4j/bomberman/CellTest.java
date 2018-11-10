package ru.job4j.bomberman;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

/**
 * @author Alexandr Kaleganov
 * 02.09.2018
 * оттестируем класс позиция
 * проверим что методы работают правилно
 */
public class CellTest {
    @Test
    public void celltotalTest() {
        Cell cell = new Cell(12, 11);
        Assert.assertThat(cell.getX(), is(12));
        Assert.assertThat(cell.getY(), is(11));
        Assert.assertThat(cell.toString(), is("Позиция по координатам X = 12 Y = 11"));
        cell.setX(2);
        cell.setY(1);
        Assert.assertThat(cell.getX(), is(2));
        Assert.assertThat(cell.getY(), is(1));
    }
}