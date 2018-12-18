package ru.job4j.tracker.modules;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;


public class TrackerSQLTest {


    public Connection init() {
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
    public void alltestfunc(BiConEx<TrackerSQL, Items> fank) {
        Items items = new Items("item", "Ничего не работает, компьютер не запускается");
        try (TrackerSQL TrackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Items expected = TrackerSQL.add(items);
            fank.submit(TrackerSQL, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @Test
    public void testirovanieTrackerSQLdelete() { //проверка метода удаления заявки
        this.alltestfunc((trek, item) -> {
            Assert.assertThat(trek.findById(item.getId()), Is.is(item));
            trek.delete(item.getId());
            Assert.assertThat(trek.findById(item.getId()), is((Items) null));
        });
    }
//
//
//    @Test
//    public void testirovanieTrackerSQLfindAll() { //проверка метода показать все заявки
//        Items items = new Items(1, "Пом", "Ничего не работает, компьютер не запускается");
//        Items items1 = new Items(2, "Пом3", "От поддержки никакого толка");
//        try (TrackerSQL TrackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
//
//            TrackerSQL.add(items);
//            TrackerSQL.add(items1);
//            ArrayList<Items> actual = TrackerSQL.findAll();
//            ArrayList<Items> expected = new ArrayList<>(Arrays.asList(items, items1));
//            assertThat(actual.get(0), is(expected.get(0)));
//            assertThat(actual.get(1), is(expected.get(1)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testirovanieTrackerSQLById() throws Exception {   // поверка метода поиск заявки по id и получение всех заявок
//        Items items = new Items(1, "Нужна помощь", "Ничего не работает, компьютер не запускается");
//        try (TrackerSQL TrackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
//            TrackerSQL.add(items);
//            assertThat(TrackerSQL.findById(1), is(items));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testirovanieTrackerSQLByName() throws Exception { // проверка метода поиска заявок  по имени(похожие заявки)
//        Items items = new Items(1, "eeeeeeeeeee", "Ничего не работает, компьютер не запускается");
//        try (TrackerSQL TrackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
//            TrackerSQL.add(items);
//            ArrayList<Items> result = null;
//            result = TrackerSQL.findByName("eeeeeeeeeee");
//            assertThat(result.get(0).getName(), is("eeeeeeeeeee"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}