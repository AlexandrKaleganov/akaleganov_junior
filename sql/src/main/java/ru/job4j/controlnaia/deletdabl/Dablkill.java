package ru.job4j.controlnaia.deletdabl;


import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * решил сделать на sqLite
 */
public class Dablkill implements AutoCloseable {
    private final static Logger LOGGER = Logger.getLogger(Dablkill.class);
    private Connection con;
    private final Config config;

    /**
     * конструткор
     *
     * @param config
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    Dablkill(Config config) throws SQLException {
        this.config = config;
        init();
        addtoTable();
    }

    /**
     * коннект к базе
     * как ранее я выяснил  Class.forName очень нужная вещь
     * для динамической подгрузки драйвера наче потом может не коннектиться к базе
     *
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    public boolean init() throws SQLException {
        this.con = DriverManager.getConnection(config.getCommand("db.host"));
        this.con.setAutoCommit(false);
        return this.con != null;
    }

    /**
     * метод создания таблицы если она отсутствует
     * не стал заморачиваться как в задании магнит
     * создастся таблица или нет - не важно данные в таблице убудут удалены для тестов
     */
    private void addtoTable() {
        try (Statement st = con.createStatement()) {
            st.executeUpdate("create table if not exists items (id serial PRIMARY KEY, name varchar(200)  NOT NULL)");
            st.executeUpdate("delete from items");
            this.con.commit();
        } catch (SQLException e) {
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                LOGGER.error(e1.getMessage(), e);
            }
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * сделаю метод добавления данных в БД,
     * добавлять буду только стринкговые значения
     * т.е. имя итемсов и всё
     */
    public void addtoItemName(String items) {
        try (PreparedStatement st = con.prepareStatement("INSERT INTO items(name) VALUES(?)")) {
            st.setString(1, items);
            st.executeUpdate();
            this.con.commit();
        } catch (SQLException e) {
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                LOGGER.error(e1.getMessage(), e);
            }
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Этот метод вернёт нам лист имём из БД
     *
     * @return
     */
    public ArrayList<String> getListItems() {
        ArrayList<String> rsl = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            try (ResultSet rs = st.executeQuery("select * from items")) {
                while (rs.next()) {
                    rsl.add(rs.getString(2));
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * метод удаления дублирующих данных из БД
     */
    public void deleteDabl() {
        try (Statement st = con.createStatement()) {
            try (ResultSet rs = st.executeQuery("select name from items group by name having count(*) > 1")) {
                while (rs.next()) {
                    try (PreparedStatement pst = con.prepareStatement("delete from items where name = ?")) {
                        pst.setString(1, rs.getString(1));
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }

                }
                this.con.commit();
            } catch (SQLException e) {
                try {
                    this.con.rollback();
                } catch (SQLException e1) {
                    LOGGER.error(e1.getMessage(), e);
                }
                LOGGER.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
        if (!this.con.isClosed()) {
            this.con.close();
        }
    }
}
