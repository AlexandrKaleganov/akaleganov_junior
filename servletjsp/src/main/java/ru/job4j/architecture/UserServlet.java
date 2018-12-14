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
 * в методе пост у меня происходит редикрект с параметрами на страницу лист и там вся информация обрабатывается
 */


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    private final DispatchDiapason dispatsh = DispatchDiapason.getInstance();

/**
 * закомментил за ненадобностью, т.к. за интерфейс теперь отвечает jsp
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        PrintWriter writer = res.getWriter();
        String send = req.getParameter("system_message");
        StringBuilder console = new StringBuilder(
                "<table border='1'>"
                        + "<tr><th>ConsoleOutsend</th></tr>");
        if (send != null) {
            console.append("<tr> <td>" + send + "</td></tr>");
        }
        console.append("</table>");
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
        List<Users> list = (List<Users>) this.dispatsh.access("findall", new Users()).get();
        list.forEach(u ->
                table.append("<tr>"
                        + "<td>" + u.getId() + "</td>"
                        + "<td>" + u.getName() + "</td>"
                        + "<td>" + u.getLogin() + "</td>"
                        + "<td>" + u.getCreateDate() + "</td>"
                        + "<td>"
                        + "<form action='" + req.getContextPath() + "/edit?=method='get'>\n"
                        + "<input type='hidden' name='id' value='" + u.getId() + "'/>"
                        + "<input type='hidden' name='name' value='" + u.getName() + "'/>"
                        + "<input type='hidden' name='login' value='" + u.getLogin() + "'/>"

                        + "<input type='submit' value='изменить'>"
                        + "</form>"
                        + "</td>"
                        + "<td>"
                        + "<form action='" + req.getContextPath() + "/list' method='post'>\n"
                        + "<input type='hidden' name='action' value='delete'/>"
                        + "<input type='hidden' name='id' value='" + u.getId() + "'/>"
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
                + "<form action='" + req.getContextPath() + "/create' method='get'>\n"
                + "<input type='submit' value='ДОБАВИТЬ'>"
                + "</form>"
                + "<br/>"
                + console
                + "<br/>"
                + table
                + "</body>\n"
                + "</html>");
        writer.print(result);
    }
*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        resp.sendRedirect(String.format("%s/?system_message=%s", req.getContextPath(), this.dispatsh.access(req.getParameter("action"),
                new Users(req.getParameter("id"), req.getParameter("name"), req.getParameter("login"))).get()));
    }
}
