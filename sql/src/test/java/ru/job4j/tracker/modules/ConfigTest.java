package ru.job4j.tracker.modules;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class ConfigTest {

    @Test
    public void getScript() {
        /**
         * тест класса, который принимает файл конфигурации
         * импортируется в хешмапу
         */
        Config config = new Config();
        Assert.assertThat(config.getScript("--items"), is("create table if not exists  items(       id serial primary key,       name varchar(100),       descc varchar(500),       created timestamp not null default now()       )"));
        Assert.assertThat(config.getScript("--comments"), is("create table if not exists comments(       id serial primary key,       comment varchar(500),       id_items integer references items(id))"));
        Assert.assertThat(config.getProperties("db.host"), is("jdbc:postgresql://localhost:5432/trackdata"));
        Assert.assertThat(config.getProperties("db.login"), is("postgres"));
        Assert.assertThat(config.getProperties("db.password"), is("444444"));
    }
}