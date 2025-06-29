package com.lunasea.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lunasea.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadarrScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.movies),
        stringResource(R.string.queue),
        stringResource(R.string.calendar),
        stringResource(R.string.wanted),
        stringResource(R.string.system)
    )
    
    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) },
                    icon = {
                        Icon(
                            imageVector = when (index) {
                                0 -> Icons.Filled.Star
                                1 -> Icons.Filled.Download
                                2 -> Icons.Filled.DateRange
                                3 -> Icons.Filled.Search
                                4 -> Icons.Filled.Settings
                                else -> Icons.Filled.Star
                            },
                            contentDescription = null
                        )
                    }
                )
            }
        }
        
        when (selectedTabIndex) {
            0 -> RadarrMoviesTab()
            1 -> RadarrQueueTab()
            2 -> RadarrCalendarTab()
            3 -> RadarrWantedTab()
            4 -> RadarrSystemTab()
        }
    }
}

@Composable
fun RadarrMoviesTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.movies),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RadarrQueueTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.queue),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RadarrCalendarTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.calendar),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RadarrWantedTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.wanted),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RadarrSystemTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.system),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.bodyMedium
        )
    }
} 