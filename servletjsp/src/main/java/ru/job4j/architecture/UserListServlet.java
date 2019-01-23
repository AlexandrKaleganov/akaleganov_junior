package ru.job4j.architecture;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * сервлет который будет возвращать нам готовый список пользователей
 * решил сделать отдельно, т.к. можно с помощью данного сервлета осуществлять и поиск
 * к примеру волучить лист всех пользователей с похожим именем
 */
public class UserListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        try {
            req.setAttribute("list", DispatchDiapason.getInstance().access(req.getParameter("action"),
                  new Users(req.getParameter("id"), req.getParameter("name"), req.getParameter("login"), req.getParameter("create_date")),
                    new ArrayList<Users>()));
            req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("err", new Err(e.getMessage(), LocalDateTime.now()));
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
