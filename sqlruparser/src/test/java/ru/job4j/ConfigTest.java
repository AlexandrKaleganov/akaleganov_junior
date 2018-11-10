package ru.job4j;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

public class ConfigTest {
    @Test
    public void testconfig() {
        Config config = new Config();
        Assert.assertThat(config.getScript("add.title"), Is.is("create table  if not exists  title (id serial PRIMARY KEY, title varchar(200)  NOT NULL UNIQUE)"));
        Assert.assertThat(config.getScript("add.author"), Is.is("create table if not exists author (id serial PRIMARY KEY, author varchar(200)  NOT NULL UNIQUE)"));
        Assert.assertThat(config.getScript("add.description"), Is.is("create table if not exists description (id serial PRIMARY KEY, description varchar NOT NULL)"));
        Assert.assertThat(config.getScript("add.datatable"), Is.is("create table if not exists datatable(id serial PRIMARY KEY, url varchar(300)  NOT NULL UNIQUE, title_id integer references title (id) not null, author_id integer references author(id) not null, description_id integer references description(id) not null, date TimeStamp not null)"));


    }

}