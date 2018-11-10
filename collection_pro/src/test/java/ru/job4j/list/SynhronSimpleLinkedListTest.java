package ru.job4j.list;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SynhronSimpleLinkedListTest {
    @Test
    public void testIterator() {
        SynhronSimpleLinkedList<Integer> value = new SynhronSimpleLinkedList<>();

        for (int i = 0; i < 19; i++) {
            value.add(i);
        }
        Iterator iterator = value.iterator();

        Thread t1 = new Thread(() -> {
            Integer expected = 0;
            while (iterator.hasNext()) {
                //проверим что наша слабосправедливая копия акая же как и должна быть от 0 до 19
                Assert.assertThat(iterator.next(), is(expected));
                expected++;
            }
            //последний проход добавит личний эемент при выходе из цикла
            //по этому сделаем декремент;
            expected--;
            Assert.assertThat(expected, is(18));
        });
        Thread t2 = new Thread(() -> {
            for (int i = 100; i < 200; i++) {
                value.add(i);
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertThat(value.getSize(), is(119));
    }
}