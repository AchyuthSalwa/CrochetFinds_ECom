# H2 Database Testing Guide

## ✅ Your H2 Database is Configured for Local Testing

Your application uses **file-based H2 database**, which means:
- ✅ Data persists between app restarts
- ✅ You can inspect the database directly
- ✅ Works the same way locally and on Railway

## Quick Test Steps

### 1. Start the Application

**Option A: Use the batch file**
```batch
run-local.bat
```

**Option B: Manual command**
```powershell
cd D:\E-commerce-App\ecommerce-app
java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar
```

### 2. Access the Application
- **Main App**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console

### 3. Test H2 Database Console

1. **Open H2 Console**: http://localhost:8080/h2-console
2. **Enter Connection Details**:
   ```
   JDBC URL: jdbc:h2:file:./data/ecommerce
   Username: sa
   Password: (leave empty)
   ```
3. **Click "Connect"**
4. **You should see tables**: `products`, `users`, `carts`, `cart_items`, `orders`, `order_items`

### 4. Test Database Queries

Try these queries in H2 Console:

**View all products:**
```sql
SELECT * FROM products;
```

**View all users:**
```sql
SELECT * FROM users;
```

**View all orders:**
```sql
SELECT * FROM orders;
```

**Count products:**
```sql
SELECT COUNT(*) FROM products;
```
(Should return 8 - from data.sql)

### 5. Test Data Persistence

1. **Register a new user** at http://localhost:8080/register
2. **Add items to cart**
3. **Place an order**
4. **Stop the app** (Ctrl+C)
5. **Start the app again**
6. **Login** - your account should still exist!
7. **Check H2 Console** - your data should still be there!

### 6. Check Database File

After running the app, check:
```
ecommerce-app/
└── data/
    └── ecommerce.mv.db  ← Your database file (should exist)
```

This file contains all your data!

## Database Connection Details

| Setting | Value |
|---------|-------|
| **JDBC URL** | `jdbc:h2:file:./data/ecommerce` |
| **Username** | `sa` |
| **Password** | (empty) |
| **Database Type** | H2 (File-based) |
| **Database File** | `./data/ecommerce.mv.db` |

## What to Verify

✅ **H2 Console accessible** - http://localhost:8080/h2-console  
✅ **Can connect to database** - Using credentials above  
✅ **See sample products** - 8 products from data.sql  
✅ **Data persists** - Restart app, data remains  
✅ **Can query database** - Run SQL queries  
✅ **Database file exists** - `data/ecommerce.mv.db` created  

## Troubleshooting

### H2 Console Not Loading?
- Make sure app is running
- Check URL: http://localhost:8080/h2-console
- Verify `spring.h2.console.enabled=true` in application.properties

### Can't Connect to Database?
- **JDBC URL must be**: `jdbc:h2:file:./data/ecommerce` (exact match)
- **Username**: `sa` (lowercase)
- **Password**: Leave empty
- Make sure app has started (database file is created on first run)

### Database File Not Created?
- Start the app first
- Database file is created when app starts
- Check `ecommerce-app/data/` folder

### Data Not Persisting?
- Make sure you're using file-based H2 (not in-memory)
- Check `application.properties`: `jdbc:h2:file:./data/ecommerce`
- Verify `data/ecommerce.mv.db` file exists

## Sample Data

The app automatically loads 8 sample products from `data.sql`:
1. Cozy Winter Scarf
2. Granny Square Blanket
3. Amigurumi Bunny
4. Crochet Market Bag
5. Baby Booties Set
6. Cable Knit Beanie
7. Coastal Throw Pillow
8. Rainbow Wall Hanging

## Ready for Railway?

Once you've verified:
- ✅ App runs locally
- ✅ H2 Console works
- ✅ Data persists
- ✅ All features work

You're ready to deploy to Railway! The same H2 file-based database will work on Railway's filesystem.
