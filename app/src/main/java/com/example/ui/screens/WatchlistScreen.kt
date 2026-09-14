package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.local.WatchlistEntity
import com.example.data.model.WatchStatus
import com.example.ui.MainViewModel
import com.example.ui.Screen
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimePink
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHighlight
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun WatchlistScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val watchlist by viewModel.watchlist.collectAsState()
    var selectedCategory by remember { mutableStateOf<WatchStatus?>(null) }

    val filteredList = if (selectedCategory == null) {
        watchlist
    } else {
        watchlist.filter { it.status == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(top = 8.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "My Watchlist",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${watchlist.size} series saved locally & synced",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }

            // Quick Add button
            IconButton(
                onClick = { viewModel.navigateTo(Screen.Search) },
                modifier = Modifier
                    .background(DarkSurfaceVariant, CircleShape)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Find anime",
                    tint = AnimeCyan,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Category Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            item {
                val isAll = selectedCategory == null
                Surface(
                    onClick = { selectedCategory = null },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isAll) AnimeCyan else DarkSurfaceVariant,
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "All (${watchlist.size})",
                        color = if (isAll) Color.Black else TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            items(WatchStatus.values()) { status ->
                val count = watchlist.count { it.status == status }
                val isSelected = selectedCategory == status
                Surface(
                    onClick = { selectedCategory = status },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) AnimeCyan else DarkSurfaceVariant,
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "${status.displayName} ($count)",
                        color = if (isSelected) Color.Black else TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // List Content
        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No anime in ${selectedCategory?.displayName ?: "Watchlist"}",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Add anime from Search or Explore to track progress and get episode notifications.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = { viewModel.navigateTo(Screen.Search) }) {
                        Text("Browse Trending Anime", color = AnimeCyan, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredList, key = { it.animeId }) { item ->
                    WatchlistItemCard(
                        item = item,
                        onPlay = {
                            viewModel.navigateTo(
                                Screen.Player(
                                    item.animeId,
                                    (item.episodesWatched + 1).coerceAtMost(item.totalEpisodes)
                                )
                            )
                        },
                        onIncrement = {
                            viewModel.updateWatchlistProgress(
                                item.animeId,
                                (item.episodesWatched + 1).coerceAtMost(item.totalEpisodes)
                            )
                        },
                        onToggleNotification = {
                            viewModel.toggleNotificationPreference(item.animeId, !item.notifyNewEpisodes)
                        },
                        onStatusChange = { newStatus ->
                            viewModel.updateWatchlistStatus(item.animeId, newStatus)
                        },
                        onDelete = {
                            viewModel.removeFromWatchlist(item.animeId)
                        },
                        onClick = {
                            viewModel.navigateTo(Screen.AnimeDetail(item.animeId))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WatchlistItemCard(
    item: WatchlistEntity,
    onPlay: () -> Unit,
    onIncrement: () -> Unit,
    onToggleNotification: () -> Unit,
    onStatusChange: (WatchStatus) -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("watchlist_item_${item.animeId}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .aspectRatio(0.72f)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.posterUrl.ifEmpty { R.drawable.poster_solo_shadow })
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.poster_solo_shadow),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.status.displayName,
                        color = AnimeCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "•  EP ${item.episodesWatched}/${item.totalEpisodes}",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Progress Indicator
                val frac = if (item.totalEpisodes > 0) item.episodesWatched.toFloat() / item.totalEpisodes.toFloat() else 0f
                LinearProgressIndicator(
                    progress = { frac.coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = AnimeCyan,
                    trackColor = DarkSurfaceHighlight
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Quick Actions: Play & +1 & More
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Quick +1 button
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(DarkSurfaceHighlight)
                        .clickable(onClick = onIncrement),
                    contentAlignment = Alignment.Center
                ) {
                    Text("+1", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Notification bell icon
                IconButton(onClick = onToggleNotification, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = if (item.notifyNewEpisodes) Icons.Default.NotificationsActive else Icons.Default.Notifications,
                        contentDescription = "Toggle Notifications",
                        tint = if (item.notifyNewEpisodes) AnimePink else TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // More Menu
                Box {
                    IconButton(onClick = { showMenu = true }, modifier = Modifier.size(30.dp)) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Options",
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(DarkSurfaceVariant)
                    ) {
                        WatchStatus.values().forEach { st ->
                            DropdownMenuItem(
                                text = { Text("Move to ${st.displayName}", color = TextPrimary, fontSize = 12.sp) },
                                onClick = {
                                    onStatusChange(st)
                                    showMenu = false
                                }
                            )
                        }
                        DropdownMenuItem(
                            text = { Text("Remove", color = Color(0xFFFF5252), fontSize = 12.sp) },
                            onClick = {
                                onDelete()
                                showMenu = false
                            }
                        )
                    }
                }
            }
        }
    }
}
