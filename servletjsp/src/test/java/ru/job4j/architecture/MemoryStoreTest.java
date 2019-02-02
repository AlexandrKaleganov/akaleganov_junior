package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BiConsumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MemoryStoreTest {

    private void fulltest(BiConsumer<MemoryStore, Users> fank) {
        Users users = new Users("12", "sacha", "alexmur07", "pass");
        MemoryStore store = MemoryStore.getInstance();
        Users expected = store.add(users);
        try {
            fank.accept(store, expected);
        } finally {
            store.deleteALL();
        }
    }

    @Test
    public void add() {
        this.fulltest((stor, exp) -> {
            assertThat(stor.findById(exp), is(exp));
        });
    }

    @Test
    public void update() {
        this.fulltest((stor, exp) -> {
            Users ex = stor.update(new Users(exp.getId(), "expected", "ale99"));
            assertThat(ex.getName(), is("expected"));
        });
    }

    @Test
    public void delete() {
        this.fulltest((stor, exp) -> {
            stor.delete(exp);
            assertThat(stor.findById(exp), is(new Users()));
        });
    }

    @Test
    public void findAll() {
        this.fulltest((store, exp) -> {
            assertThat(store.findAll().get(0), is(exp));
        });
    }

    @Test
    public void findById() {
        this.fulltest(((memoryStore, users) -> {
            assertThat(memoryStore.findById(users), is(users));
        }));
    }

    @Test
    public void deleteALL() {
        this.fulltest(((memoryStore, users) -> {
            assertThat(memoryStore.deleteALL().size(), is(0));
        }));
    }

    @Test
    public void filterTest() {
        this.fulltest(((memoryStore, users) -> {
            Assert.assertThat(memoryStore.filter(new Users("0", "", "alex",
                    LocalDateTime.parse(users.getCreateDate().toLocalDate().toString() + " 00:00",
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))).get(1).getId(), Is.is(users.getId()));
        }));
    }

    @Test
    public void isCredentional() {
        this.fulltest(((memoryStore, users) -> {
            Assert.assertThat(memoryStore.isCredentional(new Users("0", "", "alexmur07", "pass")), Is.is(true));
            Assert.assertThat(memoryStore.isCredentional(new Users("0", "", "alexmu07", "pass")), Is.is(false));
            Assert.assertThat(memoryStore.isCredentional(new Users("0", "", "alexmur07", "pas")), Is.is(false));
        }));
    }
}