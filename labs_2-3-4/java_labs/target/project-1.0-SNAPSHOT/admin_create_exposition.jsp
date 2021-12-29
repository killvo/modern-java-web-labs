<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Створити експозицію</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/index.styles.css">
</head>

<body>
<div class="container">
    <div class="back_to_main">
        <a href="/index.jsp">На головну сторінку</a>
    </div>
    <h3>Створити експозицію:</h3>
    <c:if test="${sessionScope.user.role.name.equals('ADMIN')}">
        <div class="admin_block">
            <form method="post" action="/command/">
                <input type="text" hidden name="command" value="CREATE_EXPOSITION">
                <label>
                    Тема:
                    <input type="text" name="topic"/>
                </label>
                <label>
                    Вартість квитка:
                    <input type="text" name="price"/>
                </label>
                <label>
                    Дата початку:
                    <input type="date" name="fromDate"/>
                </label>
                <label>
                    Дата завершення:
                    <input type="date" name="toDate"/>
                </label>
                <label>
                    Час початку:
                    <input type="time" name="startTime"/>
                </label>
                <label>
                    Час завершення:
                    <input type="time" name="endTime"/>
                </label>
                <select name="selectedHalls" multiple="multiple">
                    <option selected="selected"></option>
                    <c:forEach items="${sessionScope.halls}" var="hall">
                        <option value="${hall.id}">${hall.name} | ${hall.floor} поверх | ${hall.number} зала</option>
                    </c:forEach>
                </select>
                <button type="submit">Створити</button>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>
