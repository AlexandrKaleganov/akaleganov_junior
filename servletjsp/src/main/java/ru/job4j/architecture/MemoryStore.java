package ru.job4j.architecture;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore implements Store {
    private final ConcurrentHashMap<String, Users> database = new ConcurrentHashMap<>();


    @Override
    public void add(Users users) {
        this.database.put(users.getId(), users);
    }

    @Override
    public void update(String id, Users users) {
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
    public Users indById(String id) {
        return this.database.get(id);
    }
}
