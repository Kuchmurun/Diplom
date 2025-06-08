
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fill wallet</title>
</head>
<body>
<h1>Пополнение кошелька</h1>
<form method="post">
    <label>Номер кошелька:</label><br><br>
    <input name="id"/><br><br>
    <label>Сумма:</label><br><br>
    <input name="amount"/><br><br>
    <label>Валюта:</label><br><br>
    <input type="radio" name="currency" value="rub"/>
    <label>Рубли</label>
    <input type="radio" name="currency" value="usd"/>
    <label>Доллары</label>
    <input type="radio" name="currency" value="eur"/>
    <label>Евро</label><br><br>
    <input type="submit" value="Save" />
</form>
</body>
</html>
