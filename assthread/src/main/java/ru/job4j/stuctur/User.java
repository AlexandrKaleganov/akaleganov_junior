package ru.job4j.stuctur;

/**
 * Класс юзерс  содержит только два поля id  и amount  и основные методы это инкремент и декремент
 *
 */

public class User {
    private final int id;
    private int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void incrementAmmount(int update) {
        this.amount += update;
    }

    public void decrementAmount(int update) {
        if (amount < update) {
            throw new AmountBalancexception();
        }
        this.amount -= update;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        if (id != user.id) {
            return false;
        }
        return amount == user.amount;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        return " " + amount + " ";
    }
}
