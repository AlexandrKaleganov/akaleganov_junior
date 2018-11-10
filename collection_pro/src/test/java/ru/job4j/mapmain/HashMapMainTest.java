package ru.job4j.mapmain;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.map.User;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HashMapMainTest {
    private HashMapMain<String, Integer> hashMap;

    @Before
    public void singUp() {
        hashMap = new HashMapMain<String, Integer>();
        hashMap.put("Vladimir", 12);
        hashMap.put("Konstantin", 8);
        hashMap.put("Dimon", 36);
        hashMap.put("Sonia", 27);
        hashMap.put("Nika", 38);
    }

    /**
     *тест добавление по ключу + вывод на печать
     *
     */
    @Test
    public void whenputAndtoStringandDeleteTest() {
        assertThat(hashMap.put("Kotik", 4), is(true));
        assertThat(hashMap.put("Dimon", 38), is(true));
        System.out.println(hashMap);
        assertThat(hashMap.delete("Nika"), is(true));
        assertThat(hashMap.delete("Nika"), is(false));
        System.out.println(hashMap);
    }

    /**
     * тестирование get
     *
     */
    @Test
    public void whengetTesthashmapMain() {
        assertThat(hashMap.get("Dimon"), is(36));
        assertThat(hashMap.get("Peter"), is((Integer) null));
    }

    /**
     * тест метода проверить есть элементы в массиве хешкарте или нет
     */
    @Test
    public void isEmptyTest() {
        assertThat(hashMap.isEmpty(), is(true));
        HashMapMain<User, String> mapaUsers = new HashMapMain<User, String>();
        assertThat(mapaUsers.isEmpty(), is(false));
        mapaUsers.put(new User("Natali", 2, new GregorianCalendar(1987, 10, 11)), "пользователь 2");
        assertThat(mapaUsers.isEmpty(), is(true));
    }

    /**
     * тест итератора
     *
     */
    @Test(expected = NoSuchElementException.class)
    public void whenIteratorTest() {
        assertThat(hashMap.put("Nika", 38), is(true));
        Iterator<String> iterator = hashMap.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next().toString(), is("Vladimir"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next().toString(), is("Dimon"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("Sonia"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("Konstantin"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("Nika"));
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }
}