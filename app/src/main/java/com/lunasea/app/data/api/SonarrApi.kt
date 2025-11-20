package com.stack.app.data.api

import com.stack.app.data.model.SonarrQueueResponse
import com.stack.app.data.model.SonarrWantedResponse
import com.stack.app.data.model.EpisodeMonitoringRequest
import com.stack.app.data.model.SonarrCommand
import com.stack.app.data.model.SonarrCommandResponse
import com.stack.app.data.model.*
import retrofit2.http.*

/**
 * SonarrApi - Interface defining all API endpoints for Sonarr TV series management
 * 
 * This interface defines the contract for communicating with a Sonarr server instance.
 * Sonarr is a TV series collection manager that automatically downloads TV episodes from various sources.
 * 
 * Key responsibilities:
 * - Define HTTP endpoints for Sonarr API v3
 * - Provide type-safe method signatures for API calls
 * - Handle authentication via API key headers
 * - Support both synchronous and asynchronous operations
 * 
 * Architecture:
 * - Uses Retrofit annotations for HTTP method mapping
 * - Returns suspend functions for coroutine-based async operations
 * - Maps JSON responses to strongly-typed Kotlin data classes
 * - Supports query parameters, path parameters, and request bodies
 * 
 * API Categories:
 * 1. System endpoints - Server status, health, disk space
 * 2. Series endpoints - CRUD operations for TV series
 * 3. Episode endpoints - Episode management and monitoring
 * 4. Queue endpoints - Download queue management
 * 5. Calendar endpoints - Episode air date calendar
 * 6. Wanted endpoints - Missing episodes
 * 7. Search endpoints - Series discovery
 * 8. Command endpoints - Execute Sonarr commands
 */
interface SonarrApi {
    
    // ==================== SYSTEM ENDPOINTS ====================
    
    /**
     * getSystemStatus - Retrieves the current system status of the Sonarr server
     * 
     * This endpoint provides comprehensive information about the Sonarr server instance,
     * including version, build information, operating system details, and runtime information.
     * 
     * @return SonarrSystemStatus - Complete system information including:
     *         - Version and build details
     *         - Operating system information
     *         - Runtime environment details
     *         - Authentication status
     *         - Database information
     * 
     * Use cases:
     * - Check if Sonarr server is running and accessible
     * - Display server version in UI
     * - Verify system compatibility
     * - Monitor server health
     */
    @GET("system/status")
    suspend fun getSystemStatus(): SonarrSystemStatus
    
    /**
     * getHealth - Retrieves health check information from the Sonarr server
     * 
     * This endpoint returns a list of health issues, warnings, and status messages
     * that indicate the current state of the Sonarr installation.
     * 
     * @return List<SonarrHealth> - List of health issues including:
     *         - Issue source and type
     *         - Descriptive message
     *         - Optional wiki URL for more information
     * 
     * Use cases:
     * - Display health warnings in the UI
     * - Alert users to configuration issues
     * - Monitor system health status
     * - Provide troubleshooting information
     */
    @GET("health")
    suspend fun getHealth(): List<SonarrHealth>
    
    /**
     * getDiskSpace - Retrieves disk space information for all configured paths
     * 
     * This endpoint provides disk usage statistics for all paths configured in Sonarr,
     * including TV series storage locations and temporary directories.
     * 
     * @return List<SonarrDiskSpace> - List of disk space information including:
     *         - Path and label
     *         - Free space available
     *         - Total space capacity
     * 
     * Use cases:
     * - Monitor disk usage
     * - Alert when disk space is low
     * - Display storage statistics
     * - Plan storage management
     */
    @GET("diskspace")
    suspend fun getDiskSpace(): List<SonarrDiskSpace>
    
    // ==================== SERIES ENDPOINTS ====================
    
    /**
     * getSeries - Retrieves all TV series in the Sonarr library
     * 
     * This endpoint returns a complete list of all TV series that have been added to Sonarr,
     * including their metadata, episode information, and monitoring status.
     * 
     * @return List<SonarrSeries> - Complete list of series with:
     *         - Series metadata (title, year, overview, etc.)
     *         - Episode information and quality details
     *         - Monitoring and download status
     *         - Statistics and ratings
     * 
     * Use cases:
     * - Display TV series library in UI
     * - Show series collection statistics
     * - Enable series management operations
     * - Provide search and filter capabilities
     */
    @GET("series")
    suspend fun getSeries(): List<SonarrSeries>
    
