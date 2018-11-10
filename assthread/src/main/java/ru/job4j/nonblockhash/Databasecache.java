package ru.job4j.nonblockhash;
/**
 * @autor Alexander Kaleganov
 * атомарная переменная, неблокируемый кеш
 * мы должны проверить что версии файлов отличаются и если что выбросить исключение и отловить его
 *
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class Databasecache {
    private final ConcurrentHashMap<Integer, Base> database;
    private boolean isStart = true;
    private AtomicReference<Exception> ex;
    public Databasecache() {
        database = new ConcurrentHashMap<Integer, Base>();
        ex = new AtomicReference<>();
    }

    public void add(Base base) {
        this.database.put(base.getId(), base);
    }

    public void update(Base baseup) {

        this.database.compute(baseup.getId(), new BiFunction<Integer, Base, Base>() {
            @Override
            public Base apply(Integer integer, Base base) {
                try {
                    if (database.get(baseup.getId()).getVersion() == baseup.getVersion()) {
                        throw new OptimisticException("Объект уже обновлён до текущей версии, в обновлении отказано");
                    }
                    base.setName(baseup.getName());
                } catch (OptimisticException e) {
                    ex.set(e);
                }
                return base;
            }
        });
    }

    public String getEx() {
        return ex.get().getMessage();
    }

    public void delete(Base base) {
        this.database.remove(base.getId());
    }
}
