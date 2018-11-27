package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;


public class MemoryStoreTest {


    @Test
    public void tstSingleton() {
        //создали объект
        Store logic = MemoryStore.getInstance();
        Users user1 = new Users("1", "Sasha", "alexmur07");
        Users user2 = new Users("2", "Vova", "vodemar");
        //добаили двух юзеров
        logic.add(user1);
        logic.add(user2);
        //проверяем что они есть в базе
        Assert.assertThat(logic.findById("1"), Is.is(user1));
        Assert.assertThat(logic.findById("2"), Is.is(user2));
        System.out.println(logic.findAll());
        //тестируем метод удаления
        logic.delete("1");
        Assert.assertThat(logic.findAll().size(), Is.is(1));
        Assert.assertThat(logic.findById("1"), Is.is((Users) null));

        //тестируем метод бновления, обращаю внимание на то что id пользователя не меняется в базе при обновлени,
        logic.update("2", new Users("44", "Kolia", "kelvinKalan"));
        Assert.assertThat(logic.findById("2").getId(), Is.is("2"));
        Assert.assertThat(logic.findById("2").getName(), Is.is("Kolia"));
        Assert.assertThat(logic.findById("2").getLogin(), Is.is("kelvinKalan"));

    }
}