<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<fmt:setLocale value="ru_RU"/>
<fmt: setBundle basename="translations"/>

<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email">
        <fmt:message key="page.login.email"/>:
        <input type="text" name="email" id="email" value="${param.email}" required>
    </label><br>
    <label for="password"> <fmt: message key="page.login.password"/>:
        <input type="password" name="password" id="password" required>
    </label><br>
    <button type="submit"><fmt:message key="page.login.submit.button"/></button>
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button"><fmt:message key="page.login.register.button"/></button>
    </a>
    <div>
        <c:if test="${param.error != null}">
            <div style="color: red">
                <span><fmt:message key="page.login.error"/></span>
            </div>
        </c:if>
        <p>param.error value: ${param.error}</p> <--- ADD THIS LINE
    </div>
</form>
</body>
</html>
