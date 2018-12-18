package ru.job4j;
/**
 * само тело парсера
 * в конфиге  scriptADDTABLE.properties  содержатся скрипты для создания таблиц
 * автокоммит отключил
 * после коннекта к базе будут созданы таблицы
 * решил сделать так -
 */

import java.sql.*;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Alexander Kaleganov
 * @since 08-11-2018
 * грабер вакансий с сайта sql.ru
 * описание работы программы вы найдёте в файле package-info.java
 */
public class Parser implements AutoCloseable {
    private final String urlStar = "http://www.sql.ru/forum/job-offers";
    private final Config config;
    private Connection conn;
    private static final Logger LOGGER = Logger.getLogger(Parser.class);
    private Timestamp dateActual;
    private int maxSize;
    private final Month month;

    Parser(Config config) throws SQLException, IOException {
        this.config = config;                    //конфиг
        this.month = new Month();                //объект для парсинга даты
        this.init();                             //динамическая подгрузка драйверов
        this.connectTorData();                   //коннектимся к базе
        this.conn.setAutoCommit(false);          //отрубаем автокоммит
        this.createTable();                      //добавляем таблицы в случае их отсутствия
        this.maxSize = this.maxSize();           //будет хранить максимальное количество страниц
        this.dateActual = newDateactual();       //будет хранить самую свежую дату из базы
    }


    Parser(Connection connection) throws SQLException, IOException {
        this.config = new Config();                    //конфиг
        this.month = new Month();                //объект для парсинга даты
        this.init();                             //динамическая подгрузка драйверов
        this.conn = connection;                   //коннектимся к базе
        this.conn.setAutoCommit(false);          //отрубаем автокоммит
        this.createTable();                      //добавляем таблицы в случае их отсутствия
        this.maxSize = this.maxSize();           //будет хранить максимальное количество страниц
        this.dateActual = newDateactual();       //будет хранить самую свежую дату из базы
    }

