package ru.job4j.architecture;

import ru.job4j.architecture.err.DatabaseException;

import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Alexander Kaleganov
 * @version 4
 * @since 04/12/2018
 * этот класс прослойка между сервлетами и логикой программы
 * методы этого класса будут проверять: можно ли добавить этот объект,
 * в каждом методе будем проверять что id у нас соответствует формату
 * поля пользователя тоже должны соответствовать формату
 */
public class ValidateService implements Validate {
    private final Store logic = MemoryStore.getInstance();
    private static final ValidateService INSTANCE = new ValidateService();

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    /**
     * в методе если не выпали исключения то в базу будет добавлен пользователь
     * первый метод может выбросить сообщение неверный формат id
     * второй выбросит исключение если имя или логин неверного формата или пустые
     * третий выбросит исключение если пользователь уже есть в БД
     */
    @Override
    public String add(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.validNameandLogin(users.getName(), users.getLogin(),
                (n) -> n.matches("[a-zA-Z]{1,10}||[а-яА-Я]{1,10}"),
                (l) -> l.matches("[a-zA-Z, 0-9]{1,10}"));
        this.containsUsertoData(users.getId(), (k) ->
                this.logic.findAll().containsKey(k), " already exists");
        this.logic.add(users);
        return "this user add to database";
    }

    /**
     * обращаю внимание на то что метод апдейт может прининимать имя и логин с пустыми значениям
     * если пустые значения обновляться не будут
     *
     * @param
     * @param users
     * @return
     */
    @Override
    public String update(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.validNameandLogin(users.getName(), users.getLogin(),
                (n) -> n.matches("[a-zA-Z]{0,20}||[а-яА-Я]{0,20}"),
                (l) -> l.matches("[a-zA-Z, 0-9]{0,20}"));
        this.containsUsertoData(users.getId(), (k) ->
                !this.logic.findAll().containsKey(k), " is not found");
        this.logic.update(users);
        return "user id = " + users.getId() + " updated";
    }

    /**
     * при удалении поля имени и логина могут быть пустыми
     *
     * @param users
     * @return
     * @throws DatabaseException
     */
    @Override
    public String delete(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.containsUsertoData(users.getId(), (k) ->
                !this.logic.findAll().containsKey(k), " is not found");
        this.validNameandLogin(users.getName(), users.getLogin(),
                (n) -> n == null || n.matches("[a-zA-Z]{0,20}||[а-яА-Я]{0,20}"),
                (l) -> l == null || l.matches("[a-zA-Z, 0-9]{0,20}"));
        this.logic.delete(users);
        return "user id = " + users.getId() + " deleted";
    }

    @Override
    public Map<String, Users> findAll() {
        return this.logic.findAll();
    }

    //я не знал как этот момент проверить, сделал просто проверку что это должны быть символы цифры
    @Override
    public Users findById(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.containsUsertoData(users.getId(), (k) ->
                !this.logic.findAll().containsKey(k), " is not found");
        return (Users) this.logic.findById(users);
    }

    //методы генерирующие исключения
    //id должен содержать только цифры от 0 до 10 размер не более 10 символов
    private void validID(String id) throws DatabaseException {
        if (!id.matches("[0-9]{1,10}")) {
            throw new DatabaseException("id format error");
        }
    }


    //выбрасывает исключение если пользоавтель с таким id уже есть или не найден
    private void containsUsertoData(String id, Predicate<String> isID, String error) throws DatabaseException {
        if (isID.test(id)) {
            throw new DatabaseException("user id = " + id + error);
        }
    }

    //выбрасывает исключение если имя или логин пользователя введены не корректно
    //(имя должно состоять только из символов латинского алфавита или только из символов кирилицы)
    //лщгин должен состоять только из символов латинского алфавита и содержать цифры от 1 до 9
    private void validNameandLogin(String name, String login, Predicate<String> fancname, Predicate<String> fanclogin) throws DatabaseException {
        if (!fancname.test(name)) {
            throw new DatabaseException("USERNAME_" + name + " error");
        }
        if (!fanclogin.test(login)) {
            throw new DatabaseException("LOGIN_" + login + " error");
        }
    }

}
