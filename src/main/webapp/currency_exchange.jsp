
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<h1>Обмен валют</h1>
<form method="post">
  <label>Номер кошелька:</label><br><br>
  <input name="id"/><br><br>
  <label>Операция:</label>
  <select name="operation">
    <option value="rub_to_usd">Рубли -> Доллары</option>
    <option value="rub_to_eur">Рубли -> Евро</option>
    <option value="usd_to_rub">Доллары -> Рубли</option>
    <option value="usd_to_eur">Доллары -> Евро</option>
    <option value="eur_to_rub">Евро -> Рубли</option>
    <option value="eur_to_usd">Евро -> Доллары</option>
  </select>
  <br><br>
  <label>Сумма:</label><br><br>
  <input name="amount"/><br><br>
  <input type="submit" value="Save" />
</form>
</body>
</html>
