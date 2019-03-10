package ru.job4j.architecture;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.BiConsumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServletTest {
    private RequestDispatcher disp;
    private HttpServletResponse res;
    private HttpServletRequest req;
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(UserServletTest.class);

    @Before
    public void setup() throws IOException {
        disp = mock(RequestDispatcher.class);
        req = mock(HttpServletRequest.class);
        res = mock(HttpServletResponse.class);
        when(this.req.getRequestDispatcher("/WEB-INF/views/index.jsp")).thenReturn(this.disp);
        when(this.req.getParameter("id")).thenReturn("0");
        when(this.req.getParameter("name")).thenReturn("Alex");
        when(this.req.getParameter("mail")).thenReturn("alexmur07");
        when(this.req.getParameter("password")).thenReturn("pass12");
        when(this.req.getParameter("country")).thenReturn("Country");
        when(this.req.getParameter("city")).thenReturn("City");
        when(this.req.getReader()).thenReturn(mock(BufferedReader.class));


    }

    private void fulltestServlet(BiConsumer<DbStore, UserServlet> test) {
        DbStore dbStore = new DbStore(this.init());
        try {
            UserServlet servlet = new UserServlet();
            test.accept(dbStore, servlet);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            dbStore.deleteALL();
        }
    }
    private BasicDataSource init() {
        BasicDataSource source = new BasicDataSource();
        try {
            Properties settings = new Properties();
            try (InputStream in = UserServletTest.class.getClassLoader().getResourceAsStream("gradle.properties")) {
                settings.load(in);
            }
            source.setDriverClassName(settings.getProperty("db.driver"));
            source.setUrl(settings.getProperty("db.host"));
            source.setUsername(settings.getProperty("db.login"));
            source.setMinIdle(5);
            source.setMaxIdle(10);
            source.setMaxOpenPreparedStatements(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }
    private void testdoPOST(UserServlet servlet, String command) {
        when(this.req.getParameter("action")).thenReturn(command);
        try {
            servlet.doPost(this.req, this.res);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddUser() {
        this.fulltestServlet((db, servlet) -> {
            this.testdoPOST(servlet, "add");
            assertThat(db.findByMail(
                    new Users("roo",  "name", "alexmur07", "alexmur07", "Russia", "Novosibirsk")).getMail(), Is.is("alexmur07"));
        });
    }

    @Test
    public void testUpdateUser() {
        this.fulltestServlet((db, servlet) -> {
            this.testdoPOST(servlet, "add");
            when(this.req.getParameter("id")).thenReturn(db.findAll().get(0).getId());
            when(this.req.getParameter("mail")).thenReturn("test");
            this.testdoPOST(servlet, "update");
            assertThat(db.findByMail(new Users("roo", "roo", "test", "test",  "Russia", "Novosibirsk")).getMail(), is("test"));
        });
    }

    @Test
    public void testDeleteUser() {
        this.fulltestServlet((db, servlet) -> {
            when(this.req.getParameter("id")).thenReturn(db.findByMail(new Users("roo",
                    "name", "root", "root",  "Russia", "Novosibirsk")).getId());
            this.testdoPOST(servlet, "delete");
            assertThat(db.findAll().size(), is(0));
        });
    }
}