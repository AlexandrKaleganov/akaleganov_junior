package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;

import java.util.Map;

public class DbStore implements Store<Users> {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static DbStore INSTANCE = new DbStore();

    public DbStore() {
        SOURCE.setUrl("...");
        SOURCE.setUsername("...");
        SOURCE.setPassword("...");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
    }

    public static DbStore getInstance() {
        return INSTANCE;
    }
    @Override
    public void add(Users users) {

    }

    @Override
    public void update(Users users) {

    }

    @Override
    public void delete(Users users) {

    }

    @Override
    public Map findAll() {
        return null;
    }

    @Override
    public Users findById(Users users) {
        return null;
    }
}
