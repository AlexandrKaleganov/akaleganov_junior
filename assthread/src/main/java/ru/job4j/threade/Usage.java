package ru.job4j.threade;

/**
 * Первая проблема которая может возникнуть из-за путаницы объектов, как это произошло в вашем видеоуроке:
 * у Вас в уроке там 3 или 6 получается потому что  изначально counterA.add(1);
 * и тут же в эту же переменную counterA.add(2);
 * а потом идёт запуск  первого потока и он  выдаёт 3 + 0  т.к. counterB   не проинициализирован,
 * второй поток наоборот выдаёт 0+3 т.к. во втором потоке объекты  поменяны местами а монитор отлавливает объект только
 * в указанной переменной , монитор не ожидает что ты тот же объект передашь во вторую переменную
 * монитор только держит (A) но он не ожидает что в переменную (B) прилетит точно такой же объект по этому когда
 * он успевает прилетать со вторым потоком получается 6 (3+3) но чаще всего он не успевает и получается 3)
 * вот в  метод sum иногда успевает одна и таже переменная counterA зайти и складывается сама с собой, т.к.
 * наложилось из-за того что объекты лезут в разные переменные и не важно блочная синхронизация используется или нет.
 * Переменные не желательно менять местами а то никакая синхронизация не поможет.
 */
@SuppressWarnings("ALL")
public class Usage {
    public final static class Counter {
        long count = 0;

        public void add(long value) {
            this.count += value;
        }

        public void sum(final Counter a, final Counter b) {
            synchronized (a) {
                synchronized (b) {
                    a.add(b.count);
                }
            }
        }
    }

    public final static class CounterThread extends Thread {
        protected final Counter counterA;
        protected final Counter counterB;

        public CounterThread(Counter counterA, Counter counterA1) {
            this.counterA = counterA;
            this.counterB = counterA1;
        }

        @Override
        public void run() {
            counterA.sum(counterA, counterB);
        }

        public static void main(String[] args) throws InterruptedException {
            Counter counterA = new Counter();
            counterA.add(1);
            Counter counterB = new Counter();
            counterB.add(2);
            Thread threadA = new CounterThread(counterA, counterB);
            Thread threadB = new CounterThread(counterB, counterA);

            threadA.start();
            threadB.start();
            threadA.join();
            threadB.join();
            System.out.println(counterA.count);

        }
    }
}

