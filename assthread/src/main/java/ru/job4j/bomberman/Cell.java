package ru.job4j.bomberman;

/**
 * @author Alexandr Kaleganov
 * 02.09.2018
 * клас отвечает за создания объекта позиция, также переопределим экуалс  для тестирования
 * объект данного класса будет находится у куки монстров и бомберменов
 */
public class Cell {
    private int x;
    private int y;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Позиция по координатам X = " + this.x + " Y = " + this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cell)) {
            return false;
        }

        Cell cell = (Cell) o;

        if (x != cell.x) {
            return false;
        }
        return y == cell.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
