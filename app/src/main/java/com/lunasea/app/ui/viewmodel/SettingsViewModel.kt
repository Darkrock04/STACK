package com.stack.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stack.app.data.model.Server
import com.stack.app.data.model.ServerType
import com.stack.app.data.repository.ConnectionResult
import com.stack.app.data.repository.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val serverRepository: ServerRepository
) : ViewModel() {

    private val _servers = MutableStateFlow<List<Server>>(emptyList())
    val servers: StateFlow<List<Server>> = _servers.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _isAutoRefresh = MutableStateFlow(true)
    val isAutoRefresh: StateFlow<Boolean> = _isAutoRefresh.asStateFlow()

    private val _connectionTestResult = MutableStateFlow<ConnectionResult?>(null)
    val connectionTestResult: StateFlow<ConnectionResult?> = _connectionTestResult.asStateFlow()

    private val _isTestingConnection = MutableStateFlow(false)
    val isTestingConnection: StateFlow<Boolean> = _isTestingConnection.asStateFlow()

    init {
        loadServers()
    }

    private fun loadServers() {
        viewModelScope.launch {
            serverRepository.servers.collect { servers ->
                _servers.value = servers
            }
        }
    }

    fun addServer(name: String, url: String, apiKey: String, type: ServerType) {
        viewModelScope.launch {
            val server = Server(
                id = UUID.randomUUID().toString(),
                name = name,
                url = url,
                apiKey = apiKey,
                type = type
            )
            serverRepository.addServer(server)
        }
    }

    fun updateServer(server: Server) {
        viewModelScope.launch {
            serverRepository.updateServer(server)
        }
    }

    fun deleteServer(serverId: String) {
        viewModelScope.launch {
            serverRepository.deleteServer(serverId)
        }
    }

    fun testConnection(server: Server) {
        viewModelScope.launch {
            _isTestingConnection.value = true
            _connectionTestResult.value = null
            
            val result = serverRepository.testConnection(server)
            _connectionTestResult.value = result
            _isTestingConnection.value = false
        }
    }

    fun clearConnectionTestResult() {
        _connectionTestResult.value = null
    }

    fun toggleDarkTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun toggleAutoRefresh() {
        _isAutoRefresh.value = !_isAutoRefresh.value
    }
} 