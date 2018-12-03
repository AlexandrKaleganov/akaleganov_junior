package ru.job4j.architecture;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class UserUpdateServlet extends HttpServlet {
    private final DispatchDiapason dispatsh = DispatchDiapason.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/thml; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");
        StringBuilder form = new StringBuilder();

        Optional users = this.dispatsh.access("findbyid", new Users(id, "", ""));
        if (users.get().getClass().toString().contains("Users")) {
            form.append("<table border='1'>\n"
                    + "<caption>список</caption>\n"
                    + "<tr>\n"
                    + "    <th>id</th>\n"
                    + "    <th>NAME</th>\n"
                    + "    <th>LOGIN</th>\n"
                    + "    <th>CREATE_DATE</th>\n"
                    + "    <th>EDIT</th>\n"
                    + "    <th>DELETE</th>\n"
                    + "   </tr>");
            form.append("<tr>"
                    + "<td>" + id  + "</td>"
                    + "<form action='" + req.getContextPath() + "/list=method='post'>\n"
                    + "<input type='hidden' name='id' value='" + id + "'/>"
                    + "<input type='submit'>"
                    + "</form> </tr></table>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/thml; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        StringBuilder res = new StringBuilder().append(this.dispatsh.access(req.getParameter("action"),
                new Users(req.getParameter("id"), req.getParameter("name"), req.getParameter("login"))).get());

                writer.print(res);
        doGet(req, resp);
    }
}
