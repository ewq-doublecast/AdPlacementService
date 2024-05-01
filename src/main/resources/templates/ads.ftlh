<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сервис размещения объявлений</title>
</head>
<body>
<h1>Сервис размещения объявлений</h1>
<#if user.email??>
    <h3>Имя: ${user.firstName}</h3>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Выйти"/>
    </form>
    <#if user.isAdmin()>
        <a href="/admin">Панель администратора</a>
    </#if>
<#else>
    <a href="/login">Войти</a>
</#if>
<hr>
<form action="/" method="get">
    Поиск по названию объявления: <input type="text" name="title"><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Поиск"/>
</form>
<hr>
<#list ads as ad>
    <#if ad.onModeration == false>
        <div>
            <img src="/images/${ad.previewImageId}" alt="" height="60px">
            Название: ${ad.title} |
            Город: ${ad.city} |
            Цена: ${ad.price} |
            <a href="/ad/${ad.id}">Подробнее</a>
        </div>
    <#else>
        <div>Товаров нет</div>
    </#if>
</#list>
<hr>
<a href="/ad/create">Создать</a>
</body>
</html>