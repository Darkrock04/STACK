package com.stack.app.data.api

import com.stack.app.data.model.RadarrQueueResponse
import com.stack.app.data.model.RadarrWantedResponse
import com.stack.app.data.model.RadarrCommand
import com.stack.app.data.model.RadarrCommandResponse
import com.stack.app.data.model.*
import retrofit2.http.*

/**
 * RadarrApi - Interface defining all API endpoints for Radarr movie management
 * 
 * This interface defines the contract for communicating with a Radarr server instance.
 * Radarr is a movie collection manager that automatically downloads movies from various sources.
 * 
 * Key responsibilities:
 * - Define HTTP endpoints for Radarr API v3
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
 * 2. Movie endpoints - CRUD operations for movies
 * 3. Queue endpoints - Download queue management
 * 4. Calendar endpoints - Movie release calendar
 * 5. Wanted endpoints - Missing movies
 * 6. Search endpoints - Movie discovery
 * 7. Command endpoints - Execute Radarr commands
 */
interface RadarrApi {
    
    // ==================== SYSTEM ENDPOINTS ====================
    
    /**
     * getSystemStatus - Retrieves the current system status of the Radarr server
     * 
     * This endpoint provides comprehensive information about the Radarr server instance,
     * including version, build information, operating system details, and runtime information.
     * 
     * @return RadarrSystemStatus - Complete system information including:
     *         - Version and build details
     *         - Operating system information
     *         - Runtime environment details
     *         - Authentication status
     *         - Database information
     * 
     * Use cases:
     * - Check if Radarr server is running and accessible
     * - Display server version in UI
     * - Verify system compatibility
     * - Monitor server health
     */
    @GET("system/status")
    suspend fun getSystemStatus(): RadarrSystemStatus
    
    /**
     * getHealth - Retrieves health check information from the Radarr server
     * 
     * This endpoint returns a list of health issues, warnings, and status messages
     * that indicate the current state of the Radarr installation.
     * 
     * @return List<RadarrHealth> - List of health issues including:
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
    suspend fun getHealth(): List<RadarrHealth>
    
    /**
     * getDiskSpace - Retrieves disk space information for all configured paths
     * 
     * This endpoint provides disk usage statistics for all paths configured in Radarr,
     * including movie storage locations and temporary directories.
     * 
     * @return List<RadarrDiskSpace> - List of disk space information including:
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
    suspend fun getDiskSpace(): List<RadarrDiskSpace>
    
    // ==================== MOVIE ENDPOINTS ====================
    
    /**
     * getMovies - Retrieves all movies in the Radarr library
     * 
     * This endpoint returns a complete list of all movies that have been added to Radarr,
     * including their metadata, file information, and monitoring status.
     * 
     * @return List<RadarrMovie> - Complete list of movies with:
     *         - Movie metadata (title, year, overview, etc.)
     *         - File information and quality details
     *         - Monitoring and download status
     *         - Statistics and ratings
     * 
     * Use cases:
     * - Display movie library in UI
     * - Show movie collection statistics
     * - Enable movie management operations
     * - Provide search and filter capabilities
     */
    @GET("movie")
    suspend fun getMovies(): List<RadarrMovie>
    
