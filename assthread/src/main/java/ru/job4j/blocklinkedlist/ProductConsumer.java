package ru.job4j.blocklinkedlist;

public class ProductConsumer {
    /**
     * два потока  я так понял это задание
     * я в очередь должен добавить элемент а потом отдать его пользователю
     */
    public static class Product implements Runnable {
        private final SimpleBlockingQueue<String> data;
        private String line;

        public Product(SimpleBlockingQueue<String> data, String line) {
            this.data = data;
            this.line = line;
        }

        @Override
        public void run() {
            this.data.offer(line);
        }
    }

    public static class Consumer implements Runnable {
        private final SimpleBlockingQueue data;

        public Consumer(SimpleBlockingQueue data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                this.data.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
