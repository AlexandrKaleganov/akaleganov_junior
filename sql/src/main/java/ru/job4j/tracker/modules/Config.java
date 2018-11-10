package ru.job4j.tracker.modules;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

/**
 * объект данного класса содержит побъект пропертис
 * и готовые скрипты
 * сделал конфиг через пропертис, т.к. так превычней наверно всем, хотя изначально у меня хеш мапа содержала
 * настройки подключения к БД и скрипты создания таблиц
 */
public class Config {
    private final Properties properties = new Properties();
    private final HashMap<String, String> dataconfig = new HashMap<>();
    private final String wayScript = "createTable.sql";
    private final String wayConfig = "config.properties";

    public Config() {
        this.importScript();
        this.importConfig();
    }

    /**
     * импорт скриптов из файла
     * постараюсь сделать его универсальным,
     * !строки в которых есть ";"   - бует удалён последний символ чтобы возвращать готовую команду
     * !строки в которых содержаться "--" будут ключами,
     * все остальные строки значения по ключам
     *
     * @param
     */
    private void importScript() {
        StringBuilder key = new StringBuilder();
        try {
            new BufferedReader(new FileReader(Objects.requireNonNull(getClass().getClassLoader().getResource(wayScript)).getFile()))
                    .lines()
                    .map(l ->
                            l.contains(";") ? l.substring(0, l.length() - 1) : l
                    ).forEach(l -> {
                if (l.contains("--")) {
                    key.setLength(0);
                    key.append(l);
                    this.dataconfig.put(key.toString(), "");
                } else {
                    this.dataconfig.replace(key.toString(), this.dataconfig.get(key.toString()) + l);
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Файл скриптов не найден");
        }
    }

    /**
     * импорт конфигурации подключения к базе
     */
    private void importConfig() {
        InputStream fis = null;
        try {
            fis = Config.class.getClassLoader().getResourceAsStream(wayConfig);
            properties.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println("файл конфигурации не найден");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * получение комманд из файлов конфигурации
     *
     * @param key
     * @return
     */
    public String getScript(String key) {
        return this.dataconfig.get(key);
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }

    public HashMap<String, String> getDataconfig() {
        return dataconfig;
    }
}
