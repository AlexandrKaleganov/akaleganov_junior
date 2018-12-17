package ru.job4j.architecture;

import java.util.List;

/**
 * интерфейс класса, который будет напрямую работать с нашей БД
 */



public interface Store<T> {
    T add(T users);

    void update(T users);

    void delete(T users);

    ru.job4j.architecture.Users add(ru.job4j.architecture.Users users);

    void update(ru.job4j.architecture.Users users);

    void delete(ru.job4j.architecture.Users users);

    List<T> findAll();

    Users findById(T users);

    ru.job4j.architecture.Users findById(ru.job4j.architecture.Users users);
}
