package ru.job4j.bomberman;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

import static org.hamcrest.Matchers.is;

/**
 * @author Alexandr Kaleganov
 * 02.09.2018
 * оттестируем класс Board чтобы знать зачем он нам нужен
 *
 */
public class BoardTest {
    //потоком оттетсируем нашу доску
    private static class BooardTestThread<B> implements Callable {
        private final Cell dist;
        private final Cell source;
        private final Board board;

        BooardTestThread(Board board, Cell source, Cell dist) {
            this.dist = dist;
            this.source = source;
            this.board = board;
        }

        /**
         * получим результат выполнения
         * если вернёт фальш значит мы не получили лок
         *
         * @return
         * @throws Exception
         */

        @Override
        public Boolean call() throws Exception {
            Boolean rsl = null;
            //получим лок клетки которая занята
            this.board.getBoard()[source.getX()][source.getY()].lock();
            rsl = board.move(source, dist);
            return rsl;
        }
    }

    /**
     * попробем встать на незаблокированную клетку
     * попробуем встать на заблокированную клетку
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void moveBomberMan() throws ExecutionException, InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(2);
        Board board = new Board(5);
        board.getBoard()[2][2].lock();
        Boolean expectedOne = true;
        Cell souce = new Cell(1, 1);
        Cell dist = new Cell(1, 2);
        Cell distTwo = new Cell(2, 2);

        BooardTestThread booardTestThread = new BooardTestThread<Boolean>(board, souce, dist);
        BooardTestThread booardTestThreadTwo = new BooardTestThread<Boolean>(board, souce, distTwo);

        //noinspection unchecked
        Future<Boolean> rsl = executors.submit(booardTestThread);
        Thread.sleep(1000);
        //noinspection unchecked
        Future<Boolean> rslTwo = executors.submit(booardTestThreadTwo);

        Assert.assertThat(rsl.get(), is(true));
        Assert.assertThat(rslTwo.get(), is(false));
//        Thread.sleep(3000);
//        FutureTask<Boolean> futureTask = new FutureTask<>(booardTestThread);
//        System.out.println(futureTask.get());
//       new Thread(futureTask).start();
//       Thread.sleep(1000);
//        System.out.println(futureTask.get());
    }
}