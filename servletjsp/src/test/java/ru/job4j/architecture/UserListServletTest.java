package ru.job4j.architecture;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;

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
        when(this.req.getParameter("id")).thenReturn(DbStore.getInstance().findByMail(new Users("",
                "name", "root", "root", "Russia", "Novosibirsk")).getId());
        when(this.req.getParameter("name")).thenReturn("root");
        when(this.req.getParameter("mail")).thenReturn("roott");
        when(this.req.getParameter("password")).thenReturn("root");
        when(this.req.getParameter("country")).thenReturn("country");
        when(this.req.getParameter("city")).thenReturn("city");

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
}