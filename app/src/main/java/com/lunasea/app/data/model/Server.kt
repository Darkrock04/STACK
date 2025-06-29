package com.lunasea.app.data.model

data class Server(
    val id: String = "",
    val name: String = "",
    val url: String = "",
    val apiKey: String = "",
    val type: ServerType = ServerType.SONARR,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ServerType {
    SONARR,
    RADARR
} 