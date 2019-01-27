package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.model.Users;

import java.time.LocalDateTime;
import java.util.Optional;

public class UsersTest {
    @Test
    public void tstUsersrSet() {
        Users users = new Users();
        users.setId("12");
        users.setName("name");
        users.setLogin("login");
        users.setCreateDate(LocalDateTime.now());
        Assert.assertThat(users, Is.is(new Users("12", "name", "login")));
    }

    @Test
    public void isdatatest() {
        Users user = new Users("1", "name", "login", Optional.ofNullable(null));
        Assert.assertThat(user.getCreateDate(), Is.is((LocalDateTime) null));
        Users user1 = new Users("1", "name", "login", Optional.ofNullable("2018-12-12"));
        Assert.assertThat(user1.getCreateDate(), Is.is(LocalDateTime.of(2018, 12, 12, 00, 00)));
    }

}