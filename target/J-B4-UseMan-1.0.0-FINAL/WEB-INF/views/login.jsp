<%@ page import="java.util.Random" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>UseMan</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
</head>
<body class="rubik-medium">
<main class="main">
    <div class="main-form wide-form">
        <h1>Login form</h1>
        <form class="employee-form" action="${pageContext.request.contextPath}/auth/login" method="POST">
            <input name="email" type="email" placeholder="Email" />
            <input name="password" type="password" placeholder="Password" />
            <input type="submit" value="Submit" />
        </form>
    </div>
</main>
</body>
</html>