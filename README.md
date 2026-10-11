# HR Employee Management System

A Spring Boot-based REST API application designed to manage employee information and related HR operations.

## Project Overview

The HR Employee Management System provides RESTful endpoints for managing employees and supporting HR modules. It uses a layered architecture to separate controllers, business logic, and data access.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Maven
- Swagger / OpenAPI
- Spring Boot Actuator
- HikariCP Connection Pool
- JUnit and Mockito

## Modules

- Employee Management
- Department Management
- Attendance Management
- Leave Management
- Performance Review Management

## Key Features

- RESTful API endpoints
- Employee creation, retrieval, updating, and deletion
- Pagination and employee search
- Department and employee status filtering
- Input validation and centralized exception handling
- Authentication using Spring Security
- Transaction management
- Caching for employee retrieval
- API documentation using Swagger / OpenAPI
- Application health and metrics monitoring using Actuator
- MySQL database integration

## Prerequisites

- Java 17 or compatible JDK
- Maven
- MySQL Server
- An IDE such as Spring Tool Suite or Eclipse

## Configuration

Configure your database connection and application security credentials using environment variables.

Required variables:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `APP_SECURITY_USERNAME`
- `APP_SECURITY_PASSWORD`

Create the required MySQL database before starting the application. Use the configuration in `src/main/resources/application.properties`.

## Running the Application

1. Clone or download this repository.
2. Configure the required environment variables.
3. Ensure MySQL is running and the database is available.
4. Open the project in STS or Eclipse as an existing Maven project.
5. Run the Spring Boot application.

Alternatively, from the project root, use:

```bash
./mvnw spring-boot:run
```

On Windows:

```bat
mvnw.cmd spring-boot:run
```

## API Documentation

After starting the application, access Swagger UI at:

http://localhost:8080/swagger-ui/index.html

## Monitoring

Application health:

http://localhost:8080/actuator/health

Application information:

http://localhost:8080/actuator/info

Available Actuator metrics:

http://localhost:8080/actuator/metrics

## Testing

Run the Maven test suite using:

```bat
mvnw.cmd test
```

## Project Structure

```text
HR-Employee-Management-System
├── .mvn
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   └── test
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Author

Aishwarya J.
