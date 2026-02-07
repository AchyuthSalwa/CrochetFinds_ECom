# Railway Deployment Guide - Step by Step

## Prerequisites
- GitHub account
- Railway account (free at https://railway.app)

## Step 1: Initialize Git Repository

If you haven't already, initialize git in your project:

```bash
cd D:\E-commerce-App\ecommerce-app
git init
git add .
git commit -m "Initial commit - Ready for Railway deployment"
```

## Step 2: Create GitHub Repository

1. Go to https://github.com
2. Click the "+" icon in the top right → "New repository"
3. Name it: `ecommerce-app` (or any name you prefer)
4. **DO NOT** initialize with README, .gitignore, or license (we already have these)
5. Click "Create repository"

## Step 3: Push to GitHub

GitHub will show you commands. Use these (replace YOUR_USERNAME with your GitHub username):

```bash
git remote add origin https://github.com/YOUR_USERNAME/ecommerce-app.git
git branch -M main
git push -u origin main
```

## Step 4: Deploy on Railway

### 4.1 Sign Up / Login
1. Go to https://railway.app
2. Click "Login" → "Login with GitHub"
3. Authorize Railway to access your GitHub account

### 4.2 Create New Project
1. Click "New Project" button
2. Select "Deploy from GitHub repo"
3. Select your `ecommerce-app` repository
4. Railway will automatically detect it's a Spring Boot app

### 4.3 Configure Build Settings
Railway should auto-detect, but verify:
- **Build Command**: `./mvnw clean package -DskipTests` (or Railway auto-detects)
- **Start Command**: `java -jar target/ecommerceapp-0.0.1-SNAPSHOT.jar`

### 4.4 Set Environment Variables (Optional)
Click on your service → "Variables" tab:
- `SPRING_PROFILES_ACTIVE=prod` (optional, uses production config)
- `PORT` is automatically set by Railway (don't add this manually)

### 4.5 Deploy
1. Railway will automatically start building
2. Watch the build logs
3. Once deployed, Railway provides a URL like: `https://your-app-name.up.railway.app`

## Step 5: Access Your App

1. Click on your deployed service
2. Go to "Settings" → "Domains"
3. Railway provides a default domain: `your-app-name.up.railway.app`
4. You can also add a custom domain if you have one

## Step 6: Share with Colleagues

Share the Railway URL: `https://your-app-name.up.railway.app`

## Troubleshooting

### Build Fails
- Check build logs in Railway dashboard
- Make sure Java 21 is specified (we have `system.properties`)
- Verify Maven wrapper (`mvnw`) is present

### App Crashes on Start
- Check logs in Railway dashboard
- Verify database connection (H2 file-based should work)
- Check if port is correctly configured (Railway sets PORT automatically)

### Database Issues
- H2 file database will persist in Railway's filesystem
- Data persists between deployments
- To reset: delete the service and redeploy

### Port Issues
- Railway automatically sets `PORT` environment variable
- Spring Boot should pick it up automatically
- If issues, add to `application.properties`: `server.port=${PORT:8080}`

## Railway Free Tier Limits

- **$5 free credit per month** (usually enough for testing)
- **512MB RAM** per service
- **1GB storage**
- **100GB bandwidth**

For basic testing with colleagues, this is usually sufficient!

## Updating Your App

After making changes:

```bash
git add .
git commit -m "Your update message"
git push origin main
```

Railway will automatically detect the push and redeploy!

## Need Help?

- Railway Docs: https://docs.railway.app
- Railway Discord: https://discord.gg/railway
- Check Railway dashboard logs for detailed error messages