    /**
     * getSeriesById - Retrieves a specific TV series by its unique identifier
     * 
     * This endpoint fetches detailed information for a single TV series using its Sonarr ID.
     * Useful for displaying detailed series information or performing specific operations.
     * 
     * @param id Int - The unique Sonarr ID of the TV series
     * @return SonarrSeries - Complete series information including all metadata
     * 
     * Use cases:
     * - Display detailed series information
     * - Edit series settings
     * - View episode details
     * - Perform series-specific operations
     */
    @GET("series/{id}")
    suspend fun getSeriesById(@Path("id") id: Int): SonarrSeries
    
    /**
     * addSeries - Adds a new TV series to the Sonarr library
     * 
     * This endpoint creates a new TV series entry in Sonarr with the provided series data.
     * The series will be monitored for episode downloads based on the configuration.
     * 
     * @param series SonarrSeries - Complete series object with all required fields
     * @return SonarrSeries - The created series with assigned ID and additional metadata
     * 
     * Use cases:
     * - Add series from search results
     * - Import series from external sources
     * - Create custom series entries
     * - Bulk series import operations
     */
    @POST("series")
    suspend fun addSeries(@Body series: SonarrSeries): SonarrSeries
    
    /**
     * updateSeries - Updates an existing TV series in the Sonarr library
     * 
     * This endpoint modifies an existing series' settings, metadata, or configuration.
     * Changes are applied immediately and may trigger re-scanning or re-downloading.
     * 
     * @param series SonarrSeries - Updated series object with modified fields
     * @return SonarrSeries - The updated series with all changes applied
     * 
     * Use cases:
     * - Change series quality settings
     * - Update monitoring preferences
     * - Modify series metadata
     * - Adjust download settings
     */
    @PUT("series")
    suspend fun updateSeries(@Body series: SonarrSeries): SonarrSeries
    
    /**
     * deleteSeries - Removes a TV series from the Sonarr library
     * 
     * This endpoint permanently removes a TV series from Sonarr, including its metadata
     * and optionally the associated episode files from disk.
     * 
     * @param id Int - The unique Sonarr ID of the series to delete
     * 
     * Use cases:
     * - Remove unwanted series
     * - Clean up library
     * - Remove duplicate entries
     * - Manage storage space
     */
    @DELETE("series/{id}")
    suspend fun deleteSeries(@Path("id") id: Int)
    
    // ==================== EPISODE ENDPOINTS ====================
    
    /**
     * getEpisodes - Retrieves episodes for a specific series or all episodes
     * 
     * This endpoint returns episode information, either for a specific series or all episodes
     * in the library. Episodes include metadata, file information, and monitoring status.
     * 
     * @param seriesId Int? - Optional series ID to filter episodes (null for all episodes)
     * @return List<SonarrEpisode> - List of episodes with:
     *         - Episode metadata (title, air date, overview, etc.)
     *         - File information and quality details
     *         - Monitoring and download status
     *         - Series association
     * 
     * Use cases:
     * - Display episode lists for a series
     * - Show all episodes in library
     * - Enable episode management operations
     * - Provide episode search capabilities
     */
    @GET("episode")
    suspend fun getEpisodes(@Query("seriesId") seriesId: Int? = null): List<SonarrEpisode>
    
