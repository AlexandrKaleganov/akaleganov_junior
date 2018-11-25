package ru.job4j.kniga;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class Contentservlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //добавим атрибут к запросу
        req.setAttribute("loc", Locale.getDefault());
        //добавляем атрибут к сессии
        req.getSession().setAttribute("calendar", Calendar.getInstance());
        //получение объекта RequestDispatcher и вызов JSP
        req.getRequestDispatcher("/main.jsp").forward(req, resp);
    }

}
