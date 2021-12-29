<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Мої квитки</title>
</head>
<body>
<div class="container">
    <div class="content">
        <div class="back_to_main">
            <a href="/index.jsp">На головну сторінку</a>
        </div>
        <h3>Мої квитки:</h3>
        <table class="tickets" style="border: 1px solid black;">
            <tr>
                <td>Тема експозиції</td>
                <td>Вартість квитка</td>
                <td>Дата та час покупки</td>
                <td>Деталі</td>
            </tr>
                <c:forEach items="${sessionScope.tickets}" var="ticket" varStatus="loop">
                    <tr>
                        <td>
                            <c:out value="${ticket.exposition.topic}"/>
                        </td>
                        <td>
                            <c:out value="${ticket.exposition.price}"/>
                        </td>
                        <td>
                            <c:out value="${ticket.datetime}"/>
                        </td>
                        <td>
                            <form method="post" action="/command/">
                                <input hidden name="command" value="SHOW_EXPOSITION_DETAILS">
                                <input type="text" name="expositionId" hidden value="${ticket.exposition.id}"/>
                                <button type="submit">Показати деталі</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
