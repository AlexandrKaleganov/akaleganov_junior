package ru.job4j.search;

import java.io.*;
import java.util.Properties;

public class Configconnect {
    private final Properties properties = new Properties();
    private final String wayConfig = "configSQL.properties";

    public Configconnect() {
        this.importConfig();
    }



    /**
     * импорт конфигурации подключения к базе
     */
    private void importConfig() {
        InputStream fis = null;
        try {
            fis = getClass().getClassLoader().getResourceAsStream(wayConfig);
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

    public String getProperties(String key) {
        return properties.getProperty(key);
    }

}
