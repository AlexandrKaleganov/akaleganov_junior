package ru.job4j.architecture;

import java.util.List;

public interface ErrLog {
    Err add(Err err);

    List<Err> findALL();

    List<Err> deleteALL();

}
