package ru.job4j.architecture;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * методы переделаны на copyonwritearraylist
 */
public class MemoryStore implements Store<Users> {
    private final CopyOnWriteArrayList<Users> database = new CopyOnWriteArrayList<>();
    private static final MemoryStore INSTANCE = new MemoryStore();

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Users add(Users users) {
        this.database.add(users);
        this.database.get(this.database.size() - 1).setId("" + (this.database.size() - 1));
        return this.database.get(this.database.size() - 1);
    }

    @Override
    public void update(Users users) {
        if (users.getName().length() > 0) {
            this.database.get(Integer.valueOf(users.getId())).setName(users.getName());
        }
        if (users.getLogin().length() > 0) {
            this.database.get(Integer.valueOf(users.getId())).setLogin(users.getLogin());
        }
    }

    @Override
    public void delete(Users users) {
        this.database.remove(Integer.valueOf(users.getId()));
    }

    @Override
    public CopyOnWriteArrayList<Users> findAll() {
        return this.database;
    }

    @Override
    public Users findById(Users users) {
        return this.database.get(Integer.valueOf(users.getId()));
    }
}
