package ru.job4j.forum.zadacha;

import org.junit.Test;

public class PoliceVasiaTest {

    @Test
    public void testshag() {
        PoliceVasia policeVasia = new PoliceVasia();

        policeVasia.shag();
        double res = policeVasia.getPolicerost();
        System.out.println(res);
    }

}