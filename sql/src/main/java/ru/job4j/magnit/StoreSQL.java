package ru.job4j.magnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Kaleganov
 * класс будет отвечать за подключеие к БАЗЕ данных
 * генерацию данных и получение списка объектов
 */
public class StoreSQL implements AutoCloseable {
    private Connection conn = null;
    private final Conf conf;
    private final static Logger LOG = LoggerFactory.getLogger(StoreSQL.class);

    public StoreSQL(Conf conf) {
        this.conf = conf;
    }

    /**
     * коннект к базе SQLite
     *
     * @return
     */
    public boolean init() {
        try {
            this.conn = DriverManager.getConnection(this.conf.getDataconfig("db.host"));
            conn.setAutoCommit(false);
            conn.commit();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return this.conn != null;
    }

    /**
     * генерирую данные для таблицы
     * тут без заморочек данные генерируются  от 1 до 10
     *
     * @param n
     */
    public void generate(int n) {
        this.resetTable();
        for (int i = 0; i < n; i++) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO entry VALUES (?)")) {
                st.setInt(1, 1 + i);
                st.executeUpdate();
                System.out.println(i);
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        try {
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * создание новой таблицы в случае отсутствия
     * или очистка данных в сущаествующей таблице
     */
    private void resetTable() {
        if (!this.isTable("entry")) {
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("create table entry (field INTEGER)");
                conn.commit();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("DELETE FROM entry");
                conn.commit();
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * проверка есть ли в базе таблица
     * просто получаю список всех таблиц
     * и прохожусь по второй колонке и проверяю есть ли данная таблица в базе
     * и если таблица есть то получаю списко всех полей таблицы и проверяю совпадает ли в ней
     * колонки и если не т то таблицу полностью удаляю
     *
     * @param table
     * @return
     */
    private boolean isTable(String table) {
        boolean rsl = false;
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("select * from sqlite_master")) {
            while (rs.next()) {
                if (table.contains(rs.getString(2))) {
                    try (Statement stt = conn.createStatement(); ResultSet rss = stt.executeQuery("pragma table_info(entry);")) {
                        rss.next();
                        if (rss.getString(2).contains("field") && rss.getString(3).contains("INTEGER")) {
                            rsl = true;
                        } else {
                            try (Statement sttt = conn.createStatement()) {
                                sttt.executeUpdate("DELETE FROM entry;");
                                conn.commit();
                            } catch (SQLException e) {
                                conn.rollback();
                                LOG.error(e.getMessage(), e);
                            }


                        }
                    } catch (SQLException e) {
                        conn.rollback();
                        LOG.error(e.getMessage(), e);
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public void close() throws Exception {
        if (!this.conn.isClosed()) {
            this.conn.close();
        }
    }

    /**
     * получение спискать объектов
     *
     * @return
     */
    public List<EntryList.Entry> getList() {
        ArrayList<EntryList.Entry> res = new ArrayList<>();
        try (Statement statement = this.conn.createStatement(); ResultSet rs = statement.executeQuery("select * from entry;")) {
            while (rs.next()) {
                res.add(new EntryList.Entry(rs.getInt(1)));
            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return res;
    }
}
