package com.stack.app.data.repository

import com.stack.app.data.api.RadarrApi
import com.stack.app.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * RadarrRepository - Repository class for Radarr movie management operations
 * 
 * This repository class acts as an abstraction layer between the UI/ViewModel layer
 * and the data source (RadarrApi). It provides a clean interface for accessing
 * Radarr movie data and handles error management and data transformation.
 * 
 * Key responsibilities:
 * - Provide access to Radarr movie data
 * - Handle API errors and exceptions
 * - Transform API responses into appropriate formats
 * - Manage data flow using Kotlin Flow
 * - Abstract data source implementation details
 * 
 * Architecture:
 * - Uses Repository pattern for data access abstraction
 * - Implements dependency injection with Hilt (@Singleton)
 * - Returns Flow<Result<T>> for reactive data streams
 * - Handles exceptions and wraps them in Result types
 * - Provides both streaming and one-shot operations
 * 
 * Error Handling:
 * - Catches all exceptions from API calls
 * - Wraps exceptions in Result.failure()
 * - Provides descriptive error messages
 * - Maintains error context for debugging
 * 
 * Usage:
 * - Injected into ViewModels via Hilt
 * - Used by UI layer through ViewModels
 * - Provides reactive data streams for UI updates
 * - Handles all Radarr-related data operations
 */
