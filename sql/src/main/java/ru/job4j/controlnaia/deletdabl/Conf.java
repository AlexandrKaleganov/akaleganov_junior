package ru.job4j.controlnaia.deletdabl;


import ru.job4j.magnit.Conf;

class Config {
    private Conf conf = new ru.job4j.magnit.Conf("connectdablkill.properties");

    public String getCommand(String key) {
        return conf.getDataconfig(key);
    }
}
