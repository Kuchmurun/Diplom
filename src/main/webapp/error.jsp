
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>Возникли ошибки:</h1>
<c:forEach var="error" items="${errors}">
    <ul>
        <li>${error}</li>
    </ul>
</c:forEach>
<button onclick="window.history.back();">Назад</button>
</body>
</html>
