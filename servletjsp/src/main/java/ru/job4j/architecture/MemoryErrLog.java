package ru.job4j.architecture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryErrLog implements ErrLog {
    CopyOnWriteArrayList<Err> dataERR = new CopyOnWriteArrayList<>();
    private static MemoryStore INSTANCE = new MemoryStore();

    public static MemoryStore getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Err add(Err err) {
        err.setDateTime(LocalDateTime.now());
        this.dataERR.add(err);
        return err;
    }

    @Override
    public List<Err> findALL() {
        return this.dataERR;
    }

    @Override
    public List<Err> deleteALL() {
        this.dataERR.clear();
        return this.dataERR;
    }
}
