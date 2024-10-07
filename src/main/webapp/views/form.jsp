<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>UseMan</title>
    <link href="../css/style.css" rel="stylesheet" />
</head>
<body class="roboto-medium">
    <h2>Employee Creation</h2>

    <form action="${employee == null ? "save" : "update"}" method="POST">
        <input name="name" type="text" value="<c:out value="${employee.name}" default="" />" />
        <input name="email" type="text" value="<c:out value="${employee.email}" default="" />" />
        <input name="phone" type="text" value="<c:out value="${employee.phone}" default="" />" />
        <input name="post" type="text" value="<c:out value="${employee.post}" default="" />" />
        <select name="department">
            <c:forEach var="department" items="${departments}">
                <option value="${department.value.id}"
                        <c:if test="${employee.department.id == department.value.id}">selected</c:if>>
                        ${department.value.name}
                </option>
            </c:forEach>
        </select>
        <input type="submit" />
    </form>
</body>
</html>
