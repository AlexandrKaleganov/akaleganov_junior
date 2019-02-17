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
        Dispatch dispatch = Dispatch.getINSTANCE();
        Assert.assertThat(dispatch.submit("add", user, user), is(user));
        Assert.assertThat(dispatch.submit("findall", new User(), new ConcurrentHashMap<>()).get(1), Is.is(user));
    }
}