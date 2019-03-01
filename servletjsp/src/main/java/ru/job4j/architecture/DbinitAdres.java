package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;

public class DbinitAdres {
    private BasicDataSource conn;

    public DbinitAdres(BasicDataSource conn) {
        this.conn = conn;
    }
}
