<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Пример JSP с Русским Заголовком</title>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>
<h1>Список перелетов</h1>
<c:if test="${not empty requestScope.flights}">
    <ul>
        <c:forEach var="flight" items="${requestScope.flights}">
            <li>
                <a href="${pageContext.request.contextPath}/tickets?flightId=${flight.id}">${flight.description}</a>
            </li>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${empty requestScope.flights}">
    <p>No flights available.</p>
</c:if>

</body>
</html>
