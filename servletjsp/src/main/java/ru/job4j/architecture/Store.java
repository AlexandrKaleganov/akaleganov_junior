package ru.job4j.architecture;
/**
 * интерфейс класса, который будет напрямую работать с нашей БД
 */

import java.util.Map;

public interface Store<Users>{
    void add(Users users);

    void update(Users users);

    void delete(Users users);

    Map findAll();

    Users findById(Users users);
}
