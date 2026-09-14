package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist ORDER BY lastWatchedTimestamp DESC")
    fun getAllWatchlist(): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE animeId = :animeId LIMIT 1")
    fun getWatchlistEntry(animeId: String): Flow<WatchlistEntity?>

    @Query("SELECT * FROM watchlist WHERE animeId = :animeId LIMIT 1")
    suspend fun getWatchlistEntryDirect(animeId: String): WatchlistEntity?

    @Query("SELECT * FROM watchlist WHERE notifyNewEpisodes = 1")
    suspend fun getAnimeSubscribedForNotifications(): List<WatchlistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entry: WatchlistEntity)

    @Update
    suspend fun update(entry: WatchlistEntity)

    @Delete
    suspend fun delete(entry: WatchlistEntity)

    @Query("DELETE FROM watchlist WHERE animeId = :animeId")
    suspend fun deleteById(animeId: String)

    @Query("UPDATE watchlist SET episodesWatched = :episodesWatched, lastWatchedTimestamp = :timestamp WHERE animeId = :animeId")
    suspend fun updateProgress(animeId: String, episodesWatched: Int, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE watchlist SET notifyNewEpisodes = :enabled WHERE animeId = :animeId")
    suspend fun updateNotificationPreference(animeId: String, enabled: Boolean)
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM episode_notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<EpisodeNotificationEntity>>

    @Query("SELECT COUNT(*) FROM episode_notifications WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: EpisodeNotificationEntity): Long

    @Query("UPDATE episode_notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("UPDATE episode_notifications SET isRead = 1")
    suspend fun markAllAsRead()

    @Query("DELETE FROM episode_notifications WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM episode_notifications")
    suspend fun clearAll()
}
