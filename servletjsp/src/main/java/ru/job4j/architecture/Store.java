package ru.job4j.architecture;

import java.util.Map;

public interface Store {
    public void add(Users users);
    public void update(String id, Users users);
    public void delete(String id);
    public Map findAll();
    public Users findById(String id);
}
