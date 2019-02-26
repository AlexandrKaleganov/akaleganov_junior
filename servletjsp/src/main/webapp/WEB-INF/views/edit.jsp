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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>edit</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
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
<table border="1">

    <caption>Изменение данных пользователя, если поле будет стёрто, то данные поля не будут обновлены</caption>
    <form action="${pageContext.servletContext.contextPath}/" method="post">
        <tr>
            <td> ID :</td>
            <td><input readonly="readonly" name="id" value="${u.id}"></td>
        </tr>
        <br>
        <tr>
            <td> Name :</td>
            <td><input type="text" name="name" value="${u.name}"></td>
        </tr>
        <tr>
            <td> Login :</td>
            <td><input type="text" name="login" value="${u.login}"></td>
        </tr>
        <input type="hidden" name="action" value="update">
        <input type="submit" value="ИЗМЕНИТЬ">
    </form>
</table>
</body>
</html>
