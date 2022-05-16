<h3>Данный сервис используется для управления работой кофемашины, позволяя готовить разные типы напитков и производить пополнение недостающих ингредиентов</h3>

Основной стек технологий:
    
    Java 8
    Docker
    Spring Boot 2
    PostgreSQL 11
    Flyway
    Hibernate
    
    JUnit
    Mockito
    MockMvc

### запуск приложения
1 вариант: указать необходимые креды для запуска БД
2 вариант: запустить приложение с профилем "local" и выполнить команду для запуска образов БД из файла docker-compose.yaml
    
Выполнить команду для запуска образов

    docker-compose up
    
Далее API сервиса будет доступно по ссылке:

http://localhost:8080/swagger-ui/index.html#

       
