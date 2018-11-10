package ru.job4j.nonblockhash;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * написал очень интересный тест, значит суть заключается в том,
 * что если запустить 8 или 10 потоков ничего у нас не выйдет
 * вывалится нулл поинтер экцепшен, т.к. в атомарной переменной
 * не будет отловленое исключение
 * по этому я решил в цикле запустить туеву кучу потоков, скжем 100 тысяч
 * и решил подождать 5 секунд а потом проверил и вот оно исключение, хоть
 * мы и пользуемся потокобезопасной колекцией , однако мы можем наткнуться на подобную ситуацию,
 * почему - я не понял, наверно потому что мы меняем не атамарную переменную
 * не блокирующий кеш у нас кеш и правдо не блокируется
 *
 */

public class DatabasecacheTest {

    @Test
    public void update() throws InterruptedException {
        Base base = new Base(1, 1, "sasha");

        Databasecache data = new Databasecache();
        data.add(base);
        for (int i = 2; i < 10000; i++) {
            int finalI = i;
            new Thread(() -> {
                data.update(new Base(1, finalI, ""));
            }).start();
        }
        Thread.sleep(5000);
        assertThat(data.getEx(), is("Объект уже обновлён до текущей версии, в обновлении отказано"));
    }
}