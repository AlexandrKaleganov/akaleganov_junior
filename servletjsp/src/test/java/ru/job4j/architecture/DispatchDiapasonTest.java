package ru.job4j.architecture;

import org.junit.Test;
import ru.job4j.architecture.err.DatabaseException;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Kaleganov (alexmur07@mail.ru)
 * @version 12
 * @since 05/12/2018
 * тест класса диспатч
 */
public class DispatchDiapasonTest {

    /**
     * Between 14 and 18.
     */
    @Test
    public void whenBetween14and18ThenLimited() throws DatabaseException {
        Users users = new Users("12", "user", "user123");
        DispatchDiapason dispatchDiapason = DispatchDiapason.getInstance();
        assertThat(
                dispatchDiapason.access(
                        "add", users
                ).get(),
                is("this user add to database")
        );
        assertThat(
                dispatchDiapason.access(
                        "update", new Users("12", "саша", "user123")
                ).get(),
                is("user id = 12 updated")
        );
        assertThat(
                dispatchDiapason.access(
                        "findbyid", new Users("12", "саша", "user123")
                ).get(),
                is(users)
        );
        Map<String, Users> map = (Map<String, Users>) dispatchDiapason.access("findall", new Users()).get();
        assertThat(map.get("12"), is(users));
        assertThat(
                dispatchDiapason.access(
                        "delete", users
                ).get(),
                is("user id = 12 deleted")
        );

    }

}