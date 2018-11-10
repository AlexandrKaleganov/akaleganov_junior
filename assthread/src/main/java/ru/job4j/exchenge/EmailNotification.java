package ru.job4j.exchenge;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("Convert2Lambda")
@ThreadSafe
public class EmailNotification {
    @GuardedBy("this")
    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = "Notification " + user.getName() + " to email " + user.getEmail();
            String body = "Add a new event to " + user.getName();
            String email = user.getEmail();
            send(subject, body, email);
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void send(String subject, String body, String email) {
        System.out.println(subject);
        System.out.println(body);
        System.out.println(email);
    }
}
