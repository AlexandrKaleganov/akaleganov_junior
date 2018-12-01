package ru.job4j.architecture;


import ru.job4j.architecture.err.DatabaseException;

import java.util.Map;

public interface Validate {
    String add(Users users) throws DatabaseException;

    String update(Users users) throws DatabaseException;

    String delete(Users users) throws DatabaseException;

    Map findAll() throws DatabaseException;

    Users findById(Users users) throws DatabaseException;
}
