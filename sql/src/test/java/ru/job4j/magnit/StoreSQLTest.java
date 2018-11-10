package ru.job4j.magnit;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

/**
 * проверка подключения к базе  и метода получения списка
 */
public class StoreSQLTest {
    @Test
    public void storeTest() {
        try (StoreSQL storeSQL = new StoreSQL(new Conf("magnit.properties"))) {
            Assert.assertThat(storeSQL.init(), Is.is(true));
            storeSQL.generate(10);
            EntryList entryList = new EntryList(storeSQL.getList());
            String actual = entryList.toString();
            String expected = "[Entry{value=1}, Entry{value=2}, Entry{value=3}, Entry{value=4}, Entry{value=5}, Entry{value=6}, Entry{value=7}, Entry{value=8}, Entry{value=9}, Entry{value=10}]";
            Assert.assertThat(actual, Is.is(expected));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}