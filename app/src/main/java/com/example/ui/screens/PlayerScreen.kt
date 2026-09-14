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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WatchStatus
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
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val anime = viewModel.getAnime(animeId)
    var currentEpNum by remember(initialEpisodeNumber) { mutableIntStateOf(initialEpisodeNumber) }
    var activeSourceIndex by remember { mutableIntStateOf(0) }
    var playerMode by remember { mutableStateOf(PlayerViewMode.STREAM_PLAYER) }

    if (anime == null) {
        Box(modifier = modifier.fillMaxSize().background(DarkBackground), contentAlignment = Alignment.Center) {
            Text("Episode not found", color = TextSecondary)
        }
        return
    }

    val episodes = remember(anime) { viewModel.getEpisodes(anime) }
    val currentEpisode = episodes.find { it.episodeNumber == currentEpNum } ?: episodes.first()

    // Automatically update watchlist watched progress when watching
    LaunchedEffect(currentEpNum) {
        viewModel.addToWatchlist(
            anime = anime,
            status = WatchStatus.WATCHING,
            episodesWatched = currentEpNum
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Video Player HUD
        androidx.compose.runtime.key(currentEpisode.episodeNumber) {
            VideoPlayerView(
                episode = currentEpisode,
                animeTitle = anime.title,
                animeId = anime.id,
                backdropUrl = currentEpisode.thumbnail.ifEmpty { anime.bannerUrl.ifEmpty { anime.posterUrl } },
                playerViewMode = playerMode,
                selectedSourceIndex = activeSourceIndex,
                onPlayerViewModeChange = { playerMode = it },
                onSourceIndexChange = { activeSourceIndex = it },
                onBack = { viewModel.navigateTo(Screen.AnimeDetail(anime.id)) },
                onNextEpisode = {
                    val next = currentEpNum + 1
                    if (next <= anime.currentEpisodes) {
                        currentEpNum = next
                    }
                },
                onProgressUpdate = { frac ->
                    if (frac > 0.85f) {
                        // Mark watched
                        viewModel.updateWatchlistProgress(anime.id, currentEpNum)
                    }
                }
            )
        }

        // Episode Information & Quick Actions
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = anime.title,
                            color = TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = currentEpisode.title,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Next Episode Action
                    if (currentEpNum < anime.currentEpisodes) {
                        Button(
                            onClick = { currentEpNum += 1 },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceHighlight),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("Next EP", color = AnimeCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.SkipNext,
                                contentDescription = "Next Episode",
                                tint = AnimeCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Scraped Servers Available Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurface)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Tv,
                                    contentDescription = null,
                                    tint = AnimeCyan,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Available Scraped Streaming Mirrors",
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
                                                color = Color(src.provider.badgeColorHex),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = if (isSelected) "Streaming" else src.quality,
                                            color = if (isSelected) Color.White else TextSecondary,
                                            fontSize = 9.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    }
                                }
                            }
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

            // All Episodes in Playlist
            item {
                Text(
                    text = "All Episodes (${episodes.size})",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(episodes) { ep ->
                EpisodeItem(
                    episode = ep,
                    isPlaying = ep.episodeNumber == currentEpNum,
                    onClick = { currentEpNum = ep.episodeNumber },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
