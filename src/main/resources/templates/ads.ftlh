<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сервис размещения объявлений</title>
</head>
<body>
<h1>Сервис размещения объявлений</h1>
<hr>
<form action="/" method="get">
    Поиск по названию объявления: <input type="text" name="title"><br>
    <input type="submit" value="Поиск"/>
</form>
<hr>
<#list ads as ad>
    <div>
        Название: ${ad.title} |
        Описание: ${ad.description} |
        Город: ${ad.city} |
        Цена: ${ad.price} |
        Автор: ${ad.author} |
        <a href="/ad/${ad.id}">Подробнее</a>
    </div>
<#else>
    <div>Товаров нет</div>
</#list>
<hr>
<a href="/ad/create">Создать</a>
</body>
</html>