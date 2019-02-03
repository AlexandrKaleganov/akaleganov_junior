package ru.job4j.architecture;


import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServletTest {
    private RequestDispatcher disp;
    private HttpServletResponse res;
    private HttpServletRequest req;

    @Before
    public void setup() {
        disp = mock(RequestDispatcher.class);
        req = mock(HttpServletRequest.class);
        res = mock(HttpServletResponse.class);
    }

    private static interface DryConsumer<E, R, S> {
        <E, R, S> void accept(E e, R r, S s);
    }

    private void fulltestServlet(String testCommand, DryConsumer<DbStore, Users, UserServlet> test) {
        try {
            UserServlet servlet = new UserServlet();
            when(this.req.getRequestDispatcher("/WEB-INF/views/index.jsp")).thenReturn(this.disp);
            when(this.req.getParameter("id")).thenReturn("0");
            when(this.req.getParameter("name")).thenReturn("Alex");
            when(this.req.getParameter("login")).thenReturn("alexmur07");
            when(this.req.getParameter("password")).thenReturn("pass12");
            when(this.req.getParameter("action")).thenReturn(testCommand);
            servlet.doPost(this.req, this.res);
            Store db = DbStore.getInstance();
            test.accept(DbStore.getInstance(), db.findAll().get(db.findAll().size() - 1), servlet);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        } finally {
            DbStore.getInstance().deleteALL();
        }
    }


    @Test
    public void testAddUser() {
        this.fulltestServlet("add", (db, user, servlet) -> {
            assertThat(db.findAll().get(1).getLogin(), Is.is("alexmur07"));
        });
    }

}