# Employee Management System

A RESTful microservice for managing employee records built with Spring Boot 4.0.2.

## 🚀 Technologies Used

- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Data JPA**
- **H2 Database** (In-memory)
- **Lombok**
- **ModelMapper**
- **Swagger/OpenAPI 3** (API Documentation)
- **Maven**
- **JUnit 5 & Mockito** (Testing)

## 📋 Features

- ✅ Complete CRUD operations for Employee management
- ✅ RESTful API endpoints
- ✅ Input validation with Bean Validation
- ✅ Global exception handling
- ✅ Automatic DTO-Entity mapping
- ✅ H2 in-memory database with console access
- ✅ Sample data preloaded on startup
- ✅ Interactive API documentation with Swagger UI
- ✅ Comprehensive unit and integration tests
- ✅ Logging with SLF4J

## 🏗️ Project Structure

```
employee-management/
├── src/
│   ├── main/
│   │   ├── java/com/project/employee_management/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST Controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA Entities
│   │   │   ├── exception/       # Exception handlers
│   │   │   ├── repository/      # JPA Repositories
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Unit and integration tests
└── pom.xml
```

## 📦 Installation & Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/rooonnie/Employee-Management.git
   cd employee-management
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - H2 Console: `http://localhost:8080/h2-console`

## 🗄️ Database Configuration

### H2 Console Access

- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:employeedb`
- **Username:** `sa`
- **Password:** *(leave blank)*

## 📊 Employee Entity

```java
{
  "id": 1,
  "employeeId": "EMP001",
  "firstName": "Juan",
  "lastName": "Dela Cruz",
  "email": "juan.delacruz@company.com",
  "role": "Software Engineer",
  "status": "ACTIVE",           // ACTIVE, BENCH, RESIGNED
  "primarySkill": "Java",
  "secondarySkill": "Spring Boot",
  "dateCreated": "2026-02-02T10:00:00",
  "dateUpdated": "2026-02-02T10:00:00"
}
```

## 🔌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/employees` | Create a new employee |
| GET | `/employees` | Get all employees |
| GET | `/employees/{id}` | Get employee by ID |
| PUT | `/employees/{id}` | Update employee |
| DELETE | `/employees/{id}` | Delete employee |
| GET | `/employees/role/{role}` | Get employees by role |
| GET | `/employees/status/{status}` | Get employees by status |

### Example Requests

#### Create Employee
```bash
curl -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": "EMP006",
    "firstName": "Carlos",
    "lastName": "Rivera",
    "email": "carlos.rivera@company.com",
    "role": "Backend Developer",
    "status": "ACTIVE",
    "primarySkill": "Node.js",
    "secondarySkill": "MongoDB"
  }'
```

#### Get All Employees
```bash
curl http://localhost:8080/employees
```

#### Get Employee by ID
```bash
curl http://localhost:8080/employees/1
```

#### Update Employee
```bash
curl -X PUT http://localhost:8080/employees/1 \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": "EMP001",
    "firstName": "Juan",
    "lastName": "Dela Cruz",
    "email": "juan.delacruz@company.com",
    "role": "Senior Software Engineer",
    "status": "ACTIVE",
    "primarySkill": "Java",
    "secondarySkill": "Spring Boot"
  }'
```

#### Delete Employee
```bash
curl -X DELETE http://localhost:8080/employees/1
```

## 📝 Sample Data

The application comes preloaded with 5 sample employees:

1. **EMP001** - Juan Dela Cruz (Software Engineer) - ACTIVE
2. **EMP002** - Maria Santos (Senior Developer) - ACTIVE
3. **EMP003** - Pedro Garcia (Full Stack Developer) - BENCH
4. **EMP004** - Anna Reyes (DevOps Engineer) - ACTIVE
5. **EMP005** - Jose Ramos (QA Engineer) - RESIGNED

## 🧪 Testing

### Run all tests
```bash
./mvnw test
```

### Run tests with coverage
```bash
./mvnw clean test jacoco:report
```

Test coverage includes:
- ✅ Service layer unit tests (12 test cases)
- ✅ Repository integration tests
- ✅ Exception handling tests

## 📚 API Documentation

Interactive API documentation is available via Swagger UI:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/api-docs

## ⚙️ Configuration

Key properties in `application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:h2:mem:employeedb
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Logging
logging.level.com.project.employee_management=DEBUG
```

## 🔒 Validation Rules

- `employeeId`: Required, must be unique
- `firstName`: Required
- `lastName`: Required
- `email`: Required, must be valid email format, must be unique
- `role`: Required
- `status`: Required (ACTIVE, BENCH, RESIGNED)
- `primarySkill`: Required
- `secondarySkill`: Optional

## 🚦 HTTP Status Codes

| Status Code | Description |
|-------------|-------------|
| 200 OK | Request successful |
| 201 Created | Employee created successfully |
| 204 No Content | Employee deleted successfully |
| 400 Bad Request | Invalid input data |
| 404 Not Found | Employee not found |
| 500 Internal Server Error | Server error |

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the MIT License.

## 👨‍💻 Author

**Ronnie**
- GitHub: [@rooonnie](https://github.com/rooonnie)
- Repository: [Employee-Management](https://github.com/rooonnie/Employee-Management)

## 📞 Support

For issues or questions, please create an issue in the GitHub repository.

---

Made with ❤️ using Spring Boot
