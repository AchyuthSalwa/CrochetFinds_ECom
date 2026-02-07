# Testing Locally Before Railway Deployment

This guide helps you test your application locally to ensure everything works before deploying to Railway.

## Quick Local Test

### Step 1: Build the Application
```powershell
cd D:\E-commerce-App\ecommerce-app
./mvnw clean package -DskipTests
```

### Step 2: Run the Application
```powershell
java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar
```

### Step 3: Access the Application
Open your browser and go to:
- **Main App**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console

### Step 4: Test H2 Database
1. Go to http://localhost:8080/h2-console
2. Enter these connection details:
   - **JDBC URL**: `jdbc:h2:file:./data/ecommerce`
   - **Username**: `sa`
   - **Password**: (leave empty)
3. Click "Connect"
4. You should see tables: `products`, `users`, `carts`, `orders`, etc.
5. Run a query: `SELECT * FROM products;`
6. You should see 8 sample products from `data.sql`

## Verify Database Persistence

1. **Start the app** (as above)
2. **Register a new user** at http://localhost:8080/register
3. **Add items to cart**
4. **Stop the app** (Ctrl+C)
5. **Start the app again**
6. **Login** - your account should still exist!
7. **Check cart** - items should still be there!

This proves the database is persisting data to the file system.

## Check Database File

After running the app, you should see:
```
ecommerce-app/
└── data/
    └── ecommerce.mv.db  ← This is your database file
```

This file contains all your data and persists between restarts.

## Troubleshooting

### Port 8080 Already in Use?
Change port in `application.properties`:
```properties
server.port=8081
```
Then access at: http://localhost:8081

### Database Not Persisting?
- Check if `data/` folder exists
- Check if `data/ecommerce.mv.db` file exists
- Make sure you're using file-based H2 (not in-memory)

### Can't Connect to H2 Console?
- Make sure app is running
- Check URL: `jdbc:h2:file:./data/ecommerce`
- Username: `sa`
- Password: (empty)

## What to Test

✅ **Homepage loads** - http://localhost:8080  
✅ **Products display** - Should see 8 sample products  
✅ **User registration** - Create a new account  
✅ **User login** - Login with your account  
✅ **Add to cart** - Add products to cart  
✅ **View cart** - See items in cart  
✅ **Checkout** - Place an order  
✅ **View orders** - See your orders  
✅ **Admin panel** - Access /admin/products (if admin user)  
✅ **H2 Console** - Access database directly  
✅ **Data persistence** - Restart app, data should remain  

## Ready for Railway?

Once all tests pass locally, you're ready to deploy to Railway! The same H2 file-based database will work on Railway's filesystem.
