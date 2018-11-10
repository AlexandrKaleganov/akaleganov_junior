package ru.job4j;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

/**
 * объект данного класса содержит побъект пропертис
 * и готовые скрипты
 * сделал конфиг через пропертис, т.к. так превычней наверно всем,
 * хотя изначально у меня хеш мапа содержала
 * настройки подключения к БД и скрипты создания таблиц
 */
public class Config {
    private final Properties dataconfig = new Properties();
    private final Properties addTable = new Properties();
    private final String wayScript = "scriptADDTABLE.properties";
    private final String wayConfig = "app.properties";

    public Config() {
        this.importConfig(dataconfig, wayConfig);
        this.importConfig(addTable, wayScript);
    }


    /**
     * импорт конфигурации подключения к базе
     */
    private void importConfig(Properties prop, String way) {
        InputStream fis = null;
        try {
            fis = Config.class.getClassLoader().getResourceAsStream(way);
            prop.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println(String.format("файл %s не найдет", way));
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
        return this.addTable.getProperty(key);
    }

    public String getDataconfig(String key) {
        return dataconfig.getProperty(key);
    }

}
