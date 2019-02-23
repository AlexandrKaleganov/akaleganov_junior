<%--
  Created by IntelliJ IDEA.
  User: Lis
  Date: 29 янв 19
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script>
        function isnotNull() {
            var login = $("#login");
            var pass = $("#password");
            if (login.val() != "" && pass.val() != "") {
                return true;
                alert("Введите логин или пароль");
                return false;
            }
        }
    </script>
</head>
<body>
<c:if test="${err!=null && err.error != null}">
    <div style="background-color: red"/>
    <c:out value="${err.error}"/>
</c:if>
<div class="container">
    <h2>Stacked form</h2>
    <form action="${pageContext.servletContext.contextPath}/signin" method="post">
        <div class="form-group">
            <label for="login">Login:</label>
            <input type="login" class="form-control" id="login" placeholder="Enter name" name="login">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" placeholder="Enter password" name="pass">
        </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input" type="checkbox" name="remember"> Remember me
            </label>
        </div>
        <button type="submit" class="btn btn-primary" onclick= "return isnotNull();">ВХОД</button>
    </form>
</div>

</body>
<%--<table border="1">--%>
    <%--<caption>Введите логин и пароль:</caption>--%>
    <%--<tr>--%>
        <%--<form action="${pageContext.servletContext.contextPath}/signin" method="post">--%>
            <%--<td><input type=text" name="login" value=""></td>--%>
            <%--<td><input type="password" name="pass" value=""></td>--%>
            <%--<td><input type="submit"  value="ВХОД"></td>--%>
        <%--</form>--%>
    <%--</tr>--%>
<%--</table>--%>

</body>
</html>
