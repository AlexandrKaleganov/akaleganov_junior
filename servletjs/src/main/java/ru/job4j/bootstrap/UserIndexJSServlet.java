package ru.job4j.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.bootstrap.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class UserIndexJSServlet extends HttpServlet {
    private Dispatch disp = Dispatch.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader rider = req.getReader();
        StringBuilder rsl = new StringBuilder();
        String temp;
        while ((temp = rider.readLine()) != null) {
            rsl.append(temp);
            System.out.println(temp);
        }
        ObjectMapper mapper = new ObjectMapper();

        User user = mapper.readValues(rsl.toString(), User.class);
    }
}
