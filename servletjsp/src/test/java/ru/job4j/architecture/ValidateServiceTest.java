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
        Users users = new Users("12", "sasha", "alexmur07", "root", "roo", "roo");
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
        Users user1 = new Users("0", "Vasia", "vasilisk", "pass", "roo",  "roo");
        this.fulltest((val, exp) -> {
            Assert.assertThat(val.findById(exp), Is.is(exp));
            Assert.assertThat(val.findById(user1), Is.is(new Users()));
        });
    }

    @Test(expected = DatabaseException.class)
    public void testformattoLogin() throws Exception {
        Users user1 = new Users("12",  "aAAAa", "vasilis1_", "pass", "roo",  "roo");
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
        Users user1 = new Users("12", "aAAAa1", "vasilisk", "roo", "roo", "roo");
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
        Users user1 = new Users("12", "Vasia", "vasilisk", "roo", "roo",  "roo");
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
            Users expected = val.update(new Users(exp.getId(), "vass", "expected", "roo", "roo",  "roo"));
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
}