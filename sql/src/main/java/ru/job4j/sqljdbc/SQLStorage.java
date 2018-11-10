package ru.job4j.sqljdbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class SQLStorage {
    @SuppressWarnings("all")
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLStorage.class);

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/filtres";
        String login = "postgres";
        String password = "444444";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, login, password);
            /*
            //изменяем данные (удаляем) добавляем
            PreparedStatement st = conn.prepareStatement("update product set price = null where name LIKE ?");
            PreparedStatement stTwo = conn.prepareStatement("INSERT  into product(name, type_id, expiried_date, price) VALUES (?, ?, now(), ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, "%ос%");
            stTwo.setString(1, "namesir");
            stTwo.setInt(2, 2);
            stTwo.setInt(3, 300);

            int rowsDelete = st.executeUpdate();
            int sapros = stTwo.executeUpdate();
            ResultSet idNew = stTwo.getGeneratedKeys();
            idNew.next();n
            System.out.println("kolvostrok = " + sapros + " id_new =  " + idNew.getInt(1));
            System.out.println(rowsDelete + "  " );
            idNew.close();
            st.close();
*/

            //запрос на выборку ваниант 1

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM  product AS p WHERE  p.type_id = 2");
            while (rs.next()) {
                System.out.println(String.format("%s %s", rs.getString("name"), rs.getTimestamp("expiried_date")));
            }
            rs.close();
            st.close();

            //более правильный запрос на вборку с изменяемыми параметрами
            /*
            PreparedStatement st = conn.prepareStatement("SELECT p.name, t.name FROM product as p INNER JOIN type as t on p.type_id = t.id WHERE p.type_id = ?");
            st.setInt(1, 2);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println(String.format("%s %s", rs.getString(1), rs.getString(2)));
            }
            rs.close();
            st.close();
            */
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }

    }
}

