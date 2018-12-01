package ru.job4j.architecture;

import ru.job4j.architecture.err.DatabaseException;

import java.util.Map;

/**
 * этот класс прослойка между сервлетами и логикой программы
 * методы этого класса будут проверять: можно ли добавить этот объект,
 * в каждом методе будем проверять что id у нас соответствует формату
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
     * второй выбросит исключение если пользователь уже есть в БД
     */
    @Override
    public String add(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.validNameandLogin(users.getName(), users.getLogin());
        this.containsUsertoData(users.getId());
        this.logic.add(users);
        return "this user add to database";
    }

    /**
     * @param
     * @param users
     * @return
     */
    @Override

    public String update(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.validNameandLogin(users.getName(), users.getLogin());
        this.containsNonUsertoData(users.getId());
        this.logic.update(users);
        return "user id = " + users.getId() + " updated";
    }

    @Override
    public String delete(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.logic.delete(users);
        return "user id = " + users.getId() + " deleted";
    }

    @Override
    public Map findAll() throws DatabaseException {
        if (logic.findAll().size() < 1) {
            throw new DatabaseException("this database is empty");
        } else {
            return this.logic.findAll();
        }
    }

    //я не знал как этот момент проверить, сделал просто проверку что это должны быть символы цифры
    @Override
    public Users findById(Users users) throws DatabaseException {
        this.validID(users.getId());
        this.containsNonUsertoData(users.getId());
        return this.logic.findById(users);
    }

    //методы генерирующие исключения
    //id должен содержать только цифры от 0 до 10 размер не более 10 символов
    private void validID(String id) throws DatabaseException {
        if (!id.matches("[0-9]{1,10}")) {
            throw new DatabaseException("id format error");
        }
    }

    //выбрасывает исключение если пользоавтель с таким id уже есть
    private void containsUsertoData(String id) throws DatabaseException {
        if (this.logic.findAll().containsKey(id)) {
            throw new DatabaseException("user id = " + id + " already exists");
        }
    }

    //выбрасывает исключение если пользоавтель с таким id уже есть
    private void containsNonUsertoData(String id) throws DatabaseException {
        if (!this.logic.findAll().containsKey(id)) {
            throw new DatabaseException("user id = " + id + " is not found");
        }
    }

    //выбрасывает исключение если имя или логин пользователя введены не корректно
    //(имя должно состоять только из символов латинского алфавита или только из символов кирилицы)
    //лщгин должен состоять только из символов латинского алфавита и содержать цифры от 1 до 9
    private void validNameandLogin(String name, String login) throws DatabaseException {
        if (!name.matches("[a-zA-Z]{0,10}||[а-яА-Я]{1,10}")) {
            throw new DatabaseException("%USERNAME%=" + name + " error");
        }
        if (!login.matches("[a-zA-Z, 0-9]{1,10}")) {
            throw new DatabaseException("%LOGIN%=" + login + " error");
        }
    }

}
