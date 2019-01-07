package ru.job4j.architecture;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


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
            req.setAttribute("list", DispatchDiapason.getInstance().access(req.getParameter("action")));
            req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("err", new Err(e.getMessage(), LocalDateTime.now()));
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
