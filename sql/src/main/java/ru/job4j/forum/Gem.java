package ru.job4j.forum;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Gem {
    private String name;
    private boolean  preciousness;  //может быть драгоценным либо полудрагоценным.
    private String origin;        //– место добывания.

    Gem() {

    }
    Gem(String name, boolean preciousness, String origin) {
        this.name = name;
        this.preciousness = preciousness;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPreciousness() {
        return preciousness;
    }

    public void setPreciousness(boolean preciousness) {
        this.preciousness = preciousness;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
