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
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * если сработает метод get  то мы получим всю мапу,
 * если сработает метод пост то мы получим только добавленный объект
 */
public class UserIndexJSServlet extends HttpServlet {
    private Dispatch disp = Dispatch.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=Windows-1251");
            try {
                PrintWriter writer = null;
                writer = new PrintWriter(resp.getOutputStream());
                ArrayList<User> userlist = new ArrayList<>();
                this.disp.submit("findall", new User(), new ConcurrentHashMap<Integer, User>()).forEach((k, v)->userlist.add(v));
                ObjectMapper mapper = new ObjectMapper();
                String temp = mapper.writeValueAsString(userlist);
                writer.append(temp);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=Windows-1251");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader rider = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        while ((temp = rider.readLine()) != null) {
            stringBuilder.append(temp);
        }

        User user = mapper.readValue(stringBuilder.toString(), User.class);
        User userFinal = this.disp.submit("add", user, new User());
        if (userFinal.getName() != null) {
            String te = mapper.writeValueAsString(userFinal);
            System.out.println(te);
            writer.append(te);
            writer.flush();
        }
    }
}
