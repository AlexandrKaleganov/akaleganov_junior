package ru.job4j.architecture;

import javax.jws.soap.SOAPBinding;
import java.util.concurrent.ConcurrentHashMap;

/**
 * база данных на канкарент хеш мап?,
 */
public class MemoryStore implements Store {
    private final ConcurrentHashMap<String, Users> database = new ConcurrentHashMap<>();
    private static final MemoryStore INSTANCE = new MemoryStore();

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    public int getSize() {
        return database.size();
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
        Users rsl = null;
        if (this.database.containsKey(id)) {
                rsl = this.database.get(id);
        }
        return rsl;
    }
}
