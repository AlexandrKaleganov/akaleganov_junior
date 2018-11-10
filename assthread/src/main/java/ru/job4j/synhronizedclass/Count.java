package ru.job4j.synhronizedclass;



@SuppressWarnings("ALL")
public class Count {
    private int value;

    public synchronized void increment() {
        this.value++;
    }

    public synchronized int getValue() {
        return this.value;
    }

}
