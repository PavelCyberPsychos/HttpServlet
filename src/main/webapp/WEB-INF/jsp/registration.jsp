<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/registration" method="post" enctype="multipart/form-data">
    <label for="nameId"> Name:
        <input type="text" name="name" id="nameId">
    </label><br>
    <label for="birthdayId"> Birthday:
        <input type="date" name="birthday" id="birthdayId" required>
    </label><br>
    <label for="emailId"> Email:
        <input type="text" name="email" id="emailId">
    </label><br>
    <label for="imageId"> Image:
        <input type="file" name="image" id="imageId" required>
    </label><br>
    <label for="passwordId"> Password:
        <input type="password" name="password" id="passwordId">
    </label><br>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select><br>
    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" value="${gender}"> ${gender}
        <br>
    </c:forEach>
    <button type="submit">Send</button>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>
