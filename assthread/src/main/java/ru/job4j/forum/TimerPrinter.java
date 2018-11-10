package ru.job4j.forum;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * таймер принтер
 */
public class TimerPrinter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Время каждый раз " + new Date());
        Toolkit.getDefaultToolkit().beep();
    }
}
