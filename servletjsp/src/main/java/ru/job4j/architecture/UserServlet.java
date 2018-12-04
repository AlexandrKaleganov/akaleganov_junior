package ru.job4j.architecture;
/**
 * @author Alexander Kaleganov (alexmur07@mail.ru)
 * @version 8.0
 * @since 04-11-2018
 * сервлет метод doPost выводит все данные из бд
 * doGet выводит нашу бд и добавляет форму кнопок удаляения, добавления объектов
 * обращаю внимание на то, форма edit перекинет нас на новый сервлет который отвечает за редактирование формы
 * метод dopost удаляе элемент и обновляет страницу
 * <p>
 * PrintWriter writer = new PrintWriter(res.getOutputStream()); пришлось заменить на
 * PrintWriter writer = res.getWriter();  иначе не работала кодировка либо на сайте либо в среде , а так всё работает
 * d
 */

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class UserServlet extends HttpServlet {
    private final DispatchDiapason dispatsh = DispatchDiapason.getInstance();


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        PrintWriter writer = res.getWriter();
        StringBuilder table = new StringBuilder("<table border='1'>");
        table.append("<caption>список</caption>");
        table.append("<tr>\n"
                + "    <th>id</th>\n"
                + "    <th>NAME</th>\n"
                + "    <th>LOGIN</th>\n"
                + "    <th>CREATE_DATE</th>\n"
                + "    <th>EDIT</th>\n"
                + "    <th>DELETE</th>\n"
                + "   </tr>");
        Map<String, Users> map = (Map<String, Users>) this.dispatsh.access("findall", new Users()).get();
        map.forEach((k, u) ->
                table.append("<tr>"
                        + "<td>" + k + "</td>"
                        + "<td>" + u.getName() + "</td>"
                        + "<td>" + u.getLogin() + "</td>"
                        + "<td>" + u.getCreateDate() + "</td>"
                        + "<td>"
                        + "<form action='" + req.getContextPath() + "/edit?=method='get '>\n"
                        + "<input type='hidden' name='id' value='" + k + "'/>"
                        + "<input type='submit' value='изменить'>"
                        + "</form>"
                        + "</td>"
                        + "<td>"
                        + "<form action='" + req.getContextPath() + "/list' method='post'>\n"
                        + "<input type='hidden' name='action' value='delete'/>"
                        + "<input type='hidden' name='id' value='" + k + "'/>"
                        + "<input type='submit' value='удалить'>"
                        + "</form>"
                        + "</td>"
                        + "</tr>"));
        table.append("</table>");
        StringBuilder result = new StringBuilder("<!DOCTYPE html>\n"
                + "<html lang='en'>\n"
                + "<head>\n"
                + "    <meta charset='UTF-8'>\n"
                + "    <title></title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<br/>"
                + table
                + "</body>\n"
                + "</html>");
        writer.print(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();
        StringBuilder res = new StringBuilder();
        res.append(this.dispatsh.access(req.getParameter("action"),
                new Users(req.getParameter("id"), req.getParameter("name"), req.getParameter("login"))).get());
        writer.print(res);
        doGet(req, resp);
    }
}
