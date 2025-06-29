package com.lunasea.app.ui.navigation

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
import com.lunasea.app.R
import com.lunasea.app.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunaSeaNavigation() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    label = { Text(stringResource(R.string.sonarr)) },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Sonarr.route } == true,
                    onClick = {
                        navController.navigate(Screen.Sonarr.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Star, contentDescription = null) },
                    label = { Text(stringResource(R.string.radarr)) },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Radarr.route } == true,
                    onClick = {
                        navController.navigate(Screen.Radarr.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                    label = { Text(stringResource(R.string.settings)) },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Settings.route } == true,
                    onClick = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Sonarr.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Sonarr.route) {
                SonarrScreen(navController)
            }
            composable(Screen.Radarr.route) {
                RadarrScreen(navController)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController)
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Sonarr : Screen("sonarr")
    object Radarr : Screen("radarr")
    object Settings : Screen("settings")
} 