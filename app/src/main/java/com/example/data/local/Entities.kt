package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.WatchStatus

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey
    val animeId: String,
    val title: String,
    val posterUrl: String,
    val bannerUrl: String = "",
    val totalEpisodes: Int = 12,
    val episodesWatched: Int = 0,
    val userScore: Int = 0, // 0 to 10
    val status: WatchStatus = WatchStatus.PLAN_TO_WATCH,
    val notifyNewEpisodes: Boolean = true,
    val lastWatchedTimestamp: Long = System.currentTimeMillis(),
    val notes: String = "",
    val favorite: Boolean = false,
    val lastKnownEpisode: Int = 12
)

@Entity(tableName = "episode_notifications")
data class EpisodeNotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val animeId: String,
    val animeTitle: String,
    val episodeNumber: Int,
    val source: String, // e.g. "AnimePahe", "Gogoanime", "HiAnime"
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val posterUrl: String = ""
)
