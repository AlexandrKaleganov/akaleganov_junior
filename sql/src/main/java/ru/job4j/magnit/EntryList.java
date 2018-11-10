package ru.job4j.magnit;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Alexander Kaleganov
 * обек по которому будет создаваться xml документ
 * честно говоря не понял почему сразу не указать аннотацию атрибута,
 * думаю спецом так задание сделано чтобы мы научились конвертировать документ по схеме
 */
@XmlRootElement
public class EntryList {
    private List<Entry> entry;

    public EntryList() {
    }

    public EntryList(List<Entry> values) {
        this.entry = values;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    @Override
    public String toString() {
        return entry.toString();
    }

    @XmlRootElement
    public static class Entry {
        private int value;

        public Entry() {
        }
        public Entry(int value) {
            this.value = value;
        }

        //@XmlAttribute
        public int getValue() {
            return value;
        }
        @XmlElement

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entry{" + "value=" + value + '}';
        }
    }
}