# Калькулятор вклада — Backend (Spring Boot)

REST API для расчёта итоговой суммы вклада с ежемесячной капитализацией процентов.

## Стек

- Java 17
- Spring Boot 3.3.4 (Web, Validation)
- Lombok
- Gradle (Groovy DSL)
- JUnit 5 + MockMvc + AssertJ (тесты)

## Запуск

```bash
gradle bootRun
```

Приложение поднимется на `http://localhost:8080`.

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
