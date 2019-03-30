package ru.job4j.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.bootstrap.model.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserIndexJSServletTest {
    private Dispatch dispatch;
    private UserIndexJSServlet servlet;
    private HttpServletRequest req;
    private HttpServletResponse res;

    @Before
    public void init() {
        this.dispatch = Dispatch.getINSTANCE();
        this.servlet = new UserIndexJSServlet();
        this.req = mock(HttpServletRequest.class);
        this.res = mock(HttpServletResponse.class);
    }

    /**
     * проверяем что пользователь добавился в базу
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void doPost() throws IOException, ServletException {
        when(req.getReader()).thenReturn(
                new BufferedReader(
                        new StringReader("{\"id\":\"37\",\"surname\":\"Калег\",\"name\":\"саша\",\"sex\":\"М\",\"desc\":\"sascz\"}")));
        ObjectMapper mapper = new ObjectMapper();
        when(res.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        servlet.doPost(req, res);
        User expected = this.dispatch.submit("findall", new User(), new ConcurrentHashMap<Integer, User>()).get(1803610024);
        Assert.assertThat(expected.getName(), Is.is("саша"));
    }
}