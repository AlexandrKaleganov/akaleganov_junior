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
    /**
     * метод будет инициализировать нашь коннект
     * @param s
     * @param con
     */
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

    /**
     * метод рефакторинг в любом случае при тестировании мне надо будет подключиться к базе,
     * добавиитть туда объект , произвести тест метода, и очистить базу
     * с роллбаком фикгня получалась, т.к. у меня в каждом методе коннект закрывался
     * суть работы - функциональный метод получает готовую бд, и оъект который мы получили при добавлении,
     * чтобы по id  мы могли его обновить, удалить, и т.д...
     * @param fank
     */
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
     * тестируем метод добавления в бд
     */
    @Test
    public void addDD() {
        this.alltestfunc((bd, exp) -> {
            Assert.assertThat(bd.findById(exp), Is.is(exp));
        });
    }
//
//    /**
//     * тестируем метод получения всех щаявок по id
//     * @throws SQLException
//     */
//    @Test
//    public void findaaalTest() {
//        this.alltestfunc((bd, exp) -> {
//            Assert.assertThat(bd.findAll().get(0), Is.is(exp));
//        });
//    }
//    @Test
//    public void updateTest() {
//        this.alltestfunc((bd, exp)->{
//            bd.update(new Users(exp.getId(), "lex", "lex07"));
//            Assert.assertThat(bd.findById(exp).getName(), Is.is("lex"));
//        });
//    }
//    @Test
//    public void deleteTest() {
//        this.alltestfunc((db, exp)->{
//            db.delete(exp);
//            Assert.assertThat(db.findById(exp), Is.is((Users)null));
//        });
//    }
}