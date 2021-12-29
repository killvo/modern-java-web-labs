<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Виставки</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/index.styles.css">
</head>

<body>
<div class="auth">
    <c:if test="${sessionScope.user == null}">
        <a href="/login.jsp">Log In</a>
    </c:if>
    <c:if test="${sessionScope.user != null}">
        <form method="post" action="/command/">
            <input type="text" hidden name="command" value="LOGOUT"/>
            <a onclick="this.closest('form').submit();return false;">Log Out</a>
        </form>
    </c:if>
</div>

<div class="container">
    <c:if test="${sessionScope.user.role.name.equals('ADMIN')}">
        <h3>Меню:</h3>
        <div class="admin_block">
            <form method="post" action="/command/">
                <input type="text" hidden name="command" value="SHOW_ADMIN_CREATE_HALL">
                <button type="submit">Дадати зал</button>
            </form>
            <form method="post" action="/command/">
                <input type="text" hidden name="command" value="SHOW_ADMIN_CREATE_EXPOSITION">
                <button type="submit">Створити експозицію</button>
            </form>
        </div>
        <form method="post" action="/command/">
            <input type="text" hidden name="command" value="SHOW_STATISTICS">
            <button type="submit">Показати статистику</button>
        </form>
        <form method="post" action="/command/">
            <input type="text" hidden name="command" value="SHOW_MAIN_EXPOSITIONS">
            <button type="submit">Показати експозиції</button>
        </form>
        <h3>Експозиції:</h3>
        <table class="expositions" style="border: 1px solid black;">
            <tr>
                <td>Topic</td>
                <td>Price</td>
                <td>From Date</td>
                <td>To Date</td>
                <td>Start Time</td>
                <td>End Time</td>
                <td>Cancel</td>
            </tr>
            <c:forEach items="${sessionScope.expositions}" var="exposition" varStatus="loop">
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
                        <c:if test="${!exposition.cancelled}">
                            <form method="post" action="/command/">
                                <input hidden name="command" value="CANCEL_EXPOSITION">
                                <input type="text" name="expositionId" hidden value="${exposition.id}"/>
                                <button type="submit">Cancel</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>


    <c:if test="${sessionScope.user.role.name.equals('USER')}">
        <form method="post" action="/command/">
            <input hidden name="command" value="SHOW_TICKETS">
            <button type="submit">Мої квитки</button>
        </form>
    </c:if>

    <c:if test="${!sessionScope.user.role.name.equals('ADMIN')}">
        <form method="post" action="/command/">
            <input type="text" hidden name="command" value="SHOW_MAIN_EXPOSITIONS">
            <input type="text" hidden name="activityId" value="${sessionScope.activityId}" />
            <label>
                Фільтрувати за:
                <select name="showBy">
                    <option selected></option>
                    <option value="TOPIC">За темою</option>
                    <option value="PRICE">За вартістю квитка</option>
                    <option value="DATE">За датою</option>
                </select>
            </label>
            <label>
                Тема:
                <input type="text" name="topic" />
            </label>
            <label>
                Вартість від:
                <input type="text" name="priceFrom" />
            </label>
            <label>
                Вартість до:
                <input type="text" name="priceTo" />
            </label>
            <label>
                Дата:
                <input type="date" name="date" />
            </label>
            <button type="submit">Показати експозиції</button>
        </form>
        <h3>Експозиції:</h3>
        <table class="expositions" style="border: 1px solid black;">
            <tr>
                <td>Topic</td>
                <td>Price</td>
                <td>From Date</td>
                <td>To Date</td>
                <td>Start Time</td>
                <td>End Time</td>
                <c:if test="${sessionScope.user.role.name.equals('USER')}">
                    <td>Buy Ticket</td>
                </c:if>
            </tr>
            <c:forEach items="${sessionScope.expositions}" var="exposition" varStatus="loop">
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
                        <c:if test="${sessionScope.user.role.name.equals('USER')}">
                            <div data-label="Buy Ticket">
                                <c:if test="${!exposition.cancelled}">
                                    <form method="post" action="/command/">
                                        <input hidden name="command" value="BUY_TICKET">
                                        <input type="text" name="expositionId" hidden value="${exposition.id}"/>
                                        <button type="submit">Buy</button>
                                    </form>
                                </c:if>
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
