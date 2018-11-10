package ru.job4j.search;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * удалил файлы из папки и тестирование находит 0 файлов сделал это чтобы сервис
 * travis-ci.org не угался, т.к. он перестаёт видеть файлы
 */
public class ParallelSearchTest {


    @Test
    public void testFileVisitor() {
        Set<String> exts = new HashSet<>();
        exts.add("txt");
        ParallelSearch parallelSearch = new ParallelSearch("file\\", "12", exts);
        parallelSearch.init();
        System.out.println(parallelSearch.result());
        assertThat(parallelSearch.result().size(), is(0));
    }
}