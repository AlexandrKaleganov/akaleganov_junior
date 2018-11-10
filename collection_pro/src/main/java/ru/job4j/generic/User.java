package ru.job4j.generic;

/**
 * этот класс просто наследуется от класса Base
 */
public class User extends Base {

    public User(String id) {
        super(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

}
