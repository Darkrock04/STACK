package com.lunasea.app.data.repository

import com.lunasea.app.data.api.RadarrApi
import com.lunasea.app.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RadarrRepository @Inject constructor(
    private val radarrApi: RadarrApi
) {
    
    fun getSystemStatus(): Flow<Result<RadarrSystemStatus>> = flow {
        try {
            val status = radarrApi.getSystemStatus()
            emit(Result.success(status))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getHealth(): Flow<Result<List<RadarrHealth>>> = flow {
        try {
            val health = radarrApi.getHealth()
            emit(Result.success(health))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getDiskSpace(): Flow<Result<List<RadarrDiskSpace>>> = flow {
        try {
            val diskSpace = radarrApi.getDiskSpace()
            emit(Result.success(diskSpace))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getMovies(): Flow<Result<List<RadarrMovie>>> = flow {
        try {
            val movies = radarrApi.getMovies()
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getMovieById(id: Int): Flow<Result<RadarrMovie>> = flow {
        try {
            val movie = radarrApi.getMovieById(id)
            emit(Result.success(movie))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getQueue(): Flow<Result<RadarrQueueResponse>> = flow {
        try {
            val queue = radarrApi.getQueue()
            emit(Result.success(queue))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getCalendar(start: String, end: String): Flow<Result<List<RadarrMovie>>> = flow {
        try {
            val calendar = radarrApi.getCalendar(start, end)
            emit(Result.success(calendar))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
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
    
    fun searchMovies(term: String): Flow<Result<List<RadarrMovie>>> = flow {
        try {
            val results = radarrApi.searchMovies(term)
            emit(Result.success(results))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    suspend fun addMovie(movie: RadarrMovie): Result<RadarrMovie> {
        return try {
            val result = radarrApi.addMovie(movie)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    suspend fun updateMovie(movie: RadarrMovie): Result<RadarrMovie> {
        return try {
            val result = radarrApi.updateMovie(movie)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    suspend fun deleteMovie(id: Int): Result<Unit> {
        return try {
            radarrApi.deleteMovie(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    suspend fun removeFromQueue(id: Int): Result<Unit> {
        return try {
            radarrApi.removeFromQueue(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    suspend fun executeCommand(command: RadarrCommand): Result<RadarrCommandResponse> {
        return try {
            val result = radarrApi.executeCommand(command)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
} 