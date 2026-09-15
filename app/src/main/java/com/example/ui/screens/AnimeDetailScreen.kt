package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.WatchStatus
import com.example.data.scraper.CuratedAnimeCatalog
import com.example.ui.MainViewModel
import com.example.ui.Screen
import com.example.ui.components.EpisodeItem
import com.example.ui.components.SourceBadge
import com.example.ui.theme.AnimeAmber
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimePink
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHighlight
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeDetailScreen(
    animeId: String,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val anime = viewModel.getAnime(animeId)
    val watchlist by viewModel.watchlist.collectAsState()
    val watchEntry = watchlist.find { it.animeId == (anime?.id ?: animeId) }

    var showWatchlistDialog by remember { mutableStateOf(false) }
    var expandedSynopsis by remember { mutableStateOf(false) }

    val seasons = remember { CuratedAnimeCatalog.getAttackOnTitanSeasons() }
    var selectedSeasonNumber by remember { mutableIntStateOf(1) }
    val activeSeason = seasons.find { it.seasonNumber == selectedSeasonNumber } ?: seasons.first()

    if (anime == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(DarkBackground),
            contentAlignment = Alignment.Center
        ) {
            Text("Anime not found", color = TextSecondary)
        }
        return
    }

    val episodes = remember(anime, selectedSeasonNumber) {
        viewModel.getEpisodes(anime, selectedSeasonNumber)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Header & Season Poster
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
            ) {
                // Dynamic Season Background Banner
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(activeSeason.bannerUrl.ifEmpty { anime.bannerUrl }.ifEmpty { R.drawable.hero_banner })
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.hero_banner),
                    error = painterResource(R.drawable.hero_banner),
                    contentDescription = activeSeason.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark Gradients
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0x990A0910),
                                    Color(0x330A0910),
                                    Color(0xFF0A0910)
                                )
                            )
                        )
                )

                // Top Back Button
                IconButton(
                    onClick = { viewModel.navigateTo(Screen.Home) },
                    modifier = Modifier
                        .padding(start = 12.dp, top = 28.dp)
                        .background(Color(0x66000000), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                // Poster & Metadata Overlaid at bottom
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Authentic Season Poster Card
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = Modifier
                            .width(105.dp)
                            .aspectRatio(0.72f)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(activeSeason.posterUrl.ifEmpty { anime.posterUrl }.ifEmpty { R.drawable.poster_solo_shadow })
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.poster_solo_shadow),
                            contentDescription = activeSeason.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = anime.title,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${activeSeason.title} • ${activeSeason.subtitle}",
                            color = AnimeCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                        if (anime.japaneseTitle.isNotEmpty()) {
                            Text(
                                text = anime.japaneseTitle,
                                color = TextSecondary,
                                fontSize = 11.sp,
                                maxLines = 1
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Score & Status
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = AnimeAmber,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${anime.score}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "•  ${anime.seasonYear}  •  ${anime.status}",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Source badges
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            anime.availableSources.forEach { src ->
                                SourceBadge(source = src)
                            }
                        }
                    }
                }
            }
        }

        // Action Buttons Row (Play Ep 1, Watchlist, Notification Bell)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Play Episode 1 of the selected season
                Button(
                    onClick = { viewModel.navigateTo(Screen.Player(anime.id, 1, selectedSeasonNumber)) },
                    colors = ButtonDefaults.buttonColors(containerColor = AnimeCyan),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Watch",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Play S${selectedSeasonNumber} EP 1", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                // Watchlist button
                OutlinedButton(
                    onClick = { showWatchlistDialog = true },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (watchEntry != null) DarkSurfaceHighlight else Color.Transparent
                    ),
                    modifier = Modifier.height(44.dp)
                ) {
                    Icon(
                        imageVector = if (watchEntry != null) Icons.Default.Check else Icons.Default.PlaylistAdd,
                        contentDescription = "Watchlist",
                        tint = if (watchEntry != null) AnimeCyan else Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = watchEntry?.status?.displayName ?: "Watchlist",
                        color = if (watchEntry != null) AnimeCyan else Color.White,
                        fontSize = 12.sp
                    )
                }

                // Notification Bell subscription toggle
                IconButton(
                    onClick = {
                        val isSubscribed = watchEntry?.notifyNewEpisodes ?: false
                        if (watchEntry == null) {
                            viewModel.addToWatchlist(anime, WatchStatus.PLAN_TO_WATCH, notify = true)
                        } else {
                            viewModel.toggleNotificationPreference(anime.id, !isSubscribed)
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            if (watchEntry?.notifyNewEpisodes == true) AnimePink.copy(alpha = 0.2f) else DarkSurfaceVariant,
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    Icon(
                        imageVector = if (watchEntry?.notifyNewEpisodes == true) Icons.Default.NotificationsActive else Icons.Default.Notifications,
                        contentDescription = "Toggle Notifications",
                        tint = if (watchEntry?.notifyNewEpisodes == true) AnimePink else TextSecondary
                    )
                }
            }
        }

        // Synopsis
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Text(
                    text = "Synopsis (${activeSeason.title})",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = activeSeason.synopsis,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    maxLines = if (expandedSynopsis) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { expandedSynopsis = !expandedSynopsis }
                )
                Text(
                    text = if (expandedSynopsis) "Show less" else "Read more",
                    color = AnimeCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { expandedSynopsis = !expandedSynopsis }
                        .padding(top = 4.dp)
                )
            }
        }

        // Genres
        item {
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                anime.genres.forEach { genre ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = DarkSurfaceVariant
                    ) {
                        Text(
                            text = genre,
                            color = TextPrimary,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Seasons Switcher (All 4 Attack on Titan Seasons under one show)
        item {
            Spacer(modifier = Modifier.height(14.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Seasons (${seasons.size})",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "All Seasons Included",
                        color = AnimeCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(seasons) { s ->
                        val isSelected = s.seasonNumber == selectedSeasonNumber
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) AnimeCyan else DarkSurfaceHighlight,
                            modifier = Modifier.clickable {
                                selectedSeasonNumber = s.seasonNumber
                            }
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${s.title} (${s.episodeCount} EP)",
                                    color = if (isSelected) Color.Black else Color.White,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = s.year,
                                    color = if (isSelected) Color(0xCC000000) else TextSecondary,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Episode List Header for Selected Season
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${activeSeason.title} Episodes (${episodes.size})",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Fast HD Stream",
                    color = AnimeCyan,
                    fontSize = 11.sp
                )
            }
        }

        // Episode Items for Selected Season
        items(episodes) { ep ->
            EpisodeItem(
                episode = ep,
                isPlaying = false,
                onClick = {
                    viewModel.navigateTo(Screen.Player(anime.id, ep.episodeNumber, selectedSeasonNumber))
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }

    // Add to Watchlist Dialog
    if (showWatchlistDialog) {
        AlertDialog(
            onDismissRequest = { showWatchlistDialog = false },
            containerColor = DarkSurface,
            title = {
                Text("Manage Watchlist", color = Color.White, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    WatchStatus.values().forEach { status ->
                        val isSelected = watchEntry?.status == status
                        Surface(
                            onClick = {
                                viewModel.addToWatchlist(anime, status = status)
                                showWatchlistDialog = false
                            },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) AnimeCyan.copy(alpha = 0.2f) else DarkSurfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = status.displayName,
                                    color = if (isSelected) AnimeCyan else Color.White,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                                if (isSelected) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = AnimeCyan)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showWatchlistDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = AnimeCyan)
                ) {
                    Text("Close", color = Color.Black)
                }
            }
        )
    }
}
