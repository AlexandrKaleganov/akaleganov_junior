<%@ page import="ru.job4j.architecture.Users" %><%--
  Created by IntelliJ IDEA.
  User: Lis
  Date: 15 дек 18
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <%Users message = (Users) request.getAttribute("message");
         if (message!=null){%>
    <tr>
        <td><%=message.toString()%>
        </td>
    </tr>
    <%}%>
</table>
<br/>

    <table><caption>ПРИВЕТ</caption>
        <tr>
            <td>
                <form action="<%=request.getContextPath()%>/create.jsp" method="get">
                    <input type="submit" value="ДОБАВИТЬ ПОЛЬЗОВАТЕЛЯ">
                </form>
            </td>
            <td>
                <form action="<%=request.getContextPath()%>/list" method="post">
                    <input type="hidden" name="action" value="findall">
                    <input type="submit" value="СПИСОК ПОЛЬЗОВАТЕЛЕЙ">
                </form>
            </td>
            <td>
                <form action="<%=request.getContextPath()%>/error.jsp" method="get">
                    <input type="submit" value="ПОКАЗАТЬ ЛОГ ОШИБОК">
                </form>
            </td>
        </tr>
    </table>


    </tbody>
</table>
</body>
</html>
