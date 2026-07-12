# Travel Planner Backend

A Spring Boot backend for the Travel Planner and Booking System application.

## Tech Stack

- Java 21
- Spring Boot 3.5.3
- Spring Security with JWT authentication
- Spring Data JPA
- MySQL Database
- Maven
- iText (PDF generation)

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

## Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE travelapp;
```

2. Create a database user (optional, or use existing MySQL user):
```sql
CREATE USER 'travelapp_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON travelapp.* TO 'travelapp_user'@'localhost';
FLUSH PRIVILEGES;
```

## Configuration

1. Copy the example configuration file:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

2. Edit `src/main/resources/application.properties` with your actual database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/travelapp?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
jwt.secret=your_jwt_secret_key
```

**Important:** Generate a secure JWT secret key. You can use:
```bash
openssl rand -base64 32
```

## Building and Running

### Using Maven Wrapper
```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

### Using System Maven
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/change-password` - Change password

### Trips
- `GET /api/trips` - Get all trips
- `POST /api/trips` - Create a new trip
- `GET /api/trips/{id}` - Get trip by ID
- `PUT /api/trips/{id}` - Update trip
- `DELETE /api/trips/{id}` - Delete trip

### Bookings
- `GET /api/bookings` - Get all bookings
- `POST /api/bookings` - Create a booking
- `GET /api/bookings/{id}` - Get booking by ID
- `PUT /api/bookings/{id}` - Update booking

### Payments
- `POST /api/payments/initiate` - Initiate payment
- `POST /api/payments/finalize` - Finalize payment

### Invoices
- `GET /api/invoices/{id}` - Get invoice by ID
- `GET /api/invoices/{id}/download` - Download invoice PDF

### Itinerary
- `GET /api/itinerary/{tripId}` - Get trip itinerary
- `POST /api/itinerary` - Create itinerary item

### User Preferences
- `GET /api/preferences` - Get user preferences
- `POST /api/preferences` - Save user preferences

## Security

The application uses JWT (JSON Web Tokens) for authentication. Include the JWT token in the Authorization header for protected endpoints:

```
Authorization: Bearer <your_jwt_token>
```

## CORS Configuration

The backend is configured to allow CORS requests from `http://localhost:4200` (default Angular development server). Update the `CorsConfigurationSource` in `SecurityConfig.java` if your frontend runs on a different port/domain.

## Development

### Running Tests
```bash
./mvnw test
```

### Code Structure
- `config/` - Security and application configuration
- `controller/` - REST API controllers
- `dto/` - Data Transfer Objects
- `entity/` - JPA entities
- `exception/` - Custom exception handlers
- `repository/` - JPA repositories
- `security/` - JWT filter and utilities
- `service/` - Business logic layer

## Troubleshooting

### Database Connection Issues
- Ensure MySQL is running
- Verify database credentials in `application.properties`
- Check if the database exists

### Port Already in Use
If port 8080 is already in use, change it in `application.properties`:
```properties
server.port=8081
```

## License

This project is part of the Travel Planner and Booking System.
