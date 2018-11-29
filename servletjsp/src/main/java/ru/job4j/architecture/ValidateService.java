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
        return "Пользователь добавлен в БАЗУ";
    }

    /**
     * @param id
     * @param users
     * @return
     */
    @Override

    public String update(String id, Users users) throws DatabaseException {
        this.validID(id);
        this.validNameandLogin(users.getName(), users.getLogin());
        this.containsNonUsertoData(id);
        this.logic.update(id, users);
        return "Данные пользователя с id = " + id + " обновлены";
    }

    @Override
    public String delete(String id) throws DatabaseException {
        this.validID(id);
        this.containsNonUsertoData(id);
        this.logic.delete(id);
        return "Пользователь с id = " + id + " удалён";
    }

    @Override
    public Map findAll() throws DatabaseException {
        if (logic.findAll().size() < 1) {
            throw new DatabaseException("База данных пуста");
        } else {
            return this.logic.findAll();
        }
    }

    //я не знал как этот момент проверить, сделал просто проверку что это должны быть символы цифры
    @Override
    public Users findById(String id) throws DatabaseException {
        this.validID(id);
        this.containsNonUsertoData(id);
        return this.logic.findById(id);
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
            throw new DatabaseException("Пользователь с id = " + id + " уже существует");
        }
    }

    //выбрасывает исключение если пользоавтель с таким id уже есть
    private void containsNonUsertoData(String id) throws DatabaseException {
        if (!this.logic.findAll().containsKey(id)) {
            throw new DatabaseException("Пользователь с id = " + id + " не найден");
        }
    }

    //выбрасывает исключение если имя или логин пользователя введены не корректно
    //(имя должно состоять только из символов латинского алфавита или только из символов кирилицы)
    //лщгин должен состоять только из символов латинского алфавита и содержать цифры от 1 до 9
    private void validNameandLogin(String name, String login) throws DatabaseException {
        if (!name.matches("[a-zA-Z]{1,10}||[а-яА-Я]{1,10}")) {
            throw new DatabaseException("%USERNAME%=" + name + " error");
        }
        if (!login.matches("[a-zA-Z, 0-9]{1,10}")) {
            throw new DatabaseException("%LOGIN%=" + login + " error");
        }
    }

}
