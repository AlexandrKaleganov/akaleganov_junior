package ru.job4j.bomberman;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @author Alexander Kaleganov
 * 02.09.2018
 * класс будет создавать бомбермена, игрока и он будет ходить только на указанную клетку
 * рольввода даннх пользователем будет осуществлять блокирующая оередь она будет
 * поставлять постепенно поодному значению
 */

public class Boomberman extends Thread {
    private final Board board;
    private Random random = new Random();

    private final BlockingQueue<Cell> distList;  //список ходов которыем мы будем получать от пользователя

    /**
     * сделал согласно Т.З  отдельный класс для бомбермена
     * бомбермен будет делать несколько шагов по полю,
     * он будет управляться через пользователя, скажем будет поступать очередь шагов бомбермена
     * и поток будет ожидать следующего шага
     */

    Boomberman(Board board, String name, BlockingQueue<Cell> distList) {
        this.board = board;
        this.distList = distList;
        this.setName(name);
    }


    @Override
    public void run() {
        Cell source = new Cell(0, 0);
        Cell dist = null;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                dist = distList.take();

                this.board.getBoard()[source.getX()][source.getY()].lock();
                //нашь бомбермен сделает шаг по полю

                while (!board.move(source, dist)) {
                    System.out.println(dist + " для " + this.getName() + " была занята, генерирую новый ход");
                    dist.setX(this.generateGo(source.getX()));
                    dist.setY(this.generateGo(source.getY()));
                }

                //присваиваем текущее положение бомбермену
                source.setX(dist.getX());
                source.setY(dist.getY());

                System.out.println(this.getName() + " шагнул на " + source);

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * генератор хода, спомощью
     * рандома генерируется 1 или 0 в зависимости от ситуации будет значение либо увеличиваться либо уменьшаться
     *
     * @param i
     * @return
     */
    private int generateGo(int i) {
        int rsl = i;
        if (i < board.getBoard().length - 1 && random.nextInt(2) > 0) {
            rsl = i + 1;
        } else if (i > 0) {
            rsl = i - 1;
        }
        return rsl;
    }
}
