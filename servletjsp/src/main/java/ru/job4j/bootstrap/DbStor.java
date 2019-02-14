package ru.job4j.bootstrap;
/**
 * так будет выглдеть наша БД просто добавляем пользователей и возврааем лист
 * в задании больше ничего не требуется
 */

import ru.job4j.bootstrap.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DbStor<E, R> implements Db<E, R> {
    private final ConcurrentHashMap<E, R> data;
    private final static DbStor<Integer, User> INSTANCE = new DbStor();

    public DbStor() {
        this.data = new ConcurrentHashMap<>();
    }

    public static DbStor getINSTANCE() {
        return INSTANCE;
    }


    @Override
    public R add(E e, R r) {
        return this.data.put(e, r);
    }

    @Override
    public Map<E, R> findall() {
        return this.data;
    }
}
