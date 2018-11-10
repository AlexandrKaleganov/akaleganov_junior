package ru.job4j.magnit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Alexander Kaleganov
 * конфигурация подключения к базе
 *
 */
public class Conf {
    private final Properties dataconfig = new Properties();

    public Conf(String wayProperti) {
        this.loadProperti(wayProperti);
    }

    private void loadProperti(String wayProperti) {
        InputStream read = null;
        try {
            read = getClass().getClassLoader().getResourceAsStream(wayProperti);
            this.dataconfig.load(read);
        } catch (IOException e) {
            System.out.println("не удаётся загрузить настройки Properties");
            e.printStackTrace();
        }
    }

    public String getDataconfig(String key) {
        return this.dataconfig.getProperty(key);
    }
}
