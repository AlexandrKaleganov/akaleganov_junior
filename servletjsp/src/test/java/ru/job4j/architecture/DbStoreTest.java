package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.err.BiConEx;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DbStoreTest {
    /**
     * метод рефакторинг в любом случае при тестировании мне надо будет подключиться к базе,
     * добавиитть туда объект , произвести тест метода, и очистить базу
     * с роллбаком фикгня получалась, т.к. у меня в каждом методе коннект закрывался
     * суть работы - функциональный метод получает готовую бд, и оъект который мы получили при добавлении,
     * чтобы по id  мы могли его обновить, удалить, и т.д... вообщем оттестировать
     * далее в блоке finally я добавил мето очистки данных из бд т.е. после
     * прохождения теста удачного или неудачного
     * я буду очищать БД
     *
     * @param fank
     */
    private void alltestfunc(BiConEx<DbStore, Users> fank) {
        Users users = new Users("12", LocalDateTime.now(), "sacha", "alexmur07", "password", "admin", "Россия", "Чаны");
        DbStore dbStore = DbStore.getInstance();
        Users expected = dbStore.add(users);
        try {
            fank.accept(dbStore, expected);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbStore.deleteALL();
        }
    }

    /**
     * тестируем метод добавления в бд
     */
    @Test
    public void addDD() {
        this.alltestfunc((bd, exp) -> {
            Assert.assertThat(bd.findById(exp), Is.is(exp));
        });
    }

    /**
     * тестируем метод получения всех пользователй по id
     *
     * @throws SQLException
     */
    @Test
    public void findaaalTest() {
        this.alltestfunc((bd, exp) -> {
            Assert.assertThat(bd.findAll().get(0), Is.is(exp));
        });
    }

    /**
     * тестируем метод изменения пользователей
     */
    @Test
    public void updateTest() {
        this.alltestfunc((bd, exp) -> {
            bd.update(new Users(exp.getId(), LocalDateTime.now(), "lex", "lex07", "psw", "", "Россия", "Чаны"));
            Assert.assertThat(bd.findById(exp).getName(), Is.is("lex"));
        });
    }

    /**
     * тестируем удаление пользователя из бд
     */
    @Test
    public void deleteTest() {
        this.alltestfunc((db, exp) -> {
            db.delete(exp);
            Assert.assertThat(db.findById(exp).getId(), Is.is(new Users().getId()));
        });
    }

    @Test
    public void findByLogin() {
        this.alltestfunc((db, exp) -> {
            Assert.assertThat(db.findByLogin(exp).getLogin(), Is.is(exp.getLogin()));
        });
    }

    @Test
    public void filterTest() {
        this.alltestfunc((db, exp) -> {
            db.add(new Users("", LocalDateTime.now(), "name", "alexmur", "pass", "", "", ""));
            Assert.assertThat(db.filter(exp).get(0).getId(), Is.is(exp.getId()));
            Assert.assertThat(db.filter(new Users("0", LocalDateTime.parse(exp.getCreateDate().toLocalDate().toString() + " 00:00",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "", "alex", "", "", "", ""
            )).get(0).getId(), Is.is(exp.getId()));
        });
    }

    @Test
    public void isCredentional() {
        this.alltestfunc((db, exp) -> {
            Assert.assertThat(db.isCredentional(new Users("12", LocalDateTime.now(), "sacha", "alexmur07", "password", "", "", "")), Is.is(true));
            Assert.assertThat(db.isCredentional(new Users("12", LocalDateTime.now(), "sacha", "alexmu07", "password", "", "", "")), Is.is(false));
            Assert.assertThat(db.isCredentional(new Users("12", LocalDateTime.now(), "", "alexmur07", "ssword", "", "", "")), Is.is(false));
        });
    }
}