package com.stack.app.data.api

import com.stack.app.data.model.SonarrQueueResponse
import com.stack.app.data.model.SonarrWantedResponse
import com.stack.app.data.model.EpisodeMonitoringRequest
import com.stack.app.data.model.SonarrCommand
import com.stack.app.data.model.SonarrCommandResponse
import com.stack.app.data.model.*
import retrofit2.http.*

interface SonarrApi {
    
    // System endpoints
    @GET("system/status")
    suspend fun getSystemStatus(): SonarrSystemStatus
    
    @GET("health")
    suspend fun getHealth(): List<SonarrHealth>
    
    @GET("diskspace")
    suspend fun getDiskSpace(): List<SonarrDiskSpace>
    
    // Series endpoints
    @GET("series")
    suspend fun getSeries(): List<SonarrSeries>
    
    @GET("series/{id}")
    suspend fun getSeriesById(@Path("id") id: Int): SonarrSeries
    
    @POST("series")
    suspend fun addSeries(@Body series: SonarrSeries): SonarrSeries
    
    @PUT("series")
    suspend fun updateSeries(@Body series: SonarrSeries): SonarrSeries
    
    @DELETE("series/{id}")
    suspend fun deleteSeries(@Path("id") id: Int)
    
    // Episode endpoints
    @GET("episode")
    suspend fun getEpisodes(@Query("seriesId") seriesId: Int? = null): List<SonarrEpisode>
    
    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): SonarrEpisode
    
    @PUT("episode")
    suspend fun updateEpisode(@Body episode: SonarrEpisode): SonarrEpisode
    
    @PUT("episode/monitor")
    suspend fun updateEpisodeMonitoring(@Body request: EpisodeMonitoringRequest)
    
    // Queue endpoints
    @GET("queue")
    suspend fun getQueue(): SonarrQueueResponse
    
    @DELETE("queue/{id}")
    suspend fun removeFromQueue(@Path("id") id: Int)
    
    // Calendar endpoints
    @GET("calendar")
    suspend fun getCalendar(
        @Query("start") start: String,
        @Query("end") end: String
    ): List<SonarrEpisode>
    
    // Wanted endpoints
    @GET("wanted/missing")
    suspend fun getMissing(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("sortKey") sortKey: String = "airDateUtc",
        @Query("sortDir") sortDir: String = "desc"
    ): SonarrWantedResponse
    
    // Search endpoints
    @GET("series/lookup")
    suspend fun searchSeries(@Query("term") term: String): List<SonarrSeries>
    
    // Command endpoints
    @POST("command")
    suspend fun executeCommand(@Body command: SonarrCommand): SonarrCommandResponse
}

data class SonarrQueueResponse(
    val page: Int,
    val pageSize: Int,
    val sortKey: String,
    val sortDir: String,
    val totalRecords: Int,
    val records: List<SonarrQueue>
)

data class SonarrWantedResponse(
    val page: Int,
    val pageSize: Int,
    val sortKey: String,
    val sortDir: String,
    val totalRecords: Int,
    val records: List<SonarrEpisode>
)

data class EpisodeMonitoringRequest(
    val episodeIds: List<Int>,
    val monitored: Boolean
)

data class SonarrCommand(
    val name: String,
    val seriesId: Int? = null,
    val episodeIds: List<Int>? = null
)

data class SonarrCommandResponse(
    val id: Int,
    val name: String,
    val message: String,
    val body: Map<String, Any>? = null
) 