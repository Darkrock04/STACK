package com.stack.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.stack.app.ui.theme.LunaSeaTheme
import com.stack.app.ui.navigation.LunaSeaNavigation
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity - The main entry point of the Stack Android application
 * 
 * This class serves as the primary activity that hosts the entire application UI.
 * It extends ComponentActivity to support Jetpack Compose and uses Hilt for dependency injection.
 * 
 * Key responsibilities:
 * - Initialize the Compose UI system
 * - Apply the application theme (LunaSeaTheme)
 * - Set up the main navigation structure
 * - Provide a full-screen surface for the UI content
 * 
 * Architecture:
 * - Uses Jetpack Compose for modern declarative UI
 * - Integrates with Hilt for dependency injection (@AndroidEntryPoint)
 * - Follows Material Design 3 principles
 * - Implements single-activity architecture pattern
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    /**
     * onCreate - Called when the activity is first created
     * 
     * This method is the main initialization point for the activity.
     * It sets up the Compose UI content and applies the application theme.
     * 
     * @param savedInstanceState Bundle containing the activity's previously saved state
     *                           (null if this is the first time the activity is created)
     * 
     * What it does:
     * 1. Calls super.onCreate() to ensure proper parent class initialization
     * 2. Sets up Compose content using setContent { }
     * 3. Applies LunaSeaTheme for consistent styling across the app
     * 4. Creates a Surface component that fills the entire screen
     * 5. Sets the background color using Material Design color scheme
     * 6. Embeds the LunaSeaNavigation component for app navigation
     * 
     * Why it's important:
     * - This is where the entire UI is initialized
     * - The Surface component provides the foundation for all UI elements
     * - LunaSeaNavigation handles all screen transitions and bottom navigation
     * - MaterialTheme ensures consistent colors, typography, and shapes
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LunaSeaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LunaSeaNavigation()
                }
            }
        }
    }
} 