<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/boot.png">
    <link rel="alternate icon" type="image/png" href="${pageContext.request.contextPath}/images/boot.png">
    <meta charset="UTF-8"/>
    <title>Клиенты</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="header">
    <div class="container">
        <!-- Логотип-кнопка на главную -->
        <a class="brand" href="${pageContext.request.contextPath}/home" title="На главную">ShoeWorkshop</a>

        <nav class="nav">
            <a href="${pageContext.request.contextPath}/home">Главная</a>
            <a href="${pageContext.request.contextPath}/clients">Клиенты</a>
            <a href="${pageContext.request.contextPath}/receipts">Квитанции</a>
            <a href="${pageContext.request.contextPath}/favours">Услуги</a>
        </nav>
    </div>
</header>

<main class="container grid cols-2" style="margin-top:18px;">
    <!-- Список и поиск -->
    <section class="card">
        <h2>Клиенты</h2>

        <c:if test="${not empty error}">
            <div class="flash err">${fn:escapeXml(error)}</div>
        </c:if>
        <c:if test="${not empty info}">
            <div class="flash ok">${fn:escapeXml(info)}</div>
        </c:if>

        <form class="row" method="get" action="${pageContext.request.contextPath}/clients">
            <input type="hidden" name="action" value="search">
            <input class="input" type="text" name="query" value="${fn:escapeXml(param.query)}"
                   placeholder="Поиск по ФИО, телефону или email" />
            <button class="btn" type="submit">Искать</button>
            <a class="btn secondary" href="${pageContext.request.contextPath}/clients">Сбросить</a>
        </form>

        <hr class="hr"/>

        <table class="table">
            <thead>
            <tr>
                <th>ID</th><th>ФИО</th><th>Телефон</th><th>Email</th><th>Адрес</th><th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cItem" items="${clients}">
                <tr>
                    <td>${cItem.clientId}</td>
                    <td>${fn:escapeXml(cItem.fullName)}</td>
                    <td>${fn:escapeXml(cItem.phoneNumber)}</td>
                    <td>${fn:escapeXml(cItem.email)}</td>
                    <td>${fn:escapeXml(cItem.address)}</td>
                    <td class="row">
                        <a class="btn ghost" href="${pageContext.request.contextPath}/clients?action=getById&id=${cItem.clientId}">Открыть</a>
                        <form method="post" action="${pageContext.request.contextPath}/clients" onsubmit="return confirm('Удалить клиента?')">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${cItem.clientId}">
                            <input type="hidden" name="csrfToken" value="${csrfToken}">
                            <button class="btn danger" type="submit">Удалить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty clients}">
                <tr><td colspan="6">Записей нет</td></tr>
            </c:if>
            </tbody>
        </table>
    </section>

    <!-- Форма добавления -->
    <aside class="card">
        <h2>Добавить клиента</h2>
        <form method="post" action="${pageContext.request.contextPath}/clients">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="csrfToken" value="${csrfToken}">

            <label>ФИО</label>
            <input class="input" type="text" name="fullName" value="${fn:escapeXml(param.fullName)}" required/>

            <label style="margin-top:8px;">Email</label>
            <input class="input" type="email" name="email" value="${fn:escapeXml(param.email)}" required/>

            <label style="margin-top:8px;">Телефон</label>
            <input class="input" type="text" name="phone" value="${fn:escapeXml(param.phone)}" required/>

            <label style="margin-top:8px;">Адрес</label>
            <input class="input" type="text" name="address" value="${fn:escapeXml(param.address)}"/>

            <div class="row" style="margin-top:12px;">
                <button class="btn" type="submit">Сохранить</button>
                <button class="btn secondary" type="reset">Очистить</button>
            </div>
        </form>
    </aside>
</main>

</body>
</html>
