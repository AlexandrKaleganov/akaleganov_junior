package ru.job4j.forum.zadacha;

public class PoliceVasia {
    private double rost = 8;
    private double vasiaRost = 2;
    private double policerost = 1;
    private int res = 0;

   PoliceVasia() {

    }

    public void shag() {
       while (rost > 0) {
           this.rost = rost + vasiaRost - policerost;
           this.policerost += 0.2;
           res++;
       }

    }


    public int getPolicerost() {
        return res;
    }
}
