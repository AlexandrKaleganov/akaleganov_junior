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
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Трекер заявок</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="${pageContext.servletContext.contextPath}/">Home</a></li>
            <%--<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Page 1 <span--%>
            <%--class="caret"></span></a>--%>
            <%--<ul class="dropdown-menu">--%>
            <%--<li><a href="#">Page 1-1</a></li>--%>
            <%--<li><a href="#">Page 1-2</a></li>--%>
            <%--<li><a href="#">Page 1-3</a></li>--%>
            <%--</ul>--%>
            <%--</li>--%>
            <%--<li><a href="#">Page 2</a></li>--%>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="${pageContext.servletContext.contextPath}/" onclick="exit()"><span class="glyphicon glyphicon-user"></span> Выход</a>
            </li>
        </ul>
    </div>
</nav>
<br/>
<table style="border: 1px solid black;" cellpadding="1" cellspacing="1" border="2">
    <tbody>
    <caption>
        Error Message
    </caption>

    <tr>
        <td>
            <c:out value="${err.dateTime}"></c:out>
        </td>
        <td><c:out value="${err.error}"></c:out>
        </td>
    </tr>

</table>
<br/>
<form action="${pageContext.servletContext.contextPath}/" method="get">
    <input type="submit" value="ВЕРНУТЬСЯ НА ГЛАВНУЮ СТРАНИЦУ">
</form>
</body>
</html>
