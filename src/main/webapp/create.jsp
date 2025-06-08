<%--
  Created by IntelliJ IDEA.
  User: samsa
  Date: 29.05.2025
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Добавление кошелька</h1>
<form method="post">
    <label>Фамилия:</label><br><br>
    <input name="last_name"/><br><br>
    <label>Имя:</label><br><br>
    <input name="first_name"/><br><br>
    <label>Отчество:</label><br><br>
    <input name="middle_name"/><br><br>
    <label>Номер паспорта:</label><br><br>
    <input name="passport_id"/><br><br>
    <label>Рубли:</label><br><br>
    <input name="rub"/><br><br>
    <label>Доллары:</label><br><br>
    <input name="usd"/><br><br>
    <label>Евро:</label><br><br>
    <input name="eur"/><br><br>
    <input type="submit" value="Save" />
</form>
</body>
</html>
