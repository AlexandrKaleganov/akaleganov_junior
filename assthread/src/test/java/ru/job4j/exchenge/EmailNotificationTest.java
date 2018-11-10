package ru.job4j.exchenge;

import org.junit.Test;
import org.junit.runner.notification.RunListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class EmailNotificationTest {


    @Test
    public void testSend() {
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        User user = new User("Vasia", "vasia@mail.ru");
        EmailNotification notification = new EmailNotification();
        notification.emailTo(user);
        notification.close();
    }
}