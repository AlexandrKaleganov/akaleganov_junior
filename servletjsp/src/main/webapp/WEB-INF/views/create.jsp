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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>create_user</title>
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
        $(document).ready(function () {
            $.ajax({
                type: "POST",
                url: "./adres",
                data:{action: "findAllcountry"},
                success: function (data) {
                    console.log(data);
                    for (var i = 0; i < data.length; i++) {
                        $("#country option:last").after("<option>" + data[i] + "</option>");
                    }
                },
            });
        });
        function cityList() {
            console.log($("#country").val());
            $.ajax({
                type:"POST",
                url:"./adres",
                data:{action:"findAllcity", country:$("#country").val()},
                success:function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $("#city option:last").after("<option value='" + data[i] + "'>" + data[i] + "</option>");
                    }

                }
            })
        }
        function isValid(r, l) {
            var rsl = false;
            if (r.val() == l) {
                rsl = true;
                alert(r.attr("title"));
            }
            return rsl;
        };

        function valid() {
            return !(isValid($("#name"), "") + isValid($("#mail"), "") + isValid($("#password"), "")
                + isValid($("#country"), "") + isValid($("#city"), ""));
        };
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
<div>
    <form class="form-inline" action="${pageContext.servletContext.contextPath}/" method="post">
        <div class="form-group">
            <label for="id"></label>
            <input type="hidden" class="form-control" neme="id" value="37" title="Error ID. Enter ID." id="id">
        </div>
        <div class="form-group">
            <label for="name">Имя:</label>
            <input type="text" class="form-control" name="name" title="Enter name." id="name">
        </div>
        <div class="form-group">
            <label for="mail">Маил:</label>
            <input type="text" class="form-control" name="mail" title="Enter login." id="mail">
        </div>
        <div class="form-group">
            <label for="password">Проль:</label>
            <input type="password" class="form-control" name="password" title="Enter pass." id="password">
        </div>
        <div class="form-group">
            <label for="country">Страна:</label>
            <select class="form-control" title="Enter attribut dostupa." name="country" id="country" onclick="cityList()">
                <option value=""></option>
            </select>
        </div>
        <div class="form-group">
            <label for="city">Город:</label>
            <select class="form-control" name="city" title="Enter attribut dostupa." id="city">
                <option value=""></option>
            </select>
        </div>
        <button type="submit" name="action" value="add" class="btn btn-default" onclick="return valid();">Submit
        </button>
    </form>

</div>
</body>
</html>
