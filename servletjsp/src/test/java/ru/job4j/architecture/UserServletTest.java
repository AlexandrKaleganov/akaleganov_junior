package ru.job4j.architecture;


import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServletTest {
    private void fulltestServlet(String command, Consumer<DbStore> test) {
        try {
            UserServlet servlet = new UserServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            when(req.getParameter("id")).thenReturn("0");
            when(req.getParameter("name")).thenReturn("Alex");
            when(req.getParameter("login")).thenReturn("alexmur07");
            when(req.getParameter("password")).thenReturn("pass12");
            when(req.getParameter("action")).thenReturn(command);
            HttpServletResponse res = mock(HttpServletResponse.class);
            servlet.doPost(req, res);
            test.accept(DbStore.getInstance());
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        } finally {
            DbStore.getInstance().deleteALL();
        }
    }

    @Test
    public void testAddUser() {
        this.fulltestServlet("add", (db) -> {
            assertThat(db.findAll().get(1).getLogin(),  Is.is("alexmur07"));
        });
    }

}