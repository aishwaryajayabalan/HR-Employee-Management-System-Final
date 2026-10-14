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
## Security Configuration

The application uses Spring Security to protect its REST API endpoints and control access to application resources. Security is configured in `SecurityConfig.java` using Spring Security's `SecurityFilterChain`.

### Key Features

- **CSRF Protection:** CSRF protection is disabled because the application uses stateless REST API authentication rather than cookie-based sessions.
- **Stateless Authentication:** The application uses `SessionCreationPolicy.STATELESS`, meaning the server does not maintain an HTTP session for authentication between requests.
- **Public Endpoints:** The `/actuator/health`, `/actuator/info`, `/swagger-ui/**`, and `/v3/api-docs/**` endpoints are accessible without authentication.
- **Protected Endpoints:** All other API endpoints require authentication before access is granted.
- **HTTP Basic Authentication:** The application uses HTTP Basic authentication, where clients provide a username and password in the HTTP `Authorization` header.
- **Authentication Management:** Spring Security validates the supplied credentials using the configured authentication mechanism.

### How It Works

1. A client sends an HTTP request to the application.
2. Spring Security checks the requested endpoint against the configured access rules.
3. If the endpoint is public, the request can proceed without authentication.
4. If the endpoint is protected, the client must provide valid authentication credentials.
5. If authentication succeeds, the request proceeds to the appropriate controller. Otherwise, access is denied.

### Purpose

The security configuration helps prevent unauthorized access to employee information and other HR management resources while allowing public access to health monitoring and API documentation endpoints.

**Note:** HTTP Basic authentication should be used over HTTPS in production environments to protect credentials during transmission.
## Author

Aishwarya J.