    /**
     * getEpisodeById - Retrieves a specific episode by its unique identifier
     * 
     * This endpoint fetches detailed information for a single episode using its Sonarr ID.
     * Useful for displaying detailed episode information or performing specific operations.
     * 
     * @param id Int - The unique Sonarr ID of the episode
     * @return SonarrEpisode - Complete episode information including all metadata
     * 
     * Use cases:
     * - Display detailed episode information
     * - Edit episode settings
     * - View episode file details
     * - Perform episode-specific operations
     */
    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): SonarrEpisode
    
    /**
     * updateEpisode - Updates an existing episode in the Sonarr library
     * 
     * This endpoint modifies an existing episode's settings, metadata, or configuration.
     * Changes are applied immediately and may trigger re-scanning or re-downloading.
     * 
     * @param episode SonarrEpisode - Updated episode object with modified fields
     * @return SonarrEpisode - The updated episode with all changes applied
     * 
     * Use cases:
     * - Change episode quality settings
     * - Update monitoring preferences
     * - Modify episode metadata
     * - Adjust download settings
     */
    @PUT("episode")
    suspend fun updateEpisode(@Body episode: SonarrEpisode): SonarrEpisode
    
    /**
     * updateEpisodeMonitoring - Updates the monitoring status for multiple episodes
     * 
     * This endpoint allows bulk updating of episode monitoring status, enabling or disabling
     * monitoring for multiple episodes at once.
     * 
     * @param request EpisodeMonitoringRequest - Request containing episode IDs and monitoring status
     * 
     * Use cases:
     * - Enable/disable monitoring for multiple episodes
     * - Bulk episode management operations
     * - Season-wide monitoring changes
     * - Series-wide monitoring updates
     */
    @PUT("episode/monitor")
    suspend fun updateEpisodeMonitoring(@Body request: EpisodeMonitoringRequest)
    
    // ==================== QUEUE ENDPOINTS ====================
    
    /**
     * getQueue - Retrieves the current download queue
     * 
     * This endpoint returns all items currently in the download queue, including
     * active downloads, pending items, and failed downloads with retry options.
     * 
     * @return SonarrQueueResponse - Paginated response containing:
     *         - Queue items with download status
     *         - Progress information
     *         - Error messages and retry options
     *         - Download client information
     * 
     * Use cases:
     * - Display download progress
     * - Monitor download status
     * - Manage failed downloads
     * - Show download statistics
     */
    @GET("queue")
    suspend fun getQueue(): SonarrQueueResponse
    
    /**
     * removeFromQueue - Removes an item from the download queue
     * 
     * This endpoint removes a specific item from the download queue, stopping
     * the download if it's currently in progress.
     * 
     * @param id Int - The unique ID of the queue item to remove
     * 
     * Use cases:
     * - Cancel unwanted downloads
     * - Clear failed downloads
     * - Manage queue priority
     * - Free up download slots
     */
    @DELETE("queue/{id}")
    suspend fun removeFromQueue(@Path("id") id: Int)
    
    // ==================== CALENDAR ENDPOINTS ====================
    
    /**
     * getCalendar - Retrieves episodes scheduled to air within a date range
     * 
     * This endpoint returns episodes that are scheduled to air (or have aired)
     * within the specified date range, useful for calendar views and air date tracking.
     * 
     * @param start String - Start date in ISO format (YYYY-MM-DD)
     * @param end String - End date in ISO format (YYYY-MM-DD)
     * @return List<SonarrEpisode> - Episodes with air dates in the specified range
     * 
     * Use cases:
     * - Display air date calendar
     * - Show upcoming episodes
     * - Track episode air dates
     * - Plan download schedules
     */
    @GET("calendar")
    suspend fun getCalendar(
        @Query("start") start: String,
        @Query("end") end: String
    ): List<SonarrEpisode>
    
    // ==================== WANTED ENDPOINTS ====================
    
    /**
     * getMissing - Retrieves episodes that are missing from the library
     * 
     * This endpoint returns episodes that are monitored but don't have files downloaded yet.
     * Results are paginated and can be sorted by various criteria.
     * 
     * @param page Int - Page number for pagination (default: 1)
     * @param pageSize Int - Number of items per page (default: 20)
     * @param sortKey String - Field to sort by (default: "airDateUtc")
     * @param sortDir String - Sort direction: "asc" or "desc" (default: "desc")
     * @return SonarrWantedResponse - Paginated response with missing episodes
     * 
     * Use cases:
     * - Show episodes that need to be downloaded
     * - Display missing episode statistics
     * - Enable manual download triggers
     * - Monitor download progress
     */
    @GET("wanted/missing")
    suspend fun getMissing(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("sortKey") sortKey: String = "airDateUtc",
        @Query("sortDir") sortDir: String = "desc"
    ): SonarrWantedResponse
    
    // ==================== SEARCH ENDPOINTS ====================
    
    /**
     * searchSeries - Searches for TV series using a search term
     * 
     * This endpoint performs a TV series search using the provided search term,
     * typically used for discovering and adding new series to the library.
     * 
     * @param term String - Search term (series title, actor, director, etc.)
     * @return List<SonarrSeries> - List of matching series from various sources
     * 
     * Use cases:
     * - Find series to add to library
     * - Search for specific titles
     * - Discover new series
     * - Verify series information
     */
    @GET("series/lookup")
    suspend fun searchSeries(@Query("term") term: String): List<SonarrSeries>
    
    // ==================== COMMAND ENDPOINTS ====================
    
    /**
     * executeCommand - Executes a command on the Sonarr server
     * 
     * This endpoint allows execution of various Sonarr commands such as
     * refreshing the library, rescanning folders, or triggering downloads.
     * 
     * @param command SonarrCommand - Command object with name and parameters
     * @return SonarrCommandResponse - Response with command ID and status
     * 
     * Use cases:
     * - Trigger library refresh
     * - Start manual downloads
     * - Execute maintenance tasks
     * - Control Sonarr operations
     */
    @POST("command")
    suspend fun executeCommand(@Body command: SonarrCommand): SonarrCommandResponse
}

