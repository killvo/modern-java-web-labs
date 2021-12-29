<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Деталі експозиції</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/index.styles.css">
</head>

<body>
<div class="container">
    <div class="back_to_main">
        <a href="/index.jsp">На сторінку квитків</a>
    </div>
    <c:if test="${sessionScope.user.role.name.equals('USER')}">
    <div class="exposition_details">
        <div class="details_group">
            <div class="label">Тема:</div>
            <div class="value">${sessionScope.expositionDetails.exposition.topic}</div>
        </div>
        <div class="details_group">
            <div class="label">Вартість квитка:</div>
            <div class="value">${sessionScope.expositionDetails.exposition.price}</div>
        </div>
        <div class="details_group">
            <div class="label">Дата початку:</div>
            <div class="value">${sessionScope.expositionDetails.exposition.schedule.fromDate}</div>
        </div>
        <div class="details_group">
            <div class="label">Дата завершення:</div>
            <div class="value">${sessionScope.expositionDetails.exposition.schedule.toDate}</div>
        </div>
        <div class="details_group">
            <div class="label">Час початку:</div>
            <div class="value">${sessionScope.expositionDetails.exposition.schedule.startTime}</div>
        </div>
        <div class="details_group">
            <div class="label">Час завершення:</div>
            <div class="value">${sessionScope.expositionDetails.exposition.schedule.endTime}</div>
        </div>
    </div>
    <div class="halls_details">
        <div class="halls_title">Зали, у яких розміщується експозиція:</div>
        <table class="expositions" style="border: 1px solid black;">
            <tr>
                <td>Назва</td>
                <td>Поверх</td>
                <td>Номер залу</td>
            </tr>
            <c:forEach items="${sessionScope.expositionDetails.halls}" var="hall" varStatus="loop">
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
    </c:if>
</body>
</html>
