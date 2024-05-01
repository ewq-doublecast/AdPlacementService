<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сервис размещения объявлений</title>
</head>
<body>
<h1>Подробная информация</h1>
<hr>
<#list images as img>
    <img src="/images/${img.id}" height="60px"/><br><br>
</#list>
<div>
    Название: ${ad.title} <br>
    Описание: ${ad.description} <br>
    Город: ${ad.city} <br>
    Цена: ${ad.price} <br>
    Автор: ${ad.user.firstName} <br>
</div>
<hr>
<form action="/ad/delete/${ad.id}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Удалить"/>
</form>
</body>
</html>