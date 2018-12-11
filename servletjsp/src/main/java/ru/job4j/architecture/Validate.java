package ru.job4j.architecture;

/**
 * интерфейс класса, который будет выполнять проверку возможно ли данное деиствие и возвращять
 * результат решил сделать так: если получается выполнить действие
 * то всё норм
 * иначе вылетает ошибка и она обрабатываестся
 */

import ru.job4j.architecture.err.DatabaseException;

import java.util.List;


public interface Validate {
    String add(Users users) throws DatabaseException;

    String update(Users users) throws DatabaseException;

    String delete(Users users) throws DatabaseException;

    List<Users> findAll();

    Users findById(Users users) throws DatabaseException;
}
