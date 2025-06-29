package com.lunasea.app.data.model

import com.google.gson.annotations.SerializedName

// Sonarr Series Models
data class SonarrSeries(
    val id: Int,
    val title: String,
    val path: String,
    val tvdbId: Int,
    val tvMazeId: Int?,
    val imdbId: String?,
    val type: String,
    val genre: List<String>,
    val overview: String,
    val network: String?,
    val airTime: String?,
    val status: String,
    val images: List<SonarrImage>,
    val seasons: List<SonarrSeason>,
    val year: Int,
    val firstAired: String?,
    val inCinemas: String?,
    val physicalRelease: String?,
    val physicalReleaseNote: String?,
    val digitalRelease: String?,
    val digitalReleaseNote: String?,
    val certification: String?,
    val originalTitle: String?,
    val sortTitle: String?,
    val runtime: Int,
    val ratings: SonarrRatings?,
    val tags: List<Int>,
    val added: String,
    val qualityProfileId: Int,
    val languageProfileId: Int,
    val seasonFolder: Boolean,
    val monitored: Boolean,
    val useSceneNumbering: Boolean,
    val tvRageId: Int?,
    val lastInfoSync: String?,
    val seriesType: String,
    val cleanTitle: String,
    val titleSlug: String,
    val genres: List<String>,
    val statistics: SonarrStatistics?
)

data class SonarrSeason(
    val seasonNumber: Int,
    val monitored: Boolean,
    val statistics: SonarrStatistics?
)

data class SonarrEpisode(
    val id: Int,
    val seriesId: Int,
    val episodeFileId: Int,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val title: String,
    val airDate: String?,
    val airDateUtc: String?,
    val overview: String,
    val episodeFile: SonarrEpisodeFile?,
    val hasFile: Boolean,
    val monitored: Boolean,
    val absoluteEpisodeNumber: Int?,
    val unverifiedSceneNumbering: Boolean,
    val lastSearchTime: String?,
    val grabDate: String?,
    val seriesTitle: String,
    val series: SonarrSeries?
)

data class SonarrEpisodeFile(
    val id: Int,
    val seriesId: Int,
    val seasonNumber: Int,
    val relativePath: String,
    val path: String,
    val size: Long,
    val dateAdded: String,
    val sceneName: String?,
    val releaseGroup: String?,
    val quality: SonarrQuality,
    val qualityCutoffNotMet: Boolean,
    val language: SonarrLanguage
)

data class SonarrQuality(
    val quality: SonarrQualityInfo,
    val revision: SonarrRevision
)

data class SonarrQualityInfo(
    val id: Int,
    val name: String,
    val source: String,
    val resolution: Int
)

data class SonarrRevision(
    val version: Int,
    val real: Int,
    val isRepack: Boolean
)

data class SonarrLanguage(
    val id: Int,
    val name: String
)

data class SonarrImage(
    val coverType: String,
    val url: String,
    val remoteUrl: String?
)

data class SonarrRatings(
    val votes: Int,
    val value: Double
)

data class SonarrStatistics(
    val episodeCount: Int,
    val episodeFileCount: Int,
    val totalEpisodeCount: Int,
    val sizeOnDisk: Long,
    val releaseGroups: List<String>,
    val percentOfEpisodes: Double
)

// Sonarr System Models
data class SonarrSystemStatus(
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

data class SonarrHealth(
    val source: String,
    val type: String,
    val message: String,
    val wikiUrl: String?
)

data class SonarrDiskSpace(
    val path: String,
    val label: String,
    val freeSpace: Long,
    val totalSpace: Long
)

data class SonarrQueue(
    val id: Int,
    val seriesId: Int,
    val episodeId: Int,
    val language: SonarrLanguage,
    val quality: SonarrQuality,
    val size: Long,
    val title: String,
    val sizeleft: Long,
    val timeleft: String?,
    val estimatedCompletionTime: String?,
    val status: String,
    val trackedDownloadStatus: String,
    val statusMessages: List<SonarrStatusMessage>,
    val downloadId: String,
    val protocol: String,
    val downloadClient: String,
    val indexer: String,
    val outputPath: String,
    val episode: SonarrEpisode?
)

data class SonarrStatusMessage(
    val title: String,
    val messages: List<String>
)

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