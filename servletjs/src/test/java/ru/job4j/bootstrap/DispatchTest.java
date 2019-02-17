package ru.job4j.bootstrap;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.bootstrap.model.User;

import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.Matchers.is;

public class DispatchTest {

    @Test
    public void submit() {
        User user = new User(1, "fame", "name", "М", "desc");
        StringBuilder rsl = new StringBuilder();
        Dispatch dispatch = Dispatch.getINSTANCE();
        Assert.assertThat(dispatch.submit("add", user, user), is(user));
        Assert.assertThat(dispatch.submit("findall", new User(), new ConcurrentHashMap<>()).get(1), Is.is(user));
        ConcurrentHashMap<Integer, User> temp = dispatch.submit("findall", new User(), new ConcurrentHashMap<Integer, User>());
        temp.forEach((k, v) ->
                rsl.append(String.format("{id:%s, surname:%s, name:%s, sex:%s, desc:%s}",
                        v.getId(), v.getSurname(), v.getName(), v.getSex(), v.getDesc())));
    }
}