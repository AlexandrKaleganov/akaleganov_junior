package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

import static java.lang.Thread.*;

@SuppressWarnings("WeakerAccess")
public class RectangleMove implements Runnable {
    private final Rectangle rect;

    public RectangleMove(Rectangle rect) {
        this.rect = rect;
    }


    private boolean isFlag(double coordinat, boolean flag) {
        flag = !(coordinat >= 300) && flag;
        flag = coordinat <= 0 || flag;
        return flag;
    }


    @Override
    public void run() {
        boolean flagX = true;
        boolean flagY = true;
        while (!Thread.interrupted()) {
            rect.setX(flagX ? rect.getX() + 1 : rect.getX() - 1);
            flagX = isFlag(rect.getX(), flagX);
            rect.setY(flagY ? rect.getY() + 1 : rect.getY() - 1);
            flagY = isFlag(rect.getY(), flagY);
            try {

                Thread.sleep(50);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
