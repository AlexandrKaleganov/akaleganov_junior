package ru.job4j.bootstrap;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.bootstrap.model.User;

import static org.junit.Assert.*;

public class DbStorTest {
    /**
     * напишем тест на наш класс
     */
    @Test
    public void addandFindALLtest() {
        User user = new User(1, "fame", "name", "М", "desc");
        Db<Integer, User> db = DbStor.getINSTANCE();
        db.add(user.getId(), user);
        Assert.assertThat(db.findall().get(1), Is.is(user));
    }
}