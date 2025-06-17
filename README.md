1) mvn clean install
2) docker build -t command-query-command -f command-query-command/Dockerfile . && docker build -t command-command-command -f command-command-command/Dockerfile .
4) docker-compose up -d

Command API доступен на порту 8080
Query API доступен на порту 8081
RabbitMQ Management доступен на порту 15672
PostgreSQL доступен на порту 5432
MongoDB доступен на порту 27017
Redis доступен на порту 6379

1. Контейнеризация (Docker)
Проект полностью контейнеризирован
Имеет Dockerfile для каждого сервиса
Использует docker-compose для оркестрации всех сервисов
Включает контейнеры для всех необходимых компонентов (приложения, базы данных, Redis, RabbitMQ)
2. Кеширование (Redis)
Redis интегрирован в проект
Настроен как кэш для Spring Boot приложений
Используется в query сервисе
3. Event-driven архитектура (RabbitMQ)
RabbitMQ полностью интегрирован
Настроены очереди для обмена сообщениями
Реализована публикация и подписка на события
Используется для асинхронной коммуникации между сервисами
4. Observability
Реализована через Spring Boot Actuator
Настроены health checks
Интегрирован Prometheus для метрик
Включены основные эндпоинты мониторинга
5. CQRS и Event Sourcing
Четкое разделение на command и query сервисы
Реализован Event Store для хранения событий
Используется паттерн Event Sourcing (видно по структуре событий и их обработке)
Есть отдельный модуль customer-common для общих событий
Реализована обработка событий в query сервисе
