@echo off
echo ========================================
echo Testing E-commerce App Locally
echo ========================================
echo.

echo Step 1: Building the application...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo Build failed! Check errors above.
    pause
    exit /b 1
)

echo.
echo Step 2: Starting the application...
echo.
echo The app will start on: http://localhost:8080
echo H2 Console will be at: http://localhost:8080/h2-console
echo.
echo Press Ctrl+C to stop the application
echo.

java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar

pause
