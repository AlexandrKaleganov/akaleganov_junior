package ru.job4j.architecture;


public interface Validate {
    public boolean add(Users users);
    public boolean update(String id, Users users);
    public boolean delete(String id);
    public boolean findAll();
    public boolean findById(String id);
}
