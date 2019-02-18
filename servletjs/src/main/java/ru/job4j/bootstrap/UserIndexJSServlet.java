package ru.job4j.bootstrap;

import ru.job4j.bootstrap.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserIndexJSServlet extends HttpServlet {
    private Dispatch disp = Dispatch.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User rsl = disp.submit("add", new User(Integer.valueOf(req.getParameter("id")),
                req.getParameter("surname"), req.getParameter("name"), req.getParameter("sex"), req.getParameter("desc")), new User());
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        System.out.println(rsl);
        resp.getWriter().write(String.format("{'id':'%s', 'surname':'%s', 'name':'%s', 'sex':'%s', 'desc':'%s'}",
                rsl.getId(), rsl.getSurname(), rsl.getName(), rsl.getSex(), rsl.getDesc()));
//        System.out.println(rsl);
//        req.setAttribute("u", rsl);
//        req.getRequestDispatcher("/WEB-INF/views/index.html").forward(req, resp);
    }
}
