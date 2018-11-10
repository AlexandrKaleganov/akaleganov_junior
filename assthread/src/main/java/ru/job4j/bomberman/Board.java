package ru.job4j.bomberman;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Kaleganov
 * 02.09.2018
 * ирга бомбермен примитивная реализация, Боард это поле, поле у нас представленно массивом
 * ReentrantLock - это класс который как правило используют для работы с блокировкой
 * у него есть несколько методов, самые интересные это:
 * void lock(): ожидает, пока не будет получена блокировка
 * boolean tryLock(): пытается получить блокировку, если блокировка получена,
 * то возвращает true. Если блокировка не получена,
 * то возвращает false. В отличие от метода lock() не ожидает получения блокировки, если она недоступна
 * void unlock(): снимает блокировку НО БЛОКИРОВКА СНИМАЕТСЯ ТЕМ ЖЕ ПОТОКОМ, ЧТО И БЫЛА ПОЛУЧЕНА.
 */
public class Board {
    private final int size;
    private final ReentrantLock[][] board;

    //если значение переменной изменится то выбросим исключение
    private volatile boolean start = false;

    //в конструкторе мы указываем размер нашего поля
    public Board(int size) {
        this.size = size;
        this.board = new ReentrantLock[size][size];

        //проинициализируем его
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                this.board[i][j] = new ReentrantLock();
            }
        }
    }

    public boolean move(Cell source, Cell dist) throws InterruptedException {
        boolean rsl = false;
        try {
            if (start) {
                //если вырубаем поток то обязательно надо освободить лок
                //иначе будет дедлок, оттестировал в тесте StartGameTest циклом 50 раз
                this.board[source.getX()][source.getY()].unlock();   //пока эта строчка отсутствовала появлялись дедлоки и тест зависал
                throw new InterruptedException();
            }
            if (this.board[dist.getX()][dist.getY()].tryLock(500, TimeUnit.MILLISECONDS)) {
                rsl = true;
                this.board[source.getX()][source.getY()].unlock();
            }
        } catch (InterruptedException e) {
            throw new InterruptedException();
        }

        return rsl;
    }

    public ReentrantLock[][] getBoard() {
        return board;
    }


    public int getSize() {
        return size;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
