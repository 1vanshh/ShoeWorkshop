# ShoeWorkshop
### (Java, Servlets, JSP, JDBC, PostgreSQL)
Веб-приложение для обувной мастерской: клиенты, услуги, квитанции, позиции квитанций и статусы заказа.
Без Spring/Spring Boot. Чистые Servlets + JSP + JDBC на Tomcat 11 и PostgreSQL.

## Содержание
- [Стек](#стек)
- [Архитектура слоёв](#архитектура-слоёв)
- [Структура проекта](#структура-проекта)
- [Сущности (entities)](#сущности-entities)
- [Репозитории (DAO/JDBC)](#репозитории-daojdbc)
- [Сервисы (business)](#сервисы-business)
- [Web-слой (Servlets + JSP)](#web-слой-servlets--jsp)
- [Безопасность](#безопасность)
- [Конфигурация БД](#конфигурация-бд)
- [Запуск](#запуск)
- [Эндпоинты и сценарии](#эндпоинты-и-сценарии)
- [ER-диаграмма](#er-диаграмма)
- [Maven](#maven)
- [Траблшутинг](#траблшутинг)
- [TODO](#todo)
- [Лицензия](#лицензия)

---

## Стек

- **Java 21 (LTS)**
- **Apache Tomcat 11** (Jakarta Servlet 6)
- **JSP + JSTL**
- **PostgreSQL** + **JDBC (DriverManager)**
- OWASP Java HTML Sanitizer (опционально; для строгой очистки HTML)
- Maven

---

## Архитектура слоёв

- **entities** — простые POJO-модели таблиц БД.
- **repository (DAO, JDBC)** — доступ к БД через `PreparedStatement`; никаких ORM.
- **service** — бизнес-логика (валидация, расчёты, правила, смена статусов).
- **web** — контроллеры на **Servlets**, представления на **JSP**, статика (CSS, favicon).
- **db** — конфигурация и фабрика соединений.

---

## Структура проекта

---

## Сущности (entities)

- **Client**: `clientId`, `fullName`, `phone`, `email`, `address`
- **Favour**: `favourId`, `favourName`, `basePrice`
- **Receipt**: `receiptId`, `clientId`, `statusId`, `orderDate`
- **ReceiptItem**: `itemId`, `receiptId`, `favourId`, `price`, `quantity`
- **OrderStatus**: `statusId`, `statusName`

> В `ReceiptItem.price` хранится **цена за единицу** позиции на момент добавления (с учётом скидки), итог считается в сервисе.

---

## Репозитории (DAO/JDBC)

- Полностью на **PreparedStatement** (защита от SQL-инъекций).
- Типовые методы: `findById`, `findAll`, `add`, `update`, `delete`.
- Дополнительно:
  - `ClientRepository`: `findByEmail`, `searchClients(query, limit, offset)`, `sortClients(asc)`
  - `ReceiptRepository`: `findByClientId`, `updateStatus`, ...
  - `ReceiptItemRepository`: `findByReceiptId`, `deleteByReceiptId`
  - `OrderStatusRepository`: `findByName`, `getAll`

---

## Сервисы (business)

- **ClientService**: валидация (email/phone), CRUD, поиск, сортировка по имени.
- **FavourService**: CRUD, форматирование цен.
- **ReceiptService**:
  - `createReceipt(clientId, statusId)`
  - `addItem(receiptId, favourId, quantity, optionalDiscount)`  
    Скидка может быть `0.1` (10%) или `100.0` (абсолютная). Округление до 2 знаков.
  - `calculateTotal(receiptId)`
  - `changeStatus(receiptId, newStatusId)`
  - `finalizeReceipt(receiptId)` — меняет статус на “Выдан” (или твой конечный статус)
  - `getItemsByReceiptId(receiptId)`, `findByClientId(clientId)` и CRUD чеков

Бизнес-валидация: существование сущностей, положительные значения, корректная скидка и т.д.

---

## Web-слой (Servlets + JSP)

- **HomeServlet** → `/home`
- **ClientServlet** → `/clients`
  - `GET /clients` — список, поиск (action=search), сортировка по ФИО (`sort=name&dir=asc|desc`)
  - `GET /clients?action=getById&id=...` — карточка клиента
  - `POST /clients` (`action=add|update|delete`) — операции над клиентом
- **FavourServlet** → `/favours`
  - Аналогично: список/карточка/CRUD
- **ReceiptServlet** → `/receipts`
  - `GET /receipts` — список
  - `GET /receipts?action=getById&id=...` — детали чека (+ список статусов)
  - `POST /receipts`:
    - `action=create` — создать чек
    - `action=addItem` — добавить позицию
    - `action=changeStatus` — выбрать новый статус
    - `action=finalize` — завершить чек
    - `action=delete` — удалить чек (с позициями)

**JSP**: вывод через JSTL, все пользовательские строки экранируются `fn:escapeXml(...)`.  
Фавикон: `/images/boot.png`. В шапке — навигация на главную/клиенты/квитанции/услуги.  
Стили: винтажный вид, отдельный стиль для ссылок-сортировок (`.sort-link`, стрелочки `▲/▼/⇅`).

---

## Безопасность

- **SQL-инъекции**: только `PreparedStatement` (никаких конкатенаций SQL).
- **XSS**:
  - В JSP: `fn:escapeXml(...)` для всех пользовательских значений.
  - `InputSanitizationFilter` + `SanitizedRequestWrapper` — нормализация входных данных.
  - (Опционально) **OWASP Java HTML Sanitizer** (`HtmlSanitizerUtil`) — если нужно whitelist-HTML.
- **CSRF**: `CsrfFilter`
  - Токен в сессии → скрытое поле формы `csrfToken` / `_csrf` → проверка в фильтре.
- **Security headers**: `SecurityHeadersFilter`
  - `Content-Security-Policy`
  - `X-Frame-Options: DENY`
  - `X-Content-Type-Options: nosniff`
  - `Referrer-Policy`, `Permissions-Policy`
  - `X-XSS-Protection` (для старых браузеров)

---

## Конфигурация БД

`src/main/resources/db.properties`:

```properties
db.url=jdbc:postgresql://localhost:5432/Shoe_Workshop
db.user=postgres
db.password=your_password
