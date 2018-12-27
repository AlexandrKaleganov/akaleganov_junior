package ru.job4j.architecture;

import java.util.List;

/**
 * интерфейс класса, который будет напрямую работать с нашей БД
 */



public interface Store<T> {
    T add(T users);

    void update(T users);

    void delete(T users);

    List<T> findAll();

    T findById(T users);

    List<T> deleteALL();
}
