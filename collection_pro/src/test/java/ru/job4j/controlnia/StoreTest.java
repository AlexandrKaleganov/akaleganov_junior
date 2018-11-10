package ru.job4j.controlnia;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Kaleganov Alexander
 * @since 09.07.2018
 *
 */
public class StoreTest {
    @Test
    public void whentestOneexperement() {
        Store.User user1 = new Store.User(123, "vova");
        Store.User user2 = new Store.User(168, "Misha");
        Store.User user3 = new Store.User(143, "Kolia");
        Store.User user4 = new Store.User(153, "Vasia");
        Store.User user5 = new Store.User(293, "Peter");
        Store.User user6 = new Store.User(12893, "Sasha");
        List<Store.User> previoues = new ArrayList<Store.User>(Arrays.asList(user1, user2, user3, user4));
        List<Store.User> current = new ArrayList<Store.User>(Arrays.asList(user1, user2, user4, user5, user6));
        current.set(1, new Store.User(168, "Kisa"));
        //итого добавлено 2 новых элемента,  изменено 1, удалено 1
        Info expected = new Info(2, 1, 1);
        assertThat(new Store().diff(previoues, current), Is.is(expected));
        System.out.println(new Store().diff(previoues, current));

    }
}