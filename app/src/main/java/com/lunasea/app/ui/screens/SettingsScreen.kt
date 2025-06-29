package com.lunasea.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lunasea.app.R
import com.lunasea.app.data.model.Server
import com.lunasea.app.data.model.ServerType
import com.lunasea.app.data.repository.ConnectionResult
import com.lunasea.app.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val servers by viewModel.servers.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val isAutoRefresh by viewModel.isAutoRefresh.collectAsState()
    val connectionTestResult by viewModel.connectionTestResult.collectAsState()
    val isTestingConnection by viewModel.isTestingConnection.collectAsState()
    
    var showAddServerDialog by remember { mutableStateOf(false) }
    
    // Show connection test result
    LaunchedEffect(connectionTestResult) {
        connectionTestResult?.let {
            // Auto-clear result after 3 seconds
            kotlinx.coroutines.delay(3000)
            viewModel.clearConnectionTestResult()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Server Management Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Server Management",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Button(
                            onClick = { showAddServerDialog = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(R.string.add_server))
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        if (servers.isEmpty()) {
                            Text(
                                text = "No servers configured",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            servers.forEach { server ->
                                ServerItem(
                                    server = server,
                                    onDelete = { viewModel.deleteServer(server.id) },
                                    onTestConnection = { viewModel.testConnection(server) },
                                    isTestingConnection = isTestingConnection
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
            
            // App Settings Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "App Settings",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Dark Theme",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { viewModel.toggleDarkTheme() }
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Auto Refresh",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Switch(
                                checked = isAutoRefresh,
                                onCheckedChange = { viewModel.toggleAutoRefresh() }
                            )
                        }
                    }
                }
            }
            
            // About Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "About",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Stack Android",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Version 1.0.0",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
    
    // Connection test result snackbar
    connectionTestResult?.let { result ->
        val message = when (result) {
            is ConnectionResult.Success -> result.message
            is ConnectionResult.Error -> result.message
        }
        val isSuccess = result is ConnectionResult.Success
        
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(
                    onClick = { viewModel.clearConnectionTestResult() }
                ) {
                    Text("Dismiss")
                }
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    if (isSuccess) Icons.Filled.CheckCircle else Icons.Filled.Error,
                    contentDescription = null,
                    tint = if (isSuccess) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(message)
            }
        }
    }
    
    if (showAddServerDialog) {
        AddServerDialog(
            onDismiss = { showAddServerDialog = false },
            onAddServer = { name, url, apiKey, type ->
                viewModel.addServer(name, url, apiKey, type)
                showAddServerDialog = false
            }
        )
    }
}

@Composable
fun ServerItem(
    server: Server,
    onDelete: () -> Unit,
    onTestConnection: () -> Unit,
    isTestingConnection: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = server.name,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = server.url,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = server.type.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete server",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = onTestConnection,
                enabled = !isTestingConnection,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isTestingConnection) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Testing...")
                } else {
                    Icon(
                        Icons.Filled.Wifi,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Test Connection")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServerDialog(
    onDismiss: () -> Unit,
    onAddServer: (name: String, url: String, apiKey: String, type: ServerType) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var apiKey by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(ServerType.SONARR) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Server") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Server Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("Server URL") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("http://localhost:8989") }
                )
                
                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("API Key") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Server Type: ", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Row {
                        RadioButton(
                            selected = selectedType == ServerType.SONARR,
                            onClick = { selectedType = ServerType.SONARR }
                        )
                        Text("Sonarr", modifier = Modifier.padding(start = 4.dp))
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        RadioButton(
                            selected = selectedType == ServerType.RADARR,
                            onClick = { selectedType = ServerType.RADARR }
                        )
                        Text("Radarr", modifier = Modifier.padding(start = 4.dp))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && url.isNotBlank() && apiKey.isNotBlank()) {
                        onAddServer(name, url, apiKey, selectedType)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 