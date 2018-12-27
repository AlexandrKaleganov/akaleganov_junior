package ru.job4j.architecture;

import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.err.DatabaseException;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Kaleganov (alexmur07@mail.ru)
 * @version 12
 * @since 05/12/2018
 * тест класса диспатч
 */
public class DispatchDiapasonTest {


    private void fulltest(BiConsumer<DispatchDiapason, Users> fanc) {
        Users users = new Users("0", "user", "user123");
        DispatchDiapason disp = new DispatchDiapason().init();
        try {
            Users exp = (Users) disp.access("add", users).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fanc.accept(disp, new Users());
        } finally {
            try {
                disp.access("deleteAll");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testAdd() {
        this.fulltest((disp, exp) -> {
            System.out.println("");
        });
    }
//    /**
//     * Between 14 and 18.
//     */
//    @Test
//    public void whenBetween14and18ThenLimited() throws DatabaseException {
//        Users users = new Users("0", "user", "user123");
//        DispatchDiapason dispatchDiapason = new DispatchDiapason().init();
//        assertThat(
//                dispatchDiapason.access(
//                        "add", users
//                ).get(),
//                is("this user add to database")
//        );
//        assertThat(
//                dispatchDiapason.access(
//                        "update", new Users("0", "саша", "user123")
//                ).get(),
//                is("user id = 0 updated")
//        );
//        assertThat(
//                dispatchDiapason.access(
//                        "findbyid", new Users("0", "саша", "user123")
//                ).get(),
//                is(users)
//        );
//        List<Users> map = (List<Users>) dispatchDiapason.access("findall", new Users()).get();
//        assertThat(map.get(0), is(users));
//        assertThat(
//                dispatchDiapason.access(
//                        "delete", users
//                ).get(),
//                is("user id = 0 deleted")
//        );
//
//    }

}