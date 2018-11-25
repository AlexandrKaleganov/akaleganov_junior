package ru.job4j.architecture;

import java.time.LocalDateTime;

public class Users {
    private int id;
    private LocalDateTime createDate;
    private String name;
    private String login;

    //конструктор
    public Users(int id, LocalDateTime createDate, String name, String login) {
        this.id = id;
        this.createDate = createDate;
        this.name = name;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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


}
