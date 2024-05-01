<!DOCTYPE html>
<html>
<head>
    <title>Сервис размещения объявлений</title>
</head>
<body>
<h1>Сервис размещения объявлений</h1>
<hr>
<h3>Подробная информация о пользователе ${user.firstName}</h3>
<b>Номер телефона: </b> ${user.phoneNumber}<br>
<b>Email: </b> ${user.email}<br>
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