<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  <!-- ДОБАВЛЕНО -->

<!DOCTYPE html>
<html lang="ru">
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/boot.png">
  <link rel="alternate icon" type="image/png" href="${pageContext.request.contextPath}/images/boot.png">
  <meta charset="UTF-8"/>
  <title>Услуги</title>
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

<main class="container grid cols-2" style="margin-top:18px;">
  <section class="card">
    <h2>Список услуг</h2>

    <c:if test="${not empty error}">
      <div class="flash err">${fn:escapeXml(error)}</div>
    </c:if>

    <table class="table">
      <thead>
      <tr><th>ID</th><th>Название</th><th>Базовая цена</th><th></th></tr>
      </thead>
      <tbody>
      <c:forEach var="f" items="${favours}">
        <tr>
          <td>${f.favourId}</td>
          <td>${fn:escapeXml(f.favourName)}</td>
          <td>
            <c:choose>
              <c:when test="${f.basePrice != null}">
                <fmt:formatNumber value="${f.basePrice}"
                                  type="number"
                                  minFractionDigits="2"
                                  maxFractionDigits="2"/>
              </c:when>
              <c:otherwise>—</c:otherwise>
            </c:choose>
          </td>
          <td>
            <a class="btn ghost" href="${pageContext.request.contextPath}/favours?action=getById&id=${f.favourId}">Открыть</a>
            <form method="post" action="${pageContext.request.contextPath}/favours" style="display:inline" onsubmit="return confirm('Удалить услугу?')">
              <input type="hidden" name="action" value="delete">
              <input type="hidden" name="id" value="${f.favourId}">
              <input type="hidden" name="_csrf" value="${csrfToken}">
              <button class="btn danger" type="submit">Удалить</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty favours}">
        <tr><td colspan="4">Пока нет услуг</td></tr>
      </c:if>
      </tbody>
    </table>
  </section>

  <aside class="card">
    <h2>Добавить услугу</h2>
    <form method="post" action="${pageContext.request.contextPath}/favours">
      <input type="hidden" name="action" value="add">
      <input type="hidden" name="_csrf" value="${csrfToken}">

      <label>Название</label>
      <input class="input" type="text" name="favourName" required>

      <label style="margin-top:8px;">Базовая цена</label>
      <!-- number/step удобнее для цены -->
      <input class="input" type="number" step="0.01" min="0" name="basePrice" placeholder="например, 500.00" required>

      <div class="row" style="margin-top:12px;">
        <button class="btn" type="submit">Сохранить</button>
        <button class="btn secondary" type="reset">Очистить</button>
      </div>
    </form>
  </aside>
</main>
</body>
</html>
