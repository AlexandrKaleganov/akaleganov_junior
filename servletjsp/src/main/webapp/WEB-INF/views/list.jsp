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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
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
                <form action="${pageContext.servletContext.contextPath}/edit" method="post">
                    <input type="hidden" name="id" value="${u.id}">
                    <input type="hidden" name="action" value="findbyid">
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
</table>
</body>
</html>
