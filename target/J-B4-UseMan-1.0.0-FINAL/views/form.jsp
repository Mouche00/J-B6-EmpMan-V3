<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>UseMan</title>
    <link href="../css/style.css" rel="stylesheet" />
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: #e0e0e0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        form {
            background: #e0e0e0;
            border-radius: 20px;
            box-shadow: 5px 5px 10px #c8c8c8,
            -5px -5px 10px #ffffff;
            padding: 30px;
            width: 300px;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        input, select {
            background: #e0e0e0;
            border: none;
            border-radius: 10px;
            box-shadow: inset 2px 2px 5px #c8c8c8,
            inset -2px -2px 5px #ffffff;
            padding: 10px;
            font-size: 16px;
        }

        input[type="submit"] {
            background: #ffffff;
            border: none;
            border-radius: 10px;
            box-shadow: 5px 5px 10px #c8c8c8,
            -5px -5px 10px #ffffff;
            cursor: pointer;
            padding: 10px 15px;
            font-size: 16px;
            transition: all 0.3s;
        }

        input[type="submit"]:hover {
            box-shadow: 2px 2px 10px #c8c8c8,
            -2px -2px 10px #ffffff;
        }

        select {
            cursor: pointer;
        }
    </style>
</head>
<body>
<h2>Employee Creation</h2>

<form action="${employee == null ? "save" : "update"}" method="POST">
    <input name="name" type="text" placeholder="Name" value="<c:out value="${employee.name}" default="" />" />
    <input name="email" type="text" placeholder="Email" value="<c:out value="${employee.email}" default="" />" />
    <input name="phone" type="text" placeholder="Phone" value="<c:out value="${employee.phone}" default="" />" />
    <input name="post" type="text" placeholder="Post" value="<c:out value="${employee.post}" default="" />" />
    <select name="department">
        <c:forEach var="department" items="${departments}">
            <option value="${department.value.id}"
                    <c:if test="${employee.department.id == department.value.id}">selected</c:if>>
                    ${department.value.name}
            </option>
        </c:forEach>
    </select>
    <input type="submit" value="Submit" />
</form>
</body>
</html>
