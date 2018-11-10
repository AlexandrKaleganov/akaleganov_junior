package ru.job4j.forum;
/**
 * что-то типо блокирующей очереди
 * текущая загруженность порта может быть не справедлива для всех тредов одновременно,
 *  т.к. этот параметр находится в стеке разных тредов одновременно и может быть ошибочным
 */

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.Queue;
@ThreadSafe
public class PortDataBase<E> {
    @GuardedBy("this")
    private Queue<E> data = new LinkedList<E>();

    public synchronized void add(E e) {
        while (this.data.size() > 3) {
            System.out.println("Извините порт перегружен, " + Thread.currentThread().getName() + " ожидает очереди когда произведётся отгрузка из порта");
            try {
                wait();
            } catch (InterruptedException e1) {
                System.out.println("итеррапт перехвачен");
            }
        }
        try {
            Thread.sleep(0);
        } catch (InterruptedException e3) {
            e3.printStackTrace();
        }
        this.data.add(e);
        System.out.println(Thread.currentThread().getName() + " Доставил груз в порт, текущая загруженность порта " + this.data.size() + " из 3");
        notifyAll();
    }

    public synchronized E del() {
        while (this.data.size() == 0) {
            System.out.println("Извините, в порту груз отсутствует, " + Thread.currentThread().getName() + " ожидает очереди когда груз поступит в порт");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("итеррапт перехвачен");
            }
        }
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        E rsl = this.data.poll();
        System.out.println(Thread.currentThread().getName() + " Произвёл отгрузку из порта, текущая заполненность порта " + this.data.size() + " из 3");
        notifyAll();
        return rsl;
    }
}
