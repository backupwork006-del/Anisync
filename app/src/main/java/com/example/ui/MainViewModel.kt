package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.EpisodeNotificationEntity
import com.example.data.local.WatchlistEntity
import com.example.data.model.Anime
import com.example.data.model.AnimeSource
import com.example.data.model.Episode
import com.example.data.model.WatchStatus
import com.example.data.repository.AnimeRepository
import com.example.data.repository.WatchlistRepository
import com.example.data.scraper.AnimeScraperService
import com.example.data.scraper.CuratedAnimeCatalog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    object Home : Screen()
    object Search : Screen()
    object Watchlist : Screen()
    object Notifications : Screen()
    object Settings : Screen()
    data class AnimeDetail(val animeId: String) : Screen()
    data class Player(val animeId: String, val episodeNumber: Int) : Screen()
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val scraperService = AnimeScraperService()
    val watchlistRepository = WatchlistRepository(database.watchlistDao(), database.notificationDao())
    val animeRepository = AnimeRepository(scraperService, watchlistRepository)

    // Navigation State
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Home Content State
    private val _trendingAnime = MutableStateFlow<List<Anime>>(emptyList())
    val trendingAnime: StateFlow<List<Anime>> = _trendingAnime.asStateFlow()

    private val _featuredAnime = MutableStateFlow<Anime?>(null)
    val featuredAnime: StateFlow<Anime?> = _featuredAnime.asStateFlow()

    // Search & Scraper State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedSourceFilter = MutableStateFlow(AnimeSource.ALL)
    val selectedSourceFilter: StateFlow<AnimeSource> = _selectedSourceFilter.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Anime>>(emptyList())
    val searchResults: StateFlow<List<Anime>> = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    // Watchlist State from Room DB
    val watchlist: StateFlow<List<WatchlistEntity>> = watchlistRepository.allWatchlist
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Notifications State
    val notifications: StateFlow<List<EpisodeNotificationEntity>> = watchlistRepository.allNotifications
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val unreadCount: StateFlow<Int> = watchlistRepository.unreadNotificationsCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    private val _isCheckingEpisodes = MutableStateFlow(false)
    val isCheckingEpisodes: StateFlow<Boolean> = _isCheckingEpisodes.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    init {
        loadHomeData()
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun refreshHome() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val trending = animeRepository.getTrendingAnime()
                _trendingAnime.value = trending
                _featuredAnime.value = animeRepository.getFeaturedAnime()
                if (_searchQuery.value.isBlank()) {
                    _searchResults.value = trending
                }
                _statusMessage.value = "Aggregated latest anime feeds"
            } catch (e: Exception) {
                _statusMessage.value = "Scraper sync failed: ${e.message}"
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _trendingAnime.value = animeRepository.getTrendingAnime()
            _featuredAnime.value = animeRepository.getFeaturedAnime()
            _searchResults.value = animeRepository.getTrendingAnime()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        performSearch(query, _selectedSourceFilter.value)
    }

    fun onSourceFilterChanged(source: AnimeSource) {
        _selectedSourceFilter.value = source
        performSearch(_searchQuery.value, source)
    }

    private fun performSearch(query: String, source: AnimeSource) {
        viewModelScope.launch {
            _isSearching.value = true
            try {
                val results = if (query.isBlank()) {
                    val all = animeRepository.getTrendingAnime()
                    if (source == AnimeSource.ALL) all
                    else all.filter { it.availableSources.contains(source) }
                } else {
                    animeRepository.search(query, source)
                }
                _searchResults.value = results
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            } finally {
                _isSearching.value = false
            }
        }
    }

    fun getAnime(id: String): Anime? {
        return animeRepository.getAnimeById(id)
    }

    fun getEpisodes(anime: Anime): List<Episode> {
        return animeRepository.getEpisodes(anime)
    }

    // Watchlist Operations
    fun addToWatchlist(
        anime: Anime,
        status: WatchStatus = WatchStatus.WATCHING,
        episodesWatched: Int = 0,
        notify: Boolean = true
    ) {
        viewModelScope.launch {
            watchlistRepository.addToWatchlist(
                animeId = anime.id,
                title = anime.title,
                posterUrl = anime.posterUrl,
                bannerUrl = anime.bannerUrl,
                totalEpisodes = anime.totalEpisodes,
                status = status,
                episodesWatched = episodesWatched,
                notify = notify
            )
            _statusMessage.value = "Added to ${status.displayName}"
        }
    }

    fun updateWatchlistProgress(animeId: String, episodesWatched: Int) {
        viewModelScope.launch {
            watchlistRepository.updateProgress(animeId, episodesWatched)
        }
    }

    fun updateWatchlistStatus(animeId: String, status: WatchStatus) {
        viewModelScope.launch {
            val existing = watchlistRepository.getWatchlistEntryDirect(animeId)
            if (existing != null) {
                watchlistRepository.updateEntry(existing.copy(status = status))
            }
        }
    }

    fun toggleNotificationPreference(animeId: String, enabled: Boolean) {
        viewModelScope.launch {
            watchlistRepository.toggleNotification(animeId, enabled)
            _statusMessage.value = if (enabled) "Notifications enabled for this anime" else "Notifications muted"
        }
    }

    fun removeFromWatchlist(animeId: String) {
        viewModelScope.launch {
            watchlistRepository.removeFromWatchlist(animeId)
            _statusMessage.value = "Removed from Watchlist"
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    // Episode Notifications Check
    fun checkForNewEpisodes() {
        viewModelScope.launch {
            _isCheckingEpisodes.value = true
            try {
                val newEpisodes = animeRepository.checkForNewEpisodes(getApplication())
                _statusMessage.value = if (newEpisodes.isNotEmpty()) {
                    "Found ${newEpisodes.size} new episode release(s)!"
                } else {
                    "All your watchlists are up to date."
                }
            } catch (e: Exception) {
                _statusMessage.value = "Could not check for episodes: ${e.message}"
            } finally {
                _isCheckingEpisodes.value = false
            }
        }
    }

    fun markNotificationRead(id: Long) {
        viewModelScope.launch {
            watchlistRepository.markNotificationAsRead(id)
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
            watchlistRepository.markAllNotificationsAsRead()
        }
    }

    fun clearAllNotifications() {
        viewModelScope.launch {
            watchlistRepository.clearAllNotifications()
        }
    }

    // Sync / Backup
    fun exportWatchlist(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val json = watchlistRepository.exportToJson()
            onResult(json)
        }
    }

    fun importWatchlist(json: String, onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val count = watchlistRepository.importFromJson(json)
            _statusMessage.value = "Imported $count titles into Watchlist"
            onResult(count)
        }
    }
}
