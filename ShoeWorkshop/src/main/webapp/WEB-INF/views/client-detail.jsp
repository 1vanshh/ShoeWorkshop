<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/boot.png">
  <link rel="alternate icon" type="image/png" href="${pageContext.request.contextPath}/images/boot.png">
  <meta charset="UTF-8"/>
  <title>Клиент #${client.clientId}</title>
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

<main class="container">
  <div class="card">
    <h2>Клиент #${client.clientId}</h2>

    <c:if test="${not empty error}">
      <div class="flash err">${fn:escapeXml(error)}</div>
    </c:if>

    <!-- ФОРМА ОБНОВЛЕНИЯ (ОТДЕЛЬНАЯ) -->
    <form method="post" action="${pageContext.request.contextPath}/clients" style="margin-bottom:12px;">
      <input type="hidden" name="action" value="update">
      <input type="hidden" name="id" value="${client.clientId}">
      <input type="hidden" name="csrfToken" value="${csrfToken}">

      <label>ФИО</label>
      <input class="input" type="text" name="fullName" value="${fn:escapeXml(client.fullName)}" required>

      <label style="margin-top:8px;">Email</label>
      <input class="input" type="email" name="email" value="${fn:escapeXml(client.email)}" required>

      <label style="margin-top:8px;">Телефон</label>
      <input class="input" type="text" name="phone" value="${fn:escapeXml(client.phoneNumber)}" required>

      <label style="margin-top:8px;">Адрес</label>
      <input class="input" type="text" name="address" value="${fn:escapeXml(client.address)}">

      <div class="row" style="gap:8px; margin-top:12px;">
        <button class="btn" type="submit">Сохранить изменения</button>
        <a class="btn secondary" href="${pageContext.request.contextPath}/clients">Назад</a>
      </div>
    </form>

    <!-- ФОРМА УДАЛЕНИЯ (ОТДЕЛЬНАЯ) -->
    <form method="post"
          action="${pageContext.request.contextPath}/clients"
          onsubmit="return confirm('Удалить клиента?');">
      <input type="hidden" name="action" value="delete">
      <input type="hidden" name="id" value="${client.clientId}">
      <input type="hidden" name="csrfToken" value="${csrfToken}">
      <button class="btn danger" type="submit">Удалить</button>
    </form>
  </div>
</main>

</body>
</html>
