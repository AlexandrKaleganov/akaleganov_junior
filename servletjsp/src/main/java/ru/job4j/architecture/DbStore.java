package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.architecture.err.BiConEx;
import ru.job4j.architecture.err.FunEx;
import ru.job4j.architecture.err.TriplexConEx;

import java.sql.*;
import java.util.*;

public class DbStore implements Store<Users> {
    //не стал делать поле статичным, т.к. иначе не зню как прикрутить тесты
    private final BasicDataSource source;
    private static final DbStore INSTANCE = new DbStore();
    private final Map<Class<?>, TriplexConEx<Integer, PreparedStatement, Object>> dispat = new HashMap<>();

    public DbStore() {
        source = new BasicDataSource();
        this.source.setDriverClassName("org.postgresql.Driver");
        source.setUrl("jdbc:postgresql://localhost:5432/users");
        source.setUsername("postgresql");
        source.setPassword("444444");
        source.setMinIdle(5);
        source.setMaxIdle(10);
        source.setMaxOpenPreparedStatements(100);
        this.dispat.put(Integer.class, (index, ps, value) -> ps.setInt(index, (Integer) value));
        this.dispat.put(String.class, (index, ps, value) -> ps.setString(index, (String) value));
    }

    public DbStore(BasicDataSource source) {
        this.source = source;
        this.dispat.put(Integer.class, (index, ps, value) -> ps.setInt(index, (Integer) value));
        this.dispat.put(String.class, (index, ps, value) -> ps.setString(index, (String) value));
    }

    public static DbStore getInstance() {
        return INSTANCE;
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
            rsl.of(fun.apply(pr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Users add(Users user) {
        this.db(
                "insert into users (name, login) values (?, ?)", Arrays.asList(user.getName(), user.getLogin()),
                ps -> {
                    ps.executeUpdate();
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            user.setId(String.valueOf(generatedKeys.getInt(1)));
                        }
                    }
                    return user;
                }
        );
        return user;
    }

    @Override
    public void update(Users users) {

        this.db(
                "UPDATE users SET NAME = ?, login = ? where users.id = ? ", Arrays.asList(users.getName(), users.getLogin(), Integer.valueOf(users.getId())),
                ps -> {
                    ps.executeUpdate();
                    return users;
                }
        );
    }

    @Override
    public void delete(Users users) {
        this.db(
                "delete users where users.id = ? ", Arrays.asList(Integer.valueOf(users.getId())),
                ps -> {
                    ps.executeUpdate();
                    return users;
                }
        );
    }

    @Override
    public List<Users> findAll() {
        ArrayList<Users> rsl = new ArrayList<>();
        this.db(
                "select * from users", new ArrayList<>(),
                ps -> {
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            rsl.add(new Users(String.valueOf(rs.getInt("id")),
                                    rs.getString("name"),
                                    rs.getString("login"),
                                    rs.getTimestamp("create_date").toLocalDateTime()));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return rsl;
                }
        );
        return rsl;
    }

    /**метод очистки бд
     *
     */
    public void deletaALL() {
        this.db("delete from users;", new ArrayList<>(), PreparedStatement::executeUpdate);
    }

    @Override
    public Users findById(Users users) {
        this.db(
                "select * from users where id = ?", Arrays.asList(Integer.valueOf(users.getId())),
                ps -> {
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            users.setName(rs.getString("name"));
                            users.setLogin(rs.getString("login"));
                            users.setCreateDate(rs.getTimestamp("creata_date").toLocalDateTime());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return users;
                }
        );
        return users;
    }
}
