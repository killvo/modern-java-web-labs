<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Додати зал</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/index.styles.css">
</head>

<body>
<div class="container">
    <div class="back_to_main">
        <a href="/index.jsp">На головну сторінку</a>
    </div>
    <h3>Додати зал:</h3>
    <c:if test="${sessionScope.user.role.name.equals('ADMIN')}">
        <div class="admin_block">
            <form method="post" action="/command/">
                <input type="text" hidden name="command" value="CREATE_HALL">
                <label>
                    Назва:
                    <input type="text" name="name"/>
                </label>
                <label>
                    Поверх:
                    <input type="text" name="floor"/>
                </label>
                <label>
                    Номер залу:
                    <input type="text" name="number"/>
                </label>
                <button type="submit">Додати</button>
            </form>
        </div>
    </c:if>
    <div class="halls_title">Додані зали:</div>
    <table class="halls" style="border: 1px solid black;">
        <tr>
            <td>Назва</td>
            <td>Поверх</td>
            <td>Номер залу</td>
        </tr>
        <c:forEach items="${sessionScope.halls}" var="hall" varStatus="loop">
            <tr>
                <td>
                    <c:out value="${hall.name}"/>
                </td>
                <td>
                    <c:out value="${hall.floor}"/>
                </td>
                <td>
                    <c:out value="${hall.number}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
