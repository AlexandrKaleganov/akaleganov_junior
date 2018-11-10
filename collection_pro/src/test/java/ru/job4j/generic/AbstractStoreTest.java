package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AbstractStoreTest {

    private AbstractStore<User> users;
    private AbstractStore<Role> rolles;


    @Before
    public void stUP() {
        users = new UserStore();
        rolles = new RoleStore();
        users.add(new User("Petia"));
        users.add(new User("Sasha"));
        users.add(new User("Misha"));
        rolles.add(new Role("rolle1"));
        rolles.add(new Role("rolle2"));
        rolles.add(new Role("rolle3"));
    }

    @Test
    public void whenaddMetodTest() {
        users.add(new User("Vasia"));
        rolles.add(new Role("rolle4"));
        assertThat(users.findById("3").getId(), is("Vasia"));
        assertThat(rolles.findById("3").getId(), is("rolle4"));
    }

    @Test(expected = NumberFormatException.class)
    public void replace() {
        assertThat(users.replace("2", new User("Kostia")), is(true));
        assertThat(users.findById("2").getId(), is("Kostia"));
        assertThat(rolles.replace("2", new Role("trueRolles")), is(true));
        assertThat(rolles.findById("2").getId(), is("trueRolles"));
        users.replace("2askdjkasfkajs", new User("Petia"));
    }

    @Test
    public void delete() {
        assertThat(users.delete("0"), is(true));
        assertThat(users.findById("0").getId(), is("Sasha"));
        assertThat(rolles.delete("0"), is(true));
        assertThat(rolles.findById("0").getId(), is("rolle2"));
    }

    @Test
    public void findById() {
        assertThat(users.findById("0").getId(), is("Petia"));
        assertThat(users.findById("1").getId(), is("Sasha"));
        assertThat(users.findById("2").getId(), is("Misha"));
        assertThat(rolles.findById("0").getId(), is("rolle1"));
        assertThat(rolles.findById("1").getId(), is("rolle2"));
        assertThat(rolles.findById("2").getId(), is("rolle3"));
    }
}