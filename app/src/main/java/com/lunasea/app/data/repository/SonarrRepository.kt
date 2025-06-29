package com.lunasea.app.data.repository

import com.lunasea.app.data.api.SonarrApi
import com.lunasea.app.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SonarrRepository @Inject constructor(
    private val sonarrApi: SonarrApi
) {
    
    fun getSystemStatus(): Flow<Result<SonarrSystemStatus>> = flow {
        try {
            val status = sonarrApi.getSystemStatus()
            emit(Result.success(status))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getHealth(): Flow<Result<List<SonarrHealth>>> = flow {
        try {
            val health = sonarrApi.getHealth()
            emit(Result.success(health))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getDiskSpace(): Flow<Result<List<SonarrDiskSpace>>> = flow {
        try {
            val diskSpace = sonarrApi.getDiskSpace()
            emit(Result.success(diskSpace))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getSeries(): Flow<Result<List<SonarrSeries>>> = flow {
        try {
            val series = sonarrApi.getSeries()
            emit(Result.success(series))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getSeriesById(id: Int): Flow<Result<SonarrSeries>> = flow {
        try {
            val series = sonarrApi.getSeriesById(id)
            emit(Result.success(series))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getEpisodes(seriesId: Int? = null): Flow<Result<List<SonarrEpisode>>> = flow {
        try {
            val episodes = sonarrApi.getEpisodes(seriesId)
            emit(Result.success(episodes))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getQueue(): Flow<Result<SonarrQueueResponse>> = flow {
        try {
            val queue = sonarrApi.getQueue()
            emit(Result.success(queue))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getCalendar(start: String, end: String): Flow<Result<List<SonarrEpisode>>> = flow {
        try {
            val calendar = sonarrApi.getCalendar(start, end)
            emit(Result.success(calendar))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun getMissing(
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<Result<SonarrWantedResponse>> = flow {
        try {
            val missing = sonarrApi.getMissing(page, pageSize)
            emit(Result.success(missing))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    fun searchSeries(term: String): Flow<Result<List<SonarrSeries>>> = flow {
        try {
            val results = sonarrApi.searchSeries(term)
            emit(Result.success(results))
        } catch (e: Exception) {
            emit(Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e)))
        }
    }
    
    suspend fun addSeries(series: SonarrSeries): Result<SonarrSeries> {
        return try {
            val result = sonarrApi.addSeries(series)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    suspend fun updateSeries(series: SonarrSeries): Result<SonarrSeries> {
        return try {
            val result = sonarrApi.updateSeries(series)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    suspend fun deleteSeries(id: Int): Result<Unit> {
        return try {
            sonarrApi.deleteSeries(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    suspend fun removeFromQueue(id: Int): Result<Unit> {
        return try {
            sonarrApi.removeFromQueue(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
    
    suspend fun executeCommand(command: SonarrCommand): Result<SonarrCommandResponse> {
        return try {
            val result = sonarrApi.executeCommand(command)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("${e::class.java.simpleName}: ${e.message}", e))
        }
    }
} 