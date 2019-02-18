package ru.job4j.bootstrap;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.bootstrap.model.User;

import java.util.HashMap;

public class DbStorTest {
    /**
     * напишем тест на наш класс
     */
    @Test
    public void addandFindALLtest() {
        User user = new User(1, "fame", "name", "М", "desc");
        User user1 = new User(1, "fame", "name", "М", "dessdc");
        Db<Integer, User> db = DbStor.getINSTANCE();
        user1 = db.add(user);
        Assert.assertThat(db.findall().get(user.getId()), Is.is(user1));
    }
}