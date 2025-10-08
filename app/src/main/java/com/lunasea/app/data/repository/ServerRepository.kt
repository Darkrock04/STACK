package com.stack.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stack.app.data.model.Server
import com.stack.app.data.model.ServerType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "servers")

@Singleton
class ServerRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val gson = Gson()
    private val httpClient = OkHttpClient()
    
    val servers: Flow<List<Server>> = context.dataStore.data.map { preferences ->
        val serversJson = preferences[SERVERS_KEY] ?: "[]"
        val type = object : TypeToken<List<Server>>() {}.type
        gson.fromJson(serversJson, type) ?: emptyList()
    }
    
    suspend fun addServer(server: Server) {
        context.dataStore.edit { preferences ->
            val currentServers = getCurrentServers(preferences)
            val updatedServers = currentServers + server
            preferences[SERVERS_KEY] = gson.toJson(updatedServers)
        }
    }
    
    suspend fun updateServer(server: Server) {
        context.dataStore.edit { preferences ->
            val currentServers = getCurrentServers(preferences)
            val updatedServers = currentServers.map { 
                if (it.id == server.id) server else it 
            }
            preferences[SERVERS_KEY] = gson.toJson(updatedServers)
        }
    }
    
    suspend fun deleteServer(serverId: String) {
        context.dataStore.edit { preferences ->
            val currentServers = getCurrentServers(preferences)
            val updatedServers = currentServers.filter { it.id != serverId }
            preferences[SERVERS_KEY] = gson.toJson(updatedServers)
        }
    }
    
    suspend fun testConnection(server: Server): ConnectionResult = withContext(Dispatchers.IO) {
        try {
            val endpoint = when (server.type) {
                ServerType.SONARR -> "api/v3/system/status"
                ServerType.RADARR -> "api/v3/system/status"
            }
            val url = "${server.url.trimEnd('/')}" + "/$endpoint"
            println("Testing connection to: $url with API key: ${server.apiKey}")

            val request = Request.Builder()
                .url(url)
                .addHeader("X-Api-Key", server.apiKey)
                .build()

            val response = httpClient.newCall(request).execute()

            if (response.isSuccessful) {
                ConnectionResult.Success("Connection successful!")
            } else {
                ConnectionResult.Error("Server responded with status: ${response.code} - ${response.message}")
            }
        } catch (e: Exception) {
            ConnectionResult.Error("Connection failed: ${e::class.java.simpleName}: ${e.message}")
        }
    }
    
    private fun getCurrentServers(preferences: Preferences): List<Server> {
        val serversJson = preferences[SERVERS_KEY] ?: "[]"
        val type = object : TypeToken<List<Server>>() {}.type
        return gson.fromJson(serversJson, type) ?: emptyList()
    }
    
    companion object {
        private val SERVERS_KEY = stringPreferencesKey("servers")
    }
}

sealed class ConnectionResult {
    data class Success(val message: String) : ConnectionResult()
    data class Error(val message: String) : ConnectionResult()
} 