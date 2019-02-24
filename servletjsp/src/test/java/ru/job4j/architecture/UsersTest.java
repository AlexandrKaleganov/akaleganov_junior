package ru.job4j.architecture;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
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
        Assert.assertThat(users, Is.is(new Users("12", LocalDateTime.now(), "name", "login", "", "","", "")));
    }

    @Test
    public void isdatatest() throws IOException {
        Users user = new Users("1", Optional.ofNullable(null), "name", "login", "", "", "");
        Assert.assertThat(user.getCreateDate(), Is.is((LocalDateTime) null));
        Users user1 = new Users("1", Optional.ofNullable("2018-12-12"), "name", "login", "", "", "");
        Assert.assertThat(user1.getCreateDate(), Is.is(LocalDateTime.of(2018, 12, 12, 00, 00)));
        StringBuilder builder = new StringBuilder("{\"id\":\"37\",\"name\":\"Калег\"," +
                "\"login\":\"alexmur07\",\"password\":\"pass\",\"accesAttrib\":\"admin\",\"country\":\"country\",\"city\":\"city\"}");
        ObjectMapper mapper = new ObjectMapper();
        Users userrr = mapper.readValue(builder.toString(), Users.class);
        System.out.println(userrr);
    }

}