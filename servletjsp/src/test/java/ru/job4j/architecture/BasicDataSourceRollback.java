package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;

import java.lang.reflect.Proxy;
import java.sql.SQLException;

public class BasicDataSourceRollback {
    public static BasicDataSource create(BasicDataSource connection) throws SQLException {
        connection.setDefaultAutoCommit(false);
        return (BasicDataSource) Proxy.newProxyInstance(
                BasicDataSourceRollback.class.getClassLoader(),
                new Class[]{BasicDataSource.class},
                (proxy, method, args) -> {
                    Object rsl = null;
                    if ("close".equals(method.getName())) {
                        connection.getRollbackOnReturn();
                        connection.close();
                    } else {
                        rsl = method.invoke(connection, args);
                    }
                    return rsl;
                }
        );
    }
}