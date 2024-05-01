<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сервис размещения объявлений</title>
</head>
<body>
<form action="/ad/create" method="post" enctype="multipart/form-data">
    Название: <input type="text" name="title"/><br>
    Описание: <input type="text" name="description"/><br>
    Город: <input type="text" name="city"/><br>
    Цена: <input type="number" name="price"/><br>
    Первая фотография: <input type="file" name="file1"/><br><br>
    Вторая фотография: <input type="file" name="file2"/><br><br>
    Третья фотография: <input type="file" name="file3"/><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Создать"/>
</form>
</body>
</html>