    private void init() {
        try {
            Class.forName(config.getDataconfig("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    //подключение к базе данных
    private boolean connectTorData() throws SQLException {

        this.conn = DriverManager.getConnection(config.getDataconfig("jdbc.url"),
                config.getDataconfig("jdbc.username"), config.getDataconfig("jdbc.password"));
        return conn != null;
    }

    /**
     * метод добавления таблиц в случае их отсутствия
     */
    private void createTable() {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(config.getScript("add.title"));
            st.executeUpdate(config.getScript("add.author"));
            st.executeUpdate(config.getScript("add.description"));
            st.executeUpdate(config.getScript("add.datatable"));
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(e1.getMessage(), e1);
            }
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * получить сет тегов из блок кода (таблица с тегами)
     * получаем блок <tbody>
     * получаем лист элементов <tr>
     * получаем из списка с блоками элемета tr первый попавшийся блок кода tr далее через стрим обходим все страницы
     * мы не будем добавлять в базу закрытые темя, и темы с пометкой важно
     * также буду просто получать самое первое это ссылку на тему и делать запрос в бд
     * если этой ссылки в бд нету то будем добавлять
     *
     * @throws IOException
     */
    public void loadFile() {
        int count = 1;
        boolean[] stop = {false};
        while (!stop[0] && count <= maxSize) {
            Document document = null;
            try {
                document = Jsoup.connect(urlStar + "/" + count).get();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
            Element element = document.select("table[class=forumTable]").get(0);
            Elements listTR = element.select("tr");
            listTR.stream().map(e -> e.select("td"))
                    .filter(e -> e.size() > 0)
                    .filter(e -> !e.get(1).text().contains("Важно") && !e.get(1).text().contains("[закрыт]"))
                    .filter(e -> {
                        if (this.dateActual != null) {
                            Timestamp eTime = Timestamp.valueOf(this.month.getParserDate(e.get(5).text()));
                            if (eTime.getTime() <= this.dateActual.getTime()) {
                                stop[0] = true;
                            }
                        }
                        return 1 > this.isInfodatatable(e.get(1).select("a").get(0).attr("href"));
                    })
                    .map(e -> {
                        ArrayList<String> rsl = null;
                        try {
                            rsl = descDate(e.get(1).select("a").get(0).attr("href"));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        return new Vacancy(e.get(1).select("a").get(0).attr("href"),        // создаём объект который будем добавлять в базу
                                e.get(1).text().split("\\[")[0].split("\\(1")[0], e.get(2).text(),
                                this.month.getParserDate(rsl.get(0)), rsl.get(1));
                    })
                    .forEach(this::addtoData);
            count++;
        }
    }

    /**
     * получение из базы максимальной даты
     *
     * @return
     */
    private Timestamp newDateactual() {
        Timestamp timestamp = null;
        try (Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery("select max(d.date) from datatable as d")) {
                rs.next();
                timestamp = rs.getTimestamp(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return timestamp;
    }


    /**
     * метод получения ОПИСАНИЯ ВАКАНСИИ  И ТОЧНОЙ ДАТЫ СОЗДАНИЯ
     * т.к. дата создания темы равна дате создания первого поста
     * получаем первый пост
     * получаем сразу блок кода с датой и вырезаем чисто дату
     *
     * @return
     */
    private ArrayList<String> descDate(String urlTitle) throws IOException {
        ArrayList<String> rsl = new ArrayList<>();
        Document document = Jsoup.connect(urlTitle).get();
        Element table = document.select("table[class=msgTable]").get(0);
        table.select("td[colspan=2][class=msgFooter]").stream()
                .map(e -> {
                    String[] mas = e.text().split("\\[");
                    return mas[0].substring(0, mas[0].length() - 4);
                }).forEach(rsl::add);

        Element desc = table.select("tr").get(1);
        Element descrsl = desc.select("td").get(1);
        rsl.add(descrsl.text());
        return rsl;
    }

    /**
     * напишем метод  добавления данных в базу, перед добавлением я делаю запрос в таблицу авторов
     * а есть ли автор? и если есть то я получу его id
     * и просто не буду добавлять его в таблицу авторов,
     * а его id запихаю в основную таблицу datatable,
     * также проделаю и с темой и с описанием вакансии
     * онсновная таблица datatable держит связь многие ко многим
     */
    public void addtoData(Vacancy vacancyElement) {
        int addtoAuthor = this.isInfoauthor(vacancyElement.getAuthor());
        int addtoTitle = this.isInfotitle(vacancyElement.getTitle());
        int addtoDesc = this.isInfodesc(vacancyElement.getDescription());
        try (PreparedStatement staddtoData = conn.prepareStatement("insert into datatable(url, title_id, author_id, description_id, date) values(?, ?, ?, ?, ?)")) {
            if (addtoAuthor < 1) {
                try (PreparedStatement staddtoAutor = conn.prepareStatement("INSERT INTO author(author) values (?)", Statement.RETURN_GENERATED_KEYS)) {
                    staddtoAutor.setString(1, vacancyElement.getAuthor());
                    staddtoAutor.executeUpdate();
                    try (ResultSet rs = staddtoAutor.getGeneratedKeys()) {
                        rs.next();
                        addtoAuthor = rs.getInt(1);
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (addtoTitle < 1) {
                try (PreparedStatement staddtoTitle = conn.prepareStatement("INSERT INTO title(title) values (?)", Statement.RETURN_GENERATED_KEYS)) {
                    staddtoTitle.setString(1, vacancyElement.getTitle());
                    staddtoTitle.executeUpdate();
                    try (ResultSet rs = staddtoTitle.getGeneratedKeys()) {
                        rs.next();
                        addtoTitle = rs.getInt(1);
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (addtoDesc < 1) {
                try (PreparedStatement staddtoDesc = conn.prepareStatement("INSERT INTO description(description) values (?)", Statement.RETURN_GENERATED_KEYS)) {
                    staddtoDesc.setString(1, vacancyElement.getDescription());
                    staddtoDesc.executeUpdate();
                    try (ResultSet rs = staddtoDesc.getGeneratedKeys()) {
                        rs.next();
                        addtoDesc = rs.getInt(1);
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            staddtoData.setString(1, vacancyElement.getUrl());
            staddtoData.setInt(2, addtoTitle);
            staddtoData.setInt(3, addtoAuthor);
            staddtoData.setInt(4, addtoDesc);
            staddtoData.setTimestamp(5, Timestamp.valueOf(vacancyElement.getDate()));
            staddtoData.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(e1.getMessage(), e);
            }
            LOGGER.error(e.getMessage(), e);
        }
    }


    //получим максимальное количество страниц
    public int maxSize() throws IOException {
        Document document = Jsoup.connect(urlStar).get();
        Element element = document.select("table[class=sort_options]").get(1);
        Element teg = element.select("td").get(0);
        Elements a = teg.select("a");
        return Integer.valueOf(a.get(a.size() - 1).text());

    }

    //закрытие соединения
    @Override
    public void close() throws Exception {
        if (!this.conn.isClosed()) {
            this.conn.close();
        }
    }

    /**
     * метод выодит статистику по базе данных
     * как доказательство смысла нормализации базы
     * как и ожидал даже описание вакансий часто совпадают
     * зачастую просто копируют объявления
     */
    public void dataStatistic() {
        try (Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery("select * from \n"
                    + "(select count(id) from datatable) as data\n"
                    + "inner join (select count(a.id) from author as a right outer join (select dat.author_id, count(id) from datatable as dat group by dat.author_id) as res on res.author_id = a.id where res.count > 1) as aut on aut.count!=data.count\n"
                    + "inner join  (select count(t.id) from title as t right outer join (select dat.title_id, count(id) from datatable as dat group by dat.title_id) as res on res.title_id = t.id where res.count > 1) as tit on tit.count != data.count\n"
                    + "inner join (select count(des.id) from description as des right outer join (select dat.description_id, count(id) from datatable as dat group by dat.description_id) as res on res.description_id = des.id where res.count > 1) as descr on descr.count != data.count")) {
                rs.next();
                LOGGER.info(String.format("\nKolichestvo tem vsego : %s \nKolichestvo povtor author : %s  \nKolichestvo povtor title : %s \nKolichestvo povtor opisanie vakansii(desc) : %s",
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * данные методы будут проверять есть ли данные  в базе чтобы не добавлять дубли
     * если в основной базе есть url то мы вообще не брать данную тему
     *
     * @return
     */
    public int isInfodatatable(String url) {
        int rsl = 0;
        try (PreparedStatement statement = conn.prepareStatement("SELECT ID FROM datatable WHERE url = ?")) {
            statement.setString(1, url);
            statement.executeQuery();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    rsl = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rsl;
    }

    private int isInfotitle(String title) {
        int rsl = 0;
        try (PreparedStatement statement = conn.prepareStatement("SELECT ID FROM title WHERE title = ?")) {
            statement.setString(1, title);
            statement.executeQuery();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    rsl = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rsl;
    }

    private int isInfoauthor(String author) {
        int rsl = 0;
        try (PreparedStatement statement = conn.prepareStatement("SELECT ID FROM author WHERE author = ?")) {
            statement.setString(1, author);
            statement.executeQuery();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    rsl = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rsl;
    }

    private int isInfodesc(String desc) {
        int rsl = 0;
        try (PreparedStatement statement = conn.prepareStatement("SELECT ID FROM description WHERE description = ?")) {
            statement.setString(1, desc);
            statement.executeQuery();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    rsl = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rsl;
    }

}
