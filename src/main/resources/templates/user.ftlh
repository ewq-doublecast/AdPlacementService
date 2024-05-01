<!DOCTYPE html>
<html>
<head>
    <title>Сервис размещения объявлений</title>
</head>
<body>
<h1>Сервис размещения объявлений</h1>
<hr>
<div>Подробная информация о пользователе: ${user.firstName}</div>
<div>Номер телефона:</div> ${user.phoneNumber}<br>
<div>Email:</div> ${user.email}<br>
<div>Средний рейтинг пользователя: ${user.rating}</div>
<h4>Товары пользователя ${user.firstName}</h4>
<#list ads as ad>
    <div>
        <p><b>${ad.title}</b> ${ad.price} руб. | <a href="/ad/${ad.id}">Подробнее</a></p>
    </div>
<#else>
    <h3>Товаров нет</h3>
</#list>
</body>
</html>