package ru.job4j.forum;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeleteProbelTest {
    public static void main(String[] args) {

    }
    @Test
    public void formatStr() {
        System.out.println(new DeleteProbel().formatStr(" asdsa asd das asdas w"));
        System.out.println(new DeleteProbel().formatStr("asdsa asd das asdas w"));
        System.out.println(new DeleteProbel().formatStr("n asdsa asd das asdas w"));
        System.out.println(new DeleteProbel().formatStr(" n asdsa asd das asdas w"));
    }
}