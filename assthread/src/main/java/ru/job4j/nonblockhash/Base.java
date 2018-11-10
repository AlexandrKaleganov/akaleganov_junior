package ru.job4j.nonblockhash;

public class Base {
    private String name;
    private final int id;
    private int version;

    public Base(int id, int version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public int getId() {
            return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updateVersion();
    }

    private void updateVersion() {
        this.version++;
    }

    public int getVersion() {
        return version;
    }
}
