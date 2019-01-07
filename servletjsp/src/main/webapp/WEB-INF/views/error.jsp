<%@ page import="ru.job4j.architecture.Err" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Lis
  Date: 02 янв 19
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Loger_Error</title>
</head>
<body>
<br/>
<table style="border: 1px solid black;" cellpadding="1" cellspacing="1" border="2">
    <tbody>
    <caption>
        Error Message
    </caption>
        <%Err message = (Err) request.getAttribute("err");
         if (message!=null){%>
    <tr>
        <td><%=message.getDateTime()%>
        </td>
        <td><%=message.getError()%>
        </td>
    </tr>
        <%}%>
</table>
<br/>
<form action="${pageContext.servletContext.contextPath}/" method="get">
    <input type="submit" value="ВЕРНУТЬСЯ НА ГЛАВНУЮ СТРАНИЦУ">
</form>
</body>
</html>
