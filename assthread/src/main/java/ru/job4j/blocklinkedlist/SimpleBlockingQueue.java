package ru.job4j.blocklinkedlist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<E> {
    @GuardedBy("this")
    private Queue<E> data = new LinkedList<>();
    private boolean verification = false;

    public synchronized void offer(E value) {
        while (verification) {
            System.out.println("Очередь не была пуста, поток " + Thread.currentThread().getName() + " ожидает своей очереди");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Исключение InterruptedException перехвачено 1");

            }
        }

        this.data.offer(value);
        System.out.println("Очередь пуста, поток " + Thread.currentThread().getName() + " выполнился");
        verification = true;
        notify();

    }

    public synchronized E poll() throws InterruptedException {
        while (!verification) {
            System.out.println("Очередь пуста, поток " + Thread.currentThread().getName() + " ожидает своей очереди");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new InterruptedException();
            }
        }
        System.out.println("Очередь не была пуста, поток " + Thread.currentThread().getName() + " выполнился");
        verification = false;
        notify();
        return this.data.poll();
    }
    public boolean isEmpty() {
        return this.data.isEmpty();
    }
}
