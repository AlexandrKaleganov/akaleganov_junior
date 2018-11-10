package ru.job4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vacancy {
    private final String url;
    private final String title;
    private final String author;
    private final LocalDateTime date;
    private final String description;

    public Vacancy(String url, String title, String author, LocalDateTime date, String description) {
        this.url = url;
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
    }


    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vacancy)) {
            return false;
        }

        Vacancy vacancy = (Vacancy) o;

        if (!author.equals(vacancy.author)) {
            return false;
        }
        if (!url.equals(vacancy.url)) {
            return false;
        }
        return title.equals(vacancy.title);
    }

    @Override
    public int hashCode() {
        int result = author.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Vacancy{"
                + "url='" + url + '\''
                + ", title='" + title + '\''
                + ", author='" + author + '\''
                + ", date=" + date + '}';
    }
}
