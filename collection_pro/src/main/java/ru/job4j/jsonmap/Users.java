package ru.job4j.jsonmap;

import java.util.ArrayList;

public class Users {

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    private ArrayList<String> names = new ArrayList<>();
}
