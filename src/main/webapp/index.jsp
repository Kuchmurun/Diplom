<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>


<html>
<body>
<title>Bank</title>
<h1>Wallet</h1>

<table>
    <tr><th>Номер кошелька</th><th>Фамилия</th><th>Имя</th><th>Отчество</th><th>Номер паспорта</th><th>Рубли</th><th>Доллары</th><th>Евро</th><th>Статус кошелька</th></tr>
    <c:forEach var="wallet" items="${wallets}">
        <tr>
            <td>${wallet.id}</td>
            <td>${wallet.lastName}</td>
            <td>${wallet.firstName}</td>
            <td>${wallet.middleName}</td>
            <td>${wallet.passportId}</td>
            <td>${wallet.rub}</td>
            <td>${wallet.usd}</td>
            <td>${wallet.eur}</td>
            <td>${wallet.walletStatus}</td>
        </tr>
    </c:forEach>
</table>
<p><a href='<c:url value="/fill" />'>Пополнить счёт</a></p>
<p><a href='<c:url value="/currency" />'>Обмен валют</a></p>
<p><a href='<c:url value="/update" />'>Обновить кошелёк</a></p>
<p><a href='<c:url value="/create" />'>Добавить кошелёк</a></p>
<p><a href='<c:url value="/delete" />'>Удалить кошелёк</a></p>
...Четвертая ссылка или кнопка...
</body>
</html>
