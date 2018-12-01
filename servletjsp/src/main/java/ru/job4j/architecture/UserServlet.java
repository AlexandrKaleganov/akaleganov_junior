package ru.job4j.architecture;
/**
 * @autor Alexander KAleganov
 * @version 1.0
 * @since 01-11-2018
 * сервлет допост отвечает за работу методов обновление, удаления, добавления
 * doGet только выводит нашу бд
 * поиск по id  я не стал никуда добавлять в сервлет на данном этапе, т.к. в задании такого не увидел
 * но метод поиска по id  в базе имеется, управление реализовано через диспетчер
 *
 */

import ru.job4j.architecture.err.DatabaseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {
    private final Validate validate = ValidateService.getInstance();
    private final DispatchDiapason dispatsh = new DispatchDiapason().init();


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter writer = new PrintWriter(res.getOutputStream());
        String r;
        try {
            r = validate.findAll().toString();
        } catch (DatabaseException e) {
            r = e.getMessage();
        }
        writer.append(r);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String res = " ";
        res = this.dispatsh.access(validate, req.getParameter("action"),
                new Users(req.getParameter("id"), req.getParameter("name"), req.getParameter("login")));
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(res + "\n");
        writer.flush();
        doGet(req, resp);
    }
}
