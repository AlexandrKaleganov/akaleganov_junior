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
    public void whenUserHashcodeNonEqualsTest() {
        User userOne = new User("Vova", 2, new GregorianCalendar(1990, 11, 25));
        User userTwo = new User("Vova", 2, new GregorianCalendar(1990, 11, 25));
        HashMap<User, String> userMap = new HashMap<User, String>();
        userMap.put(userOne, userOne.getName());
        userMap.put(userTwo, userTwo.getName());
        System.out.println(userMap);
    }
}