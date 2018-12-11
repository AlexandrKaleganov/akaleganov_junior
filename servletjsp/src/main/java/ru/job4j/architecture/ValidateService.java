package ru.job4j.architecture;

import ru.job4j.architecture.err.DatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
     * третий выбросит исключение если пользователь уже есть в БД или если ЛОГИН ЕСТЬ В БД
     */
    @Override
    public String add(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.validNameandLogin(users.getName(), users.getLogin(),
                (n) -> n.matches("[a-zA-Z]{1,10}||[а-яА-Я]{1,10}"),
                (l) -> l.matches("[a-zA-Z, 0-9]{1,10}"));
        this.containsUsertoData(users, (k) -> {
            CopyOnWriteArrayList<Users> list = (CopyOnWriteArrayList<Users>) this.logic.findAll();
            boolean rsl = false;
            rsl = this.logic.findAll().contains(k);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getLogin().contains(k.getLogin())) {
                    rsl = true;
                    break;
                }
            }
            return rsl;
        }, " already exists");
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
        this.containsUsertoData(users, (k) ->
                this.logic.findAll().size() > Integer.valueOf(k.getId()) && Integer.valueOf(k.getId()) >= 0, " is not found");
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
        this.containsUsertoData(users, (k) ->
                this.logic.findAll().size() > Integer.valueOf(k.getId()) && Integer.valueOf(k.getId()) >= 0, " is not found");
        this.validNameandLogin(users.getName(), users.getLogin(),
                (n) -> n == null || n.matches("[a-zA-Z]{0,20}||[а-яА-Я]{0,20}"),
                (l) -> l == null || l.matches("[a-zA-Z, 0-9]{0,20}"));
        this.logic.delete(users);
        return "user id = " + users.getId() + " deleted";
    }

    @Override
    public List<Users> findAll() {
        return this.logic.findAll();
    }

    /**
     * если k == null то будет исключение
     *
     * @param users
     * @return
     * @throws DatabaseException
     */
    @Override
    public Users findById(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.containsUsertoData(users, (k) ->
                this.logic.findAll().size() > Integer.valueOf(k.getId()) && Integer.valueOf(k.getId()) >= 0, " is not found");
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
    private void containsUsertoData(Users users, Predicate<Users> isID, String error) throws DatabaseException {
        if (isID.test(users)) {
            throw new DatabaseException("user id = " + users.getId() + error);
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
