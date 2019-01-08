<%@ page import="ru.job4j.architecture.Users" %><%--
  Created by IntelliJ IDEA.
  User: Lis
  Date: 15 дек 18
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Start</title>
</head>
<body>
<br/>
<table style="border: 1px solid black;" cellpadding="1" cellspacing="1" border="2">
    <tbody>
    <tr>
        <th>System Message</th>
    </tr>
    <tr>
             <td><c:out value="${message}"></c:out>
        </td>

    </tr>
</table>
<br/>

<table>
    <caption>ПРИВЕТ</caption>
    <tr>
        <td>
            <form action="${pageContext.servletContext.contextPath}/create" method="get">
                <input type="submit" value="ДОБАВИТЬ ПОЛЬЗОВАТЕЛЯ">
            </form>
        </td>
        <td>
            <form action="${pageContext.servletContext.contextPath}/list" method="post">
                <input type="hidden" name="action" value="findall">
                <input type="submit" value="СПИСОК ПОЛЬЗОВАТЕЛЕЙ">
            </form>
        </td>
    </tr>
</table>
</tbody>
</table>
</body>
</html>
