<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8"/>
  <title>Произошла ошибка</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%
  // 1) База
  String ctx = request.getContextPath();
  String defaultBack = ctx + "/receipts";

  // 2) Источники "куда вернуться"
  String back = request.getParameter("back");
  if (back == null || back.isBlank()) {
    back = request.getHeader("Referer");
  }

  // 3) Безопасная нормализация
  String backUrl = defaultBack;
  try {
    if (back != null && !back.isBlank()) {
      back = back.trim();

      // относительный путь внутри приложения
      if (back.startsWith("/")) {
        backUrl = ctx + back;
      }
      // абсолютный URL, но на наш же хост (простая проверка на host)
      else {
        String scheme = request.getScheme();
        String host   = request.getServerName();
        String prefix = scheme + "://" + host;
        if (back.startsWith(prefix)) {
          backUrl = back;
        }
        // ещё один вариант — уже содержит contextPath (подходит для http://.../app/xxx)
        else if (back.contains(ctx)) {
          backUrl = back;
        }
      }
    }
  } catch (Exception ignore) {
    backUrl = defaultBack;
  }

  request.setAttribute("backUrl", backUrl);
%>

<div class="container">
  <div class="card">
    <h2>Произошла ошибка</h2>

    <c:if test="${not empty error}">
      <p style="white-space:pre-line;"><strong>${error}</strong></p>
    </c:if>

    <div class="row" style="margin-top:12px;">
      <a class="btn" href="${backUrl}">Назад</a>
    </div>
  </div>
</div>

</body>
</html>
