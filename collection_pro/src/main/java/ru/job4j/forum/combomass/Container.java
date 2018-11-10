package ru.job4j.forum.combomass;


/**
 * Класс Будет содержать в себе value - позицию элемента
 * и флаг, флаг будет означать что перед элементом отсутствуютобъекты с такими же значениями value
 * при добавлении в список естественно значения все будут отличаться, по этому флаг будет true
 */
public class Container {

    private int value;
    private boolean flag;

    Container(int value) {
        this.value = value;
        flag = true;
    }

    public int getValue() {
        return value;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void incrementValue() {
        this.value++;
        this.flag = false;
    }

    public void zeroValue() {
        this.value = 0;
        this.flag = false;
    }

}
