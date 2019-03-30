package ru.job4j.bootstrap;
/**
 * так будет выглдеть наша БД просто добавляем пользователей и возврааем лист
 * в задании больше ничего не требуется
 */

import ru.job4j.bootstrap.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DbStor implements Db<Integer, User> {
    private final ConcurrentHashMap<Integer, User> data;
    private final static DbStor INSTANCE = new DbStor();

    public DbStor() {
        this.data = new ConcurrentHashMap<>();
    }

    public static DbStor getINSTANCE() {
        return INSTANCE;
    }


    @Override
    public User add(User r) {
        Integer id = r.hashCode();
        r.setId(id);
        if (this.data.putIfAbsent(id, r) != null) {
            return new User();
        } else {
            return this.data.get(id);
        }
    }

    @Override
    public Map<Integer, User> findall() {
        return this.data;
    }
}
