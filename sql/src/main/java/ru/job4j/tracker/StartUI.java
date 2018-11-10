package ru.job4j.tracker;

import ru.job4j.tracker.modules.*;

public class StartUI {
    private final ITracker tracker;
    private final Input input;

//    private final Output output = new OutConsole();

    StartUI(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    StartUI(ITracker tracker, Input input) {
        this.input = input;
        this.tracker = tracker;
    }

    public static void main(String[] args) {
        try (TrackerSQL tracker = new TrackerSQL(new Config())) {
            new StartUI(new ValidateInput(new ConsoleInput()), tracker).init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void init() {
        boolean exit = false;
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillAction();
        int[] range = menu.returnFINALmenu();

        do {
            menu.shou(System.out::println);
            menu.select(input.inputCommand("Select:", range));
        } while (tracker.getExitProgramm()); // в трекер добавил поле, boolean, пока его не изменяет метод
    }


}
