package ru.job4j.stuctur;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {
    public class ThreadUsertransfer extends Thread {
        private final UserStorage userStorage;

        public ThreadUsertransfer(UserStorage userStorage) {
            this.userStorage  = userStorage;
        }

        @Override
        public void run() {
            this.userStorage.transfer(1, 2, 10);
        }
    }

    public class ThreadUserAdd extends Thread {
        private final UserStorage userStorage;
        private final User user;

        public ThreadUserAdd(UserStorage userStorage, User user) {
            this.userStorage = userStorage;
            this.user = user;
        }


        @Override
        public void run() {
            this.userStorage.add(user);
        }
    }

    @Test
    public void testClassUserStorageAddANDTransfer() throws InterruptedException {
        final UserStorage userStorage = new UserStorage();
        User useOne = new User(1, 180);
        User useTwo = new User(2, 200);
        Thread threadAddOne = new ThreadUserAdd(userStorage, useOne);
        Thread threadAddTwo = new ThreadUserAdd(userStorage, useTwo);
        Thread first = new ThreadUsertransfer(userStorage);
        Thread second = new ThreadUsertransfer(userStorage);
        threadAddOne.start();
        threadAddTwo.start();
        threadAddOne.join();
        threadAddTwo.join();
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(useOne.getAmount(), is(160));
        assertThat(useTwo.getAmount(), is(220));
        assertThat(userStorage.update(new User(8, 150)), is(false));
        assertThat(userStorage.delete(new User(1, 160)), is(true));

    }
}