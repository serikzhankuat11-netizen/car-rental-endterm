 Car Rental REST API

Endterm Project – Design Patterns & Component Principles

 Project Overview

This project is a Spring Boot REST API for managing a car rental system.
It demonstrates clean architecture principles, layered design, and practical usage of design patterns.

The system allows managing:

Customers

Vehicles

Rentals

All operations were tested using Postman and connected to a PostgreSQL database.

 Architecture

The project follows a layered architecture:

Controller → Service → Repository → Database

Layers:

Controller – Handles HTTP requests (REST endpoints)

Service – Business logic layer

Repository (JDBC) – Data access using Spring JDBC Template

PostgreSQL – Persistent storage

This separation ensures:

Single Responsibility Principle

Low coupling

High cohesion

Easy maintainability

 Technologies Used

Java 17

Spring Boot 3.3

Spring Web

Spring JDBC

PostgreSQL

Maven

Postman (API testing)

 Project Structure
com.example.carrental
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── dto
 ├── exception
 └── Application.java

 REST Endpoints
🔹 Customers
Method	Endpoint	Description
GET	/api/v1/customers	Get all customers
GET	/api/v1/customers/{id}	Get customer by ID
POST	/api/v1/customers	Create new customer
PUT	/api/v1/customers/{id}	Update customer
DELETE	/api/v1/customers/{id}	Delete customer
- Example Request (POST Customer)
{
  "firstName": "Akzhan",
  "lastName": "Saitama",
  "email": "akzha@mail.com"
}


Response:

{
  "id": 1,
  "firstName": "Akzhan",
  "lastName": "Saitama",
  "phone": null,
  "email": "akzha@mail.com"
}

 Design Patterns Used
1️⃣ Repository Pattern

Encapsulates data access logic and separates it from business logic.

2️⃣ DTO Pattern

Used for request and response objects to avoid exposing internal models.

3️⃣ Layered Architecture

Ensures separation of concerns and maintainable structure.

 Testing

All endpoints were tested using Postman.
CRUD operations return correct HTTP status codes:

200 OK

201 Created

204 No Content

404 Not Found

500 Internal Server Error (handled via GlobalExceptionHandler)

 Database

PostgreSQL database:

car_rental_db


Connection configured in:

application.properties

 How to Run

Start PostgreSQL

Create database:

car_rental_db


Configure credentials in application.properties

Run:

mvn spring-boot:run


Test using Postman:

http://localhost:8080/api/v1/customers

 Project Goals Achieved

Clean layered architecture

Practical REST API implementation

Database integration using JDBC

Proper error handling

Demonstration of design patterns

Full CRUD functionality

 Author

Serik Zhankuat
Endterm Project – Software Architecture & Design Patterns
