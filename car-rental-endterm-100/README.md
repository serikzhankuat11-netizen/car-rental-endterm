# Car Rental Endterm REST API (100/100 Template)

Бұл жоба **Endterm Project** талаптарына толық сәйкес келеді:
- Spring Boot REST API (Controller → Service → Repository → DB)
- Design Patterns: Singleton, Factory, Builder
- Component Principles: REP, CCP, CRP
- SOLID + Global Exception Handling
- PostgreSQL (JDBC арқылы)

## 1) How to run
1. PostgreSQL-та база аш: `car_rental_db`
2. `src/main/resources/application.properties` ішінде username/password дұрыс қой.
3. Қосымшада автоматты түрде `schema.sql` іске қосылады (кестелер құрылады).
4. Run:
```bash
mvn spring-boot:run
```

## 2) Base URL
`http://localhost:8080/api/v1`

## 3) Endpoints (қысқаша)
### Vehicles
- GET `/vehicles`
- GET `/vehicles/{id}`
- POST `/vehicles`
- PUT `/vehicles/{id}`
- DELETE `/vehicles/{id}`

### Customers
- GET `/customers`
- GET `/customers/{id}`
- POST `/customers`
- PUT `/customers/{id}`
- DELETE `/customers/{id}`

### Rentals
- GET `/rentals`
- GET `/rentals/{id}`
- POST `/rentals`
- DELETE `/rentals/{id}`  (cancel)

## 4) Design Patterns қай жерде?
- Singleton: `patterns/singleton/AppConfig`, `patterns/singleton/LoggerService`
- Factory: `patterns/factory/VehicleFactory` (ECONOMY/SUV/LUXURY → subclass)
- Builder: `patterns/builder/RentalBuilder` (күрделі Rental объектісі)

## 5) Database Schema
`src/main/resources/schema.sql`

> Ескерту: Бұл шаблон — қорғауға, README мен код құрылымын 100 бал деңгейіне жеткізуге арналған. Өз Assignment 3/4 логикаң болса, service/repo бөліміне қосып кеңейтесің.
