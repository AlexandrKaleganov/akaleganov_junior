package ru.job4j.forum;


public class CyberForum {
    public int minimnum(int[] k) {
        int rsl = 0;
        int tem = k[0];
        for (int i = 0; i < k.length; i++) {
            if (k[i] < tem) {
                tem = k[i];
                rsl = i;
            }
        }
        return rsl;
    }

    public static void main(String[] args) {
        int[] mas = {5, 10, 2, 4, 6, 8, 9};
        System.out.println(new CyberForum().minimnum(mas));
    }
}
