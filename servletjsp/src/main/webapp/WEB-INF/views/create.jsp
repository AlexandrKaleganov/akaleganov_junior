<%--
  Created by IntelliJ IDEA.
  User: Kaleganov Alxandr
  Date: 08 дек 18
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-16">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>create_user</title>
    <script>
        function isValid(r, l) {
            var rsl = false;
            if (r.val() == l) {
                rsl = true;
                alert(r.attr("title"));
            }
            return rsl;
        }
        function valid() {
             return !(isValid($("#name"), "") + isValid($("#login"), "")+ isValid($("#password"), "") + isValid($("#dostup"), "")
                 + isValid($("#country"), "") + isValid($("#city"), ""));
        }

    </script>
</head>
<body>
<br/>
<form action="${pageContext.servletContext.contextPath}/" method="post">
    <input type="hidden" name="exit" value="">
    <input type="submit" value="ВЫХОД">
</form>
<br/>
<div>
    <form class="form-inline" action="${pageContext.servletContext.contextPath}/" method="post">
        <div class="form-group">
            <label for="id"></label>
            <input type="hidden" class="form-control" value="37" title="Error ID. Enter ID." id="id">
        </div>
        <div class="form-group">
            <label for="name">Имя:</label>
            <input type="text" class="form-control" title="Enter name." id="name">
        </div>
        <div class="form-group">
            <label for="login">Логин:</label>
            <input type="text" class="form-control" title="Enter login." id="login">
        </div>
        <div class="form-group">
            <label for="password">Проль:</label>
            <input type="password" class="form-control" title="Enter pass." id="password">
        </div>
        <div class="form-group">
            <label for="dostup">Права:</label>
            <select class="form-control" title="Enter attribut dostupa." id="dostup">
                <option value=""></option>
                <option value="Admin">Admin</option>
                <option value="User">User</option>
            </select>
        </div>
        <div class="form-group">
            <label for="country">Страна:</label>
            <input type="text" class="form-control" title="Enter country" id="country">
        </div>
        <div class="form-group">
            <label for="city">Город:</label>
            <input type="text" class="form-control" title="Enter city" id="city"></input>
        </div>
        <button type="button" class="btn btn-default" onclick="return valid();">Submit</button>
    </form>
</div>
</table>
</body>
</html>
