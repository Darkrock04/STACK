package com.lunasea.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lunasea.app.R
import com.lunasea.app.ui.viewmodel.SonarrViewModel
import com.lunasea.app.ui.viewmodel.SonarrSeriesState
import com.lunasea.app.ui.viewmodel.SonarrQueueState
import com.lunasea.app.ui.viewmodel.SonarrSystemState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SonarrScreen(navController: NavController) {
    val viewModel: SonarrViewModel = hiltViewModel()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.series),
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
                                0 -> Icons.Filled.Home
                                1 -> Icons.Filled.Download
                                2 -> Icons.Filled.DateRange
                                3 -> Icons.Filled.Search
                                4 -> Icons.Filled.Settings
                                else -> Icons.Filled.Home
                            },
                            contentDescription = null
                        )
                    }
                )
            }
        }
        
        when (selectedTabIndex) {
            0 -> SonarrSeriesTab(viewModel)
            1 -> SonarrQueueTab(viewModel)
            2 -> SonarrCalendarTab()
            3 -> SonarrWantedTab()
            4 -> SonarrSystemTab(viewModel)
        }
    }
}

@Composable
fun SonarrSeriesTab(viewModel: SonarrViewModel) {
    val seriesState by viewModel.seriesState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.series),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        when (seriesState) {
            is SonarrSeriesState.Loading -> {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is SonarrSeriesState.Success -> {
                val series = (seriesState as SonarrSeriesState.Success).series
                if (series.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_data),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = "Found ${series.size} series",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is SonarrSeriesState.Error -> {
                Text(
                    text = (seriesState as SonarrSeriesState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun SonarrQueueTab(viewModel: SonarrViewModel) {
    val queueState by viewModel.queueState.collectAsState()
    
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
        
        when (queueState) {
            is SonarrQueueState.Loading -> {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is SonarrQueueState.Success -> {
                val queue = (queueState as SonarrQueueState.Success).queue
                if (queue.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_data),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = "${queue.size} items in queue",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is SonarrQueueState.Error -> {
                Text(
                    text = (queueState as SonarrQueueState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun SonarrCalendarTab() {
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
fun SonarrWantedTab() {
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
fun SonarrSystemTab(viewModel: SonarrViewModel) {
    val systemState by viewModel.systemState.collectAsState()
    
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
        
        when (systemState) {
            is SonarrSystemState.Loading -> {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is SonarrSystemState.Success -> {
                val status = (systemState as SonarrSystemState.Success).status
                if (status != null) {
                    Text(
                        text = "Version: ${status.version}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "OS: ${status.osName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = stringResource(R.string.no_data),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is SonarrSystemState.Error -> {
                Text(
                    text = (systemState as SonarrSystemState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
} 