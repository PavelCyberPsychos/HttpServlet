<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Пример JSP с Русским Заголовком</title>
</head>
<body>
<c:if test="${not empty requestScope.tickets}">
    <ul>
        <h1> Купленные билеты:</h1>
        <c:forEach var="ticket" items = "${requestScope.tickets}">
            <li>${fn:toLowerCase(ticket.seat_no)}</li>

        </c:forEach>
    </ul>
</c:if>
</body>
</html>