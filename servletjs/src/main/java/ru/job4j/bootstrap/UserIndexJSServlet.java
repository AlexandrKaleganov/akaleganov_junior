package ru.job4j.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.swing.interop.SwingInterOpUtils;
import ru.job4j.bootstrap.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserIndexJSServlet extends HttpServlet {
    private Dispatch disp = Dispatch.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader rider = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        int i = 1;
        while ((temp = rider.readLine()) != null) {
            stringBuilder.append(temp);
        }

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(stringBuilder.toString(), User.class);
        User userFinal = this.disp.submit("add", user, new User());
        if (userFinal.getName() != null) {
            System.out.println(mapper.writeValueAsString(userFinal));
        }
    }
}
