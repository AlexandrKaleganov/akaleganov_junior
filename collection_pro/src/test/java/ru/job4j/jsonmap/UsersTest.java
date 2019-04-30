package ru.job4j.jsonmap;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UsersTest {

    /**
     * можно воспользоваться обжект маппером чтобы велосипед не изобретать
     * у json  есть ясный и понятный формат всё что в фигурных скобках - это объект
     * поля обозначаются так "names":name
     * массивы обозначаются квадратными скобками, итак давайте попробуем создать объект
     * User и посмотрим что получится
     *
     * @throws IOException
     */
    @Test
    public void testJson() throws IOException {
        Users users = new Users();
        users.getNames().add("names1");
        users.getNames().add("names2");
        //выведем в консоль для наглядности
        System.out.println(new ObjectMapper().writeValueAsString(users));
        //оттестируем
        assertThat(new ObjectMapper().writeValueAsString(users), is("{\"names\":[\"names1\",\"names2\"]}"));
        //оттестируем в обратном направлении создадим объект из стринга
        Users usertest = new ObjectMapper().readValue("{\"names\":[\"names1\",\"names2\"]}", Users.class);
        assertThat(usertest.getNames().get(0), is("names1"));
        assertThat(usertest.getNames().get(1), is("names2"));
    }

    /**
     * и так мы разобрались как запихать массив {"names":["names1","names2"]} в объект
     * и как потом обратиться к элементам
     * но у нас же ещё есть users? {"users":{"names": ["name1", "name2"]}}
     * тут вообще проще некуда создаём класс и помещаем объект в юзерс в поле класса назавём этот класс маппером
     * теперь мы можем делать ну что захотим
     */
    @Test
    public void maperTest() throws IOException {
        Maper users1 = new ObjectMapper().readValue("{\"users\":{\"names\": [\"name1\", \"name2\"]" + "}" + "}", Maper.class);
        System.out.println(users1.getUsers().getNames()); //т.е. когда мы сказали getNames  мы дёнуи список [name1, name2]
        assertThat(users1.getUsers().getNames().toString(), is("[name1, name2]"));
    }

    /**
     * если ты спросишь что делать если у тебя большой подобный список через запятуюнапример:
     * [{"users":{"names":["name1","name2"]}},{"users":{"names":["name1","name2"]}}] и т.д.
     * то можно его также запихать
     */
    @Test
    public void maperTestList() throws IOException {
        Maper[] maperList = new ObjectMapper().readValue("[{\"users\":{\"names\":[\"name1\",\"name2\"]}},{\"users\":{\"names\":[\"name3\",\"name4\"]}}]", Maper[].class);
        System.out.println(maperList[0].getUsers().getNames()); //т.е. дёрнули имена первого пользователя
        System.out.println(maperList[1].getUsers().getNames()); //т.е. дёрнули имена второго пользователя
        assertThat(maperList[0].getUsers().getNames().toString(), is("[name1, name2]"));
        assertThat(maperList[1].getUsers().getNames().toString(), is("[name3, name4]"));
    }

    /**
     * подводим итог: создадав два просты класса описанных выше
     * мы можем полуить массив имён в одну строчку
     */
    @Test
    public void refMapper() throws IOException {
        System.out.println(new ObjectMapper().readValue("{\"users\":{\"names\":[\"name1\",\"name2\"]}}", Maper.class).getUsers().getNames());
    }
}