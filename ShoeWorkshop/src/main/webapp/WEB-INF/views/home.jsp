<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8"/>
  <title>ShoeWorkshop — главная</title>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vintage.css">
</head>
<body>

<header class="header">
  <div class="container">
    <div class="brand">ShoeWorkshop</div>
    <nav class="nav">
      <a href="${pageContext.request.contextPath}/home">Главная</a>
      <a href="${pageContext.request.contextPath}/clients">Клиенты</a>
      <a href="${pageContext.request.contextPath}/favours">Услуги</a>
      <a href="${pageContext.request.contextPath}/receipts">Квитанции</a>
    </nav>
  </div>
</header>

<main class="container" style="margin-top:24px;">
  <section class="hero card" style="text-align:center;">
    <h1 style="margin-bottom:8px;">Обувная мастерская</h1>
    <p style="opacity:.85;">
      Учитывайте клиентов, услуги и квитанции в одном месте. Надёжно. Просто. Быстро.
    </p>

    <div class="row" style="justify-content:center; gap:12px; margin-top:16px;">
      <a class="btn" href="${pageContext.request.contextPath}/clients">Перейти к клиентам</a>
      <a class="btn" href="${pageContext.request.contextPath}/favours">Справочник услуг</a>
      <a class="btn" href="${pageContext.request.contextPath}/receipts">Журнал квитанций</a>
    </div>
  </section>

  <section class="grid cols-3" style="margin-top:18px;">
    <div class="card">
      <h3>Клиенты</h3>
      <p>Добавляйте и редактируйте карточки клиентов, ищите по email и имени.</p>
    </div>
    <div class="card">
      <h3>Услуги</h3>
      <p>Ведите прайс: название и базовая цена. Используется при расчёте позиций чека.</p>
    </div>
    <div class="card">
      <h3>Квитанции</h3>
      <p>Создавайте квитанции, добавляйте позиции, применяйте скидки и меняйте статус.</p>
    </div>
  </section>
</main>

<footer class="container" style="margin:24px auto; text-align:center; opacity:.7;">
  <small>&copy; <%= java.time.Year.now() %> ShoeWorkshop</small>
</footer>

</body>
</html>
