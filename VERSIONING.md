# Version Management Guide

## 🏷️ Version Numbering

We use [Semantic Versioning](https://semver.org/) (SemVer) for our releases:

- **MAJOR** (X.0.0): Breaking changes, major new features
- **MINOR** (X.Y.0): New features, backward compatible
- **PATCH** (X.Y.Z): Bug fixes, small improvements

## 📋 Release Process

### For Major Changes (New Features, Breaking Changes):
```powershell
# 1. Make sure you're on main branch
git checkout main

# 2. Ensure all changes are committed
git add .
git commit -m "feat: add new major feature"

# 3. Create release
.\release.ps1 v2.0.0 "Major release with new features and breaking changes"
```

### For Minor Changes (New Features, Backward Compatible):
```powershell
.\release.ps1 v1.1.0 "Added new UI components and improved performance"
```

### For Patch Changes (Bug Fixes):
```powershell
.\release.ps1 v1.0.1 "Fixed critical bug in data loading"
```

## 🚀 Current Release

**v1.0.0** - Initial Release
- Complete Android project structure
- Radarr and Sonarr integration
- Jetpack Compose UI
- MVVM architecture with Hilt
- Proper package naming (com.stack.app)

## 📝 Release Checklist

Before creating a release:

- [ ] All changes are committed and pushed
- [ ] Working directory is clean
- [ ] You're on the main branch
- [ ] Tests pass (if applicable)
- [ ] README.md is updated (if needed)
- [ ] Version number follows SemVer
- [ ] Release message is descriptive

## 🔄 Release History

| Version | Date | Description |
|---------|------|-------------|
| v1.0.0 | 2025-06-29 | Initial release with complete project structure |

## 📱 GitHub Releases

View all releases on GitHub: https://github.com/Darkrock04/STACK/releases

## 🛠️ Manual Release (if script fails)

```bash
# Create tag
git tag -a v1.1.0 -m "Release v1.1.0: Your release message"

# Push tag
git push origin v1.1.0

# Create GitHub release (via web interface)
# Go to: https://github.com/Darkrock04/STACK/releases/new
```
