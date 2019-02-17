package ru.job4j.bootstrap;

import ru.job4j.bootstrap.model.User;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

public class Dispatch {
    private final Db<Integer, User> db;
    private final HashMap<String, Function<User, Optional>> command;
    private final static Dispatch INSTANCE = new Dispatch();

    public Dispatch() {
        this.db = DbStor.getINSTANCE();
        this.command = new HashMap<>();
        this.command.put("add", (u ->
                Optional.of(this.db.add(u.getId(), u))
        ));
        this.command.put("findall", (u ->
                Optional.of(this.db.findall())
        ));
    }

    public static Dispatch getINSTANCE() {
        return INSTANCE;
    }

    /**
     * @param cmd
     * @param user
     * @param e
     * @param <E>  парамтр, который мы хотим получить
     * @return
     */
    public <E> E submit(String cmd, User user, E e) {
        Optional<E> rsl = Optional.empty();
        rsl = this.command.get(cmd).apply(user);
        return rsl.get();
    }
}
