package ru.job4j.architecture;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.err.BiConEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;


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
        Users users = new Users("12", "sacha", "alexmur07", "password", "Россия", "Чаны");
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
            bd.update(new Users(exp.getId(), "lex", "lex07", "psw", "Россия", "Чаны"));
            Assert.assertThat(bd.findById(exp).getName(), Is.is("lex"));
        });
    }

    /**
     * тестируем удаление пользователя из бд
     */
    @Test
    public void deleteTest() {
        this.alltestfunc((db, exp) -> {
            db.delete(exp).getId();
            Assert.assertThat(db.findById(exp).getId(), Is.is(new Users().getId()));
        });
    }

    @Test
    public void findByLogin() {
        this.alltestfunc((db, exp) -> {
            Assert.assertThat(db.findByMail(exp).getMail(), Is.is(exp.getMail()));
        });
    }

    @Test
    public void isCredentional() {
        this.alltestfunc((db, exp) -> {
            Assert.assertThat(db.isCredentional(new Users("12", "sacha", "alexmur07", "password", "", "")), Is.is(true));
            Assert.assertThat(db.isCredentional(new Users("12", "sacha", "alexmu07", "password", "", "")), Is.is(false));
            Assert.assertThat(db.isCredentional(new Users("12", "", "alexmur07", "ssword", "", "")), Is.is(false));
        });
    }

    /**
     * JSON  города и страны
     */
    @Test
    public void testTestCity() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json");
        StringBuilder bilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String temp;
        while ((temp = reader.readLine()) != null) {
            bilder.append(temp);
        }
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, ArrayList<String>> mapa = mapper.readValue(bilder.toString(), HashMap.class);
        mapa.remove("");
        System.out.println(mapa.get("Russia"));
    }
}