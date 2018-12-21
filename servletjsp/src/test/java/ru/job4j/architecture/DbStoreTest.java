package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
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
            source.setPassword(settings.getProperty(""));
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

    /**
     * @throws SQLException
     */
    @Test
    public void addDD() throws SQLException {
        Users users = new Users("12", "sacha", "alexmur07");
        BasicDataSource cor = new PoolRollback();
        this.init(cor, source -> {
                    DbStore dbStore = new DbStore(source);
                    Users users1 = dbStore.add(users);
                    System.out.println(users1.getId());

                    Assert.assertThat(dbStore.findById(users1).getName(), Is.is(users.getName()));
                }

        );
        System.out.println(cor.isClosed());
    }

    /**
     * @throws SQLException
     */
    @Test
    public void findaaalTest() throws SQLException {
        Users users = new Users("12", "sacha", "alexmur07");
        BasicDataSource cor = new PoolRollback();
        this.init(cor, source -> {
                    DbStore dbStore = new DbStore(source);
                    Users expected = dbStore.add(users);
                    Assert.assertThat(expected, Is.is(dbStore.findAll().get(0)));
                }

        );
        System.out.println(cor.isClosed());
    }
}