package ru.job4j.forum;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class Turma {
    private static class Podozrevaimie implements Comparable<Podozrevaimie> {
        private final String name;
        private final Integer agressor;

        private Podozrevaimie(String name, int agressor) {
            this.name = name;
            this.agressor = agressor;
        }

        @Override
        public int compareTo(Podozrevaimie o) {
            return this.agressor.compareTo(o.agressor);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Podozrevaimie)) {
                return false;
            }

            Podozrevaimie that = (Podozrevaimie) o;

            if (name != null ? !name.equals(that.name) : that.name != null) {
                return false;
            }
            return agressor != null ? agressor.equals(that.agressor) : that.agressor == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (agressor != null ? agressor.hashCode() : 0);
            return result;
        }
    }

    private static class Zona {
        private Podozrevaimie[] podozrevaimie = new Podozrevaimie[10];
        private int i = 0;

        public void add(Podozrevaimie e) {
            this.podozrevaimie[i++] = e;
        }

        public Podozrevaimie sudiaDREDD() {
            Arrays.sort(this.podozrevaimie);
            return this.podozrevaimie[9];
        }
    }

    @Test
    public void testSud() {
        Podozrevaimie expected = new Podozrevaimie("Чикатило", 100500);
        Zona zona = new Zona();
        zona.add(new Podozrevaimie("Петя", 12));
        zona.add(new Podozrevaimie("Василий", 5));
        zona.add(new Podozrevaimie("Коля", 3));
        zona.add(new Podozrevaimie("Миша", 99));
        zona.add(new Podozrevaimie("Мелкий", 200));
        zona.add(new Podozrevaimie("БомжДядяКоля", 0));
        zona.add(new Podozrevaimie("Олень", 15));
        zona.add(new Podozrevaimie("Чикатило", 100500));
        zona.add(new Podozrevaimie("Проститутка", 77));
        zona.add(new Podozrevaimie("Хомячёк", 0));
        assertThat(zona.sudiaDREDD(), is(expected));
    }
}
