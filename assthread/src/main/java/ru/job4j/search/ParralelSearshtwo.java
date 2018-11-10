package ru.job4j.search;

import ru.job4j.blocklinkedlist.SimpleBlockingQueue;


public class ParralelSearshtwo {
    private volatile static boolean isConsumer = true;

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.interrupted()) {

                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                    }
                    System.out.println("поток завершил работу");
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }

        ).start();
    }
}
