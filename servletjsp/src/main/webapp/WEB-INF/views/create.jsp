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
    <title>create_user</title>
</head>
<body>
<br>
<table border="1">
    <form action="${pageContext.servletContext.contextPath}/" method="post">
        <input type="hidden" name="action" value="add">
        <tr>
            <td> ID :</td>
            <td><input type='text' readonly="readonly" name="id" value="0"></td>
        </tr>
        <tr>
            <td> Name :</td>
            <td><input type="text" name="name" value="${u.name}"></td>
        </tr>
        <tr>
            <td> Login :</td>
            <td><input type="text" name="login" value="${u.login}"></td>
        </tr>
        <tr>
            <td>Password :</td>
            <td><input type="password" , name="password" , value="${u.password}"></td>
        </tr>
        <input type="submit" value="Добавить">
    </form>
</table>
</body>
</html>
