package ru.job4j.tracker.modules;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;


public class TrackerSQLTest {

    /**
     * тест добавления item
     */
    @Test
    public void testirovanieTrackerSQLADD() {
        this.alltestfunc((trek, item) -> {
            Assert.assertThat(trek.findById(item.getId()).getName(), Is.is("item"));
        });
    }

    /**
     * тест добавления коментария
     */
    @Test
    public void addCommentsTest() {
        this.alltestfunc((trek, item) -> {
            trek.addComment(item.getId(), "comment");
            Assert.assertThat(trek.findById(item.getId()).getComments().get(0), Is.is("comment"));
        });
    }

    /**
     * проверка метода удаления заявки
     */
    @Test
    public void testirovanieTrackerSQLdelete() {
        this.alltestfunc((trek, item) -> {
            Assert.assertThat(trek.findById(item.getId()), Is.is(item));
            trek.delete(item.getId());
            Assert.assertThat(trek.findById(item.getId()), is((Items) null));
        });
    }

    /**
     * проверка метода показать все заявки
     *
     * @return
     */
    @Test
    public void testirovanieTrackerSQLfindAll() {
        this.alltestfunc((trek, item) -> {
            ArrayList<Items> expected = trek.findAll();
            Assert.assertThat(expected.get(0), is(item));
        });
    }

    /**
     * поверка метода поиск заявки по id и получение всех заявок
     *
     * @return
     */
    @Test
    public void testirovanieTrackerSQLById() throws Exception {   //
        this.alltestfunc((trek, item) -> {
            Assert.assertThat(trek.findById(item.getId()), Is.is(item));
        });
    }

    /**
     * проверка метода поиска заявок  по имени(похожие заявки)
     *
     * @return
     */
    @Test
    public void testirovanieTrackerSQLByName() throws Exception { //
        this.alltestfunc((trek, item) -> {
            ArrayList<Items> expected = trek.findByName(item.getName());
            Assert.assertThat(expected.get(0), is(item));
        });
    }

    private Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("db.driver"));
            return DriverManager.getConnection(
                    config.getProperty("db.host"),
                    config.getProperty("db.login"),
                    config.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * рефактор тастов удаление  корявого трайкетч
     *
     * @param fank
     */
    private void alltestfunc(BiConEx<TrackerSQL, Items> fank) {
        Items items = new Items("item", "Ничего не работает, компьютер не запускается");
        try (TrackerSQL TrackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Items expected = TrackerSQL.add(items);
            fank.submit(TrackerSQL, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}