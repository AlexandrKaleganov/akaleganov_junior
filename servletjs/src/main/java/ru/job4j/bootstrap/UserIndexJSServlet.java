package ru.job4j.bootstrap;

import ru.job4j.bootstrap.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class UserIndexJSServlet extends HttpServlet {
    private Dispatch disp = Dispatch.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/json");
            User user = new User(1, "fame", "name", "М", "desc");
            PrintWriter writer = resp.getWriter();
            StringBuilder rsl = new StringBuilder("[");
            disp.submit("add", user, user);
            ConcurrentHashMap<Integer, User> temp = disp.submit("findall", new User(), new ConcurrentHashMap<Integer, User>());
            temp.forEach((k, v) ->
                    rsl.append(String.format("{'id':'%s', 'surname':'%s', 'name':'%s', 'sex':'%s', 'desc':'%s'}",
                            v.getId(), v.getSurname(), v.getName(), v.getSex(), v.getDesc())));
            rsl.append("]");
            System.out.println(rsl);
            writer.append(rsl);
            writer.flush();

        req.setAttribute("js", rsl);
        req.getRequestDispatcher("/WEB-INF/views/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("desc"));
        BufferedReader reader = req.getReader();
        System.out.println(reader);
        doGet(req, resp);
    }
}
