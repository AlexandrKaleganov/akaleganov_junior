package ru.job4j.generic;

/**
 * класс хранилище для объектов вкласса User своего рода база
 */
public class UserStore extends AbstractStore<User> implements Store<User> {
    SimpleArray<User> users = new SimpleArray<>(10);
}
