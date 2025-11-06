<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8"/>
  <title>Услуга #${favour.favourId}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vintage.css">
</head>
<body>
<header class="header">
  <div class="container">
    <div class="brand">ShoeWorkshop</div>
    <nav class="nav">
      <a href="${pageContext.request.contextPath}/favours">Услуги</a>
      <a href="${pageContext.request.contextPath}/clients">Клиенты</a>
      <a href="${pageContext.request.contextPath}/receipts">Квитанции</a>
    </nav>
  </div>
</header>

<main class="container">
  <div class="card">
    <h2>Услуга #${favour.favourId}</h2>

    <c:if test="${not empty error}">
      <div class="flash err">${fn:escapeXml(error)}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/favours">
      <input type="hidden" name="action" value="update">
      <input type="hidden" name="id" value="${favour.favourId}">
      <input type="hidden" name="_csrf" value="${csrfToken}">

      <label>Название</label>
      <input class="input" type="text" name="favourName" value="${fn:escapeXml(favour.favourName)}" required>

      <label style="margin-top:8px;">Базовая цена</label>
      <input class="input" type="text" name="basePrice" value="${favour.basePrice}" required>

      <div class="row" style="margin-top:12px;">
        <button class="btn" type="submit">Сохранить</button>
        <a class="btn secondary" href="${pageContext.request.contextPath}/favours">Назад</a>
        <form method="post" action="${pageContext.request.contextPath}/favours" onsubmit="return confirm('Удалить услугу?')" style="margin-left:auto">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="id" value="${favour.favourId}">
          <input type="hidden" name="_csrf" value="${csrfToken}">
          <button class="btn danger" type="submit">Удалить</button>
        </form>
      </div>
    </form>
  </div>
</main>
</body>
</html>
