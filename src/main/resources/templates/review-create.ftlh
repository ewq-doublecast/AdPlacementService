<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сервис размещения объявлений</title>
</head>
<body>
<form action="/review/create/${ad.id}" method="post">
    Оценка: <input type="number" name="rating"/><br>
    Текст: <input type="text" name="text"/><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Создать"/>
</form>
</body>
</html>