# Information Security "Secure Spring Boot API" lab

---

# Описание проекта

Данный проект представляет из себя веб-приложение, демонстрирующее современные практики веб-безопасности, реализуя с помощью Java/Spring:
- JWT-аутентификация.
- Валидация входных данных для предотвращения XSS атак.
- ORM запросы для предотвращения использования SQL-инъекции.
- Конфигурация на основе переменных окружения для безопасного развёртывания приложения.

# Методы API

## 1. POST /auth/login -- Аутентификация пользователя

Описание:
Метод принимает логин и пароль пользователя. В случае успешной аутентификации возвращает JWT-токен.

Request:

POST /auth/login

```
{
  "username": "user",
  "password": "password"
}
```

Response (JSON):

```
{
    "token": "eyJ0eXAiOiJKV...",
    "errorMsg": null
}
```

Ошибки:

* 403 Forbidden -- если логин/пароль неверны.

---

## 2. GET /api/data -- Получение всех файлов с исходным кодом из заданного репозитория

Описание:
Возвращает список сущностей Tracks. Доступ только для аутентифицированных пользователей (с валидным JWT).

Request:

GET /api/data
Authorization: Bearer <JWT>

```
{
    "repoName": "InformationSecurity"
}
```

Response (JSON):

```
[
    {
        "id": 1,
        "username": "tekassh1",
        "name": "InformationSecurity",
        "fileName": "file1.cpp",
        "sourceCode": "#include <iostream> #include <fstream> using namespace std; int main() { // Create and open a text file ofstream MyFile(\"filename.txt\"); // Write to the file MyFile << \"Files can be tricky, but it is fun enough!\"; // Close the file MyFile.close(); }"
    },
    {
        "id": 2,
        "username": "tekassh1",
        "name": "InformationSecurity",
        "fileName": "file2.java",
        "sourceCode":"import java.util.Scanner;\\r\\n\\r\\npublic class HelloJava {\\r\\n    public static void main(String[] args) {\\r\\n        Scanner scanner = new Scanner(System.in);\\r\\n        System.out.print(\\\"\В\в\е\д\и\т\е \в\а\ш\е \и\м\я: \\\");\\r\\n        String name = scanner.nextLine();\\r\\n        System.out.println(\\\"\П\р\и\в\е\т, \\\" + name + \\\"!\\\");\\r\\n        scanner.close();\\r\\n    }\\r\\n}\\r\\n"
    },
    {
        "id": 3,
        "username": "tekassh1",
        "name": "InformationSecurity",
        "fileName": "file3.py",
        "sourceCode": "def main(): name = input(\"Введите ваше имя: \") print(f\"Привет, {name}!\") if __name__ == \"__main__\": main()"
    },
    {
        "id": 4,
        "username": "tekassh1",
        "name": "InformationSecurity",
        "fileName": "file4.ts",
        "sourceCode": "import * as readline from 'readline'; const rl = readline.createInterface({ input: process.stdin, output: process.stdout }); rl.question(\"Введите ваше имя: \", (name: string) => { console.log(`Привет, ${name}!`); rl.close(); });"
    }
]
```

Ошибки:

* 403 Forbidden -- если отсутствует или невалиден JWT.

---

## 3. POST /api/data -- Добавление нового файла с исходным кодом в репозиторий

Описание:
Добавляет новую сущность repository в список. Доступ только для аутентифицированных пользователей.

Request:

POST /api/data
Authorization: Bearer <JWT>

```
{
    "repoName": "InformationSecurity",
    "fileName": "file5.ts",
    "sourceCode": "import * as readline from 'readline'; const rl = readline.createInterface({ input: process.stdin, output: process.stdout }); rl.question(\"\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0432\u0430\u0448\u0435 \u0438\u043C\u044F: \", (name: string) => { console.log(`\u041F\u0440\u0438\u0432\u0435\u0442, ${name}!`); rl.close(); });"
}
```

Response:

OK

Ошибки:

* 400 Bad Request -- если данные невалидны.
* 403 Forbidden -- если отсутствует или невалиден JWT.

# Меры защиты

API использует JWT-токены для защиты эндпоинтов.

* Пользователь проходит аутентификацию через POST /auth/login.
* В ответ получает accessToken (JWT).
* Для доступа к защищённым эндпоинтам необходимо передавать токен в заголовке:

Authorization: Bearer <JWT>

## Противодействие SQL Injection (SQLi)

* Используется Spring Data JDBC, который применяет параметризованные запросы → данные подставляются безопасно, без конкатенации SQL-строк.
* Таким образом, классические SQLi невозможны.

## Противодействие XSS

* REST API возвращает только JSON и не исполняет JavaScript.
* Устанавливаются заголовки HTTP X-XSS-Protection: 1; mode=block и Content-Security-Policy: script-src 'self'

## Реализация аутентификации

* Используется JWT-токен, который подписывается секретным ключом.
* Токен проверяется middleware (Spring Security OncePerRequestFilter).
* Без токена доступ к защищённым эндпоинтам запрещён.

# Скриншот отчета OWASP

# Результат SpotBugs
