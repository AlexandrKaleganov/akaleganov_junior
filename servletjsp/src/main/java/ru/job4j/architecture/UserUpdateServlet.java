package ru.job4j.architecture;
/**
 * сервлет обновления пользователя
 * решил что можно редактировать только имя и логин
 * id   и дату создания оставим неизменной
 */

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
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");
        StringBuilder form = new StringBuilder();

        Optional users = this.dispatsh.access("findbyid", new Users(id, "", ""));
        if (users.get().getClass().toString().contains("Users")) {
            Users users1 = (Users) users.get();
            form.append("<table border='1'>"
                    + "<tr><th>Редактирование пользователя, если поля очистить, то они останутся без изменения</th></tr> \n");

            form.append("<tr><td><form action='" + req.getContextPath() + "/list' method='post'>\n"
                    + "<input type='hidden' name='action' value='update'/>"
                    + "USER______ID : <input type='text' readonly='readonly' name='id' value='" + id + "'/>"
                    + "<br/>"
                    + "CREATE_DATE : <input type='text' readonly='readonly' name='CREATE_DATE' value='" + users1.getCreateDate() + "'/>"
                    + "<br/>"
                    + "Name : <input type='text' name='name' value='" + users1.getName() + "'/>"
                    + "<br/>"
                    + "Login : <input type='text' name='login'  value='" + users1.getLogin() + "'/>"
                    + "<br/>"
                    + "<input type='submit' value='изменить'>"
                    + "</form></tr></td></table>");
        } else {
            form.append("<table border='1'>\n"
                    + "<tr>\n"
                    + "<td>" + users.get().toString() + "</td>"
                    + "<td>"
                    + "<form action='" + req.getContextPath() + "/list' method='post'>\n"
                    + "<input type='submit'>"
                    + "</td>"
                    + "</tr>"
                    + "</form>"
                    + "</table>");
        }
        StringBuilder htmledit = new StringBuilder("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>edit</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<br/>"
                + form
                + "</body>\n"
                + "</html>");
        writer.print(htmledit);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        StringBuilder res = new StringBuilder(this.dispatsh.access(req.getParameter("action"),
                new Users(req.getParameter("id"), req.getParameter("name"), req.getParameter("login"))).get().toString());
        writer.print(res);
        doGet(req, resp);
    }


}
