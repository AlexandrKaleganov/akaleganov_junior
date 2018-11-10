package ru.job4j.piterservice;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class WordIndexTest {

    @Test
    public void getIndex4Word() {
        String spisok = "петя вася мама тест петя тест томас";
        WordIndex wordIndex = new WordIndex();
        wordIndex.loadFile(spisok);
        Set<Integer> expected = new HashSet<Integer>();
        expected.add(spisok.indexOf("мама"));
        Set<Integer> result = wordIndex.getIndex4Word("мама");
        assertThat(result, Is.is(expected));
        System.out.println(wordIndex.getIndex4Word("петя"));
        System.out.println(wordIndex.getIndex4Word("вася"));
        System.out.println(wordIndex.getIndex4Word("мама"));
        System.out.println(wordIndex.getIndex4Word("тест"));
        System.out.println(wordIndex.getIndex4Word("томас"));
        Object nool = null;
        assertThat(wordIndex.getIndex4Word("sasha"), Is.is(nool));
    }
}