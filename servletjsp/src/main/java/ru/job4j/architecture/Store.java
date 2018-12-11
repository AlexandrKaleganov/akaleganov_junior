package ru.job4j.architecture;

import java.util.List;

/**
 * интерфейс класса, который будет напрямую работать с нашей БД
 */



public interface Store<Users> {
    Users add(Users users);

    void update(Users users);

    void delete(Users users);

    ru.job4j.architecture.Users add(ru.job4j.architecture.Users users);

    void update(ru.job4j.architecture.Users users);

    void delete(ru.job4j.architecture.Users users);

    List<Users> findAll();

    Users findById(Users users);

    ru.job4j.architecture.Users findById(ru.job4j.architecture.Users users);
}
