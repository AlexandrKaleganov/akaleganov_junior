package ru.job4j.architecture;

import org.junit.Test;
import ru.job4j.architecture.err.DatabaseException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test for person permission  by age.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class DispatchDiapasonTest {

    /**
     * Between 14 and 18.
     */
    @Test
    public void whenBetween14and18ThenLimited() throws DatabaseException {
        Validate validate = new ValidateService();
        String key = "add";
        Users users = new Users("12", "user", "user123");
        assertThat(
                new DispatchDiapason().init().access(
                        validate, key, users
                ),
                is("this user add to database")
        );
    }

}