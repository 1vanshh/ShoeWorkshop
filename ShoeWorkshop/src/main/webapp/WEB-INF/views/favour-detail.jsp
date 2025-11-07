<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/boot.png">
  <meta charset="UTF-8"/>
  <title>Услуга #${favour.favourId}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header class="header">
  <div class="container">
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
    <h2>Услуга #${favour.favourId}</h2>

    <c:if test="${not empty error}">
      <div class="flash err">${fn:escapeXml(error)}</div>
    </c:if>

    <!-- ФОРМА ОБНОВЛЕНИЯ -->
    <form method="post" action="${pageContext.request.contextPath}/favours" id="favourUpdateForm">
      <input type="hidden" name="action" value="update">
      <input type="hidden" name="id" value="${favour.favourId}">
      <input type="hidden" name="_csrf" value="${csrfToken}">

      <label>Название</label>
      <input class="input" type="text" name="favourName"
             value="${fn:escapeXml(favour.favourName)}" required>

      <label style="margin-top:8px;">Базовая цена</label>
      <input class="input" type="number" name="basePrice" step="0.01" min="0"
             value="${favour.basePrice}" required>
    </form>

    <!-- Кнопочная панель: три кнопки в один ряд -->
    <div class="row" style="gap:8px; margin-top:12px; align-items:center;">
      <button class="btn" type="submit" form="favourUpdateForm">Сохранить</button>
      <a class="btn secondary" href="${pageContext.request.contextPath}/favours">Назад</a>

      <!-- ФОРМА УДАЛЕНИЯ ОТДЕЛЬНО -->
      <form method="post" action="${pageContext.request.contextPath}/favours"
            onsubmit="return confirm('Удалить услугу?')">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="${favour.favourId}">
        <input type="hidden" name="_csrf" value="${csrfToken}">
        <button class="btn danger" type="submit">Удалить</button>
      </form>
    </div>

  </div>
</main>
</body>
</html>
