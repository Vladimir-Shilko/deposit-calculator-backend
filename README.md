# Калькулятор вклада — Backend (Spring Boot)

REST API для расчёта итоговой суммы вклада с ежемесячной капитализацией процентов.

## Стек

- Java 17
- Spring Boot 3.3.4 (Web, Validation)
- Lombok
- Gradle (Groovy DSL)
- JUnit 5 + MockMvc + AssertJ (тесты)

## Структура проекта

```
src/main/java/com/example/deposit/
 ├── DepositCalculatorApplication.java   — точка входа
 ├── controller/CalculatorController.java — REST-эндпоинт /api/calculate
 ├── service/CalculatorService.java       — бизнес-логика расчёта (BigDecimal)
 ├── dto/CalculateRequest.java            — входные данные + валидация
 ├── dto/CalculateResponse.java           — ответ (total, profit)
 ├── exception/GlobalExceptionHandler.java — единый формат ошибок
 ├── exception/ErrorResponse.java
 └── config/CorsConfig.java               — CORS для фронтенда (React dev-сервер)

src/main/resources/application.yml
src/test/java/...                          — юнит- и интеграционные тесты

build.gradle       — конфигурация сборки (Groovy DSL)
settings.gradle    — имя проекта
```

## Запуск

```bash
gradle bootRun
```

Приложение поднимется на `http://localhost:8080`.

(Если в проект добавить Gradle Wrapper командой `gradle wrapper`, дальше можно
запускать через `./gradlew bootRun` без локально установленного Gradle.)

## Запуск тестов

```bash
gradle test
```

## Сборка jar

```bash
gradle clean build
java -jar build/libs/deposit-calculator-1.0.0.jar
```

## API

### POST /api/calculate

**Запрос:**

```json
{
  "amount": 100000,
  "months": 12,
  "rate": 8.5
}
```

| Поле   | Тип     | Ограничения             |
|--------|---------|--------------------------|
| amount | number  | от 1 000 до 10 000 000  |
| months | integer | от 1 до 60               |
| rate   | number  | от 1 до 20 (% годовых)  |

**Успешный ответ `200 OK`:**

```json
{
  "total": 108300.50,
  "profit": 8300.50
}
```

**Ответ при ошибке валидации `400 Bad Request`:**

```json
{
  "timestamp": "2026-09-20T10:00:00Z",
  "status": 400,
  "error": "Ошибка валидации",
  "messages": [
    "amount: Сумма вклада не может быть меньше 1000"
  ]
}
```

### Формула расчёта

```
Месячная ставка = Ставка / 100 / 12
Итог             = Сумма × (1 + Месячная ставка) ^ Срок_в_месяцах
Прибыль          = Итог − Сумма
```

Все вычисления выполняются через `BigDecimal` (без `double`/`float`), чтобы
избежать погрешностей округления при работе с денежными суммами.

## Пример проверки (curl)

```bash
curl -X POST http://localhost:8080/api/calculate \
  -H "Content-Type: application/json" \
  -d '{"amount": 100000, "months": 12, "rate": 8.5}'
```

## Примечания по best practices

- Валидация входных данных через `jakarta.validation` (`@Valid`, `@DecimalMin/Max`, `@Min/Max`) —
  ошибки не «утекают» в бизнес-логику.
- `GlobalExceptionHandler` (`@RestControllerAdvice`) — единый формат ошибок для всего API,
  без утечки стектрейсов клиенту.
- Слоистая архитектура: `controller → service → dto`, без бизнес-логики в контроллере.
- `BigDecimal` + `MathContext`/`RoundingMode.HALF_UP` — корректная работа с деньгами.
- CORS вынесен в отдельный конфиг (`CorsConfig`), не захардкожен в бизнес-логике.
- Тесты покрывают как сервис (расчёт), так и контроллер (HTTP-контракт, валидация).
- Конструкторная инъекция зависимостей (`@RequiredArgsConstructor`), без `@Autowired` на полях.
