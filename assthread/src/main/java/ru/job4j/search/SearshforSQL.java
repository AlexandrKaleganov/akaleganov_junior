package ru.job4j.search;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static java.nio.file.Paths.get;

public class SearshforSQL {
    //путь до папки откуда надо осуществлять поиск
    private final String root;
    //расширение которое мы будем искать
    private final String exts;
    //глубина обхода дерева каталогов при макс будет максимальная глубина
    //можешь параметр добавить в конструктор и управлять им
    private final int maxDeph = Integer.MAX_VALUE;
    //конфиг подключение к БД
    Configconnect config = new Configconnect();
    Connection connection;
    // сюда просаем пути которые не удалось прочитать
    private final ArrayList<String> failed = new ArrayList<>();

    public void getPaths() {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM pathtable");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startSearsh() {
        //Набор опций при обходк
        Set<FileVisitOption> opts = Collections.emptySet();
        try {
            Files.walkFileTree(get(root), opts, maxDeph, new SearshforSQL.MyFileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SearshforSQL(String root, String exts) {
        this.root = root;
        this.exts = exts;
        try {
            connection = DriverManager.getConnection(config.getProperties("db.host"), config.getProperties("db.login"), config.getProperties("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            try {
                PreparedStatement st = connection.prepareStatement("INSERT into pathtable(path) VALUES (?)");

                String exe = null;
                //ищем расширение в название файла , всякое бывает решил искать сплитом последнняя фраза после точки и будет расширением
                for (String temp : file.getFileName().toString().split("\\.")) {
                    exe = temp;
                }
                if (exts.contains(exe)) {
                    st.setString(1, file.toString());
                    st.executeUpdate();
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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