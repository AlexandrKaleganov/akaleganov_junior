package ru.job4j.exchenge;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@SuppressWarnings("SameParameterValue")
@ThreadSafe
public class User {

    @GuardedBy("this")
    private final String name;
    private final String email;

    User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
