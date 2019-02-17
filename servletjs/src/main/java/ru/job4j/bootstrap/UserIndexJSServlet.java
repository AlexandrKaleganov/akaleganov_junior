package ru.job4j.bootstrap;

import ru.job4j.bootstrap.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class UserIndexJSServlet extends HttpServlet {
    private Dispatch disp = Dispatch.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


//        req.setAttribute("js", rsl);
        req.getRequestDispatcher("/WEB-INF/views/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("data"));
        BufferedReader reader = req.getReader();
        String string = reader.readLine();
        System.out.println(string);
        System.out.println(req.getParameter("id"));
        req.getRequestDispatcher("/WEB-INF/views/index.html").forward(req, resp);
    }
}
