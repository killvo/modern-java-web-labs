<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Вхід</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/login_styles.css">
</head>
<body>
<div class="container">
    <form method="post" action="/command/">
        <input type="text" hidden name="command" value="LOGIN" />
        <h4>Форма авторизації</h4>
        <label>
            Логін:
            <input type="text" placeholder="Логін..." name="username" />
        </label>
        <label>
            Пароль:
            <input type="password" placeholder="Пароль..." name="password" />
        </label>
        <button type="submit">Увійти</button>

        <div>
            <a href="/register.jsp">Реєстрація</a>
        </div>
    </form>
</div>
</body>
</html>
