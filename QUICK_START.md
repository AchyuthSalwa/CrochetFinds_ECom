# Quick Start - Deploy & Share with Colleagues

## 🚀 Fastest Way: Local Network Sharing (5 minutes)

### Step 1: Build the Application
```bash
cd ecommerce-app
./mvnw clean package -DskipTests
```

### Step 2: Run the Application
```bash
java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar
```

### Step 3: Find Your IP Address

**Windows:**
```bash
ipconfig
```
Look for "IPv4 Address" (e.g., 192.168.1.100)

**Mac/Linux:**
```bash
ifconfig
# or
ip addr
```
Look for "inet" address

### Step 4: Share the Link
Share with your colleagues: **`http://YOUR_IP:8080`**

**Example:** If your IP is `192.168.1.100`, share `http://192.168.1.100:8080`

⚠️ **Firewall Note:** Make sure Windows Firewall allows connections on port 8080

---

## ☁️ Cloud Deployment: Railway (Recommended - Free)

### Step 1: Push to GitHub
```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin YOUR_GITHUB_REPO_URL
git push -u origin main
```

### Step 2: Deploy on Railway
1. Go to https://railway.app
2. Sign up with GitHub
3. Click "New Project" → "Deploy from GitHub repo"
4. Select your repository
5. Railway auto-detects Spring Boot and deploys!

You'll get a URL like: `https://your-app.up.railway.app`

---

## 📝 Default Login

The app includes sample data. You can:
- Register a new account
- Or use the admin setup at `/admin/setup` (if configured)

---

## 🔧 Troubleshooting

**Port 8080 already in use?**
Edit `application.properties` and change:
```properties
server.port=8081
```

**Can't access from other computers?**
- Check Windows Firewall settings
- Make sure you're on the same network
- Try disabling firewall temporarily for testing

**Database reset?**
Delete the `data/` folder and restart the app.

---

## 📦 What Changed for Deployment

✅ Database switched from in-memory to file-based (data persists)  
✅ Production profile created  
✅ Deployment files added (Procfile, system.properties)  
✅ Documentation created  

Your data is now stored in `./data/ecommerce.mv.db` and will persist between restarts!
