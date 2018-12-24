package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.err.BiConEx;
import ru.job4j.architecture.err.ConEx;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;


public class DbStoreTest {
    //попробуем реализовать ласс для тестов


    private void init(BasicDataSource s, ConEx<BasicDataSource> con) {
        BasicDataSource source = s;
        try {
            Properties settings = new Properties();
            try (InputStream in = new FileInputStream(new File("src//main//resources//gradle.properties"))) {
                settings.load(in);
            }
            source.setDriverClassName(settings.getProperty("db.driver"));
            source.setUrl(settings.getProperty("db.host"));
            source.setUsername(settings.getProperty("db.login"));
            source.setPassword(settings.getProperty("db.password"));
            source.setMinIdle(5);
            source.setMaxIdle(10);
            source.setMaxOpenPreparedStatements(100);
            con.accept(source);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                source.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void alltestfunc(BiConEx<DbStore, Users> fank) {
        Users users = new Users("12", "sacha", "alexmur07");
        this.init(new BasicDataSource(), source -> {
                    DbStore dbStore = new DbStore(source);
                    Users expected = dbStore.add(users);
                    try {
                        fank.accept(dbStore, expected);
                    } finally {
                        dbStore.deletaALL();
                    }
                }
        );
    }

    /**
     */
    @Test
    public void addDD() {
        this.alltestfunc((bd, exp) -> {
            Assert.assertThat(bd.findById(exp), Is.is(exp));
        });
    }

    /**
     * @throws SQLException
     */
    @Test
    public void findaaalTest() {
        this.alltestfunc((bd, exp) -> {
            System.out.println(bd.findAll().get(0));
            Assert.assertThat(bd.findAll().get(0), Is.is(exp));
        });
    }
}