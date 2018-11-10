package ru.job4j.forum;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MyProjectTest {

    @Test
    public void massToArrArifmeticsr() {
        MyProject myProject = new MyProject();
        String[] strok = new String[]{"mama", "mila", "ramu"};  //у тебя в задании воодишь массив

        int expected = myProject.massToArrArifmeticsr(strok);
        for (int i = 0; i < 100500; i++) {
            assertThat(myProject.massToArrArifmeticsr(strok), is(expected));
        }

    }
}