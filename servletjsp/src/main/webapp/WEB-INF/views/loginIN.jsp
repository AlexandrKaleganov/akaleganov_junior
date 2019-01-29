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
    <title>loginIN</title>
</head>
<body>
<c:if test="${err!=null && err.error != null}">
    <div style="background-color: red"/>
    <c:out value="${err.error}"/>
</c:if>
<table border="1">
    <caption>Введите логин и пароль:</caption>
    <tr>
        <form action="${pageContext.servletContext.contextPath}/signin" method="post">
            <td><input type=text" name="login" value=""></td>
            <td><<input type="password" name="pass" value=""></td>
        </form>
    </tr>
</table>

</body>
</html>
