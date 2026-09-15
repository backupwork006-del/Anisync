package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WatchStatus
import com.example.data.scraper.CuratedAnimeCatalog
import com.example.ui.MainViewModel
import com.example.ui.Screen
import com.example.ui.components.EpisodeItem
import com.example.ui.components.PlayerViewMode
import com.example.ui.components.VideoPlayerView
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimePink
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHighlight
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun PlayerScreen(
    animeId: String,
    initialEpisodeNumber: Int,
    initialSeasonNumber: Int = 1,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val anime = viewModel.getAnime(animeId)
    val seasons = remember { CuratedAnimeCatalog.getAttackOnTitanSeasons() }
    var currentSeasonNumber by remember(initialSeasonNumber) { mutableIntStateOf(initialSeasonNumber) }
    var currentEpNum by remember(initialEpisodeNumber, currentSeasonNumber) { mutableIntStateOf(initialEpisodeNumber) }
    var activeSourceIndex by remember { mutableIntStateOf(0) }
    var playerMode by remember { mutableStateOf(PlayerViewMode.STREAM_PLAYER) }

    if (anime == null) {
        Box(modifier = modifier.fillMaxSize().background(DarkBackground), contentAlignment = Alignment.Center) {
            Text("Episode not found", color = TextSecondary)
        }
        return
    }

    val episodes = remember(anime, currentSeasonNumber) {
        viewModel.getEpisodes(anime, currentSeasonNumber)
    }
    val currentEpisode = episodes.find { it.episodeNumber == currentEpNum } ?: episodes.first()
    val activeSeason = seasons.find { it.seasonNumber == currentSeasonNumber } ?: seasons.first()

    // Automatically update watchlist watched progress when watching
    LaunchedEffect(currentEpNum, currentSeasonNumber) {
        viewModel.addToWatchlist(
            anime = anime,
            status = WatchStatus.WATCHING,
            episodesWatched = currentEpNum
        )
    }

    var isFullscreen by remember { mutableStateOf(false) }

    if (isFullscreen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            androidx.compose.runtime.key(currentEpisode.episodeNumber, currentSeasonNumber) {
                VideoPlayerView(
                    episode = currentEpisode,
                    animeTitle = "${anime.title} (${activeSeason.title})",
                    animeId = anime.id,
                    backdropUrl = currentEpisode.thumbnail.ifEmpty { activeSeason.bannerUrl.ifEmpty { anime.bannerUrl } },
                    playerViewMode = playerMode,
                    selectedSourceIndex = activeSourceIndex,
                    isFullscreen = true,
                    onPlayerViewModeChange = { playerMode = it },
                    onSourceIndexChange = { activeSourceIndex = it },
                    onFullscreenToggle = { isFullscreen = it },
                    onBack = { isFullscreen = false },
                    onNextEpisode = {
                        val nextEp = episodes.find { it.episodeNumber == currentEpNum + 1 }
                        if (nextEp != null) {
                            currentEpNum = nextEp.episodeNumber
                        }
                    }
                )
            }
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Embedded Player
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        ) {
            androidx.compose.runtime.key(currentEpisode.episodeNumber, currentSeasonNumber) {
                VideoPlayerView(
                    episode = currentEpisode,
                    animeTitle = "${anime.title} (${activeSeason.title})",
                    animeId = anime.id,
                    backdropUrl = currentEpisode.thumbnail.ifEmpty { activeSeason.bannerUrl.ifEmpty { anime.bannerUrl } },
                    playerViewMode = playerMode,
                    selectedSourceIndex = activeSourceIndex,
                    isFullscreen = false,
                    onPlayerViewModeChange = { playerMode = it },
                    onSourceIndexChange = { activeSourceIndex = it },
                    onFullscreenToggle = { isFullscreen = it },
                    onBack = { viewModel.navigateTo(Screen.AnimeDetail(anime.id)) },
                    onNextEpisode = {
                        val nextEp = episodes.find { it.episodeNumber == currentEpNum + 1 }
                        if (nextEp != null) {
                            currentEpNum = nextEp.episodeNumber
                        }
                    }
                )
            }
        }

        // Scrollable Episode & Info Section
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Title & Episode Info
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = anime.title,
                            color = TextSecondary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${activeSeason.title} • Ep $currentEpNum: ${currentEpisode.title.substringAfter("Episode $currentEpNum: ").ifEmpty { currentEpisode.title }}",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Action buttons
                    Row {
                        val watchlist by viewModel.watchlist.collectAsState()
                        val isBookmarked = watchlist.any { it.animeId == anime.id }

                        IconButton(
                            onClick = {
                                if (isBookmarked) {
                                    viewModel.removeFromWatchlist(anime.id)
                                } else {
                                    viewModel.addToWatchlist(anime, WatchStatus.WATCHING)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) AnimeCyan else TextSecondary
                            )
                        }

                        IconButton(onClick = { /* Share episode */ }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = TextSecondary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Server Selection Card (Pahe, Gogo, HiAnime)
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(AnimeCyan, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Streaming Sources",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Mode Chip Toggle
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (playerMode == PlayerViewMode.WEB_MIRROR) AnimePink.copy(alpha = 0.2f) else AnimeCyan.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, if (playerMode == PlayerViewMode.WEB_MIRROR) AnimePink else AnimeCyan),
                                modifier = Modifier.clickable {
                                    playerMode = if (playerMode == PlayerViewMode.STREAM_PLAYER) {
                                        PlayerViewMode.WEB_MIRROR
                                    } else {
                                        PlayerViewMode.STREAM_PLAYER
                                    }
                                }
                            ) {
                                Text(
                                    text = if (playerMode == PlayerViewMode.WEB_MIRROR) "Mirror Active" else "Direct Player",
                                    color = if (playerMode == PlayerViewMode.WEB_MIRROR) AnimePink else AnimeCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            currentEpisode.sources.forEachIndexed { idx, src ->
                                val isSelected = activeSourceIndex == idx && playerMode == PlayerViewMode.WEB_MIRROR
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) Color(src.provider.badgeColorHex).copy(alpha = 0.25f) else DarkSurfaceHighlight,
                                    border = if (isSelected) BorderStroke(1.5.dp, Color(src.provider.badgeColorHex)) else null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            activeSourceIndex = idx
                                            playerMode = PlayerViewMode.WEB_MIRROR
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(6.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(6.dp)
                                                    .background(Color(src.provider.badgeColorHex), CircleShape)
                                            )
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text(
                                                text = src.serverName.split(" ").first(),
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 1
                                            )
                                        }
                                        Text(
                                            text = src.provider.name,
                                            color = TextSecondary,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Season Selector (All 4 Seasons grouped under Attack on Titan)
            item {
                Text(
                    text = "Seasons",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(seasons) { s ->
                        val isSelected = s.seasonNumber == currentSeasonNumber
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) AnimeCyan else DarkSurface,
                            modifier = Modifier.clickable {
                                if (!isSelected) {
                                    currentSeasonNumber = s.seasonNumber
                                    currentEpNum = 1
                                }
                            }
                        ) {
                            Text(
                                text = "${s.title} (${s.episodeCount} EP)",
                                color = if (isSelected) Color.Black else Color.White,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Quick Jump Episode Row (Pills 1..N)
            item {
                Text(
                    text = "Quick Episode Jump",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(episodes) { ep ->
                        val isCurrent = ep.episodeNumber == currentEpNum
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isCurrent) AnimeCyan else DarkSurface)
                                .clickable { currentEpNum = ep.episodeNumber },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${ep.episodeNumber}",
                                color = if (isCurrent) Color.Black else TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Episode List Header for Selected Season
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${activeSeason.title} Episodes (${episodes.size})",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Auto-Next: ON",
                        color = AnimeCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Episode Cards
            items(episodes) { ep ->
                val isPlaying = ep.episodeNumber == currentEpNum
                EpisodeItem(
                    episode = ep,
                    isPlaying = isPlaying,
                    onClick = { currentEpNum = ep.episodeNumber },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
