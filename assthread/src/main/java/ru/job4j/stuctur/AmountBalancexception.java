package ru.job4j.stuctur;

public class AmountBalancexception extends RuntimeException {
    AmountBalancexception() {
        super("Недостаточно средств на счёте");
    }
}
