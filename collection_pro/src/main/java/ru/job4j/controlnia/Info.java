package ru.job4j.controlnia;

public class Info {

    private int addElement;
    private int replaseElement;
    private int deleteElement;

    public Info(int i, int i1, int i2) {
        this.addElement = i;
        this.replaseElement = i1;
        this.deleteElement = i2;
    }

    Info() {

    }

    public int getAddElement() {
        return addElement;
    }

    public int getDeleteElement() {
        return deleteElement;
    }

    public int getReplaseElement() {
        return replaseElement;
    }

    public void setReplaseElement() {
        this.replaseElement++;
    }

    public void deleteTemp() {
        this.deleteElement++;
    }

    public void setDeleteElement(int size) {
        this.deleteElement = size - this.deleteElement;
    }

    public void setAddElement(int prevoisSize, int currentSize) {
        this.addElement = currentSize + this.deleteElement - prevoisSize;
    }

    @Override
    public String toString() {
        return "Статистика об изменении коллекции{" + "Количество добавленных элементов = " + addElement + ", количество изменённых элементов = " + replaseElement + ", количество удалённых элементов = " + deleteElement + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Info)) {
            return false;
        }

        Info info = (Info) o;

        if (replaseElement != info.replaseElement) {
            return false;
        }
        if (addElement != info.addElement) {
            return false;
        }
        return deleteElement == info.deleteElement;
    }

    @Override
    public int hashCode() {
        int result = replaseElement;
        result = 31 * result + addElement;
        result = 31 * result + deleteElement;
        return result;
    }
}