// ==================== DATA CLASSES ====================

/**
 * SonarrQueueResponse - Response wrapper for queue endpoint
 * 
 * This data class represents the paginated response from the queue endpoint,
 * containing queue items and pagination metadata.
 * 
 * @property page Current page number
 * @property pageSize Number of items per page
 * @property sortKey Field used for sorting
 * @property sortDir Sort direction (asc/desc)
 * @property totalRecords Total number of records
 * @property records List of queue items
 */
data class SonarrQueueResponse(
    val page: Int,
    val pageSize: Int,
    val sortKey: String,
    val sortDir: String,
    val totalRecords: Int,
    val records: List<SonarrQueue>
)

/**
 * SonarrWantedResponse - Response wrapper for wanted/missing endpoint
 * 
 * This data class represents the paginated response from the wanted endpoint,
 * containing missing episodes and pagination metadata.
 * 
 * @property page Current page number
 * @property pageSize Number of items per page
 * @property sortKey Field used for sorting
 * @property sortDir Sort direction (asc/desc)
 * @property totalRecords Total number of records
 * @property records List of missing episodes
 */
data class SonarrWantedResponse(
    val page: Int,
    val pageSize: Int,
    val sortKey: String,
    val sortDir: String,
    val totalRecords: Int,
    val records: List<SonarrEpisode>
)

/**
 * EpisodeMonitoringRequest - Request object for bulk episode monitoring updates
 * 
 * This data class represents a request to update the monitoring status
 * for multiple episodes at once.
 * 
 * @property episodeIds List of episode IDs to update
 * @property monitored New monitoring status (true/false)
 */
data class EpisodeMonitoringRequest(
    val episodeIds: List<Int>,
    val monitored: Boolean
)

/**
 * SonarrCommand - Command object for executing Sonarr operations
 * 
 * This data class represents a command to be executed on the Sonarr server,
 * with optional parameters for specific operations.
 * 
 * @property name Name of the command to execute
 * @property seriesId Optional series ID for series-specific commands
 * @property episodeIds Optional list of episode IDs for episode-specific commands
 */
data class SonarrCommand(
    val name: String,
    val seriesId: Int? = null,
    val episodeIds: List<Int>? = null
)

/**
 * SonarrCommandResponse - Response object for command execution
 * 
 * This data class represents the response from executing a Sonarr command,
 * containing the command ID and status information.
 * 
 * @property id Unique command ID
 * @property name Name of the executed command
 * @property message Status message from the command
 * @property body Optional additional response data
 */
data class SonarrCommandResponse(
    val id: Int,
    val name: String,
    val message: String,
    val body: Map<String, Any>? = null
) 