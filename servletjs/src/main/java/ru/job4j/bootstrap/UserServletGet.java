package ru.job4j.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.bootstrap.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class UserServletGet extends HttpServlet {
    private Dispatch disp = Dispatch.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/json");
        try {
            PrintWriter writer = null;
            writer = new PrintWriter(resp.getOutputStream());
            ArrayList<User> userlist = new ArrayList<>();
            this.disp.submit("findall", new User(), new ConcurrentHashMap<Integer, User>()).forEach((k, v)->userlist.add(v));
            ObjectMapper mapper = new ObjectMapper();
            String temp = mapper.writeValueAsString(userlist);
            System.out.println(temp);
            writer.append(temp);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
