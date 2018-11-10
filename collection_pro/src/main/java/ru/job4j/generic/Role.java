package ru.job4j.generic;

/**
 * этот класс просто наследуется от класса Base
 */
public class Role extends Base {
    public Role(String id) {
        super(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