    /**
     * getMovieById - Retrieves a specific movie by its unique identifier
     * 
     * This endpoint fetches detailed information for a single movie using its Radarr ID.
     * Useful for displaying detailed movie information or performing specific operations.
     * 
     * @param id Int - The unique Radarr ID of the movie
     * @return RadarrMovie - Complete movie information including all metadata
     * 
     * Use cases:
     * - Display detailed movie information
     * - Edit movie settings
     * - View movie file details
     * - Perform movie-specific operations
     */
    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): RadarrMovie
    
    /**
     * addMovie - Adds a new movie to the Radarr library
     * 
     * This endpoint creates a new movie entry in Radarr with the provided movie data.
     * The movie will be monitored for downloads based on the configuration.
     * 
     * @param movie RadarrMovie - Complete movie object with all required fields
     * @return RadarrMovie - The created movie with assigned ID and additional metadata
     * 
     * Use cases:
     * - Add movies from search results
     * - Import movies from external sources
     * - Create custom movie entries
     * - Bulk movie import operations
     */
    @POST("movie")
    suspend fun addMovie(@Body movie: RadarrMovie): RadarrMovie
    
    /**
     * updateMovie - Updates an existing movie in the Radarr library
     * 
     * This endpoint modifies an existing movie's settings, metadata, or configuration.
     * Changes are applied immediately and may trigger re-scanning or re-downloading.
     * 
     * @param movie RadarrMovie - Updated movie object with modified fields
     * @return RadarrMovie - The updated movie with all changes applied
     * 
     * Use cases:
     * - Change movie quality settings
     * - Update monitoring preferences
     * - Modify movie metadata
     * - Adjust download settings
     */
    @PUT("movie")
    suspend fun updateMovie(@Body movie: RadarrMovie): RadarrMovie
    
    /**
     * deleteMovie - Removes a movie from the Radarr library
     * 
     * This endpoint permanently removes a movie from Radarr, including its metadata
     * and optionally the associated movie files from disk.
     * 
     * @param id Int - The unique Radarr ID of the movie to delete
     * 
     * Use cases:
     * - Remove unwanted movies
     * - Clean up library
     * - Remove duplicate entries
     * - Manage storage space
     */
    @DELETE("movie/{id}")
    suspend fun deleteMovie(@Path("id") id: Int)
    
    // ==================== QUEUE ENDPOINTS ====================
    
    /**
     * getQueue - Retrieves the current download queue
     * 
     * This endpoint returns all items currently in the download queue, including
     * active downloads, pending items, and failed downloads with retry options.
     * 
     * @return RadarrQueueResponse - Paginated response containing:
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
    suspend fun getQueue(): RadarrQueueResponse
    
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
     * getCalendar - Retrieves movies scheduled for release within a date range
     * 
     * This endpoint returns movies that are scheduled to be released (or have been released)
     * within the specified date range, useful for calendar views and release tracking.
     * 
     * @param start String - Start date in ISO format (YYYY-MM-DD)
     * @param end String - End date in ISO format (YYYY-MM-DD)
     * @return List<RadarrMovie> - Movies with release dates in the specified range
     * 
     * Use cases:
     * - Display release calendar
     * - Show upcoming releases
     * - Track release dates
     * - Plan download schedules
     */
    @GET("calendar")
    suspend fun getCalendar(
        @Query("start") start: String,
        @Query("end") end: String
    ): List<RadarrMovie>
    
    // ==================== WANTED ENDPOINTS ====================
    
    /**
     * getMissing - Retrieves movies that are missing from the library
     * 
     * This endpoint returns movies that are monitored but don't have files downloaded yet.
     * Results are paginated and can be sorted by various criteria.
     * 
     * @param page Int - Page number for pagination (default: 1)
     * @param pageSize Int - Number of items per page (default: 20)
     * @param sortKey String - Field to sort by (default: "physicalRelease")
     * @param sortDir String - Sort direction: "asc" or "desc" (default: "desc")
     * @return RadarrWantedResponse - Paginated response with missing movies
     * 
     * Use cases:
     * - Show movies that need to be downloaded
     * - Display missing movie statistics
     * - Enable manual download triggers
     * - Monitor download progress
     */
    @GET("wanted/missing")
    suspend fun getMissing(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("sortKey") sortKey: String = "physicalRelease",
        @Query("sortDir") sortDir: String = "desc"
    ): RadarrWantedResponse
    
    // ==================== SEARCH ENDPOINTS ====================
    
    /**
     * searchMovies - Searches for movies using a search term
     * 
     * This endpoint performs a movie search using the provided search term,
     * typically used for discovering and adding new movies to the library.
     * 
     * @param term String - Search term (movie title, actor, director, etc.)
     * @return List<RadarrMovie> - List of matching movies from various sources
     * 
     * Use cases:
     * - Find movies to add to library
     * - Search for specific titles
     * - Discover new movies
     * - Verify movie information
     */
    @GET("movie/lookup")
    suspend fun searchMovies(@Query("term") term: String): List<RadarrMovie>
    
    // ==================== COMMAND ENDPOINTS ====================
    
    /**
     * executeCommand - Executes a command on the Radarr server
     * 
     * This endpoint allows execution of various Radarr commands such as
     * refreshing the library, rescanning folders, or triggering downloads.
     * 
     * @param command RadarrCommand - Command object with name and parameters
     * @return RadarrCommandResponse - Response with command ID and status
     * 
     * Use cases:
     * - Trigger library refresh
     * - Start manual downloads
     * - Execute maintenance tasks
     * - Control Radarr operations
     */
    @POST("command")
    suspend fun executeCommand(@Body command: RadarrCommand): RadarrCommandResponse
} 