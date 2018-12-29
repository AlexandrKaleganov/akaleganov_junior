package ru.job4j.architecture;

import java.util.List;
import java.util.Optional;
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
    public Users update(Users users) {
        if (users.getName().length() > 0) {
            this.database.get(Integer.valueOf(users.getId())).setName(users.getName());
        }
        if (users.getLogin().length() > 0) {
            this.database.get(Integer.valueOf(users.getId())).setLogin(users.getLogin());
        }
        return this.findById(users);
    }

    /**
     * вернёт старый объект
     * @param users
     * @return
     */
    @Override
    public Users delete(Users users) {
        int i = Integer.valueOf(users.getId());
        return this.database.remove(i);
    }

    @Override
    public CopyOnWriteArrayList<Users> findAll() {
        return this.database;
    }

    @Override
    public Users findById(Users users) {
        Optional<Users> rsl = Optional.empty();
        rsl = Optional.of(this.database.get(Integer.valueOf(users.getId())));
        return rsl.orElse(new Users());
    }

    /**
     * метод очистки бд был создан для тестов
     * @return
     */
    @Override
    public List<Users> deleteALL() {
        this.database.clear();
        return this.findAll();
    }
}
