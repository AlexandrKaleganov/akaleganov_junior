package ru.job4j.pool;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void workTestPool() {

        // Создает пул нитей по количеству доступных процессоров.
        ThreadPool pool = new ThreadPool(Runtime.getRuntime().availableProcessors());

        //  Добавляет задачу в пул и сразу ее выполняет.
        pool.work(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute " + Thread.currentThread().getName());
            }
        });

        //Добавляет задачу в пул и сразу ее выполняет
        pool.work(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute " + Thread.currentThread().getName());
            }
        });
    }
}