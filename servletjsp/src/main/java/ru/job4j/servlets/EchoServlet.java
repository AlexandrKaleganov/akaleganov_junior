package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class EchoServlet extends HttpServlet {
    private ConcurrentHashMap<String, String> name = new ConcurrentHashMap<>();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=windows-1251");
        PrintWriter writer = new PrintWriter(res.getOutputStream());
        StringBuilder bilder = new StringBuilder("<table border='1'>");
        bilder.append("<caption>список</caption>");
        bilder.append("<tr>\n"
                + "    <th><b>орол<b></th>\n"
                + "    <th>NAME</th>\n"
                + "   </tr>");
        this.name.forEach((k, n) -> bilder.append("<tr><td><b>" + k + "</b></td>" + "<td><b>" + n + "</b></td></tr>"));
        bilder.append("</table>");
        writer.append("<!DOCTYPE html>\n"
                + "<html lang='en'>\n"
                + "<head>\n"
                + "    <meta charset='UTF-16'>\n"
                + "    <title></title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<form action='" + req.getContextPath() + "/echo' method='post'>"
                + "key : <input type='text' name='key'/>"
                + "Name : <input type='text' name='login'/>"
                + "<input type='submit'>"
                + "</form>"
                + "<br/>"
                + bilder
                + "</body>\n"
                + "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        this.name.put(req.getParameter("key"), req.getParameter("login"));
        doGet(req, resp);
    }
}