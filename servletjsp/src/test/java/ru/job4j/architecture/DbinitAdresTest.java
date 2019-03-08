package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DbinitAdresTest {

    @Test
    public void addtoDataTableInfo() throws SQLException {
        DbinitAdres adres = new DbinitAdres();
        adres.addtoDataTableInfo();
        Assert.assertThat(adres.getMap().containsKey("Russia"), Is.is(true));
        adres.deleteAllInfo();
    }
}