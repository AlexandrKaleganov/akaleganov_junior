package ru.job4j.architecture;
/**
 * @author Alexander Kaleganov (alexmur07@mail.ru)
 * @version 8.0
 * @since 04-11-2018
 * метод dopost  отвечает только за добавление, изменение, обновление пользователей, за получение списка отвечает сервлет list
 * <p>
 * PrintWriter writer = new PrintWriter(res.getOutputStream()); пришлось заменить на
 * PrintWriter writer = res.getWriter();  иначе не работала кодировка либо на сайте либо в среде , а так всё работает
 * в методе пост у меня происходит редикрект с параметрами на страницу лист и там вся информация обрабатывается
 */


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private final DispatchDiapason dispatsh = DispatchDiapason.getInstance();

    /**
     * закомментил за ненадобностью, т.к. за интерфейс теперь отвечает jsp
     *
     * @Override public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
     * res.setContentType("text/html; charset=utf-8");
     * req.setCharacterEncoding("utf-8");
     * PrintWriter writer = res.getWriter();
     * String send = req.getParameter("system_message");
     * StringBuilder console = new StringBuilder(
     * "<table border='1'>"
     * + "<tr><th>ConsoleOutsend</th></tr>");
     * if (send != null) {
     * console.append("<tr> <td>" + send + "</td></tr>");
     * }
     * console.append("</table>");
     * StringBuilder table = new StringBuilder("<table border='1'>");
     * table.append("<caption>список</caption>");
     * table.append("<tr>\n"
     * + "    <th>id</th>\n"
     * + "    <th>NAME</th>\n"
     * + "    <th>LOGIN</th>\n"
     * + "    <th>CREATE_DATE</th>\n"
     * + "    <th>EDIT</th>\n"
     * + "    <th>DELETE</th>\n"
     * + "   </tr>");
     * List<Users> list = (List<Users>) this.dispatsh.access("findall", new Users()).get();
     * list.forEach(u ->
     * table.append("<tr>"
     * + "<td>" + u.getId() + "</td>"
     * + "<td>" + u.getName() + "</td>"
     * + "<td>" + u.getLogin() + "</td>"
     * + "<td>" + u.getCreateDate() + "</td>"
     * + "<td>"
     * + "<form action='" + req.getContextPath() + "/edit?=method='get'>\n"
     * + "<input type='hidden' name='id' value='" + u.getId() + "'/>"
     * + "<input type='hidden' name='name' value='" + u.getName() + "'/>"
     * + "<input type='hidden' name='login' value='" + u.getLogin() + "'/>"
     * <p>
     * + "<input type='submit' value='изменить'>"
     * + "</form>"
     * + "</td>"
     * + "<td>"
     * + "<form action='" + req.getContextPath() + "/data' method='post'>\n"
     * + "<input type='hidden' name='action' value='delete'/>"
     * + "<input type='hidden' name='id' value='" + u.getId() + "'/>"
     * + "<input type='submit' value='удалить'>"
     * + "</form>"
     * + "</td>"
     * + "</tr>"));
     * table.append("</table>");
     * StringBuilder result = new StringBuilder("<!DOCTYPE html>\n"
     * + "<html lang='en'>\n"
     * + "<head>\n"
     * + "    <meta charset='UTF-8'>\n"
     * + "    <title></title>\n"
     * + "</head>\n"
     * + "<body>\n"
     * + "<br/>"
     * + "<form action='" + req.getContextPath() + "/create' method='get'>\n"
     * + "<input type='submit' value='ДОБАВИТЬ'>"
     * + "</form>"
     * + "<br/>"
     * + console
     * + "<br/>"
     * + table
     * + "</body>\n"
     * + "</html>");
     * writer.print(result);
     * }
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        try {
            req.setAttribute("message", this.dispatsh.access(req.getParameter("action"),
                    new Users(req.getParameter("id"), req.getParameter("name"), req.getParameter("login"))));
            req.getRequestDispatcher("/").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/loggererror.jsp").forward(req, resp);
        }
    }
}
