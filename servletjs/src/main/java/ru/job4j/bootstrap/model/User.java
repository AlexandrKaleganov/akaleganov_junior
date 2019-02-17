package ru.job4j.bootstrap.model;

import java.util.Objects;

/**
 * модель нашего обхекта
 */
public class User {
    private Integer id;
    private String surname;
    private String name;
    private String sex;
    private String desc;

    public User(Integer id, String surname, String name, String sex, String desc) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.sex = sex;
        this.desc = desc;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(surname, user.surname)
                && Objects.equals(name, user.name)
                && Objects.equals(sex, user.sex)
                && Objects.equals(desc, user.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, sex, desc);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id + ", surname='" + surname + '\'' + ", name='" + name + '\''
                + ", sex='" + sex + '\'' + ", desc='" + desc + '\'' + '}';
    }
}
