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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List</title>
</head>
<body>
<br/>
<form action="${pageContext.servletContext.contextPath}/" method="get">
    <input type="submit" value="ВЕРНУТЬСЯ НА ГЛАВНУЮ СТРАНИЦУ">
</form>
<br/>
<table border="1">
    <caption>DataBase FILTER</caption>
    <tr>



        <th>ID</th>
        <th>NAME</th>
        <th>LOGIN</th>
        <th>CREATE_DATE</th>
        <th>FILTER</th>
    </tr>
    <tr>
        <form action="${pageContext.servletContext.contextPath}/list" METHOD="post">
            <td><input type="text" name="id" value=""></td>
            <td><input type="text" name="name" value=""></td>
            <td><input type="text" name="login" value=""></td>
            <td><input type="date" name="CREATE_DATE" value=""></td>
            <td><input type="hidden" name="action" value="filter"><input type="submit" value="FILTER"></td>
        </form>
    </tr>
</table>
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
    <c:forEach items="${list}" var="u">
        <tr>
            <td><c:out value="${u.id}"/></td>
            <td><c:out value="${u.name}"/></td>
            <td><c:out value="${u.login}"/></td>
            <td><c:out value="${u.createDate}"/></td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/edit" method="get">
                    <input type="hidden" name="id" value="${u.id}">
                    <input type="hidden" name="name" value="${u.name}">
                    <input type="hidden" name="login" value="${u.login}">
                    <input type="submit" value="EDIT">
                </form>
            </td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${u.id}">
                    <input type="submit" value="DELETE">
                </form>
            </td>
        </tr>
    </c:forEach>
    <%----%>
    <%--<%--%>
    <%--List<Users> list = (List<Users>) request.getAttribute("list");--%>
    <%--for (Users u : list) {--%>
    <%--%>--%>
    <%--<tr>--%>
    <%--<td><%=u.getId()%>--%>
    <%--</td>--%>
    <%--<td><%=u.getName()%>--%>
    <%--</td>--%>
    <%--<td><%=u.getLogin()%>--%>
    <%--</td>--%>
    <%--<td><%=u.getCreateDate()%>--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<form action="${pageContext.servletContext.contextPath}/edit" method="get">--%>
    <%--<input type="hidden" name="id" value="<%=u.getId()%>">--%>
    <%--<input type="hidden" name="name" value="<%=u.getName()%>">--%>
    <%--<input type="hidden" name="login" value="<%=u.getLogin()%>">--%>
    <%--<input type="submit" value="EDIT">--%>
    <%--</form>--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<form action="${pageContext.servletContext.contextPath}/" method="post">--%>
    <%--<input type="hidden" name="action" value="delete">--%>
    <%--<input type="hidden" name="id" value="<%=u.getId()%>">--%>
    <%--<input type="submit" value="DELETE">--%>
    <%--</form>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<%}%>--%>
</table>
</body>
</html>
