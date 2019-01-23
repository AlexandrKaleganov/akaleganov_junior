package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.architecture.err.BiConEx;
import ru.job4j.architecture.err.FunEx;
import ru.job4j.architecture.err.TriplexConEx;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DbStore implements Store<Users> {
    //не стал делать поле статичным, т.к. иначе не зню как прикрутить тесты
    private BasicDataSource source;
    private static final DbStore INSTANCE = new DbStore();
    private final Map<Class<?>, TriplexConEx<Integer, PreparedStatement, Object>> dispat = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(DbStore.class);

    public DbStore() {
        this.source = new BasicDataSource();
        this.init();
        this.dispat.put(Integer.class, (index, ps, value) -> ps.setInt(index, (Integer) value));
        this.dispat.put(String.class, (index, ps, value) -> ps.setString(index, (String) value));
        this.addTable();
    }

    /**
     * метод инициализации объекта
     */
    private void init() {
        try {
            Properties settings = new Properties();
            try (InputStream in = DbStore.class.getClassLoader().getResourceAsStream("gradle.properties")) {
                settings.load(in);
            }
            this.source.setDriverClassName(settings.getProperty("db.driver"));
            this.source.setUrl(settings.getProperty("db.host"));
            this.source.setUsername(settings.getProperty("db.login"));
            this.source.setPassword(settings.getProperty("db.password"));
            this.source.setMinIdle(5);
            this.source.setMaxIdle(10);
            this.source.setMaxOpenPreparedStatements(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public DbStore(BasicDataSource source) {
        this.source = source;
        this.dispat.put(Integer.class, (index, ps, value) -> ps.setInt(index, (Integer) value));
        this.dispat.put(String.class, (index, ps, value) -> ps.setString(index, (String) value));
        this.addTable();
    }

    public static DbStore getInstance() {
        return INSTANCE;
    }

    /**
     * добавление таблицы
     */
    public void addTable() {
        try {
            Properties settings = new Properties();
            try (InputStream in = DbStore.class.getClassLoader().getResourceAsStream("gradle.properties")) {
                settings.load(in);
            }
            db(settings.getProperty("add.tableUser"), new ArrayList<>(), pr -> pr.executeUpdate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * в цикле перебираем лист с параметкрами
     *
     * @param list
     * @param metod
     * @throws Exception
     * @paramd <T>
     */
    private <T> void forIdex(List<T> list, BiConEx<Integer, T> metod) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            metod.accept(i, list.get(i));
        }
    }

    /**
     * избавляемся от трай кетч мне почему то не понравилось какая реализация ыла в видео,
     * исключение мы всёравно отловим по этому здесь я сделал по своему
     *
     * @param sql
     * @param param
     * @param fun
     * @return
     */
    public <R> Optional<R> db(String sql, List<Object> param, FunEx<PreparedStatement, R> fun) {
        Optional<R> rsl = Optional.empty();
        try (Connection conn = source.getConnection();
             PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            this.forIdex(param, (index, value) -> dispat.get(value.getClass()).accept(index + 1, pr, value));
            rsl = Optional.ofNullable(fun.apply(pr));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public Users add(Users user) {
        this.db(
                "insert into users (name, login, pass) values (?, ?, ?)", Arrays.asList(user.getName(), user.getLogin(), user.getPassword()),
                ps -> {
                    ps.executeUpdate();
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            user.setId(String.valueOf(generatedKeys.getInt(1)));
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return user;
                }
        );
        return user;
    }

    @Override
    public Users update(Users users) {
        this.db(
                "UPDATE users SET NAME = ?, login = ? where users.id = ? ", Arrays.asList(users.getName(), users.getLogin(), Integer.valueOf(users.getId())),
                ps -> {
                    ps.executeUpdate();
                    return users;
                }
        );
        return this.findById(users);
    }

    @Override
    public Users delete(Users users) {
        Users rsl = this.findById(users);
        this.db(
                "delete from users where users.id = ? ", Arrays.asList(Integer.valueOf(users.getId())),
                ps -> {
                    ps.executeUpdate();
                    return users;
                }
        );
        return rsl;
    }

    @Override
    public List<Users> findAll() {
        return this.db(
                "select * from users", new ArrayList<>(),
                ps -> {
                    ArrayList<Users> rsl = new ArrayList<>();
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            rsl.add(new Users(String.valueOf(rs.getInt("id")), rs.getString("name"),
                                    rs.getString("login"), rs.getTimestamp("create_date").toLocalDateTime()));
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return rsl;
                }
        ).get();
    }

    /**
     * метод очистки бд
     */
    @Override
    public List<Users> deleteALL() {
        this.db("delete from users;", new ArrayList<>(), pr -> pr.executeUpdate());
        return this.findAll();
    }

    /**
     * поиск пользоваеля по логину
     *
     * @param users
     * @return
     */
    @Override
    public Users findByLogin(Users users) {
        return this.db(
                "select * from users where login = ?", Arrays.asList(users.getLogin()),
                ps -> {
                    Users res = null;
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            res = new Users(String.valueOf(rs.getInt("id")), rs.getString("name"),
                                    rs.getString("login"), rs.getTimestamp("create_date").toLocalDateTime());
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return res;
                }
        ).orElse(new Users());
    }

    @Override
    public Users findById(Users users) {
        return this.db(
                "select * from users where id = ?", Arrays.asList(Integer.valueOf(users.getId())),
                ps -> {
                    Users res = null;
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            res = new Users(String.valueOf(rs.getInt("id")), rs.getString("name"),
                                    rs.getString("login"), rs.getTimestamp("create_date").toLocalDateTime());
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return res;
                }
        ).orElse(new Users());
    }

    @Override
    public List<Users> filter(Users users) {
        List<Users> rsl = new ArrayList<>();
        if (Integer.valueOf(users.getId()) > 0) {
            rsl.add(this.findById(users));
        } else {
            this.db("select * from users where name like ? and login like ?",
                    Arrays.asList("%" + users.getName() + "%", "%" + users.getLogin() + "%"),
                    ps -> {
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                rsl.add(new Users(String.valueOf(rs.getInt("id")), rs.getString("name"),
                                        rs.getString("login"), rs.getTimestamp("create_date").toLocalDateTime()));

                            }
                        } catch (SQLException e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                        return rsl;
                    }
            );
        }
        if (users.getCreateDate() != null) {
            for (int i = 0; i < rsl.size(); i++) {
                if (rsl.get(i).getCreateDate().toLocalDate().compareTo(users.getCreateDate().toLocalDate()) != 0) {
                    rsl.remove(i);
                }
            }
        }
        return rsl;
    }
}
