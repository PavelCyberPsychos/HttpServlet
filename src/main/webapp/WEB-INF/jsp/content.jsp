<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Пример JSP с Русским Заголовком</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <span>Content. Русский</span>
    <p>Size: ${requestScope.flights.size()}</p>
    <p>Id: ${requestScope.flights.get(0).id}</p>
    <p>Id 2: ${requestScope.flights[1].id}</p>
    <p>Map Id 2: ${SessionScope.flightsMap[1]}</p>
    <p>JSESSION Id 2: ${cookie["JSESSIONID"].value},unique identifier</p>
    <p>Header: ${header["Cookie"]}</p>
    <p>Param id: ${param.test}</p>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>