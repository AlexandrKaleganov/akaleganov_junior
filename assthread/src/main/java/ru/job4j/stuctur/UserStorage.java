package ru.job4j.stuctur;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

/**
 * потоко безопасная структура
 */
@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<Integer, User>();


    public synchronized boolean add(User user) {
        if (this.users.containsKey(user.getId())) {
            return false;
        } else {
            this.users.put(user.getId(), user);
            return true;
        }
    }

    public synchronized boolean delete(User user) {
        if (!this.users.containsKey(user.getId())) {
            return false;
        } else {
            this.users.remove(user.getId());
            return true;
        }
    }

    public synchronized boolean update(User user) {
        if (!this.users.containsKey(user.getId())) {
            return false;
        } else {
            this.users.put(user.getId(), user);
            return true;
        }
    }

    public synchronized User getUser(int i) {
        return this.users.get(i);
    }

    public synchronized Map<Integer, User> getUsers() {
        return users;
    }

    public synchronized void transfer(int o1, int o2, int summTrensfer) {
        if (this.users.containsKey(o1) && this.users.containsKey(o2)) {
            this.users.get(o1).decrementAmount(summTrensfer);
            this.users.get(o2).incrementAmmount(summTrensfer);
        } else {
            throw new NoSuchElementException("Пльзователи в базе отсутствуют");
        }
    }

}
