package ru.job4j.threade;

/**
 * Третья проблема если метод объекта не синхронизирован, а к нему будут обращаться сразу два
 * потока то программа будет работать не предсказуемо: без синхронизации будет каша.
 * В моём примере получается что оба потока одновременно читают и изменяют переменные
 * иногда второй поток делает инкремент к неизменённой переменной в результате
 * получается то 14 то 16 то 17, в этом случае метод add необходимо сделать синхронным.
 */
@SuppressWarnings("ALL")
public class AssinhronThread {
    private int count;

    public synchronized void add() {
        this.count += 1;
        System.out.println(this.count);
    }


    static class AddUser implements Runnable {
        private final AssinhronThread assinhronThread;

        AddUser(AssinhronThread assinhronThread) {
            this.assinhronThread = assinhronThread;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                assinhronThread.add();
            }
        }
    }


    static class Starttest {
        public static void main(String[] args) throws InterruptedException {
            AssinhronThread assinhronThread = new AssinhronThread();
            Thread t1 = new Thread(new AddUser(assinhronThread));
            Thread t2 = new Thread(new AddUser(assinhronThread));
            t1.start();
            t2.start();
            t1.join();
            t2.join();

        }
    }
}
