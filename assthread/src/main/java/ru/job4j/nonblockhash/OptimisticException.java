package ru.job4j.nonblockhash;

public class OptimisticException extends RuntimeException {
    public OptimisticException(String msg) {
        super(msg);
    }
}
