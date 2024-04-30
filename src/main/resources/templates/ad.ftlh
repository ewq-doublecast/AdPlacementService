<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сервис размещения объявлений</title>
</head>
<body>
<h1>Подробная информация</h1>
<hr>
<div>
    Название: ${ad.title} <br>
    Описание: ${ad.description} <br>
    Город: ${ad.city} <br>
    Цена: ${ad.price} <br>
    Автор: ${ad.author} <br>
</div>
<hr>
<form action="/ad/delete/${ad.id}" method="post">
    <input type="submit" value="Удалить"/>
</form>
</body>
</html>