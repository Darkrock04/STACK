package com.lunasea.app.data.model

// Radarr Movie Models
data class RadarrMovie(
    val id: Int,
    val title: String,
    val path: String,
    val tmdbId: Int,
    val imdbId: String?,
    val year: Int,
    val overview: String,
    val status: String,
    val images: List<RadarrImage>,
    val movieFile: RadarrMovieFile?,
    val hasFile: Boolean,
    val monitored: Boolean,
    val inCinemas: String?,
    val physicalRelease: String?,
    val physicalReleaseNote: String?,
    val digitalRelease: String?,
    val digitalReleaseNote: String?,
    val certification: String?,
    val originalTitle: String?,
    val sortTitle: String?,
    val runtime: Int,
    val ratings: RadarrRatings?,
    val tags: List<Int>,
    val added: String,
    val qualityProfileId: Int,
    val languageProfileId: Int,
    val statistics: RadarrStatistics?
)

data class RadarrMovieFile(
    val id: Int,
    val movieId: Int,
    val relativePath: String,
    val path: String,
    val size: Long,
    val dateAdded: String,
    val sceneName: String?,
    val releaseGroup: String?,
    val quality: RadarrQuality,
    val qualityCutoffNotMet: Boolean,
    val language: RadarrLanguage
)

data class RadarrQuality(
    val quality: RadarrQualityInfo,
    val revision: RadarrRevision
)

data class RadarrQualityInfo(
    val id: Int,
    val name: String,
    val source: String,
    val resolution: Int
)

data class RadarrRevision(
    val version: Int,
    val real: Int,
    val isRepack: Boolean
)

data class RadarrLanguage(
    val id: Int,
    val name: String
)

data class RadarrImage(
    val coverType: String,
    val url: String,
    val remoteUrl: String?
)

data class RadarrRatings(
    val votes: Int,
    val value: Double
)

data class RadarrStatistics(
    val movieFileCount: Int,
    val sizeOnDisk: Long,
    val releaseGroups: List<String>,
    val percentOfMovies: Double
)

// Radarr System Models
data class RadarrSystemStatus(
    val version: String,
    val buildTime: String,
    val isDebug: Boolean,
    val isProduction: Boolean,
    val isAdmin: Boolean,
    val isUserInteractive: Boolean,
    val startupPath: String,
    val appData: String,
    val osName: String,
    val osVersion: String,
    val isMono: Boolean,
    val isLinux: Boolean,
    val isOsx: Boolean,
    val isWindows: Boolean,
    val isDocker: Boolean,
    val mode: String,
    val branch: String,
    val authentication: String,
    val sqliteVersion: String,
    val urlBase: String,
    val runtimeVersion: String,
    val runtimeName: String
)

data class RadarrHealth(
    val source: String,
    val type: String,
    val message: String,
    val wikiUrl: String?
)

data class RadarrDiskSpace(
    val path: String,
    val label: String,
    val freeSpace: Long,
    val totalSpace: Long
)

data class RadarrQueue(
    val id: Int,
    val movieId: Int,
    val language: RadarrLanguage,
    val quality: RadarrQuality,
    val size: Long,
    val title: String,
    val sizeleft: Long,
    val timeleft: String?,
    val estimatedCompletionTime: String?,
    val status: String,
    val trackedDownloadStatus: String,
    val statusMessages: List<RadarrStatusMessage>,
    val downloadId: String,
    val protocol: String,
    val downloadClient: String,
    val indexer: String,
    val outputPath: String,
    val movie: RadarrMovie?
)

data class RadarrStatusMessage(
    val title: String,
    val messages: List<String>
)

data class RadarrQueueResponse(
    val page: Int,
    val pageSize: Int,
    val sortKey: String,
    val sortDir: String,
    val totalRecords: Int,
    val records: List<RadarrQueue>
)

data class RadarrWantedResponse(
    val page: Int,
    val pageSize: Int,
    val sortKey: String,
    val sortDir: String,
    val totalRecords: Int,
    val records: List<RadarrMovie>
)

data class RadarrCommand(
    val name: String,
    val movieId: Int? = null
)

data class RadarrCommandResponse(
    val id: Int,
    val name: String,
    val message: String,
    val body: Map<String, Any>? = null
) 