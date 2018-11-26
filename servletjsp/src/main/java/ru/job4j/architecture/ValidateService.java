package ru.job4j.architecture;

/**
 * в каждом методе будем проверять что id у нас соответствует формату
 */
public class ValidateService implements Validate {
    private final Store logic = MemoryStore.getInstance();

    private static final ValidateService INSTANCE = new ValidateService();

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(Users users) {
        boolean rsl = true;
        if (!this.findById(users.getId()) || logic.findById(users.getId()) != null) {
            rsl = false;
        }
        return rsl;
    }

    @Override
    public boolean update(String id, Users users) {
        boolean rsl = true;
        if (!this.findById(id) || logic.findById(id) == null) {
            rsl = false;
        }
        return rsl;
    }

    @Override
    public boolean delete(String id) {
        boolean rsl = true;

        if (!this.findById(id) || logic.findById(id) == null) {
            rsl = false;
        }
        return rsl;
    }

    @Override
    public boolean findAll() {
        boolean rsl = true;
        if (logic.findAll().size() < 1) {
            rsl = false;
        }
        return rsl;
    }

    //я не знал как этот момент проверить, сделал просто проверку что это должны быть символы цифры
    @Override
    public boolean findById(String id) {
        boolean rsl = true;
        if (!id.matches("[0-9]{1,10}")) {
            rsl = false;
        }
        return rsl;
    }
}
