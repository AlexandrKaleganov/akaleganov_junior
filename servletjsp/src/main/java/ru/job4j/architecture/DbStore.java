package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.architecture.err.BiConEx;
import ru.job4j.architecture.err.FunEx;
import ru.job4j.architecture.err.TriplexConEx;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
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
        this.initRoot();
    }

    private void initRoot() {
        if (this.findByLogin(new Users("0", LocalDateTime.now(), "root", "root", "root", "Admin", "", "")).getLogin() == null) {
            this.add(new Users("0", LocalDateTime.now(), "root", "root", "root", "Admin", "root", "root"));
        }
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
        this.initRoot();
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
            db(settings.getProperty("add.tableCountry"), new ArrayList<>(), pr -> pr.executeUpdate());
            db(settings.getProperty("add.tableCity"), new ArrayList<>(), pr -> pr.executeUpdate());
            db(settings.getProperty("add.tableaccesAttrib"), new ArrayList<>(), pr -> pr.executeUpdate());
            db(settings.getProperty("add.tableAdresHelp"), new ArrayList<>(), pr -> pr.executeUpdate());
            db(settings.getProperty("add.tableaccesAttribhelp"), new ArrayList<>(), pr -> pr.executeUpdate());
            db(settings.getProperty("add.tableUserview"), new ArrayList<>(), pr -> pr.executeUpdate());
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
    private <R> Optional<R> db(String sql, List<Object> param, FunEx<PreparedStatement, R> fun) {
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

    /**
     * рефоктор поиска индексов по запросам
     */
    private Integer isIndex(String command, List<Object> att) {
        return this.db(command, att, ps -> {
            Integer k = 0;
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    k = resultSet.getInt("id");
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
            return k;
        }).get();
    }

    /**
     * добавление данных в таблицы и получение индекса
     *
     * @param command
     * @param att
     * @return
     */
    private Integer addToTable(String command, List<Object> att) {
        return this.db(command, att, ps -> {
            Integer k = 0;
            ps.executeUpdate();
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                while (resultSet.next()) {
                    k = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
            return k;
        }).get();
    }

    /**
     * если id нулевой то будем добавлять запись в таблицу и получать id
     *
     * @param command
     * @param id
     * @return
     */
    private Integer isnotNullId(String command, List<Object> att, Integer id) {
        if (id == 0) {
            return addToTable(command, att);
        } else {
            return id;
        }
    }

    @Override
    public Users add(Users user) {
        Integer country = isIndex("select * from country where country = ?", Arrays.asList(user.getCountry()));
        Integer city = isIndex("select * from city where city = ?", Arrays.asList(user.getCity()));
        Integer accesAttrib = isIndex("select * from accesAttrib where accesAttrib = ?", Arrays.asList(user.getAccesAttrib()));
        country = isnotNullId("insert into country(country) values(?)", Arrays.asList(user.getCountry()), country);
        city = isnotNullId("insert into city(city) values(?)", Arrays.asList(user.getCity()), city);
        accesAttrib = isnotNullId("insert into accesAttrib(accesAttrib) values (?)", Arrays.asList(user.getAccesAttrib()), accesAttrib);
        this.db(
                "insert into users (name, login, pass) values (?, ?, ?)",
                Arrays.asList(user.getName(), user.getLogin(), user.getPassword()),
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
        this.addToTable("insert into adreshelp(user_id, country_id, city_id) values(?, ?, ?)", Arrays.asList(Integer.valueOf(user.getId()), country, city));
        this.addToTable("insert into accesAttribhelp(user_id, accesAttrib_id) values(?, ?)", Arrays.asList(Integer.valueOf(user.getId()), accesAttrib));
        return user;
    }

    @Override
    public Users update(Users users) {
        this.db(
                "UPDATE users SET NAME = ?, login = ? where users.id = ? ",
                Arrays.asList(users.getName(), users.getLogin(), Integer.valueOf(users.getId())),
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
                            rsl.add(new Users(String.valueOf(rs.getInt("id")), rs.getTimestamp("create_date").toLocalDateTime(),
                                    rs.getString("name"), rs.getString("login"), rs.getString("pass"),
                                    rs.getString("accesAttrib"),
                                    rs.getString("country"), rs.getString("city")));
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
        this.db("delete from adreshelp;", new ArrayList<>(), pr -> pr.executeUpdate());
        this.db("delete from accesAtribhelp;", new ArrayList<>(), pr -> pr.executeUpdate());
        this.db("delete from country;", new ArrayList<>(), pr -> pr.executeUpdate());
        this.db("delete from city;", new ArrayList<>(), pr -> pr.executeUpdate());
        this.db("delete from accesAtrib;", new ArrayList<>(), pr -> pr.executeUpdate());
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
                "select * from userview where login = ?", Arrays.asList(users.getLogin()),
                ps -> {
                    Users res = null;
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            res = new Users(String.valueOf(rs.getInt("id")), rs.getTimestamp("create_date").toLocalDateTime(),
                                    rs.getString("name"), rs.getString("login"), "", rs.getString("accesAttrib"),
                                    rs.getString("country"), rs.getString("city"));
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return res;
                }
        ).orElse(new Users());
    }

    /**
     * проверяет есть ли пользователь с таким логином и паролем
     *
     * @param users
     * @return
     */
    @Override
    public boolean isCredentional(Users users) {
        return this.db(
                "select * from users where login = ? and pass = ?", Arrays.asList(users.getLogin(), users.getPassword()),
                ps -> {
                    Boolean res = false;
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            res = true;
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return res;
                }
        ).get();
    }

    @Override
    public Users findById(Users users) {
        return this.db(
                "select * from userview where id = ?", Arrays.asList(Integer.valueOf(users.getId())),
                ps -> {
                    Users res = null;
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            res = new Users(String.valueOf(rs.getInt("id")), rs.getTimestamp("create_date").toLocalDateTime(),
                                    rs.getString("name"), rs.getString("login"), "", rs.getString("accesAttrib"),
                                    rs.getString("country"), rs.getString("city"));
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
            this.db("select * from userview where name like ? and login like ? and accesAttrib like ? and country like ? and city like ?",
                    Arrays.asList("%" + users.getName() + "%", "%" + users.getLogin() + "%", "%" + users.getAccesAttrib() + "%", "%" + users.getCountry() + "%", "%" + users.getCity() + "%"),
                    ps -> {
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                rsl.add(new Users(String.valueOf(rs.getInt("id")), rs.getTimestamp("create_date").toLocalDateTime(),
                                        rs.getString("name"), rs.getString("login"), "", rs.getString("accesAttrib"),
                                        rs.getString("country"), rs.getString("city")));

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
