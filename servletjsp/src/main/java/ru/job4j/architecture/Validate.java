package ru.job4j.architecture;


import ru.job4j.architecture.err.DatabaseException;

import java.util.Map;

public interface Validate {
    public String add(Users users) throws DatabaseException;
    public String update(String id, Users users) throws DatabaseException;
    public String delete(String id) throws DatabaseException;
    public Map findAll() throws DatabaseException;
    public Users findById(String id) throws DatabaseException;
}
