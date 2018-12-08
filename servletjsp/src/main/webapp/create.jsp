<%--
  Created by IntelliJ IDEA.
  User: Kaleganov Alxandr
  Date: 08 дек 18
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>create_user</title>
</head>
<body>
<br>
<table border="1">
    <form action="<%=request.getContextPath()%>/list" method="post">
        <input type="hidden" name="action" value="add">
        <tr>
            <td> ID :</td>
            <td><input type="text" name="id" value=""></td>
        </tr>
        <tr>
            <td> Name :</td>
            <td><input type="text" name="name" value=""></td>
        </tr>
        <tr>
            <td> Login :</td>
            <td><input type="text" name="login" value=""></td>
        </tr>
        <input type="submit" value="ДОБАВИТЬ">
    </form>
</table>
</body>
</html>
