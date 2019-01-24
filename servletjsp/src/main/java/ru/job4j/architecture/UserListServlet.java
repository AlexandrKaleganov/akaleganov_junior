package ru.job4j.architecture;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * сервлет который будет возвращать нам готовый список пользователей
 * решил сделать отдельно, т.к. можно с помощью данного сервлета осуществлять и поиск
 * к примеру волучить лист всех пользователей с похожим именем
 */
public class UserListServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(UserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        try {
            Users users = new Users();
            users.setId(req.getParameter("id"));
            users.setName(req.getParameter("name"));
            users.setLogin(req.getParameter("login"));
            System.out.println(req.getParameter("CREATE_DATE"));
            if (req.getParameter("CREATE_DATE") != null) {
                users.setCreateDate(LocalDateTime.parse(req.getParameter("CREATE_DATE") + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
            req.setAttribute("list", DispatchDiapason.getInstance().access(req.getParameter("action"),
                    users, new ArrayList<Users>()));
            req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            req.setAttribute("err", new Err(e.getMessage(), LocalDateTime.now()));
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
