<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сервис размещения объявлений</title>
</head>
<body>
<form action="/ad/create" method="post">
    Название: <input type="text" name="title"/><br>
    Описание: <input type="text" name="description"/><br>
    Город: <input type="text" name="city"/><br>
    Цена: <input type="number" name="price"/><br>
    Автор: <input type="text" name="author"/><br>
    <input type="submit" value="Создать"/>
</form>
</body>
</html>