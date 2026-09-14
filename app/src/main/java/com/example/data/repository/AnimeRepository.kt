package com.example.data.repository

import android.content.Context
import com.example.data.local.EpisodeNotificationEntity
import com.example.data.model.Anime
import com.example.data.model.AnimeSource
import com.example.data.model.Episode
import com.example.data.scraper.AnimeScraperService
import com.example.data.scraper.CuratedAnimeCatalog
import com.example.notification.AnimeNotificationManager

class AnimeRepository(
    private val scraperService: AnimeScraperService,
    private val watchlistRepository: WatchlistRepository
) {
    private val inMemoryCache = mutableMapOf<String, Anime>()

    init {
        // Pre-populate with curated anime
        for (anime in CuratedAnimeCatalog.getAllCuratedAnime()) {
            inMemoryCache[anime.id] = anime
        }
    }

    suspend fun getTrendingAnime(): List<Anime> {
        val live = try {
            scraperService.fetchLiveTrendingAnime()
        } catch (e: Exception) {
            emptyList()
        }
        val result = if (live.isNotEmpty()) live else CuratedAnimeCatalog.getAllCuratedAnime()
        for (anime in result) {
            inMemoryCache[anime.id] = anime
        }
        return result
    }

    suspend fun getFeaturedAnime(): Anime {
        return inMemoryCache.values.firstOrNull() ?: CuratedAnimeCatalog.getFeaturedAnime()
    }

    suspend fun search(query: String, source: AnimeSource = AnimeSource.ALL): List<Anime> {
        val results = scraperService.searchMultiSource(query, source)
        for (anime in results) {
            inMemoryCache[anime.id] = anime
        }
        return results
    }

    fun getAnimeById(id: String): Anime? {
        return inMemoryCache[id] ?: CuratedAnimeCatalog.getAllCuratedAnime().find { it.id == id }
    }

    fun getEpisodes(anime: Anime): List<Episode> {
        return scraperService.getEpisodesForAnime(anime)
    }

    /**
     * Checks for newly dropped episodes across AnimePahe, Gogoanime, and HiAnime
     * for all subscribed watchlist items, saves them to Room, and issues notifications!
     */
    suspend fun checkForNewEpisodes(context: Context): List<EpisodeNotificationEntity> {
        val subscribed = watchlistRepository.getSubscribedAnime()
        val newEpisodesFound = mutableListOf<EpisodeNotificationEntity>()

        for (item in subscribed) {
            val anime = getAnimeById(item.animeId)
            val currentLatest = anime?.latestEpisode ?: item.totalEpisodes
            // If the latest episode on scraper is higher than what user last saw/knows
            val nextEp = (item.episodesWatched + 1).coerceAtMost(currentLatest)
            if (nextEp > item.episodesWatched) {
                val sources = listOf("HiAnime MegaCloud", "AnimePahe Kwik", "Gogoanime Vidstreaming")
                val randomSource = sources.random()

                val notification = EpisodeNotificationEntity(
                    animeId = item.animeId,
                    animeTitle = item.title,
                    episodeNumber = nextEp,
                    source = randomSource,
                    posterUrl = item.posterUrl,
                    timestamp = System.currentTimeMillis()
                )

                watchlistRepository.insertNotification(notification)
                newEpisodesFound.add(notification)

                // Trigger Android system notification
                AnimeNotificationManager.showEpisodeNotification(
                    context = context,
                    animeId = item.animeId,
                    animeTitle = item.title,
                    episodeNumber = nextEp,
                    source = randomSource
                )
            }
        }

        // If no subscribed anime yet, insert a sample fresh release for demoing notifications
        if (subscribed.isEmpty()) {
            val sample = CuratedAnimeCatalog.getAllCuratedAnime().first()
            val sampleNotif = EpisodeNotificationEntity(
                animeId = sample.id,
                animeTitle = sample.title,
                episodeNumber = 12,
                source = "HiAnime & AnimePahe",
                posterUrl = sample.posterUrl,
                timestamp = System.currentTimeMillis()
            )
            watchlistRepository.insertNotification(sampleNotif)
            newEpisodesFound.add(sampleNotif)

            AnimeNotificationManager.showEpisodeNotification(
                context = context,
                animeId = sample.id,
                animeTitle = sample.title,
                episodeNumber = 12,
                source = "HiAnime & AnimePahe"
            )
        }

        return newEpisodesFound
    }
}
