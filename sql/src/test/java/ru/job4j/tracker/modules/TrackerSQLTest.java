package ru.job4j.tracker.modules;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TrackerSQLTest {

    private final static Logger LOG = (Logger) LoggerFactory.getLogger(TrackerSQLTest.class);


    @Test
    public void testirovanieTrackerSQLADD() { //проверка метода add
        Config config = new Config();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(config.getProperties("db.host"), config.getProperties("db.login"), config.getProperties("db.password"));

            Items items = new Items("Нужна помощь", "Ничего не работает, компьютер не запускается");
            Items items1 = new Items("Хелп", "От поддержки никакого толка");
            Items expected = null;
            try (TrackerSQL TrackerSQL = new TrackerSQL(config)) {
                expected = TrackerSQL.add(items);
                try (Statement st = connection.createStatement();
                     ResultSet rs = st.executeQuery("SELECT i.name FROM items as i where i.name LIKE '%Хелп%' ");) {
                    assertThat(expected.getName(), is(items.getName()));
                    rs.next();
                    assertThat(rs.getString(1), is(items1.getName()));
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void addCommentsTest() { //проверка метода добавления коментариев
        try (TrackerSQL TrackerSQL = new TrackerSQL(new Config())) {
            TrackerSQL.addComment(1, "вот комментарий");
            Assert.assertThat(TrackerSQL.findById(1).getComments().get(0), is("вот комментарий"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testirovanieTrackerSQLReplace() { //проверка метода изменения заявки
        Items items3 = new Items("Я твой дом труба шатал", "zzz");
        Config config = new Config();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(config.getProperties("db.host"), config.getProperties("db.login"), config.getProperties("db.password"));

            try (TrackerSQL TrackerSQL = new TrackerSQL(config)) {

                TrackerSQL.replace(1, items3);
                try (Statement st = connection.createStatement();
                     ResultSet rs = st.executeQuery("SELECT i.name, i.descc FROM items as i where i.id = 1 ")) {
                    rs.next();
                    assertThat(rs.getString(1), is(items3.getName()));
                    assertThat(rs.getString(2), is(items3.getDesc()));
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testirovanieTrackerSQLdelete() { //проверка метода удаления заявки
        Config config = new Config();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(config.getProperties("db.host"), config.getProperties("db.login"), config.getProperties("db.password"));

            try (TrackerSQL TrackerSQL = new TrackerSQL(config);
                 Statement st = connection.createStatement();
                 ResultSet rs = st.executeQuery("SELECT COUNT(id) FROM items")) {

                rs.next();
                //запоминаем сколько у нас колонок было, и отнимаем 1
                int expected = rs.getInt(1) - 1;
                //удаяляем последнюю заявку в базе
                TrackerSQL.delete(expected);
                try (ResultSet rs1 = st.executeQuery("SELECT COUNT(id) FROM items")) {
                    rs1.next();
                    int actual = rs1.getInt(1);
                    assertThat(actual, is(expected));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testirovanieTrackerSQLfindAll() { //проверка метода показать все заявки
        Config config = new Config();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(config.getProperties("db.host"), config.getProperties("db.login"), config.getProperties("db.password"));


            try (TrackerSQL TrackerSQL = new TrackerSQL(config);
                 TrackerSQL TrackerSQL1 = new TrackerSQL(config)) {
                Items items = new Items("Нужна помощь", "Ничего не работает, компьютер не запускается");
                Items items1 = new Items("Хелп", "От поддержки никакого толка");
                TrackerSQL.add(items);
                TrackerSQL.add(items1);
                ArrayList<Items> ekepted = TrackerSQL.findAll();
                for (Items i : ekepted) {
                    TrackerSQL1.add(i);
                }
                assertThat(TrackerSQL.findAll(), is(TrackerSQL1.findAll()));

            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testirovanieTrackerSQLById() throws Exception {   // поверка метода поиск заявки по id и получение всех заявок
        Config config = new Config();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(config.getProperties("db.host"), config.getProperties("db.login"), config.getProperties("db.password"));


            Boolean rsl = null;
            try (TrackerSQL TrackerSQL = new TrackerSQL(config)) {
                int actual = TrackerSQL.findById(1).getId();
                rsl = false;
                ArrayList<Items> temp = TrackerSQL.findAll();
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getId() == actual) {
                        rsl = true;
                        break;
                    }
                }
                assertThat(rsl, is(true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testirovanieTrackerSQLByName() { // проверка метода поиска заявок  по имени(похожие заявки)
        Config config = new Config();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(config.getProperties("db.host"), config.getProperties("db.login"), config.getProperties("db.password"));

            ArrayList<Items> result = null;
            try (TrackerSQL TrackerSQL = new TrackerSQL(config)) {
                result = TrackerSQL.findByName("Нужна");
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertThat(result.get(0).getName(), is("Нужна помощь"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}