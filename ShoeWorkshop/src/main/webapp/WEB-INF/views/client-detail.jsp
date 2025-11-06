<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8"/>
  <title>Клиент #${client.clientId}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vintage.css">
</head>
<body>

<header class="header">
  <div class="container">
    <div class="brand">ShoeWorkshop</div>
    <nav class="nav">
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

    <form method="post" action="${pageContext.request.contextPath}/clients">
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

      <div class="row" style="margin-top:12px;">
        <button class="btn" type="submit">Сохранить изменения</button>
        <a class="btn secondary" href="${pageContext.request.contextPath}/clients">Назад к списку</a>

        <form method="post" action="${pageContext.request.contextPath}/clients" onsubmit="return confirm('Удалить клиента?')" style="margin-left:auto">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="id" value="${client.clientId}">
          <input type="hidden" name="csrfToken" value="${csrfToken}">
          <button class="btn danger" type="submit">Удалить</button>
        </form>
      </div>
    </form>
  </div>
</main>

</body>
</html>
