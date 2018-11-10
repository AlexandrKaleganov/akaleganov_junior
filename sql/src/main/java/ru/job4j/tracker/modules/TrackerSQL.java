package ru.job4j.tracker.modules;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

/**
 * я переделал на лямбды все методы, которые осуществялют поиск
 * за исключением поиск по имени заявки
 * дело в том что поиск по имени заявки у меня возвращает не конкретную заявку, а список заявок
 * которые содержат в названии запрос СПИСОК - по этому код не стал множить
 */
public class TrackerSQL implements ITracker, AutoCloseable {
    private Connection conn;
    private final Config config;
    private ArrayList<Items> items = new ArrayList<>();
    private final static Logger LOG = LoggerFactory.getLogger(Tracker.class);
    private boolean exitProgramm = true; //пока параметр переменной будет равен шести, программу будет продолжнать работать

    public TrackerSQL(Config config) {
        this.config = config;
        this.connectToData();
        this.addTable();
    }

    /**
     * метод подключения к базе
     */
    private void connectToData() {
        try {
            this.conn = DriverManager.getConnection(config.getProperties("db.host"), config.getProperties("db.login"), config.getProperties("db.password"));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }

    }

    private void addTable() {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(config.getScript("--items"));
            st.executeUpdate(config.getScript("--comments"));
            st.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }


    /**
     * добавление заявок - и мы просто делаем один шаг по элемену++
     *
     * @param item
     * @return
     */
    public Items add(Items item) {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO items(name, descc) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);) {
            st.setString(1, item.getName());
            st.setString(2, item.getDesc());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
                item.setId(rs.getInt(1));
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
    }

    /**
     * редактирование заявок здесь у нас водит id  заявки и новые данные заявки
     * в методе id  присваивается объекту итем и после мы шагаем по массиву и ищем заявку с таким же id а когда накодим присваиваем этой заявке
     * новые знавения с входящей заявке при этом id  останется преждним
     *
     * @param id
     * @param
     */
    public void replace(int id, Items item) {
        try (PreparedStatement st = conn.prepareStatement("UPDATE items SET NAME = ?, descc = ? where items.id = ? ");) {
            st.setString(1, item.getName());
            st.setString(2, item.getDesc());
            st.setInt(3, id);
            updateExecut(st);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * удаление заявок сначало удаляем все коментарии заявок , потом удаляем сами заявки
     * и присваиваю ей значеие null
     *
     * @param id
     */
    public void delete(int id) {
        try (PreparedStatement st = conn.prepareStatement("delete from comments where comments.id_items = ?");
             PreparedStatement sttwo = conn.prepareStatement("DELETE from items where items.id = ?")) {
            st.setInt(1, id);
            sttwo.setInt(1, id);
            updateExecut(st);
            updateExecut(sttwo);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void updateExecut(PreparedStatement st) throws SQLException {

        st.executeUpdate();

    }

    /**
     * получение списка всех заявок - этот метод нам не просто возвраящает все заявки но и сортирует их по id
     *
     * @return
     */
    public ArrayList<Items> findAll() {
        ArrayList<Items> rsl = new ArrayList<>();

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("select * from items ORDER BY items.id")) {
            while (rs.next()) {
                rsl.add(new Items(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4).getTime()));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * получение списка заявок по имени
     *
     * @param key
     * @return
     */
    public ArrayList<Items> findByName(String key) {
        ArrayList<Items> rsl = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement("SELECT * FROM items as i where i.name like ?")) {
            st.setString(1, "%" + key + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    rsl.add(new Items(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4).getTime()));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }

        return rsl;
    }


    /**
     * получение заявки по id и возвращаем пользвателю + список коментариев по данной заявке
     *
     * @param id
     * @return
     */
    public Items findById(int id) {
        Items rsl = null;
        try (PreparedStatement st = conn.prepareStatement("select i.id, i.name, i.descc, i.created, c.comment FROM items as i left  OUTER JOIN comments as c on c.id_items =  i.id where i.id = ?")) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                rsl = new Items(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4).getTime());
                rsl.addComment(rs.getString(5));
                while (rs.next()) {
                    rsl.addComment(rs.getString(5));
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }

        return rsl;
    }


    /**
     * Метод добавления комментарий в заявку
     */
    public void addComment(int id, String comments) {
        try (PreparedStatement st = conn.prepareStatement("insert INTO comments(comment, id_items) VALUES (?, ?)")) {
            st.setString(1, comments);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }

    }

    /**
     * планирую сделать так: привыборе в меню цифры 6, у нас вызовется бьект класса
     * Exitprogramm , и его метод  public void execute(Input input, Tracker tracker)
     * этот метод вызовет нашь метод setExitProgramm. который в свою очередь изменит параметр
     * переменно exitProgramm и программа завершит свою работу
     */
    public void setExitPrograpp() {
        this.exitProgramm = false;
    }

    public boolean getExitProgramm() {
        return exitProgramm;
    }

    @Override
    public void close() throws Exception {
        if (!this.conn.isClosed()) {
            this.conn.close();
        }
    }
}