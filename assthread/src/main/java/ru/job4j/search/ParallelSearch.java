package ru.job4j.search;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static java.nio.file.Paths.*;

/**
 * поиск файлов в Блокирующую очередь paths я добавляю пути к файлам, которые подходят по расширению
 * список расширений содержится в сете, во первых не даёт добавить повторяющихся
 * во вторых очень быстро проверить соответствует наше расширение какому-ибудь  через contains
 * в потоке рид  мы каждый раз пытаемся  получить из очереди путь к файлу чтобы прочитать его
 * используем метод take() чтобы поток ждал своей очереди
 */
@ThreadSafe
public class ParallelSearch {
    private final String root;              //путь до папки откуда надо осуществлять поиск
    private final String text;              //текст задани
    private final Set<String> exts;        //расширения файлов в которых нужно делать поиск. решил сделать хешсет бустрее будет
    @GuardedBy("this")
    //что-то запутано получилось сделал так здесь
    // у меня храняться пути к файлам, подходящимим по расширению
    private final BlockingDeque<String> paths = new LinkedBlockingDeque<>();
    @GuardedBy("this")
    //здесь будут содержаться имена файлов в которых есть слова
    private final List<String> files = new ArrayList<>();

    public ParallelSearch(String root, String text, Set<String> exts) {
        this.root = root;          //путь до папки откуда надо осуществлять поиск
        this.text = text;          //текст задани
        this.exts = exts;          //расширения файлов в которых нужно делать поиск.
    }

    public void init() {
//поменял потоки местами
        Thread read = new Thread() {
            @Override
            public void run() {
                System.out.println("поток read стартанул");
                while (paths.size() > 0 && !Thread.currentThread().isInterrupted()) {
                    Path path = get("");
                    try {
                        path = get(paths.take());
                    } catch (InterruptedException e) {
                        System.out.println("исключение отловлено");
                    }
                    try {
                        BufferedReader rid = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), StandardCharsets.UTF_8));
                        String line;
                        while ((line = rid.readLine()) != null) {
                            if (line.contains(text)) {
                                files.add(path.getFileName().toString());
                                break;
                            }
                        }
                    } catch (IOException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " завершил свою работу");
            }
        };
        Thread search = new Thread() {
            @Override
            public void run() {
                System.out.println("Поток  sersh стартанул");
                //Набор опций при обходк
                Set<FileVisitOption> opts = Collections.emptySet();
                //глубина обхода дерева каталогов при макс будет максимальная глубина
                final int maxDeph = Integer.MAX_VALUE;
                try {
                    Files.walkFileTree(get(root), opts, maxDeph, new MyFileVisitor());
                } catch (IOException e) {
                    read.interrupt();
                }
                System.out.println(Thread.currentThread().getName() + " завершил свою работу");
                read.interrupt();
            }
        };
        read.start();
        search.start();

        try {
            search.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {

            read.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized List<String> result() {
        return this.files;
    }

    private class MyFileVisitor implements FileVisitor<Path> {
        //действия при посещении каталога
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("Каждый раз печатаю посещение каталога");
            System.out.format("Каталог: %s%n", dir); //печать каждого посещения каталога
            return FileVisitResult.CONTINUE;
        }

        //действия при посещении каждого файла
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            String exe = null;
            //ищем расширение в название файла , всякое бывает решил искать сплитом последнняя фраза после точки и будет расширением
            for (String temp : file.getFileName().toString().split("\\.")) {
                exe = temp;
            }
            if (exts.contains(exe)) {
                paths.offer(file.toString());
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            throw new InterruptedIOException("запрос на остановку потока");
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }
}