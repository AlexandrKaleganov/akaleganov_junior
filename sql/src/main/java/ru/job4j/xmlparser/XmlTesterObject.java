package ru.job4j.xmlparser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlTesterObject {
    private int id;
    private String name;
    private String fameli;

    XmlTesterObject(int id, String name, String fameli) {
        this.id = id;
        this.name = name;
        this.fameli = fameli;
    }

    XmlTesterObject() {
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getFameli() {
        return fameli;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    //
    public void setFameli(String fameli) {
        this.fameli = fameli;
    }

    @Override
    public String toString() {
        return "XmlTesterObject{" + "id=" + id + ", name='" + name + '\'' + ", fameli='" + fameli + '\'' + '}';
    }
}
