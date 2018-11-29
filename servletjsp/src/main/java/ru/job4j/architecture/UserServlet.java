package ru.job4j.architecture;

import ru.job4j.architecture.err.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {
    private final Validate validate = ValidateService.getInstance();

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
        String res;
        try {
            res = this.validate.add(new Users(req.getParameter("id"), req.getParameter("name"), req.getParameter("login")));
        } catch (DatabaseException e) {
            res = e.getMessage();
        }
        doGet(req, resp);
    }
}
