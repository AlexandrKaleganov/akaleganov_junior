package ru.job4j.map;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * @author Alexandr Kaleganov
 * @since 09.07.2018
 */
public class User {
    private String name;
    private Integer children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", children=" + children + ", birthday=" + birthday.getTime().toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return children == user.children && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, children * 31, birthday);
    }

    public String getName() {
        return name;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public int getChildren() {
        return children;
    }
}
