package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.err.BiConEx;
import ru.job4j.architecture.err.DatabaseException;

import java.util.List;
import java.util.function.BiConsumer;

import static org.hamcrest.core.Is.is;

/**
 * @author Alexander Kaleganov (alexmur07@mail.ru)
 * @version 12
 * @since 05/12/2018
 * тест класса диспатч
 * исключение будет пробрасываться выше в сервлеты
 * и там либо я получаю объект и вывожу сообщение либо получаю либо получаю сообщение об ошибке
 */
public class DispatchDiapasonTest {


    private void fulltest(BiConEx<DispatchDiapason, Users> fanc) {
        var users = new Users("1", "user", "user123");
        var disp = new DispatchDiapason().init();
        try {
            var exp = (Users) disp.access("add", users);

            fanc.accept(disp, exp);
        } catch (Exception e) {
        } finally {
            try {
                disp.access("deleteAll");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * тест добавления и получения пользователя по id
     */
    @Test
    public void testAdd() {
        this.fulltest((disp, exp) -> {
            Assert.assertThat(disp.access("findbyid", exp), is(exp));
        });
    }

    /**
     * тест получения всех пользователей
     *
     */
    @Test
    public void findall() {
        this.fulltest((disp, exp) -> {
            Assert.assertThat(disp.access("findall").get(0), Is.is(exp));
        });
    }
    @Test
    public void delete() {
this.fulltest((disp, exp)->{
    disp.access("update", new Users(exp.getId(), "вася", "vasia2"));
    Assert.assertThat(disp.access("findbyid", exp).getName(), is("вася"));
});    }
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