<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8"/>
  <title>Ошибка</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vintage.css">
</head>
<body>
<div class="container">
  <div class="card">
    <h2>Произошла ошибка</h2>
    <p style="white-space:pre-line;"><strong>${requestScope.error}</strong></p>
    <a class="btn" href="${pageContext.request.contextPath}/clients">К списку клиентов</a>
  </div>
</div>
</body>
</html>
