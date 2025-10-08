package com.stack.app.data.api

import com.stack.app.data.model.RadarrQueueResponse
import com.stack.app.data.model.RadarrWantedResponse
import com.stack.app.data.model.RadarrCommand
import com.stack.app.data.model.RadarrCommandResponse
import com.stack.app.data.model.*
import retrofit2.http.*

interface RadarrApi {
    
    // System endpoints
    @GET("system/status")
    suspend fun getSystemStatus(): RadarrSystemStatus
    
    @GET("health")
    suspend fun getHealth(): List<RadarrHealth>
    
    @GET("diskspace")
    suspend fun getDiskSpace(): List<RadarrDiskSpace>
    
    // Movie endpoints
    @GET("movie")
    suspend fun getMovies(): List<RadarrMovie>
    
    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): RadarrMovie
    
    @POST("movie")
    suspend fun addMovie(@Body movie: RadarrMovie): RadarrMovie
    
    @PUT("movie")
    suspend fun updateMovie(@Body movie: RadarrMovie): RadarrMovie
    
    @DELETE("movie/{id}")
    suspend fun deleteMovie(@Path("id") id: Int)
    
    // Queue endpoints
    @GET("queue")
    suspend fun getQueue(): RadarrQueueResponse
    
    @DELETE("queue/{id}")
    suspend fun removeFromQueue(@Path("id") id: Int)
    
    // Calendar endpoints
    @GET("calendar")
    suspend fun getCalendar(
        @Query("start") start: String,
        @Query("end") end: String
    ): List<RadarrMovie>
    
    // Wanted endpoints
    @GET("wanted/missing")
    suspend fun getMissing(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("sortKey") sortKey: String = "physicalRelease",
        @Query("sortDir") sortDir: String = "desc"
    ): RadarrWantedResponse
    
    // Search endpoints
    @GET("movie/lookup")
    suspend fun searchMovies(@Query("term") term: String): List<RadarrMovie>
    
    // Command endpoints
    @POST("command")
    suspend fun executeCommand(@Body command: RadarrCommand): RadarrCommandResponse
} 