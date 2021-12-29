<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Реєстрація</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/register_styles.css">
</head>
<body>
<div class="container">
    <form class="register-form" method="post" action="/command/">
        <input type="text" hidden name="command" value="REGISTER" />
        <h4>Форма реєстрації</h4>
        <label>
            Логін:
            <input type="text" placeholder="Логін..." name="username" />
        </label>
        <label>
            Пароль:
            <input type="password" placeholder="Пароль..." name="password" />
        </label>
        <button type="submit">Зареєструватися</button>
    </form>
</div>
</body>
</html>
