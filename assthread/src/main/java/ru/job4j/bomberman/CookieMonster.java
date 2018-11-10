package ru.job4j.bomberman;

import java.util.Random;

/**
 * @author Alexandr Kaleganov
 * 02.09.2018
 * класс будет создавать монстра, он будет ходить рандомно по полю
 */
public class CookieMonster implements Runnable {
    private final Board board;
    private Random random = new Random();
    private final String name;
    final Cell source;

    CookieMonster(Board board, String name) {
        this.board = board;
        this.name = name;
        //монстр всегда будет начинать ход с конца поля
        this.source = new Cell(this.board.getSize() - 1, this.board.getSize() - 1);

    }

    public Cell getSource() {
        return source;
    }

    @Override
    public void run() {
        this.board.getBoard()[source.getX()][source.getY()].lock();
        //наш  монстр будет ходить пока не запросится прерывание
        while (!Thread.interrupted()) {
            final Cell dist = new Cell(this.generateGo(source.getX()), this.generateGo(source.getY()));
            try {
                while (source.equals(dist) || !board.move(source, dist)) {
                    System.out.println(dist + " для " + name + " была занята, генерирую новый ход");
                    dist.setX(this.generateGo(source.getX()));
                    dist.setY(this.generateGo(source.getY()));
                    System.out.println("Сгенерирован ход для " + this.name + " " + dist);
                }
                //присваиваем текущее положение монстру
                source.setX(dist.getX());
                source.setY(dist.getY());
                System.out.println(name + " шагнул на " + source);

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * генератор хода, кадый раз приходит предыдущая позиция в этот метод и спомощью
     * рандома генерируется 1 или 0 в зависимости от ситуации будет значение либо увеличиваться либо уменьшаться
     * что касается
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