package ru.job4j.architecture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Users {
    private String id;
    private LocalDateTime createDate;
    private String name;
    private String login;
    private String password;

    //конструктор
    public Users(String id, String name, String login) {
        this.id = iscorrectedID(id);
        this.name = name;
        this.login = login;
    }

    public Users(String id, String name, String login, String password) {
        this.id = iscorrectedID(id);
        this.name = name;
        this.login = login;
        this.password = password;
        this.createDate = LocalDateTime.now();
    }

    public Users(String id, String name, String login, LocalDateTime date) {
        this.id = iscorrectedID(id);
        this.createDate = date;
        this.name = name;
        this.login = login;
    }

    public Users(String name, String login) {
        this.name = name;
        this.login = login;
    }

    public Users() {
        this.id = "0";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = iscorrectedID(id);
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

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + this.id + " createDate=" + createDate + ", name=" + name + " , login=" + login + "}";
    }

    private String iscorrectedID(String id) {
        return id != null && id.length() > 0 ? id : "0";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Users users = (Users) o;
        return Objects.equals(name, users.name) && Objects.equals(login, users.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login);
    }


}
