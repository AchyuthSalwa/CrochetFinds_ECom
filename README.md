# E-commerce App - CrochetFinds

A Spring Boot e-commerce application for selling handmade crochet products.

## Features

- Product catalog with categories, filtering, and search
- Shopping cart functionality
- User authentication (login/register)
- Order management
- Admin panel for product management
- Responsive design

## Tech Stack

- **Backend**: Spring Boot 3.2.5, Java 21
- **Database**: H2 Database (file-based)
- **Frontend**: Thymeleaf, HTML, CSS
- **Build Tool**: Maven

## Quick Start

### Local Development

```bash
# Build the application
./mvnw clean package -DskipTests

# Run the application
java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar
```

Access the app at: http://localhost:8080

### Deployment

See `RAILWAY_DEPLOYMENT.md` for detailed Railway deployment instructions.

## Default Access

- **Application**: http://localhost:8080
- **H2 Console** (dev): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/ecommerce`
  - Username: `sa`
  - Password: (empty)

## Project Structure

```
ecommerce-app/
├── src/
│   ├── main/
│   │   ├── java/          # Java source code
│   │   └── resources/     # Configuration and templates
│   └── test/              # Test files
├── pom.xml                # Maven configuration
└── README.md             # This file
```

## License

This project is for demonstration purposes.
