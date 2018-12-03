package ru.job4j.architecture;
/**
 * класс диспетчер комманд
 * в хешмапе содержаться три команды
 * add, update, delete  и реализованы они через лямбы
 * с помощью функционального интерфейса
 * глядя на тот шаблон, который предоставляется в задании я ничего лучше не смог придумать надеюсь подход правильный
 * метод init  инициализирует нашу мапу
 * метод access получает доступ к нужной функции на входи идёт валидатор, ключ и созданный пользователь
 */

import ru.job4j.architecture.err.DatabaseException;
import sun.plugin2.message.Message;

import javax.xml.bind.ValidationEvent;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class DispatchDiapason {
    /**
     * Dispatch.
     */

    private final Map<String, Function<Users, Optional>> dispatch = new HashMap<String, Function<Users, Optional>>();
    private final Validate validate = ValidateService.getInstance();
    private final static DispatchDiapason INSTANCE = new DispatchDiapason().init();

    public static DispatchDiapason getInstance() {
        return INSTANCE;
    }

    /**
     * Load initial handlers.
     *
     * @return current object.
     */
    public DispatchDiapason init() {
        this.dispatch.put("add", (users) -> {
                    try {
                        return Optional.of(this.validate.add(users));
                    } catch (DatabaseException e) {
                        return Optional.of(e.getMessage());
                    }
                }
        );
        this.dispatch.put("update", (users) -> {
            try {
                return Optional.of(this.validate.update(users).toString());
            } catch (DatabaseException e) {
                return Optional.of(e.getMessage());
            }
        });
        this.dispatch.put("delete", (users) -> {
                    try {
                        return Optional.of(this.validate.delete(users));
                    } catch (DatabaseException e) {
                        return Optional.of(e.getMessage());
                    }
                }
        );
        this.dispatch.put("findall", (users) ->
                Optional.of(this.validate.findAll())
        );
        this.dispatch.put("findbyid", (users) -> {
                    try {
                        return Optional.of(this.validate.findById(users));
                    } catch (DatabaseException e) {
                        return Optional.of(e.getMessage());
                    }
                }
        );
        return this;
    }

    /**
     * Check access for person by age.
     *
     * @return true if access are allowed
     */
    public Optional access(String key, Users users) {
        return this.dispatch.get(key).apply(users);
    }
}
