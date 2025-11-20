package com.stack.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stack.app.R
import com.stack.app.ui.screens.*

/**
 * LunaSeaNavigation - Main navigation component for the Stack Android app
 * 
 * This composable function provides the main navigation structure for the application,
 * including bottom navigation bar and screen routing. It implements a tab-based
 * navigation pattern with three main sections: Sonarr, Radarr, and Settings.
 * 
 * Key responsibilities:
 * - Provide bottom navigation bar with three tabs
 * - Handle navigation between main screens
 * - Manage navigation state and back stack
 * - Display appropriate icons and labels for each tab
 * - Implement proper navigation behavior (single top, state saving)
 * 
 * Architecture:
 * - Uses Jetpack Compose Navigation
 * - Implements Material Design 3 navigation patterns
 * - Uses Scaffold for consistent layout structure
 * - Manages navigation state with NavController
 * - Provides proper navigation animations and transitions
 * 
 * Navigation Structure:
 * - Sonarr tab: TV series management
 * - Radarr tab: Movie management  
 * - Settings tab: App configuration and server management
 * 
 * Features:
 * - Bottom navigation bar with icons and labels
 * - Proper navigation state management
 * - Single top launch mode for tabs
 * - State saving and restoration
 * - Material Design 3 styling
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunaSeaNavigation() {
    // Create and remember the navigation controller
    val navController = rememberNavController()
    
    // Main scaffold with bottom navigation bar
    Scaffold(
        bottomBar = {
            NavigationBar {
                // Get current navigation state
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                // Sonarr Tab
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    label = { Text(stringResource(R.string.sonarr)) },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Sonarr.route } == true,
                    onClick = {
                        navController.navigate(Screen.Sonarr.route) {
                            // Clear back stack to start destination
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Prevent multiple instances of same destination
                            launchSingleTop = true
                            // Restore saved state
                            restoreState = true
                        }
                    }
                )
                
                // Radarr Tab
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Star, contentDescription = null) },
                    label = { Text(stringResource(R.string.radarr)) },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Radarr.route } == true,
                    onClick = {
                        navController.navigate(Screen.Radarr.route) {
                            // Clear back stack to start destination
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Prevent multiple instances of same destination
                            launchSingleTop = true
                            // Restore saved state
                            restoreState = true
                        }
                    }
                )
                
                // Settings Tab
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                    label = { Text(stringResource(R.string.settings)) },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Settings.route } == true,
                    onClick = {
                        navController.navigate(Screen.Settings.route) {
                            // Clear back stack to start destination
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Prevent multiple instances of same destination
                            launchSingleTop = true
                            // Restore saved state
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        // Navigation host for screen routing
        NavHost(
            navController = navController,
            startDestination = Screen.Sonarr.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Sonarr screen route
            composable(Screen.Sonarr.route) {
                SonarrScreen(navController)
            }
            // Radarr screen route
            composable(Screen.Radarr.route) {
                RadarrScreen(navController)
            }
            // Settings screen route
            composable(Screen.Settings.route) {
                SettingsScreen(navController)
            }
        }
    }
}

/**
 * Screen - Sealed class defining all navigation destinations
 * 
 * This sealed class defines all possible navigation destinations in the app.
 * Each destination has a unique route string that corresponds to its navigation path.
 * 
 * Key responsibilities:
 * - Define all possible navigation routes
 * - Provide type-safe navigation destinations
 * - Enable easy route management and updates
 * - Support navigation testing and validation
 * 
 * Architecture:
 * - Uses sealed class for type safety
 * - Each destination is an object with a route property
 * - Routes are simple strings for easy navigation
 * - Supports future expansion with additional destinations
 * 
 * Destinations:
 * - Sonarr: TV series management screen
 * - Radarr: Movie management screen
 * - Settings: App configuration screen
 */
sealed class Screen(val route: String) {
    /**
     * Sonarr - TV series management screen
     * 
     * This destination represents the main Sonarr screen where users can
     * manage their TV series collection, view episodes, and monitor downloads.
     * 
     * Route: "sonarr"
     * Features: Series list, queue, calendar, wanted episodes, system status
     */
    object Sonarr : Screen("sonarr")
    
    /**
     * Radarr - Movie management screen
     * 
     * This destination represents the main Radarr screen where users can
     * manage their movie collection, view releases, and monitor downloads.
     * 
     * Route: "radarr"
     * Features: Movie list, queue, calendar, wanted movies, system status
     */
    object Radarr : Screen("radarr")
    
    /**
     * Settings - App configuration screen
     * 
     * This destination represents the settings screen where users can
     * configure servers, manage app preferences, and view app information.
     * 
     * Route: "settings"
     * Features: Server management, app preferences, about information
     */
    object Settings : Screen("settings")
} 