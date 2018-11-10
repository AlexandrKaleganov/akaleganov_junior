package ru.job4j.forum;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class ForumPodschetmassTest {

    @Test
    public void getResult() {
        ForumPodschetmass poschet = new ForumPodschetmass();
        int[] a = new int[]{1, 5, 2, 4, 3};
        int result = poschet.getResult(a);
        Assert.assertThat(result, is(4));
    }

    @Test
    public void stringParsremuw() {
        ForumPodschetmass poschet = new ForumPodschetmass();
        String stroka = "932932492349249342376";
        String symbol = "9";
        String newsymbol = "5";
        String result = poschet.stringParsremuw(stroka, symbol, newsymbol);
        String expected = "932532452345245342376";
        Assert.assertThat(result, is(expected));
    }
}