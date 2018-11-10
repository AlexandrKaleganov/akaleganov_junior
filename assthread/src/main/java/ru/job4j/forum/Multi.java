package ru.job4j.forum;

public class Multi {
    public static void main(String... args) {
        //давай выведем имя внешнего потока
        System.out.println(Thread.currentThread().getName());
        new Subthread();

    }
}

class Subthread extends Thread {
    Subthread() {
        //тут ты просто вызвал конструктор по умолчанию где стрнг это имя потока которое ты ему присвоил
        //т.е. имя у твоего созданого потока "Второй поток"
        super("Второй поток");
       //тут ты выводишь поток на печать
        //и если ты зайдёшь в класс Thread
        //то увидешь как у него переопределён метод toString()
        //[" + getName() + "," + getPriority() + "," +
        //                           group.getName() + "]"
        //т.е. он выводит имя потока, приоритет потока, и группу потока
        //под группой потока я так полога имя потока который вызвал текущий поток,имя внешнего потока

        System.out.println("Создан второй поток " + this.getName());
        start();
    }

    public void run() {
        System.out.println("Mой поток запущен...");
        try {
            for (int i = 3; i > 0; i--) {  // i начинается с трёх потом идёт убываение
                System.out.println("Второй поток: " + i);  //выведешь 3  потом 2 потом 1
                Thread.sleep(500);
            }
        } catch (InterruptedException ex) {
            System.out.println("Второй поток прерван");
        }
    }
}