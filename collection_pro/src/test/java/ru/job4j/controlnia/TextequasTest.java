package ru.job4j.controlnia;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * тестирование класса в первом методе был косяк, поправил.
 */
public class TextequasTest {

    @Test
    public void textEqualsTwo() {
        String one = "мама";
        String two = "амам";
        String twoFalse = "амаа";
        Textequas textequas = new Textequas();
        assertThat((textequas.textEqualsOne(one, two)), Is.is(true));
        assertThat((textequas.textEqualsOne(one, twoFalse)), Is.is(false));
        assertThat((textequas.textEqualsTwo(one, two)), Is.is(true));
        assertThat((textequas.textEqualsTwo(one, twoFalse)), Is.is(false));
        assertThat((textequas.textEqualsTry(one, two)), Is.is(true));
        assertThat((textequas.textEqualsTry(one, twoFalse)), Is.is(false));
    }
}