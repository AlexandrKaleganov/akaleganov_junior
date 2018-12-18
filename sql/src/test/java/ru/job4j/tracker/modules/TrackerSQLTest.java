package ru.job4j.tracker.modules;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.*;

public class TrackerSQLTest {


    public Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("db.driver"));
            return DriverManager.getConnection(
                    config.getProperty("db.host"),
                    config.getProperty("db.login"),
                    config.getProperty("")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
//    @Test
//    public void testirovanieTrackerSQLADD() { //проверка метода add
//        Items items = new Items("1231231313", "Ничего не работает, компьютер не запускается");
//        Items expected = null;
//        try (TrackerSQL TrackerSQL = new TrackerSQL(this.init())) {
//            expected = TrackerSQL.add(items);
//            Assert.assertThat(expected.getName(), Is.is(items.getName()));
//            TrackerSQL.deleteAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testirovanieTrackerSQLADD() { //проверка метода add
        Items items = new Items("1231231313", "Ничего не работает, компьютер не запускается");
        Items expected = null;
        try (Connection connection = ConnectionRollback.create(this.init());
                TrackerSQL TrackerSQL = new TrackerSQL(connection)) {
            expected = TrackerSQL.add(items);
            Assert.assertThat(expected, Is.is(items));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    @Test
//    public void addCommentsTest() { //проверка метода добавления коментариев
//        Items items = new Items("dddddddd", "Ничего не работает, компьютер не запускается");
//        try (TrackerSQL TrackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
//            TrackerSQL.add(items);
//            TrackerSQL.addComment(1, "вот комментарий");
//            Assert.assertThat(TrackerSQL.findById(1).getComments().get(0), is("вот комментарий"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testirovanieTrackerSQLdelete() { //проверка метода удаления заявки
//        Items items3 = new Items("Я твой дом труба шатал", "zzz");
//        try (TrackerSQL TrackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
//            TrackerSQL.add(items3);
//            TrackerSQL.delete(1);
//            Assert.assertThat(TrackerSQL.findById(1), Is.is((Items) null));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
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