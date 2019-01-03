<%@ page import="ru.job4j.architecture.Err" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Lis
  Date: 02 янв 19
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Loger_Error</title>
</head>
<body>
<br/>
<table style="border: 1px solid black;" cellpadding="1" cellspacing="1" border="2">
    <tbody>
    <tr>
        <th>Error Message</th>
    </tr>
        <%Err message = (Err) request.getAttribute("err");
         if (message!=null){%>
    <tr>
        <td><%=message%>
        </td>
    </tr>
        <%}%>
</table>
<br/>
<form action="<%=request.getContextPath()%>/" method="get">
    <input type="submit" value="ВЕРНУТЬСЯ НА ГЛАВНУЮ СТРАНИЦУ">
</form>
<table style="border: 1px solid black;" cellpadding="1" cellspacing="1" border="2">
    <tbody>
    <tr>
        <th>Error List</th>
    </tr>
        <%List<Err> list = (List<Err>) request.getAttribute("list");
         for(Err err : list){%>
    <tr>
        <td><%=err.getError()%>
        </td>
        <td><%=err.getDateTime()%>
        </td>
    </tr>
        <%}%>
</table>
</body>
</html>
