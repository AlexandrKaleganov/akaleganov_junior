package ru.job4j.bomberman;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;

/**
 * @author Alexandr Kaleganov
 * @since 02.09.2018
 * оттестируем касс StartGame  просто запустим движуху
 * заодно создадим поток, который будет имитировать работу пользователя
 * стартанём игру, и проверим что все потоки живы и работают
 * потом завершим игру, и проверим остановились ли потоки
 */
public class StartGameTest {
    @Test
    public void testStart() {
//        for (int i = 0; i < 50; i++) {

        BlockingQueue<Cell> distList = new LinkedBlockingQueue<>();

        Thread polzovatel = new Thread(() -> {
            //пользователь будет управлять бомберменом и сделает 10 шагов
            try {
                distList.offer(new Cell(1, 2), 2000, TimeUnit.SECONDS);
                distList.offer(new Cell(1, 2), 2000, TimeUnit.SECONDS);
                distList.offer(new Cell(2, 3), 2000, TimeUnit.SECONDS);
                distList.offer(new Cell(2, 2), 2000, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        StartGame startGame = new StartGame(8, 3, distList);
        startGame.start();
        //потоки работают
        Assert.assertThat(startGame.isAlivethread(), is(true));
        polzovatel.start();
        try {
            polzovatel.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //надо малость поспать текущему потоку, а то игра стартануть толком не успевает
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startGame.close();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //потоки остановлены
        Assert.assertThat(startGame.isAlivethread(), is(false));
//        }
    }

}