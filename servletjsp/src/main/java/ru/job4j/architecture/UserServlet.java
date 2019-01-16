package ru.job4j.architecture;
/**
 * @author Alexander Kaleganov (alexmur07@mail.ru)
 * @version 8.0
 * @since 04-11-2018
 * сервлет делает редерект на JSP  /WEB-INF/views/index.jsp и решает за логику
 */


import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


public class UserServlet extends HttpServlet {
    private final DispatchDiapason dispatsh = DispatchDiapason.getInstance();
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(UserServlet.class);

    /**
     * проинициализируем нашь сервлет
     * при инициализации добавим сразу пользователя админа
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        try {
            DispatchDiapason.getInstance().access("add", new Users("", "root", "root", "root"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * закомментил за ненадобностью, т.к. за интерфейс теперь отвечает jsp
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String link = req.getParameter("link");
        if (link != null) {
            req.getRequestDispatcher(String.format("/WEB-INF/views/%s.jsp", req.getParameter("link"))).forward(req, resp);
        } else {
            req.getRequestDispatcher(String.format("/WEB-INF/views/%s.jsp", "index")).forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        try {
            req.setAttribute("message", this.dispatsh.access(req.getParameter("action"),
                    new Users(req.getParameter("id"), req.getParameter("name"), req.getParameter("login"), req.getParameter("password"))));
            req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("err", new Err(e.getMessage(), LocalDateTime.now()));
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
