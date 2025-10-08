# STACK Release Management Script
# Usage: .\release.ps1 [version] [message]
# Example: .\release.ps1 v1.1.0 "Added new features"

param(
    [Parameter(Mandatory=$true)]
    [string]$Version,
    
    [Parameter(Mandatory=$true)]
    [string]$Message
)

Write-Host "🚀 Creating release $Version..." -ForegroundColor Green

# Check if we're on main branch
$currentBranch = git branch --show-current
if ($currentBranch -ne "main") {
    Write-Host "❌ Error: You must be on the main branch to create a release" -ForegroundColor Red
    Write-Host "Current branch: $currentBranch" -ForegroundColor Yellow
    Write-Host "Please run: git checkout main" -ForegroundColor Yellow
    exit 1
}

# Check if working directory is clean
$status = git status --porcelain
if ($status) {
    Write-Host "❌ Error: Working directory is not clean" -ForegroundColor Red
    Write-Host "Please commit or stash your changes first" -ForegroundColor Yellow
    git status
    exit 1
}

# Create and push tag
Write-Host "📝 Creating tag $Version..." -ForegroundColor Blue
git tag -a $Version -m "Release $Version`: $Message"

Write-Host "📤 Pushing tag to remote..." -ForegroundColor Blue
git push origin $Version

Write-Host "✅ Release $Version created successfully!" -ForegroundColor Green
Write-Host "🌐 View on GitHub: https://github.com/Darkrock04/STACK/releases" -ForegroundColor Cyan

# Show recent tags
Write-Host "`n📋 Recent releases:" -ForegroundColor Yellow
git tag --sort=-version:refname | Select-Object -First 5
