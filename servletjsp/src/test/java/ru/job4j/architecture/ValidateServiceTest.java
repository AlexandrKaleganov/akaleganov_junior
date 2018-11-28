package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.err.DatabaseException;

public class ValidateServiceTest {

    /**
     * тест исключения "id format error"
     *
     * @throws DatabaseException
     */
    @Test(expected = DatabaseException.class)
    public void formatTestID() throws DatabaseException {
        Users userDubl = new Users("1DD2", "Nrrrrrrrr", "vasilisk");
        Validate validate = new ValidateService();
        validate.add(userDubl);
    }

    /**
     * тест исключения "%USERNAME%=" + name + " error"
     *
     * @throws DatabaseException
     */
    @Test(expected = DatabaseException.class)
    public void testformattoName() throws DatabaseException {
        Users user1 = new Users("12", "aAAAa12", "vasilisk");
        Validate validate = new ValidateService();
        validate.add(user1);
    }

    /**
     * тест исключения "%LOGIN%=" + login + " error"
     *
     * @throws DatabaseException
     */
    @Test(expected = DatabaseException.class)
    public void testformattoLogin() throws DatabaseException {
        Users user1 = new Users("12", "aAAAa", "vasilis1_");
        Validate validate = new ValidateService();
        validate.add(user1);
    }

    /**
     * тест метода findByID если объект  есть в базе то метод вернёт объект
     * тест метода findByID если объекта нету в базе вернёт ошибку
     *
     * @throws DatabaseException
     */
    @Test(expected = DatabaseException.class)
    public void finbyid() throws DatabaseException {
        Users user1 = new Users("12", "Vasia", "vasilisk");
        Validate validate = new ValidateService();
        Assert.assertThat(validate.add(user1), Is.is("Пользователь добавлен в БАЗУ"));
        Assert.assertThat(validate.findById(user1.getId()), Is.is(user1));
        validate.findById("15");
    }

    /**
     * тест метода add если объекта нету в базе то объект будет добалвен в нашу базу
     * тест метода add если объект есть в базе то метод выбросит исключение
     *
     * @throws DatabaseException
     */
    @Test(expected = DatabaseException.class)
    public void add() throws DatabaseException {
        Users user1 = new Users("12", "Vasia", "vasilisk");
        Users userDubl = new Users("12", "Nrrrrrrrr", "vasilisk");
        Validate validate = new ValidateService();
        Assert.assertThat(validate.add(user1), Is.is("Пользователь добавлен в БАЗУ"));
        validate.add(userDubl);
    }

    /**
     * тест метода delete если объект найден, то объект будет удалён из базы
     * если объект не найдет будет выброшено исключение
     *
     * @throws DatabaseException
     */
    @Test(expected = DatabaseException.class)
    public void delete() throws DatabaseException {
        Users user1 = new Users("12", "Vasia", "vasilisk");
        Users users2 = new Users("1", "Alex", "alexmur07");
        Validate validate = new ValidateService();
        validate.add(user1);
        validate.add(users2);
        Assert.assertThat(validate.findAll().size(), Is.is(2));
        Assert.assertThat(validate.delete("12"), Is.is("Пользователь с id = 12 удалён"));
        Assert.assertThat(validate.findAll().size(), Is.is(1));
        validate.delete("12");
    }

    /**
     * тест метода update если объект найден, то объект будет обновлён обнавляются только имя и логини дата создания
     * id  остаётся неизменным
     * объект c id не найдет будет выброшено исключение
     */
    @Test(expected = DatabaseException.class)
    public void update() throws DatabaseException {
        Users user1 = new Users("12", "Vasia", "vasilisk");
        Users users2 = new Users("1", "Alex", "alexmur07");
        Users users3 = new Users("55", "Nikita", "alexmur07");
        Validate validate = new ValidateService();
        validate.add(user1);
        validate.add(users2);
        Assert.assertThat(validate.update("1", users3), Is.is("Данные пользователя с id = 1 обновлены"));
        Assert.assertThat(validate.findById("1").getName(), Is.is("Nikita"));
        validate.update("55", users2);
    }

    //тест метода findAll если в бд есть данные
    //то мы получим наши данные, в противном случае мы получим исключение "База пуста"
    @Test(expected = DatabaseException.class)
    public void findAll() throws DatabaseException {
        Users user1 = new Users("12", "Vasia", "vasilisk");
        Validate validate = new ValidateService();
        validate.add(user1);
        Assert.assertThat(validate.findAll().size(), Is.is(1));
        validate.delete("12");
        validate.findAll();
    }
}