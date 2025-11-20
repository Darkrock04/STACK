---
description: Build and release STACK app
---

# STACK Build and Release Workflow

This workflow guides you through building and releasing the STACK Android app.

## Prerequisites
- Ensure you are on the `main` branch
- All changes must be committed
- Working directory should be clean

## Steps

### 1. Check Git Status
```powershell
git status
```
Make sure there are no uncommitted changes.

### 2. Stage and Commit Changes (if needed)
```powershell
git add .
git commit -m "Your commit message here"
```

### 3. Push to Main Branch
```powershell
git push origin main
```

### 4. Create Release
Run the release script with your version number and message:
```powershell
.\release.ps1 "v1.0.0" "Release description"
```

This script will:
- Verify you're on the main branch
- Verify working directory is clean
- Build the release APK via `.\gradlew assembleRelease`
- Copy APK to `releases/` folder
- Create and push a git tag
- Display release information

### 5. Verify Release
Check the following:
- APK is in the `releases/` folder
- Git tag exists locally: `git tag`
- Tag is pushed to remote: `git ls-remote --tags`
- Release appears on GitHub

## Troubleshooting

### Build Failures
If `gradlew assembleRelease` fails:
1. Check for compilation errors in Kotlin files
2. Ensure all dependencies are properly configured
3. Try cleaning the build: `.\gradlew clean`

### Git Issues
If git operations fail:
- Ensure you have push access to the repository
- Check your network connection
- Verify your git credentials
