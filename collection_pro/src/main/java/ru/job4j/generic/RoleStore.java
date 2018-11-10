package ru.job4j.generic;

/**
 * класс хранилище для оъектов класса Role своего рода база
 */
public class RoleStore extends AbstractStore<Role> implements Store<Role> {
    private SimpleArray<Role> roles = new SimpleArray<>(10);

}
