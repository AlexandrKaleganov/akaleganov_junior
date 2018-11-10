package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>(); //вообще это блокирующая очередь

    ThreadPool(int size) {                 //инициализация пула
        System.out.println(size + " ядер" + " создаю столько же нитий");
        for (int i = 0; i < size; i++) {

            this.threads.add(new ThreadList(this.tasks, "поток № " + i));
        }
    }

    //добавляем таски
    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(e -> {
            e.interrupt();
        });

    }

    //из этих тредов будет состоять линкедлист
    private static class ThreadList extends Thread {
        private final BlockingQueue<Runnable> task;
        Thread t;
        ThreadList(BlockingQueue<Runnable> queou, String name) {
            t = this;
            t.setName(name);
            System.out.println("создаю поток " + name);
            this.task = queou;
            start();
           }

        @Override
        public void run() {
            try {
                task.take().run();   //если поток влезает в очередь то он ждёт
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
