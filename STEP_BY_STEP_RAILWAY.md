# 🚂 Railway Deployment - Step by Step Guide

Follow these steps in order to deploy your app to Railway and share it with colleagues.

---

## ✅ STEP 1: Set Up Git Repository

### 1.1 Open PowerShell
Open PowerShell in the `ecommerce-app` folder:
- Press `Win + X` → Select "Windows PowerShell" or "Terminal"
- Navigate to your project:
  ```powershell
  cd D:\E-commerce-App\ecommerce-app
  ```

### 1.2 Initialize Git
```powershell
git init
git add .
git commit -m "Initial commit - Ready for Railway deployment"
```

---

## ✅ STEP 2: Create GitHub Repository

### 2.1 Go to GitHub
1. Open https://github.com in your browser
2. Sign in (or create account if needed)

### 2.2 Create New Repository
1. Click the **"+"** icon in top right corner
2. Select **"New repository"**
3. Fill in:
   - **Repository name**: `ecommerce-app` (or any name you like)
   - **Description**: (optional) "E-commerce app for testing"
   - **Visibility**: Choose **Public** (or Private if you have GitHub Pro)
   - ⚠️ **IMPORTANT**: 
     - ❌ **DO NOT** check "Add a README file"
     - ❌ **DO NOT** check "Add .gitignore" 
     - ❌ **DO NOT** choose a license
4. Click **"Create repository"**

### 2.3 Copy Repository URL
After creating, GitHub shows you commands. **Copy the repository URL** - it looks like:
```
https://github.com/YOUR_USERNAME/ecommerce-app.git
```

---

## ✅ STEP 3: Push Code to GitHub

### 3.1 Connect Local Repository to GitHub
In PowerShell (still in `ecommerce-app` folder), run:

```powershell
git remote add origin https://github.com/YOUR_USERNAME/ecommerce-app.git
git branch -M main
git push -u origin main
```

**Replace `YOUR_USERNAME`** with your actual GitHub username!

**Example**: If your username is `johndoe`:
```powershell
git remote add origin https://github.com/johndoe/ecommerce-app.git
git branch -M main
git push -u origin main
```

### 3.2 Authentication
- **Username**: Your GitHub username
- **Password**: Use a **Personal Access Token** (not your GitHub password)

#### If You Need a Personal Access Token:
1. Go to: https://github.com/settings/tokens
2. Click **"Generate new token"** → **"Generate new token (classic)"**
3. Name it: `Railway Deployment`
4. Select scope: ✅ **`repo`** (check the box)
5. Click **"Generate token"**
6. **COPY THE TOKEN** (you won't see it again!)
7. Use this token as your password when pushing

### 3.3 Verify Push
After pushing, refresh your GitHub repository page. You should see all your files!

---

## ✅ STEP 4: Deploy on Railway

### 4.1 Sign Up / Login to Railway
1. Go to https://railway.app
2. Click **"Login"** → **"Login with GitHub"**
3. Authorize Railway to access your GitHub account

### 4.2 Create New Project
1. Click the **"New Project"** button (big green button)
2. Select **"Deploy from GitHub repo"**
3. You'll see a list of your GitHub repositories
4. Find and click on **`ecommerce-app`** (or whatever you named it)

### 4.3 Railway Auto-Detection
Railway will automatically:
- ✅ Detect it's a Spring Boot application
- ✅ Set up build commands
- ✅ Configure start command
- ✅ Start building

**You'll see build logs** - just wait for it to finish (usually 2-5 minutes).

### 4.4 Get Your App URL
Once deployment is complete:
1. Click on your service (the box that appeared)
2. Go to **"Settings"** tab
3. Scroll to **"Domains"** section
4. Railway provides a default domain like: `your-app-name.up.railway.app`
5. **Copy this URL** - this is your live app!

---

## ✅ STEP 5: Share with Colleagues! 🎉

Share the Railway URL with your colleagues:
```
https://your-app-name.up.railway.app
```

They can now:
- Browse products
- Register accounts
- Add items to cart
- Place orders
- Test admin features (if you set up admin account)

---

## 🔧 Troubleshooting

### Build Fails?
- Check the **"Deployments"** tab in Railway
- Click on the failed deployment to see logs
- Common issues:
  - Java version mismatch (we have `system.properties` with Java 21)
  - Maven wrapper missing (should be included)

### App Crashes?
- Check **"Logs"** tab in Railway dashboard
- Look for error messages
- Common issues:
  - Port configuration (we added `${PORT:8080}` - should work)
  - Database connection (H2 file-based should work)

### Can't Push to GitHub?
- Make sure you're using Personal Access Token, not password
- Verify repository URL is correct
- Check you have write access to the repository

### Need to Update App?
After making changes:
```powershell
git add .
git commit -m "Your update description"
git push origin main
```
Railway will automatically redeploy!

---

## 📊 Railway Free Tier

Railway gives you:
- **$5 free credit per month**
- **512MB RAM**
- **1GB storage**
- **100GB bandwidth**

This is usually enough for testing with colleagues!

---

## 🎯 Quick Checklist

- [ ] Git initialized and committed
- [ ] GitHub repository created
- [ ] Code pushed to GitHub
- [ ] Railway account created
- [ ] Project deployed on Railway
- [ ] Got the Railway URL
- [ ] Shared URL with colleagues

---

## 💡 Pro Tips

1. **Monitor Usage**: Check Railway dashboard for resource usage
2. **View Logs**: Railway dashboard → Your service → "Logs" tab
3. **Custom Domain**: You can add your own domain in Railway settings
4. **Environment Variables**: Add them in Railway → Your service → "Variables" tab

---

**Need Help?** Check Railway docs: https://docs.railway.app

Good luck with your deployment! 🚀
