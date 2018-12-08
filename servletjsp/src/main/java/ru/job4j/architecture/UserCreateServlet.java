package ru.job4j.architecture;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserCreateServlet extends HttpServlet {
    private final DispatchDiapason dispatsh = DispatchDiapason.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        PrintWriter print = resp.getWriter();

        StringBuilder result = new StringBuilder("<!DOCTYPE html>\n"
                + "<html lang='en'>\n"
                + "<head>\n"
                + "    <meta charset='UTF-8'>\n"
                + "    <title></title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<br/>"
                + "<form action='" + req.getContextPath() + "/list' method='post'>\n"
                + "<input type='hidden' name='action' value='add'/>"
                + "ID : <input type='text' name='id' value=''/>"
                + "Name : <input type='text' name='name' value=''/>"
                + "Login : <input type='text' name='login' value=''/>"
                + "<input type='submit' value='ДОБАВИТЬ'>"
                + "</form>"
                + "<br/>"
                + "</body>\n"
                + "</html>");
        print.print(result);
    }

}
