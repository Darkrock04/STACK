package com.stack.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * StackApplication - The main Application class for the Stack Android app
 * 
 * This class extends Android's Application class and serves as the entry point
 * for the entire application lifecycle. It's responsible for initializing
 * application-wide components and dependencies.
 * 
 * Key responsibilities:
 * - Initialize Hilt dependency injection framework
 * - Set up application-wide configurations
 * - Provide a central point for application-level initialization
 * - Manage application lifecycle events
 * 
 * Architecture:
 * - Uses Hilt for dependency injection (@HiltAndroidApp)
 * - Follows the Application class pattern for Android apps
 * - Enables dependency injection throughout the entire app
 * - Provides a singleton instance accessible from anywhere in the app
 * 
 * Why it's important:
 * - Hilt requires an Application class annotated with @HiltAndroidApp
 * - This annotation tells Hilt to generate the necessary dependency injection code
 * - It allows the app to use dependency injection in Activities, Fragments, Services, etc.
 * - It's the foundation for the entire dependency injection system
 */
@HiltAndroidApp
class StackApplication : Application() 