package ru.job4j.set;

/**
 * будет содержать хеш код и ссылку на объект
 */
@SuppressWarnings("ALL")
public class Bucket {
    private final ObjectAndLink newObject;
    private int heshcod = hashCode();

    Bucket(ObjectAndLink newObject) {
        this.newObject = newObject;
        this.heshcod = newObject.hashCode();
    }


    public int getHeshcod() {
        return heshcod;
    }

    public ObjectAndLink getLinkObjct() {
        return newObject;
    }

    @Override
    public String toString() {
        return "Bucket{" + "newObject=" + newObject + ", heshcod=" + heshcod + '}';
    }
}
