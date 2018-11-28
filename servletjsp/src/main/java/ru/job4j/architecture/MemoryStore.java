package ru.job4j.architecture;

import javax.jws.soap.SOAPBinding;
import java.util.concurrent.ConcurrentHashMap;

/**
 * база данных на канкарент хеш мап, в этом классе никаких проверок вообще нету
 * голая логика, если напрямую пробывать работать с этим классом
 * то никаких проверок по изменению в БД не будет метод адд
 * в наглую сможет изменить объект, который уже есть в бд
 * а метод findById() может выбросить исключение null pointer exception
 */
public class MemoryStore implements Store {
    private final ConcurrentHashMap<String, Users> database = new ConcurrentHashMap<>();
    private static final MemoryStore INSTANCE = new MemoryStore();

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(Users users) {
        this.database.put(users.getId(), users);
    }

    @Override
    public void update(String id, Users users) {
        users.setId(id);
        this.database.put(id, users);
    }

    @Override
    public void delete(String id) {
        this.database.remove(id);
    }

    @Override
    public ConcurrentHashMap<String, Users> findAll() {
        return this.database;
    }

    @Override
    public Users findById(String id) {
        return this.database.get(id);
    }
}
