package ru.job4j.architecture;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UsersTest {
    @Test
    public void tstUsersrSet() {
        Users users = new Users();
        users.setId("12");
        users.setName("name");
        users.setLogin("login");
        users.setCreateDate(LocalDateTime.now());
        System.out.println(users);
    }

}