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
  <title>Квитанция #${receipt.receiptId}</title>
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

  <!-- Левая колонка: детали и позиции -->
  <section class="card">
    <h2>Квитанция #${receipt.receiptId}</h2>

    <c:if test="${not empty error}">
      <div class="flash err">${fn:escapeXml(error)}</div>
    </c:if>

    <div class="row">
      <div><b>Клиент:</b>
        <c:choose>
          <c:when test="${not empty receiptClient}">
            ${fn:escapeXml(receiptClient.fullName)}
          </c:when>
          <c:otherwise>ID ${receipt.clientId}</c:otherwise>
        </c:choose>
      </div>
      <div style="margin-left:16px;"><b>Статус:</b> ${fn:escapeXml(currentStatusName)}</div>
      <div style="margin-left:16px;">
        <b>Дата:</b>
        <c:choose>
          <c:when test="${not empty receipt.orderDate}">
            ${receipt.orderDate}
          </c:when>
          <c:otherwise>—</c:otherwise>
        </c:choose>
      </div>
    </div>

    <hr class="hr"/>

    <h3>Позиции</h3>
    <table class="table">
      <thead>
      <tr>
        <th>#</th>
        <th>Услуга (ID)</th>
        <th>Кол-во</th>
        <th>Цена за ед.</th>
        <th>Сумма</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="it" items="${items}" varStatus="st">
        <tr>
          <td>${st.index + 1}</td>
          <td>${it.favourId}</td>
          <td>${it.quantity}</td>
          <td><fmt:formatNumber value="${it.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
          <td><fmt:formatNumber value="${it.price * it.quantity}" minFractionDigits="2" maxFractionDigits="2"/></td>
        </tr>
      </c:forEach>

      <c:if test="${empty items}">
        <tr><td colspan="5">Позиции отсутствуют</td></tr>
      </c:if>
      </tbody>

      <tfoot>
      <tr>
        <td colspan="4" style="text-align:right;">Итого:</td>
        <td><b><fmt:formatNumber value="${total}" minFractionDigits="2" maxFractionDigits="2"/></b></td>
      </tr>
      </tfoot>
    </table>
  </section>

  <!-- Правая колонка: операции -->
  <aside class="card">
    <h3>Добавить позицию</h3>
    <form method="post" action="${pageContext.request.contextPath}/receipts">
      <input type="hidden" name="action"     value="addItem">
      <input type="hidden" name="csrfToken"  value="${csrfToken}">
      <input type="hidden" name="receiptId"  value="${receipt.receiptId}">

      <label>Услуга (ID)</label>
      <input class="input" type="number" name="favourId" min="1" required>

      <label style="margin-top:8px;">Количество</label>
      <input class="input" type="number" name="quantity" min="1" value="1" required>

      <label style="margin-top:8px;">Скидка (опционально)</label>
      <input class="input" type="text" name="discount" placeholder="0.1 = 10% или 100 = 100₽">

      <div class="row" style="margin-top:12px;">
        <button class="btn" type="submit">Добавить</button>
      </div>
    </form>

    <hr class="hr"/>

    <h3>Сменить статус</h3>
    <form method="post" action="${pageContext.request.contextPath}/receipts">
      <input type="hidden" name="action"     value="changeStatus">
      <input type="hidden" name="csrfToken"  value="${csrfToken}">
      <input type="hidden" name="receiptId"  value="${receipt.receiptId}">

      <label>Новый статус</label>
      <select class="select" name="statusId" required>
        <c:forEach var="s" items="${statuses}">
          <option value="${s.statusId}"
                  <c:if test="${s.statusId == receipt.statusId}">selected</c:if>>
              ${fn:escapeXml(s.statusName)}
          </option>
        </c:forEach>
      </select>

      <div class="row" style="margin-top:12px;">
        <button class="btn" type="submit">Применить</button>
      </div>
    </form>

    <hr class="hr"/>

    <h3>Завершить чек</h3>
    <form method="post" action="${pageContext.request.contextPath}/receipts"
          onsubmit="return confirm('Подсчитать чек?')">
      <input type="hidden" name="action"     value="finalize">
      <input type="hidden" name="csrfToken"  value="${csrfToken}">
      <input type="hidden" name="receiptId"  value="${receipt.receiptId}">
      <button class="btn" type="submit">Завершить</button>
    </form>

    <hr class="hr"/>

    <h3>Удалить чек</h3>
    <form method="post" action="${pageContext.request.contextPath}/receipts"
          onsubmit="return confirm('Удалить квитанцию полностью?')">
      <input type="hidden" name="action"     value="delete">
      <input type="hidden" name="csrfToken"  value="${csrfToken}">
      <input type="hidden" name="id"         value="${receipt.receiptId}">
      <button class="btn danger" type="submit">Удалить</button>
    </form>

    <div class="row" style="margin-top:12px;">
      <a class="btn secondary" href="${pageContext.request.contextPath}/receipts">Назад к списку</a>
    </div>
  </aside>
</main>

</body>
</html>
