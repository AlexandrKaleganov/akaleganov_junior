package ru.job4j.bomberman;

import java.util.concurrent.*;

/**
 * @author Alexandr Kaleganov
 * 02.09.2018
 * запуск игры
 * для запуска необходимо создать объект этого класса и передать сюда
 * очередь позицый которая будет именировать ввод пользователя
 * при создании объекта также указывается размер поля, и количество монстров,
 * которые будут гулять по полю
 * решил воспользоваться классом экзекутор сервисом
 * он запустит всех наших монстров
 * метод close зменит volatile переменную у объекта board
 * а при изменении этой переменной board выбросит исключение Итерррапт, это исключение будет отловлено кукимонстрами
 * и потоки кукимонстров завершаться
 * также мы запросим прерывание у бомбермена
 */
public class StartGame {
    private final Board board;
    private final ExecutorService poolMonster;
    private final int cOokieMonster;
    private final Thread boomberMan;

    //размер поля и количество монстров
    @SuppressWarnings("SameParameterValue")
    StartGame(int size, int cOokieMonster, BlockingQueue<Cell> distList) {
        this.board = new Board(size);
        poolMonster = Executors.newFixedThreadPool(cOokieMonster);
        this.cOokieMonster = cOokieMonster;
        boomberMan = new Boomberman(this.board, "Бомбермен вася", distList);
    }

    public void start() {
        for (int i = 0; i < cOokieMonster; i++) {
            poolMonster.submit(new CookieMonster(this.board, "CookieMonster - " + i));
        }
        boomberMan.start();
    }

    public void close() {
        this.board.setStart(true); //остановим задачи монстров
        boomberMan.interrupt();          //сделаем запрос на прерывание у бомбермена
        poolMonster.shutdown();

        while (!poolMonster.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //проверим живы ли потоки
    public boolean isAlivethread() {
        return this.boomberMan.isAlive() && !poolMonster.isTerminated();
    }
}
