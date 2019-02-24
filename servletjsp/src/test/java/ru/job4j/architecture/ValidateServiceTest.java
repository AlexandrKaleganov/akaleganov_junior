package ru.job4j.architecture;

import net.bytebuddy.asm.Advice;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.err.BiConEx;
import ru.job4j.architecture.err.DatabaseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ValidateServiceTest {

    private void fulltest(BiConEx<Validate<Users>, Users> test) throws Exception {
        Validate<Users> valid = ValidateService.getInstance();
        Users users = new Users("12", LocalDateTime.now(), "sasha", "alexmur07", "root", "","", "");
        try {
            Users exp = valid.add(users);
            test.accept(valid, exp);
        } finally {
            valid.deleteALL();
        }
    }

    /**
     * тест метода findByID если объект  есть в базе то метод вернёт объект
     * тест метода findByID если объекта нету в базе вернёт j,]trn c gecnsvb pyfxtybzvb
     *
     * @throws DatabaseException
     */
    @Test
    public void findbyid() throws Exception {
        Users user1 = new Users("", LocalDateTime.now(), "Vasia", "vasilisk", "pass", "","", "");
        this.fulltest((val, exp) -> {
            Assert.assertThat(val.findById(exp), Is.is(exp));
            Assert.assertThat(val.findById(user1), Is.is(new Users()));
        });
    }

    @Test(expected = DatabaseException.class)
    public void testformattoLogin() throws Exception {
        Users user1 = new Users("12", LocalDateTime.now(), "aAAAa", "vasilis1_", "pass", "","", "");
        this.fulltest((val, exp) -> {
            val.add(user1);
        });
    }

    /**
     * тест исключения "%USERNAME%=" + name + " error"
     *
     * @throws DatabaseException
     */
    @Test(expected = DatabaseException.class)
    public void testformattoName() throws Exception {
        Users user1 = new Users("12", LocalDateTime.now(), "aAAAa1", "vasilisk", "", "","", "");
        this.fulltest((val, exp) -> {
            val.add(user1);
        });
    }


    /**
     * тест метода delete если объект найден, то объект будет удалён из базы
     * если объект не найдет то объект с пустыми значениями вернёт
     *
     * @throws DatabaseException
     */
    @Test
    public void delete() throws Exception {
        Users user1 = new Users("12", LocalDateTime.now(), "Vasia", "vasilisk", "", "","", "");
        this.fulltest((val, exp) -> {
            Assert.assertThat(val.delete(exp), Is.is(exp));
            Assert.assertThat(val.delete(exp), Is.is(new Users()));
        });
    }

    /**
     * тест метода update если объект найден, то объект будет обновлён обнавляются только имя и логини дата создания
     * id  остаётся неизменным
     * после обновления метод вернёт обновлённый объект
     */
    @Test
    public void update() throws Exception {
        this.fulltest((val, exp) -> {
            Users expected = val.update(new Users(exp.getId(), LocalDateTime.now(), "vass", "expected", "roo", "","", ""));
            Assert.assertThat(val.findById(exp), Is.is(expected));
        });
    }

    //тест метода findAll если в бд есть данные
    //то мы получим наши данные, в противном случае мы получим пустой список
    @Test
    public void findAll() throws Exception {
        this.fulltest((val, exp) -> {
            Assert.assertThat(val.findAll().get(0), Is.is(exp));
            val.delete(exp);
            Assert.assertThat(val.findAll().size(), Is.is(0));
        });
    }

    @Test
    public void filterTest() throws Exception {
        this.fulltest((val, exp) -> {
            Assert.assertThat(val.filter(exp).get(0).getId(), Is.is(exp.getId()));
            Assert.assertThat(val.filter(new Users("0", LocalDateTime.parse(exp.getCreateDate().toLocalDate().toString() + " 00:00",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "", "alex", "", "","", ""
            )).get(0).getId(), Is.is(exp.getId()));
        });
    }

}