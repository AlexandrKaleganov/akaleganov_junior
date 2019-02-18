package ru.job4j.bootstrap;

import ru.job4j.bootstrap.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class UserServletGet extends HttpServlet {
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
        writer.append(rsl);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
