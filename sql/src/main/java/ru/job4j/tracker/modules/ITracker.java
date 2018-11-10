package ru.job4j.tracker.modules;

import java.util.ArrayList;

public interface ITracker {
    Items add(Items item);

    void replace(int id, Items item);

    void delete(int id);

    ArrayList<Items> findAll();

    ArrayList<Items> findByName(String key);

    Items findById(int id);

    void addComment(int id, String comments);

    void setExitPrograpp();

    boolean getExitProgramm();
}