@Singleton
class RadarrRepository @Inject constructor(
    private val radarrApi: RadarrApi
) {
    
    /**
     * getSystemStatus - Retrieves the current system status of the Radarr server
     * 
     * This method fetches comprehensive system information from the Radarr server,
     * including version, build details, operating system information, and runtime status.
     * 
     * @return Flow<Result<RadarrSystemStatus>> - Reactive stream containing:
     *         - Success: RadarrSystemStatus with complete system information
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.getSystemStatus() method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Emits the result through the Flow
     * 
     * Use cases:
     * - Display server version in UI
     * - Check server connectivity
     * - Monitor server health
     * - Verify system compatibility
     * 
     * Error handling:
     * - Network errors (connection timeout, DNS resolution)
     * - Authentication errors (invalid API key)
     * - Server errors (500, 404, etc.)
     * - JSON parsing errors
     */
    fun getSystemStatus(): Flow<Result<RadarrSystemStatus>> = flow {
        try {
            val status = radarrApi.getSystemStatus()
            emit(Result.success(status))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    /**
     * getHealth - Retrieves health check information from the Radarr server
     * 
     * This method fetches health status information from the Radarr server,
     * including warnings, errors, and system health indicators.
     * 
     * @return Flow<Result<List<RadarrHealth>>> - Reactive stream containing:
     *         - Success: List of health issues and warnings
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.getHealth() method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Emits the result through the Flow
     * 
     * Use cases:
     * - Display health warnings in UI
     * - Alert users to configuration issues
     * - Monitor system health status
     * - Provide troubleshooting information
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Server-side errors
     * - Data parsing errors
     */
    fun getHealth(): Flow<Result<List<RadarrHealth>>> = flow {
        try {
            val health = radarrApi.getHealth()
            emit(Result.success(health))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    /**
     * getDiskSpace - Retrieves disk space information for all configured paths
     * 
     * This method fetches disk usage statistics for all paths configured in Radarr,
     * including movie storage locations and temporary directories.
     * 
     * @return Flow<Result<List<RadarrDiskSpace>>> - Reactive stream containing:
     *         - Success: List of disk space information
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.getDiskSpace() method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Emits the result through the Flow
     * 
     * Use cases:
     * - Monitor disk usage
     * - Alert when disk space is low
     * - Display storage statistics
     * - Plan storage management
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Server-side errors
     * - Data parsing errors
     */
    fun getDiskSpace(): Flow<Result<List<RadarrDiskSpace>>> = flow {
        try {
            val diskSpace = radarrApi.getDiskSpace()
            emit(Result.success(diskSpace))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    /**
     * getMovies - Retrieves all movies in the Radarr library
     * 
     * This method fetches a complete list of all movies that have been added to Radarr,
     * including their metadata, file information, and monitoring status.
     * 
     * @return Flow<Result<List<RadarrMovie>>> - Reactive stream containing:
     *         - Success: Complete list of movies with metadata
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.getMovies() method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Emits the result through the Flow
     * 
     * Use cases:
     * - Display movie library in UI
     * - Show movie collection statistics
     * - Enable movie management operations
     * - Provide search and filter capabilities
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Server-side errors
     * - Data parsing errors
     */
    fun getMovies(): Flow<Result<List<RadarrMovie>>> = flow {
        try {
            val movies = radarrApi.getMovies()
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    /**
     * getMovieById - Retrieves a specific movie by its unique identifier
     * 
     * This method fetches detailed information for a single movie using its Radarr ID.
     * Useful for displaying detailed movie information or performing specific operations.
     * 
     * @param id Int - The unique Radarr ID of the movie
     * @return Flow<Result<RadarrMovie>> - Reactive stream containing:
     *         - Success: Complete movie information
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.getMovieById(id) method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Emits the result through the Flow
     * 
     * Use cases:
     * - Display detailed movie information
     * - Edit movie settings
     * - View movie file details
     * - Perform movie-specific operations
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Movie not found (404)
     * - Server-side errors
     * - Data parsing errors
     */
    fun getMovieById(id: Int): Flow<Result<RadarrMovie>> = flow {
        try {
            val movie = radarrApi.getMovieById(id)
            emit(Result.success(movie))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    /**
     * getQueue - Retrieves the current download queue
     * 
     * This method fetches all items currently in the download queue, including
     * active downloads, pending items, and failed downloads with retry options.
     * 
     * @return Flow<Result<RadarrQueueResponse>> - Reactive stream containing:
     *         - Success: Queue response with download items
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.getQueue() method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Emits the result through the Flow
     * 
     * Use cases:
     * - Display download progress
     * - Monitor download status
     * - Manage failed downloads
     * - Show download statistics
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Server-side errors
     * - Data parsing errors
     */
    fun getQueue(): Flow<Result<RadarrQueueResponse>> = flow {
        try {
            val queue = radarrApi.getQueue()
            emit(Result.success(queue))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    /**
     * getCalendar - Retrieves movies scheduled for release within a date range
     * 
     * This method fetches movies that are scheduled to be released (or have been released)
     * within the specified date range, useful for calendar views and release tracking.
     * 
     * @param start String - Start date in ISO format (YYYY-MM-DD)
     * @param end String - End date in ISO format (YYYY-MM-DD)
     * @return Flow<Result<List<RadarrMovie>>> - Reactive stream containing:
     *         - Success: List of movies with release dates in range
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.getCalendar(start, end) method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Emits the result through the Flow
     * 
     * Use cases:
     * - Display release calendar
     * - Show upcoming releases
     * - Track release dates
     * - Plan download schedules
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Invalid date format
     * - Server-side errors
     * - Data parsing errors
     */
    fun getCalendar(start: String, end: String): Flow<Result<List<RadarrMovie>>> = flow {
        try {
            val calendar = radarrApi.getCalendar(start, end)
            emit(Result.success(calendar))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    /**
     * getMissing - Retrieves movies that are missing from the library
     * 
     * This method fetches movies that are monitored but don't have files downloaded yet.
     * Results are paginated and can be sorted by various criteria.
     * 
     * @param page Int - Page number for pagination (default: 1)
     * @param pageSize Int - Number of items per page (default: 20)
     * @return Flow<Result<RadarrWantedResponse>> - Reactive stream containing:
     *         - Success: Paginated response with missing movies
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.getMissing(page, pageSize) method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Emits the result through the Flow
     * 
     * Use cases:
     * - Show movies that need to be downloaded
     * - Display missing movie statistics
     * - Enable manual download triggers
     * - Monitor download progress
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Invalid pagination parameters
     * - Server-side errors
     * - Data parsing errors
     */
    fun getMissing(
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<Result<RadarrWantedResponse>> = flow {
        try {
            val missing = radarrApi.getMissing(page, pageSize)
            emit(Result.success(missing))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    /**
     * searchMovies - Searches for movies using a search term
     * 
     * This method performs a movie search using the provided search term,
     * typically used for discovering and adding new movies to the library.
     * 
     * @param term String - Search term (movie title, actor, director, etc.)
     * @return Flow<Result<List<RadarrMovie>>> - Reactive stream containing:
     *         - Success: List of matching movies from various sources
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.searchMovies(term) method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Emits the result through the Flow
     * 
     * Use cases:
     * - Find movies to add to library
     * - Search for specific titles
     * - Discover new movies
     * - Verify movie information
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Empty or invalid search terms
     * - Server-side errors
     * - Data parsing errors
     */
    fun searchMovies(term: String): Flow<Result<List<RadarrMovie>>> = flow {
        try {
            val results = radarrApi.searchMovies(term)
            emit(Result.success(results))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    /**
     * addMovie - Adds a new movie to the Radarr library
     * 
     * This method creates a new movie entry in Radarr with the provided movie data.
     * The movie will be monitored for downloads based on the configuration.
     * 
     * @param movie RadarrMovie - Complete movie object with all required fields
     * @return Result<RadarrMovie> - Result containing:
     *         - Success: The created movie with assigned ID and additional metadata
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.addMovie(movie) method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Returns the result directly (not a Flow)
     * 
     * Use cases:
     * - Add movies from search results
     * - Import movies from external sources
     * - Create custom movie entries
     * - Bulk movie import operations
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Invalid movie data
     * - Duplicate movie entries
     * - Server-side errors
     * - Data parsing errors
     */
    suspend fun addMovie(movie: RadarrMovie): Result<RadarrMovie> {
        return try {
            val result = radarrApi.addMovie(movie)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    /**
     * updateMovie - Updates an existing movie in the Radarr library
     * 
     * This method modifies an existing movie's settings, metadata, or configuration.
     * Changes are applied immediately and may trigger re-scanning or re-downloading.
     * 
     * @param movie RadarrMovie - Updated movie object with modified fields
     * @return Result<RadarrMovie> - Result containing:
     *         - Success: The updated movie with all changes applied
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.updateMovie(movie) method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Returns the result directly (not a Flow)
     * 
     * Use cases:
     * - Change movie quality settings
     * - Update monitoring preferences
     * - Modify movie metadata
     * - Adjust download settings
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Movie not found
     * - Invalid movie data
     * - Server-side errors
     * - Data parsing errors
     */
    suspend fun updateMovie(movie: RadarrMovie): Result<RadarrMovie> {
        return try {
            val result = radarrApi.updateMovie(movie)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    /**
     * deleteMovie - Removes a movie from the Radarr library
     * 
     * This method permanently removes a movie from Radarr, including its metadata
     * and optionally the associated movie files from disk.
     * 
     * @param id Int - The unique Radarr ID of the movie to delete
     * @return Result<Unit> - Result containing:
     *         - Success: Unit indicating successful deletion
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.deleteMovie(id) method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Returns the result directly (not a Flow)
     * 
     * Use cases:
     * - Remove unwanted movies
     * - Clean up library
     * - Remove duplicate entries
     * - Manage storage space
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Movie not found
     * - Permission errors
     * - Server-side errors
     */
    suspend fun deleteMovie(id: Int): Result<Unit> {
        return try {
            radarrApi.deleteMovie(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    /**
     * removeFromQueue - Removes an item from the download queue
     * 
     * This method removes a specific item from the download queue, stopping
     * the download if it's currently in progress.
     * 
     * @param id Int - The unique ID of the queue item to remove
     * @return Result<Unit> - Result containing:
     *         - Success: Unit indicating successful removal
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.removeFromQueue(id) method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Returns the result directly (not a Flow)
     * 
     * Use cases:
     * - Cancel unwanted downloads
     * - Clear failed downloads
     * - Manage queue priority
     * - Free up download slots
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Queue item not found
     * - Permission errors
     * - Server-side errors
     */
    suspend fun removeFromQueue(id: Int): Result<Unit> {
        return try {
            radarrApi.removeFromQueue(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    /**
     * executeCommand - Executes a command on the Radarr server
     * 
     * This method allows execution of various Radarr commands such as
     * refreshing the library, rescanning folders, or triggering downloads.
     * 
     * @param command RadarrCommand - Command object with name and parameters
     * @return Result<RadarrCommandResponse> - Result containing:
     *         - Success: Command response with ID and status
     *         - Failure: Exception with error details
     * 
     * What it does:
     * 1. Calls the RadarrApi.executeCommand(command) method
     * 2. Wraps the response in Result.success()
     * 3. Catches any exceptions and wraps them in Result.failure()
     * 4. Returns the result directly (not a Flow)
     * 
     * Use cases:
     * - Trigger library refresh
     * - Start manual downloads
     * - Execute maintenance tasks
     * - Control Radarr operations
     * 
     * Error handling:
     * - Network connectivity issues
     * - Authentication failures
     * - Invalid command parameters
     * - Command execution errors
     * - Server-side errors
     * - Data parsing errors
     */
    suspend fun executeCommand(command: RadarrCommand): Result<RadarrCommandResponse> {
        return try {
            val result = radarrApi.executeCommand(command)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
} 