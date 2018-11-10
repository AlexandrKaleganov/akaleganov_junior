package ru.job4j.tracker;

import ru.job4j.tracker.modules.ITracker;
import ru.job4j.tracker.modules.Tracker;

import java.sql.SQLException;

public interface UserAction {
    int key();

    void execute(Input input, ITracker tracker);

    String info();
}
