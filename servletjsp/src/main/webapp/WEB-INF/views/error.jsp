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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <script>
        function exit() {
            $.ajax({
                type: "POST",
                url: "./",
                data: {exit: "exit"}
            })
        };
        $(document).ready(function () {
            <c:if test="${message!=null}">
            alert("${message}");
            </c:if>
        });
    </script>
    </head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Трекер: список пользователей</a>
        </div>
        <ul id="do" class="nav navbar-nav">
            <li class="active"><a href="${pageContext.servletContext.contextPath}/">Home</a></li>
            <li class="active"><a href="${pageContext.servletContext.contextPath}/create">Добавить пользователя</a></li>
            <li class="active"><a href="${pageContext.servletContext.contextPath}/list">Список пользователей</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="${pageContext.servletContext.contextPath}/" onclick="exit()"><span
                    class="glyphicon glyphicon-user"></span> Выход</a>
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
