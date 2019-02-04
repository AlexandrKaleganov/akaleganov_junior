package ru.job4j.architecture;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.BiConsumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserListServletTest {
    private RequestDispatcher disp;
    private HttpServletResponse res;
    private HttpServletRequest req;

    @Before
    public void setup() {
        disp = mock(RequestDispatcher.class);
        req = mock(HttpServletRequest.class);
        res = mock(HttpServletResponse.class);
        when(this.req.getRequestDispatcher("/WEB-INF/views/list.jsp")).thenReturn(this.disp);
        when(this.req.getParameter("id")).thenReturn(DbStore.getInstance().findByLogin(new Users("root", "root")).getId());
        when(this.req.getParameter("name")).thenReturn("root");
        when(this.req.getParameter("login")).thenReturn("root");
        when(this.req.getParameter("CREATE_DATE")).thenReturn(null);
    }
    private void fulltestServlet(BiConsumer<DbStore, UserListServlet> test) {
        try {
            UserListServlet servlet = new UserListServlet();

            test.accept(DbStore.getInstance(), servlet);
        } finally {
            DbStore.getInstance().deleteALL();
        }
    }

    private void testdoPOST(UserListServlet servlet, String command) {
        when(this.req.getParameter("action")).thenReturn(command);
        try {
            servlet.doPost(this.req, this.res);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFilterUser() {
        this.fulltestServlet((db, servlet) -> {
            this.testdoPOST(servlet, "filter");
            System.out.println(req.getAttribute("list"));
        });
    }
}