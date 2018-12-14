<%@ page import="ru.job4j.architecture.DispatchDiapason" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.job4j.architecture.Users" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Kaleganov Alxandr
  Date: 07 дек 18
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<br/>
<form action="<%=request.getContextPath()%>/create.jsp" method="get">
    <input type="submit" value="ДОБАВИТЬ">
</form>
<br/>
<table style="border: 1px solid black;" cellpadding="1" cellspacing="1" border="2">
    <tbody>
    <tr>
        <th>System Message</th>
    </tr>
    <%String message = request.getParameter("system_message");%>
    <%if (message != null) {%>
    <tr>
        <td><%=message%>
        </td>
    </tr>
    <%}%>
    </tbody>
</table>
<br/>
<table border="1">
    <caption>DataBase</caption>
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>LOGIN</th>
        <th>CREATE_DATE</th>
        <th>EDIT</th>
        <th>DELETE</th>
    </tr>
    <%
        List<Users> list = (List<Users>) DispatchDiapason
                .getInstance()
                .access("findall", new Users("", "", "")).get();
        for (Users u : list) {
    %>
    <tr>
        <td><%=u.getId()%>
        </td>
        <td><%=u.getName()%>
        </td>
        <td><%=u.getLogin()%>
        </td>
        <td><%=u.getCreateDate()%>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/edit.jsp?=" method="get">
                <input type="hidden" name="id" value="<%=u.getId()%>">
                <input type="hidden" name="name" value="<%=u.getName()%>">
                <input type="hidden" name="login" value="<%=u.getLogin()%>">
                <input type="submit" value="EDIT">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/list" method="post">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%=u.getId()%>">
                <input type="submit" value="DELETE">
            </form>
        </td>
    </tr>
    <%}%>
</table>
</body>
</html>
