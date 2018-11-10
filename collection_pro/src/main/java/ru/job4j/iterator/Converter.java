package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * итератор итераторов
 */
public class Converter {

    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> iteratorKrug = it.hasNext() ? it.next() : null;

            @Override
            public boolean hasNext() {
                boolean result = false;
                if (iteratorKrug.hasNext()) {
                    result = true;
                } else if (it.hasNext()) {
                    iteratorKrug = it.next();
                    return hasNext();
                } else {
                    result = false;
                }
                return result;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    return iteratorKrug.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}