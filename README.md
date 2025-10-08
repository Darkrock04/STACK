# STACK - Android Media Management App

A modern Android application for managing media servers like Radarr and Sonarr, built with Jetpack Compose and following MVVM architecture.

## 🚀 Features

- **Radarr Integration**: Manage movie collections and monitor downloads
- **Sonarr Integration**: Handle TV series management and episode tracking
- **Modern UI**: Built with Jetpack Compose for a beautiful, responsive interface
- **MVVM Architecture**: Clean separation of concerns with ViewModels
- **Repository Pattern**: Efficient data management with local and remote sources
- **Dependency Injection**: Using Hilt for clean dependency management

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp
- **Async Operations**: Coroutines + Flow
- **Build System**: Gradle with Kotlin DSL

## 📱 Screenshots

![6271737350333778902_121](https://github.com/user-attachments/assets/52febd57-6328-4633-89e3-246922825f52)


## 🏗️ Project Structure

```
app/src/main/java/com/stack/app/
├── data/
│   ├── api/           # API interfaces (RadarrApi, SonarrApi)
│   ├── model/         # Data models (RadarrModels, SonarrModels, Server)
│   └── repository/    # Repository implementations
├── di/                # Dependency injection modules
├── ui/
│   ├── components/    # Reusable UI components
│   ├── navigation/    # Navigation setup
│   ├── screens/       # Screen implementations
│   ├── theme/         # App theme and styling
│   └── viewmodel/     # ViewModels for each screen
├── MainActivity.kt    # Main activity entry point
└── StackApplication.kt # Application class
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 26+
- Kotlin 2.0+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Darkrock04/STACK.git
   ```

2. Open the project in Android Studio

3. Sync Gradle files and build the project

4. Run the app on your device or emulator

## 📋 Configuration

### Server Setup

1. Configure your Radarr server settings in the app:
   - **IP Address**: `localhost`
   - **Port**: Default is `7878` for Radarr
   - **API Key**: Your Radarr API key

2. Configure your Sonarr server settings in the app:
   - **IP Address**: `localhost`
   - **Port**: Default is `8989` for Sonarr
   - **API Key**: Your Sonarr API key

3. Ensure your servers are accessible from your device

### API Configuration

The app uses the following APIs:
- **Radarr API**: For movie management
- **Sonarr API**: For TV series management

## 🧪 Testing

Run tests using:
```bash
./gradlew test
```

## 📦 Building

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## 🤝 Contributing

We welcome contributions to make STACK even better! Here's how you can help:

### How to Contribute

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/amazing-feature`
3. **Make your changes**: Write clean, well-documented code
4. **Test your changes**: Ensure everything works as expected
5. **Commit your changes**: Use clear, descriptive commit messages
6. **Push to your branch**: `git push origin feature/amazing-feature`
7. **Open a Pull Request**: Provide a clear description of your changes

### Contribution Guidelines

- Follow the existing code style and conventions
- Add tests for new features
- Update documentation as needed
- Ensure your code compiles without errors
- Follow Android development best practices

### What We're Looking For

- 🐛 Bug fixes and improvements
- ✨ New features and enhancements
- 📱 UI/UX improvements
- 📚 Documentation updates
- 🧪 Additional test coverage
- 🔧 Performance optimizations

### Getting Help

If you need help or have questions:
- Open an issue for bugs or feature requests
- Join our discussions for general questions
- Check existing issues and pull requests

### Code of Conduct

We're committed to providing a welcoming and inclusive environment for all contributors. Please be respectful and constructive in all interactions.

---

**Thank you for contributing to STACK!** 🎉

Your contributions help make this app better for everyone in the media management community.
