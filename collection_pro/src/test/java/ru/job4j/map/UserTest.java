package ru.job4j.map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    @Ignore
    public void whenisUsertest() {
        User usertest = new User("Vova", 2, new GregorianCalendar(1990, 11, 25));
        System.out.println(usertest);
        assertThat(usertest.getName(), is("Vova"));
    }

    @Test
    @Ignore
    public void whenUsernonhashCodenon() {
        User userOne = new User("Vova", 2, new GregorianCalendar(1990, 11, 25));
        User userTwo = new User("Vova", 2, new GregorianCalendar(1990, 11, 25));
        User userTree = new User("Vova", 2, new GregorianCalendar(1990, 11, 25));
        HashMap<User, String> userMap = new HashMap<User, String>();
        userMap.put(userOne, userOne.getName());
        userMap.put(userTwo, userTwo.getName());
        Integer expected = null;
        assertThat(userMap.get(userTree), is(expected)); //т.к. хеш не переопределён, то все объекты будут считаться разными,
        // даже если поля у них одинаковые
        assertThat(userMap.get(userOne), is("Vova")); //одинаковыми объекты по экуалс и по хеш - коду будут считаться только
        assertThat(userMap.get(userTwo), is("Vova")); //те объекты которые имеют одинаковую ссылку
    }

    @Test
    public void whenUserHashcodeNonEqualsTest() {
        User userOne = new User("Vova", 2, new GregorianCalendar(1990, 11, 25));
        User userTwo = new User("Vova", 2, new GregorianCalendar(1990, 11, 25));
        HashMap<User, String> userMap = new HashMap<User, String>();
        userMap.put(userOne, userOne.getName());
        userMap.put(userTwo, userTwo.getName());
        System.out.println(userMap);
    }
}