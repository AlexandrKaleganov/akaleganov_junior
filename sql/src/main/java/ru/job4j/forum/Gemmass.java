package ru.job4j.forum;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Gemmass {
    ArrayList<Gem> gems = new ArrayList<>();

    Gemmass() {
    }

    Gemmass(ArrayList<Gem> list) {
        this.gems = list;
    }

    public ArrayList<Gem> getGems() {
        return gems;
    }

    public void setGems(ArrayList<Gem> gems) {
        this.gems = gems;
    }
}
