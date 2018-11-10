package ru.job4j.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * конструктор лямбда
 */
public class UserConvert {
    public static class User {
        private final String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" + "name='" + name + "'" + "}";
        }
    }


    public List<User> convert(List<String> names, Function<String, User> op) {
        List<User> users = new ArrayList<>();
        names.forEach(n -> users.add(op.apply(n)));
        return users;
    }

    public static void main(String[] args) {
        List<String> users = Arrays.asList("Petia", "Vasia", "Kolia");
        UserConvert userConvert = new UserConvert();
        List<User> data = userConvert.convert(users, User::new);
        data.forEach(System.out::println);
    }
}
