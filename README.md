# Задание:

## 🧪 В проекте с автотестами на Rest Assured:

* Прокиньте токен из @BeforeAll во все тесты.
* Сделайте это без статики.
* Сохраните архитектурную чистоту (SOLID, KISS).

[Оригинал задания](https://t.me/qa_guru/1072)

### Что было сделано:
1. Создан TokenManager - Хранит токены по ролям (GUEST, AUTH) в ThreadLocal<EnumMap<>>

Использует ленивую инициализацию computeIfAbsent
Позволяет получить токен через TokenManager.getToken() или .getToken(UserRole)

2. Создано JUnit-расширение - GuestTokenExtension и заготовка для будущего AuthTokenExtension

В beforeAll() устанавливает роль (TokenManager.setCurrentRole(...))
И иницилизируется токен (TokenManager.getToken())

3. Контроллер (BagController) не знает о ролях

Просто вызывает TokenManager.getToken() — и получает нужный токен
Роль уже была установлена расширением → нет дублирования

4. Отдельный контроллер для токенов (TokenClient)

Передаем авторизационный хедер из свойств, если нужно можно даже сделать секретными данными, для Guest общедоступная информация.

### Почему решение архитектурно чистое:

1. KISS	- Простой TokenManager, один вызов в контроллере
2. Single Responsibility Principle - TokenManager отвечает только за токены, контроллер — за API
3. Open/Closed Principle - Добавить новую роль — легко (новое расширение)
4. Dependency Inversion Principle - Контроллер не зависит напрямую от способа получения токена
5. Без static в тестах	- всё управление токеном — через @ExtendWith(...)