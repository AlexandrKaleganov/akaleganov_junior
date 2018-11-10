package ru.job4j.bomberman;

import org.junit.Test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Alexandr Kaleganov
 * 02.09.2018
 * тестирование потока бомбермен
 * для этого создадим объект боард, проверим что клетка не занята
 * создадим блокирующую очередедь, которая будет имитировать ввод пользователя
 * попробуем шагнуть на одну клетку,  и остановим поток, после проверим что
 * 1 поток остановлен
 * 2 клетка 1 х 1 занята, а клетка 0 х 0 свободна
 * изначально бомбермен начинает движение с клетки 0х0
 */
public class BoombermanTest {

    @Test
    public void testBombermanrun() {
        Board board = new Board(4);
        //проверим что наша клетка не занята
        assertThat(board.getBoard()[1][1].isLocked(), is(false));
        BlockingDeque<Cell> disrList = new LinkedBlockingDeque<>();
        Cell dist = new Cell(1, 1);
        disrList.offer(dist);
        Boomberman boomberman = new Boomberman(board, "Бомбермен Вася", disrList);
        boomberman.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //остановим поток
        boomberman.interrupt();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //проверим что мы остановили  поток
        assertThat(boomberman.isAlive(), is(false));
        //проверим что клетка 1 х 1 занята
        assertThat(board.getBoard()[1][1].isLocked(), is(true));
        //проверим что клетка 0 х 0 не занята
        assertThat(board.getBoard()[0][0].isLocked(), is(false));
    }
}