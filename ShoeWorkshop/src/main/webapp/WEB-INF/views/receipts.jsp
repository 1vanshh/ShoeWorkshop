<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/boot.png">
  <link rel="alternate icon" type="image/png" href="${pageContext.request.contextPath}/images/boot.png">
  <meta charset="UTF-8"/>
  <title>Квитанции</title>
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

  <!-- Список квитанций -->
  <section class="card">
    <h2>Квитанции</h2>

    <c:if test="${not empty error}">
      <div class="flash err">${fn:escapeXml(error)}</div>
    </c:if>
    <c:if test="${not empty info}">
      <div class="flash ok">${fn:escapeXml(info)}</div>
    </c:if>

    <table class="table">
      <thead>
      <tr>
        <th>ID</th>
        <th>Клиент</th>
        <th>Статус</th>
        <th>Дата</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="r" items="${receipts}">
        <tr>
          <td>${r.receiptId}</td>
          <td>${r.clientId}</td>
          <td>${r.statusId}</td>
          <td>
            <c:choose>
              <c:when test="${not empty r.orderDate}">
                ${r.orderDate}
              </c:when>
              <c:otherwise>—</c:otherwise>
            </c:choose>
          </td>
          <td>
            <a class="btn ghost"
               href="${pageContext.request.contextPath}/receipts?action=getById&id=${r.receiptId}">Открыть</a>

            <form method="post"
                  action="${pageContext.request.contextPath}/receipts"
                  style="display:inline"
                  onsubmit="return confirm('Удалить квитанцию?')">
              <input type="hidden" name="action"    value="delete">
              <input type="hidden" name="id"        value="${r.receiptId}">
              <input type="hidden" name="csrfToken" value="${csrfToken}">
              <button class="btn danger" type="submit">Удалить</button>
            </form>
          </td>
        </tr>
      </c:forEach>

      <c:if test="${empty receipts}">
        <tr><td colspan="5">Пока нет квитанций</td></tr>
      </c:if>
      </tbody>
    </table>
  </section>

  <!-- Создание новой квитанции -->
  <aside class="card">
    <h2>Создать квитанцию</h2>

    <form method="post" action="${pageContext.request.contextPath}/receipts">
      <input type="hidden" name="action"    value="create">
      <input type="hidden" name="csrfToken" value="${csrfToken}">

      <label>Клиент (ID)</label>
      <input class="input" type="number" name="clientId" min="1" required>

      <label style="margin-top:8px;">Статус (ID)</label>
      <input class="input" type="number" name="statusId" min="1" required>

      <%--
      Если начнёшь прокидывать список статусов в request как "statuses",
      раскомментируй это и УДАЛИ input выше:

      <label style="margin-top:8px;">Статус</label>
      <select class="select" name="statusId" required>
        <c:forEach var="s" items="${statuses}">
          <option value="${s.statusId}">${fn:escapeXml(s.statusName)}</option>
        </c:forEach>
      </select>
      --%>

      <div class="row" style="margin-top:12px;">
        <button class="btn" type="submit">Создать</button>
        <button class="btn secondary" type="reset">Очистить</button>
      </div>
    </form>
  </aside>
</main>

</body>
</html>
