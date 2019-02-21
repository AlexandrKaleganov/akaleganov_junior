package ru.job4j.bootstrap;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.bootstrap.model.User;

import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.Matchers.is;

public class DispatchTest {
    private Dispatch dispatch;

    @Before
    public void init() {
        dispatch = new Dispatch();
    }

    @Test
    public void submit() {
            User user = new User(1, "fame", "nae", "М", "desc");
            StringBuilder rsl = new StringBuilder();

            User expected = dispatch.submit("add", user, user);
            Assert.assertThat(expected.getName(), is(user.getName()));
            User actual = dispatch.submit("findall", new User(), new ConcurrentHashMap<Integer, User>()).get(expected.getId());
            Assert.assertThat(actual.getName(), Is.is(user.getName()));
    }
}