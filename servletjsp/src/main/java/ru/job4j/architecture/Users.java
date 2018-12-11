package ru.job4j.architecture;

import java.time.LocalDateTime;
import java.util.Objects;

public class Users {
    private String id;
    private LocalDateTime createDate;
    private String name;
    private String login;

    //конструктор
    public Users(String id, String name, String login) {
        this.id = id;
        this.createDate = LocalDateTime.now();
        this.name = name;
        this.login = login;
    }
    public Users(String name, String login) {
        this.createDate = LocalDateTime.now();
        this.name = name;
        this.login = login;
    }
    Users() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Users{" + "createDate=" + createDate + ", name='" + name + '\''
                + ", login='" + login + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return  Objects.equals(name, users.name) &&
                Objects.equals(login, users.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login);
    }
}
