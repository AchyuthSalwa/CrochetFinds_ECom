# Deployment Guide for E-commerce App

This guide provides multiple options to deploy and share your application with colleagues for testing.

## Prerequisites
- Java 21 installed
- Maven installed (or use included `mvnw`)

## Option 1: Quick Local Network Sharing (Fastest for Testing)

### Step 1: Build the Application
```bash
cd ecommerce-app
mvnw clean package -DskipTests
```

### Step 2: Run the Application
```bash
java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar
```

### Step 3: Find Your IP Address
- **Windows**: Open Command Prompt and run `ipconfig` - look for IPv4 Address
- **Mac/Linux**: Run `ifconfig` or `ip addr` - look for inet address

### Step 4: Share the Link
Share with your colleagues: `http://YOUR_IP_ADDRESS:8080`

**Example**: If your IP is `192.168.1.100`, share `http://192.168.1.100:8080`

**Note**: Make sure your firewall allows incoming connections on port 8080.

---

## Option 2: Deploy to Railway (Recommended - Free & Easy)

Railway offers free hosting with automatic deployments.

### Step 1: Create Railway Account
1. Go to https://railway.app
2. Sign up with GitHub

### Step 2: Create New Project
1. Click "New Project"
2. Select "Deploy from GitHub repo"
3. Connect your repository

### Step 3: Configure Build
Railway will auto-detect Spring Boot. Add these environment variables:
- `PORT` (Railway sets this automatically)
- `DB_PASSWORD` (optional, for database security)

### Step 4: Deploy
Railway will automatically build and deploy. You'll get a URL like:
`https://your-app-name.up.railway.app`

---

## Option 3: Deploy to Render (Free Tier Available)

### Step 1: Create Render Account
1. Go to https://render.com
2. Sign up with GitHub

### Step 2: Create New Web Service
1. Connect your GitHub repository
2. Configure:
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar`
   - **Environment**: Java

### Step 3: Set Environment Variables
- `PORT` (Render sets this automatically)
- `SPRING_PROFILES_ACTIVE=prod`

### Step 4: Deploy
Render will build and deploy. You'll get a URL like:
`https://your-app-name.onrender.com`

---

## Option 4: Deploy to Heroku (Free Tier Discontinued, Paid Only)

### Step 1: Install Heroku CLI
Download from https://devcenter.heroku.com/articles/heroku-cli

### Step 2: Login and Create App
```bash
heroku login
heroku create your-app-name
```

### Step 3: Set Java Version
Create `system.properties` in root:
```
java.runtime.version=21
```

### Step 4: Deploy
```bash
git push heroku main
```

---

## Building the JAR File

To build a production-ready JAR:

```bash
cd ecommerce-app
./mvnw clean package -DskipTests
```

The JAR will be created at: `target/ecommerceapp-0.0.1-SNAPSHOT.jar`

## Running the Application

### Development Mode
```bash
./mvnw spring-boot:run
```

### Production Mode
```bash
java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar
```

### With Production Profile
```bash
java -jar -Dspring.profiles.active=prod target/ecommerceapp-0.0.1-SNAPSHOT.jar
```

## Default Access

- **Application**: http://localhost:8080
- **H2 Console** (dev only): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/ecommerce`
  - Username: `sa`
  - Password: (empty)

## Important Notes

1. **Database**: The app now uses file-based H2 database. Data will persist in `./data/ecommerce.mv.db`
2. **Port**: Default port is 8080. Change with `server.port` property or `PORT` environment variable
3. **Security**: H2 console is disabled in production profile for security
4. **Data Persistence**: All data is stored in `./data/` directory. Back up this folder for data preservation

## Troubleshooting

### Port Already in Use
Change port in `application.properties`:
```properties
server.port=8081
```

### Database Issues
Delete `./data/` folder and restart to reset database.

### Build Errors
Make sure Java 21 is installed:
```bash
java -version
```

## Quick Start for Testing

1. Build: `./mvnw clean package -DskipTests`
2. Run: `java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar`
3. Access: http://localhost:8080
4. Share your IP address with colleagues for network access
