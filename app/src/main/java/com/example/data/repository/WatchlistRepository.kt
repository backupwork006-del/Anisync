package com.example.data.repository

import com.example.data.local.EpisodeNotificationEntity
import com.example.data.local.NotificationDao
import com.example.data.local.WatchlistDao
import com.example.data.local.WatchlistEntity
import com.example.data.model.WatchStatus
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject

class WatchlistRepository(
    private val watchlistDao: WatchlistDao,
    private val notificationDao: NotificationDao
) {
    val allWatchlist: Flow<List<WatchlistEntity>> = watchlistDao.getAllWatchlist()
    val allNotifications: Flow<List<EpisodeNotificationEntity>> = notificationDao.getAllNotifications()
    val unreadNotificationsCount: Flow<Int> = notificationDao.getUnreadCount()

    fun getWatchlistEntry(animeId: String): Flow<WatchlistEntity?> =
        watchlistDao.getWatchlistEntry(animeId)

    suspend fun getWatchlistEntryDirect(animeId: String): WatchlistEntity? =
        watchlistDao.getWatchlistEntryDirect(animeId)

    suspend fun addToWatchlist(
        animeId: String,
        title: String,
        posterUrl: String,
        bannerUrl: String = "",
        totalEpisodes: Int = 12,
        status: WatchStatus = WatchStatus.WATCHING,
        episodesWatched: Int = 0,
        notify: Boolean = true
    ) {
        val existing = watchlistDao.getWatchlistEntryDirect(animeId)
        val entry = existing?.copy(
            status = status,
            episodesWatched = episodesWatched,
            notifyNewEpisodes = notify,
            lastWatchedTimestamp = System.currentTimeMillis()
        ) ?: WatchlistEntity(
            animeId = animeId,
            title = title,
            posterUrl = posterUrl,
            bannerUrl = bannerUrl,
            totalEpisodes = totalEpisodes,
            episodesWatched = episodesWatched,
            status = status,
            notifyNewEpisodes = notify,
            lastWatchedTimestamp = System.currentTimeMillis()
        )
        watchlistDao.insertOrUpdate(entry)
    }

    suspend fun updateEntry(entry: WatchlistEntity) {
        watchlistDao.insertOrUpdate(entry)
    }

    suspend fun updateProgress(animeId: String, episodesWatched: Int) {
        watchlistDao.updateProgress(animeId, episodesWatched)
    }

    suspend fun toggleNotification(animeId: String, enabled: Boolean) {
        watchlistDao.updateNotificationPreference(animeId, enabled)
    }

    suspend fun removeFromWatchlist(animeId: String) {
        watchlistDao.deleteById(animeId)
    }

    // Notification operations
    suspend fun markNotificationAsRead(id: Long) {
        notificationDao.markAsRead(id)
    }

    suspend fun markAllNotificationsAsRead() {
        notificationDao.markAllAsRead()
    }

    suspend fun clearAllNotifications() {
        notificationDao.clearAll()
    }

    suspend fun insertNotification(notification: EpisodeNotificationEntity): Long {
        return notificationDao.insertNotification(notification)
    }

    suspend fun getSubscribedAnime(): List<WatchlistEntity> {
        return watchlistDao.getAnimeSubscribedForNotifications()
    }

    // Export/Sync functionality for cross-platform / backup
    suspend fun exportToJson(): String {
        val list = watchlistDao.getAnimeSubscribedForNotifications() // or query all
        val array = JSONArray()
        for (item in list) {
            val obj = JSONObject().apply {
                put("animeId", item.animeId)
                put("title", item.title)
                put("posterUrl", item.posterUrl)
                put("totalEpisodes", item.totalEpisodes)
                put("episodesWatched", item.episodesWatched)
                put("userScore", item.userScore)
                put("status", item.status.name)
                put("notifyNewEpisodes", item.notifyNewEpisodes)
                put("notes", item.notes)
                put("favorite", item.favorite)
            }
            array.put(obj)
        }
        return array.toString(2)
    }

    suspend fun importFromJson(jsonString: String): Int {
        var count = 0
        try {
            val array = JSONArray(jsonString)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val statusStr = obj.optString("status", WatchStatus.PLAN_TO_WATCH.name)
                val status = try { WatchStatus.valueOf(statusStr) } catch (e: Exception) { WatchStatus.PLAN_TO_WATCH }

                val entry = WatchlistEntity(
                    animeId = obj.getString("animeId"),
                    title = obj.getString("title"),
                    posterUrl = obj.optString("posterUrl", ""),
                    totalEpisodes = obj.optInt("totalEpisodes", 12),
                    episodesWatched = obj.optInt("episodesWatched", 0),
                    userScore = obj.optInt("userScore", 0),
                    status = status,
                    notifyNewEpisodes = obj.optBoolean("notifyNewEpisodes", true),
                    notes = obj.optString("notes", ""),
                    favorite = obj.optBoolean("favorite", false)
                )
                watchlistDao.insertOrUpdate(entry)
                count++
            }
        } catch (e: Exception) {
            // parse error
        }
        return count
    }
}
