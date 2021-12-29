<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Статистика</title>
</head>
<body>
<div class="container">
    <div class="back_to_main">
        <a href="/index.jsp">На головну сторінку</a>
    </div>
    <c:if test="${sessionScope.user.role.name.equals('ADMIN')}">
        <h3>Експозиції з кількість куплених квитків:</h3>
        <table class="expositions" style="border: 1px solid black;">
            <tr>
                <td>Topic</td>
                <td>Price</td>
                <td>From Date</td>
                <td>To Date</td>
                <td>Start Time</td>
                <td>End Time</td>
                <td>Amount of Tickets</td>
            </tr>
            <c:forEach items="${sessionScope.statistics}" var="exposition" varStatus="loop">
                <tr>
                    <td>
                        <c:out value="${exposition.topic}"/>
                    </td>
                    <td>
                        <c:out value="${exposition.price}"/>
                    </td>
                    <td>
                        <c:out value="${exposition.schedule.fromDate}"/>
                    </td>
                    <td>
                        <c:out value="${exposition.schedule.toDate}"/>
                    </td>
                    <td>
                        <c:out value="${exposition.schedule.startTime}"/>
                    </td>
                    <td>
                        <c:out value="${exposition.schedule.endTime}"/>
                    </td>
                    <td>
                        <c:out value="${exposition.ticketsCount}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
