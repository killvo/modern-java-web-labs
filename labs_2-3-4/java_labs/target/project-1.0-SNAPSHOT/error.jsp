<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Помилка</title>
</head>
<body>
<div class="container">
    <div class="content">
        Відбулась помилка:
        <br>
        <h4>${requestScope.get("statusCode")}</h4>
        <h4>${requestScope.get("errorMessage")}</h4>
        <div class="back_to_main">
            <a href="/index.jsp">На головну сторінку</a>
        </div>
    </div>
</div>
</body>
</html>
