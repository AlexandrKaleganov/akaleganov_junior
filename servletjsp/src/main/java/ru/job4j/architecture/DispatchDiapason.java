package ru.job4j.architecture;

import ru.job4j.architecture.err.DatabaseException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class DispatchDiapason {
    /**
     * Dispatch.
     */

    private final Map<String, BiFunction<Validate, Users, String>> dispatch = new HashMap<String, BiFunction<Validate, Users, String>>();

    /**
     * Load initial handlers.
     *
     * @return current object.
     */
    public DispatchDiapason init() {
        this.dispatch.put("add", (validate, users) -> {
                    String rsl = "";
                    try {
                        rsl = validate.add(users);
                    } catch (DatabaseException e) {
                        rsl = e.getMessage();
                    }
                    return rsl;
                }
        );

        this.dispatch.put("update", (validate, users) -> {
            String rsl = "";
            try {
                rsl = validate.update(users).toString();
            } catch (DatabaseException e) {
                rsl = e.getMessage();
            }
            return rsl;
        });
        this.dispatch.put("delete", (validate, users) -> {
                    String rsl = "";
                    try {
                        rsl = validate.delete(users);
                    } catch (DatabaseException e) {
                        rsl = e.getMessage();
                    }
                    return rsl;
                }
        );
        return this;
    }

    /**
     * Check access for person by age.
     *
     * @return true if access are allowed
     */
    public String access(Validate validate, String key,  Users users) {
        return this.dispatch.get(key).apply(validate, users);
    }
}
