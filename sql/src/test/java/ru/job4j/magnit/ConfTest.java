package ru.job4j.magnit;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

/**
 * тестирование подключения к базе
 */
public class ConfTest {
    @Test
    public void testConfig() {
        Conf conf = new Conf("magnit.properties");
        Assert.assertThat(conf.getDataconfig("db.host"), Is.is("jdbc:sqlite:src//main//resources//magnit//magnit.db"));

    }

}