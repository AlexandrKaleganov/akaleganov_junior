package ru.job4j.search;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

import static java.nio.file.Paths.get;

/**
 * поиск файлов по расширению и добавление путей файлов в список
 */
public class SearshFile {
    //путь до папки откуда надо осуществлять поиск
    private final String root;
    //расширение которое мы будем искать
    private final String exts;
    //глубина обхода дерева каталогов при макс будет максимальная глубина
    //можешь параметр добавить в конструктор и управлять им
    private final int maxDeph = Integer.MAX_VALUE;
    //сюда будем ложить пути к файлам с нуными  расширениями
    private final LinkedList<String> paths = new LinkedList<>();
    // сюда просаем пути которые не удалось прочитать
    private final ArrayList<String> failed = new ArrayList<>();

    public LinkedList<String> getPaths() {
        return paths;
    }

    public void startSearsh() {
        //Набор опций при обходк
        Set<FileVisitOption> opts = Collections.emptySet();
        try {
            Files.walkFileTree(get(root), opts, maxDeph, new MyFileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SearshFile(String root, String exts) {
        this.root = root;
        this.exts = exts;
    }

    private class MyFileVisitor implements FileVisitor<Path> {
        //действия при посещении каталога
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            //я думаю тебе не пригодится
            /*
            System.out.println("Каждый раз печатаю посещение каталога");
            System.out.format("Каталог: %s%n", dir); //печать каждого посещения каталога
            */
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

        //В случае неудачной попытки доступа к path, данный путь в виде строки добавляется в лист failed ему подобных,
// и программа продолжает обход, не посещая его поддиректории
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            failed.add(file.toString());
            return FileVisitResult.SKIP_SUBTREE;
        }

        //Завершают работу все вышеописанные методы, возвращая «результаты посещения» (FileVisitResult) объекта, которые принадлежат enum множеству и могут принимать следующие значения:
//
//CONTINUE — продолжает обход дерева;
//TERMINATE — заканчивает обход дерева;
//SKIP_SUBTREE — продолжает обход, без захода в данную директорию;
//SKIP_SIBLINGS — исключает из обхода «родственников» данного файла или директории;
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }
}
