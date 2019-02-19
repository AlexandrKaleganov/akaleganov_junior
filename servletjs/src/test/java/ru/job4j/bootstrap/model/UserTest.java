package ru.job4j.bootstrap.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;

public class UserTest {

    @Test
    public void equals() {
        User user = new User(1, "fame", "name", "М", "desc");
        User user2 = new User(1, "fame", "name", "М", "desc");
        User user3 = new User(1, "fame", "nam1e", "М", "desc");
        Assert.assertThat(user.equals(user2), is(true));
        Assert.assertThat(user.equals(user3), is(false));
        StringBuilder test = new StringBuilder("id=37&surname=zzz&name=xxx&sex=%D0%9C&desc=vvv");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User usser = objectMapper.readValue(test.toString(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}