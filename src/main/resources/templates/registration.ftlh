<!DOCTYPE html>
<html>
<head>
    <title>Сервис размещения объявлений</title>
</head>
<body>
<h1>Сервис размещения объявлений</h1>
<hr>
<h4>Регистрация</h4>
<form action="/registration" method="post">
    Имя: <input type="text" name="firstName"><br><br>
    Фамилия: <input type="text" name="lastName"><br><br>
    Эл. почта: <input type="email" name="email"><br><br>
    Номер телефона: <input type="text" name="phoneNumber"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Зарегистрироваться"/>
</form>
</body>
</html>