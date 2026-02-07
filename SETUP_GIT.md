# Quick Git Setup for Railway Deployment

Follow these steps to set up Git and push to GitHub:

## Step 1: Initialize Git (Run in PowerShell)

Open PowerShell in the `ecommerce-app` folder and run:

```powershell
cd D:\E-commerce-App\ecommerce-app
git init
git add .
git commit -m "Initial commit - Ready for Railway deployment"
```

## Step 2: Create GitHub Repository

1. Go to https://github.com and sign in
2. Click the **"+"** icon (top right) → **"New repository"**
3. Repository name: `ecommerce-app` (or any name)
4. **IMPORTANT**: 
   - ✅ Make it **Public** (or Private if you have GitHub Pro)
   - ❌ **DO NOT** check "Add a README file"
   - ❌ **DO NOT** check "Add .gitignore"
   - ❌ **DO NOT** choose a license
5. Click **"Create repository"**

## Step 3: Connect and Push to GitHub

After creating the repo, GitHub will show you commands. Use these (replace `YOUR_USERNAME`):

```powershell
git remote add origin https://github.com/YOUR_USERNAME/ecommerce-app.git
git branch -M main
git push -u origin main
```

**Example**: If your GitHub username is `johndoe`, use:
```powershell
git remote add origin https://github.com/johndoe/ecommerce-app.git
git branch -M main
git push -u origin main
```

You'll be asked for your GitHub username and password (use a Personal Access Token instead of password).

## Step 4: If You Need a Personal Access Token

1. GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Generate new token (classic)
3. Give it a name like "Railway Deployment"
4. Select scopes: `repo` (full control)
5. Generate token and **copy it** (you won't see it again!)
6. Use this token as your password when pushing

## Next Steps

Once pushed to GitHub, follow `RAILWAY_DEPLOYMENT.md` for Railway deployment!
