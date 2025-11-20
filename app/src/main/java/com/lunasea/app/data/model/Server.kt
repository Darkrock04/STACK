package com.stack.app.data.model

/**
 * Server - Data class representing a media server configuration
 * 
 * This data class represents a configured media server (Sonarr or Radarr) that the app
 * can connect to. It stores all necessary connection information and metadata.
 * 
 * Key responsibilities:
 * - Store server connection details (URL, API key)
 * - Identify server type (Sonarr or Radarr)
 * - Track server status and metadata
 * - Provide unique identification for each server
 * 
 * Architecture:
 * - Immutable data class with default values
 * - Uses enum for type safety
 * - Includes timestamp for creation tracking
 * - Designed for JSON serialization/deserialization
 * 
 * Usage:
 * - Created when user adds a new server in settings
 * - Stored in DataStore for persistence
 * - Used by repositories to make API calls
 * - Displayed in UI for server management
 */
data class Server(
    /**
     * id - Unique identifier for the server
     * 
     * This property provides a unique identifier for each server configuration.
     * Typically generated using UUID.randomUUID().toString() when creating a new server.
     * 
     * Purpose:
     * - Uniquely identify each server configuration
     * - Enable server updates and deletions
     * - Support multiple server configurations
     * - Maintain referential integrity
     */
    val id: String = "",
    
    /**
     * name - Human-readable name for the server
     * 
     * This property stores a user-friendly name for the server, typically entered
     * by the user when adding a new server configuration.
     * 
     * Purpose:
     * - Display in UI for easy identification
     * - Help users distinguish between multiple servers
     * - Provide context for server purpose
     * - Enable user-friendly server management
     */
    val name: String = "",
    
    /**
     * url - Base URL of the media server
     * 
     * This property stores the complete URL to the media server instance,
     * including protocol (http/https) and port number.
     * 
     * Examples:
     * - "http://localhost:8989" (Sonarr default)
     * - "http://localhost:7878" (Radarr default)
     * - "https://sonarr.example.com"
     * - "http://192.168.1.100:8989"
     * 
     * Purpose:
     * - Establish connection to the media server
     * - Support both local and remote servers
     * - Enable HTTPS for secure connections
     * - Allow custom port configurations
     */
    val url: String = "",
    
    /**
     * apiKey - API key for authenticating with the media server
     * 
     * This property stores the API key required to authenticate with the media server.
     * The API key is obtained from the server's web interface under Settings > General.
     * 
     * Security considerations:
     * - Should be treated as sensitive data
     * - Stored securely in DataStore
     * - Not logged or exposed in error messages
     * - Required for all API operations
     * 
     * Purpose:
     * - Authenticate API requests
     * - Access protected server resources
     * - Enable automated operations
     * - Maintain security for server access
     */
    val apiKey: String = "",
    
    /**
     * type - Type of media server (Sonarr or Radarr)
     * 
     * This property identifies the type of media server, determining which API
     * endpoints and data models to use when communicating with the server.
     * 
     * Values:
     * - ServerType.SONARR - TV series management server
     * - ServerType.RADARR - Movie management server
     * 
     * Purpose:
     * - Determine appropriate API interface
     * - Route requests to correct endpoints
     * - Display appropriate UI elements
     * - Validate server compatibility
     */
    val type: ServerType = ServerType.SONARR,
    
    /**
     * isActive - Whether the server is currently active/enabled
     * 
     * This property indicates whether the server configuration is currently active
     * and should be used for API operations. Allows users to temporarily disable
     * servers without deleting them.
     * 
     * Purpose:
     * - Enable/disable server without deletion
     * - Support server maintenance periods
     * - Allow temporary server unavailability
     * - Provide server status management
     */
    val isActive: Boolean = true,
    
    /**
     * createdAt - Timestamp when the server was created
     * 
     * This property stores the timestamp when the server configuration was first created,
     * using System.currentTimeMillis() for the current time in milliseconds.
     * 
     * Purpose:
     * - Track server creation time
     * - Support server management features
     * - Enable chronological ordering
     * - Provide audit trail information
     */
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * ServerType - Enumeration of supported media server types
 * 
 * This enum defines the types of media servers that the application supports.
 * Each type corresponds to a different media management system with its own API.
 * 
 * Values:
 * - SONARR - TV series collection manager
 * - RADARR - Movie collection manager
 * 
 * Purpose:
 * - Type-safe server type identification
 * - Enable type-specific API routing
 * - Support different data models
 * - Allow extensibility for future server types
 * 
 * Usage:
 * - Used in Server data class
 * - Determines which API interface to use
 * - Enables type-specific UI elements
 * - Supports server validation logic
 */
enum class ServerType {
    /**
     * SONARR - TV series collection manager
     * 
     * Sonarr is a PVR (Personal Video Recorder) for Usenet and BitTorrent users.
     * It can monitor multiple RSS feeds for new episodes of your favorite shows
     * and will grab, sort and rename them.
     * 
     * Features:
     * - Automatic TV series discovery
     * - Episode download management
     * - Quality profile management
     * - Calendar and scheduling
     * - Health monitoring
     */
    SONARR,
    
    /**
     * RADARR - Movie collection manager
     * 
     * Radarr is a movie collection manager for Usenet and BitTorrent users.
     * It can monitor multiple RSS feeds for new movies and will grab, sort
     * and rename them.
     * 
     * Features:
     * - Automatic movie discovery
     * - Movie download management
     * - Quality profile management
     * - Calendar and scheduling
     * - Health monitoring
     */
    RADARR
} 