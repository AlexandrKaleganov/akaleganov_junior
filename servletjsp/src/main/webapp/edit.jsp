<%@ page import="ru.job4j.architecture.Users" %>
<%@ page import="ru.job4j.architecture.DispatchDiapason" %>
<%@ page import="java.util.Optional" %><%--
  Created by IntelliJ IDEA.
  User: Kaleganov Alxandr
  Date: 08 дек 18
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>edit</title>
</head>
<body>
<table border="1">

    <caption>Изменение данных пользователя, если поле будет стёрто, то данные поля не будут обновлены</caption>
    <form action="<%=request.getContextPath()%>/list" method="post">
        <tr>
            <td> ID :</td>
            <td><input readonly="readonly" name="id" value="<%=request.getParameter("id")%>"></td>
        </tr>
        <br>
        <tr>
            <td> Name :</td>
            <td><input type="text" name="name" value="<%=request.getParameter("name")%>"></td>
        </tr>
        <tr>
            <td> Login :</td>
            <td><input type="text" name="login" value="<%=request.getParameter("login")%>"></td>
        </tr>
        <input type="hidden" name="action" value="update">
        <input type="submit" value="ИЗМЕНИТЬ">
    </form>
</table>
</body>
</html>
