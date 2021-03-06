package ru.job4j.tracker.modules;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Класс итемс это сама заявка, точнее один элемент заявки
 * Решая эту задачу я побольше узнал о методе toString и на сколько он удобен, также узнал про то что лонг может хранить
 * необхоимую дту, а с помощью некоторых методов из длинного числа long  мы можем получить дату для того чтобы пользователю
 * было понятно отоброжаемое
 */


public class Items implements Comparable<Items> {
    private Integer id;
    private String name;
    private String desc;
    private long created;
    private ArrayList<String> comments = new ArrayList<>();

    /**
     * в конструкторе при создании я добавил только имя завки и описание
     * всё остальное добавляется автоматом,комментарии к заявке я не стал пока добавлять
     * эти данные мы наверно будетм как то потом вносить , как пока не знаю
     * id - генерируется методом generate
     *
     * @param name
     * @param desc
     */
    public Items(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.created = millisreturn();
    }
    public Items(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.created = millisreturn();
    }
    public Items(int id, String name, String desc, long created) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.created = created;
    }

    public Items() {

    }

    public void addComment(String string) {
        this.comments.add(string);
    }

    /**
     * возвращает имя заявки
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * возвращает дату в виде long
     *
     * @return
     */
    long getCreated() {
        return created;
    }

    /**
     * возвращает описание заявки
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }

    /**
     * возвращает id заявки
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * меняет id  заявки
     *
     * @param newId
     */
    void setId(int newId) {
        this.id = newId;
    }

    @Override
    public String toString() {
        StringBuilder rsl = new StringBuilder();
        rsl.append("Items{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", desc='" + desc + '\'' + ", created=" + new Date(created) + ", comments=" + '}' + "\n");
        this.comments.forEach(e-> rsl.append(e).append("\n"));
        return rsl.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    /**
     * если я правильно понял мы тут получаем время в милисекундах текущее
     *
     * @return
     */
    private long millisreturn() {
        long k = System.currentTimeMillis();
        return k;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Items items = (Items) o;
        return Objects.equals(id, items.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public int compareTo(Items o) {
        return this.id.compareTo(o.id);
    }
}
