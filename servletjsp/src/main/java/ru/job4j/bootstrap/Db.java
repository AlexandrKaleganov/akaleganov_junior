package ru.job4j.bootstrap;

import java.util.Map;

public interface Db<E, R> {

    R add(E e, R r);

    Map<E, R> findall();
}
