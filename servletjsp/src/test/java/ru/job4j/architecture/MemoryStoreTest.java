package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.BiConsumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MemoryStoreTest {

    private void fulltest(BiConsumer<MemoryStore, Users> fank) {
        Users users = new Users("12", "sacha", "alexmur07");
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
}