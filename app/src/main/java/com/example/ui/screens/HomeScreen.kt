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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.example.data.model.Anime
import com.example.data.model.AnimeSource
import com.example.data.model.WatchStatus
import com.example.ui.MainViewModel
import com.example.ui.Screen
import com.example.ui.components.AnimeCard
import com.example.ui.components.SourceBadge
import com.example.ui.theme.AnimeAmber
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimePink
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHighlight
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GogoBadgeColor
import com.example.ui.theme.HiAnimeBadgeColor
import com.example.ui.theme.PaheBadgeColor
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val trending by viewModel.trendingAnime.collectAsState()
    val featured by viewModel.featuredAnime.collectAsState()
    val watchlist by viewModel.watchlist.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val continueWatching = watchlist.filter { it.status == WatchStatus.WATCHING }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // App Top Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "ANIME",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "STREAM",
                            color = AnimeCyan,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "Aggregated from Pahe, Gogo & HiAnime",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                // Sources status pill & Sync button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        onClick = { viewModel.refreshHome() },
                        shape = RoundedCornerShape(20.dp),
                        color = DarkSurfaceVariant
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            if (isRefreshing) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(10.dp),
                                    strokeWidth = 1.5.dp,
                                    color = AnimeCyan
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Syncing...",
                                    color = AnimeCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            } else {
                                Box(modifier = Modifier.size(6.dp).background(SuccessGreen, CircleShape))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Scrapers Live",
                                    color = SuccessGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Scrapers Banner
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScraperChip(name = "AnimePahe", domain = "animepahe.pw", color = PaheBadgeColor, modifier = Modifier.weight(1f))
                ScraperChip(name = "Gogoanime", domain = "anitaku.to", color = GogoBadgeColor, modifier = Modifier.weight(1f))
                ScraperChip(name = "HiAnime", domain = "hianime.to", color = HiAnimeBadgeColor, modifier = Modifier.weight(1f))
            }
        }

        // Featured Hero Anime Banner
        if (featured != null) {
            item {
                FeaturedHeroBanner(
                    anime = featured!!,
                    onWatchClick = {
                        viewModel.navigateTo(Screen.Player(featured!!.id, 1))
                    },
                    onDetailClick = {
                        viewModel.navigateTo(Screen.AnimeDetail(featured!!.id))
                    },
                    onAddWatchlist = {
                        viewModel.addToWatchlist(featured!!, WatchStatus.PLAN_TO_WATCH)
                    }
                )
            }
        }

        // Continue Watching Section (if user has watching items)
        if (continueWatching.isNotEmpty()) {
            item {
                SectionHeader(title = "Continue Watching", subtitle = "Resume where you left off")
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(continueWatching) { item ->
                        ContinueWatchingCard(
                            item = item,
                            onPlay = {
                                viewModel.navigateTo(Screen.Player(item.animeId, (item.episodesWatched + 1).coerceAtMost(item.totalEpisodes)))
                            },
                            onIncrement = {
                                viewModel.updateWatchlistProgress(item.animeId, (item.episodesWatched + 1).coerceAtMost(item.totalEpisodes))
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Trending Anime Row
        item {
            SectionHeader(
                title = "Trending Across Sources",
                subtitle = "Top aggregated streams right now",
                onSeeAll = { viewModel.navigateTo(Screen.Search) }
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trending) { anime ->
                    AnimeCard(
                        anime = anime,
                        onClick = { viewModel.navigateTo(Screen.AnimeDetail(anime.id)) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Top Airing & New Episodes
        item {
            SectionHeader(
                title = "New Episode Drops",
                subtitle = "Fresh episodes available on servers",
                onSeeAll = { viewModel.navigateTo(Screen.Notifications) }
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trending.reversed()) { anime ->
                    AnimeCard(
                        anime = anime,
                        onClick = { viewModel.navigateTo(Screen.AnimeDetail(anime.id)) }
                    )
                }
            }
        }
    }
}

@Composable
fun ScraperChip(
    name: String,
    domain: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(DarkSurfaceVariant)
            .padding(vertical = 6.dp, horizontal = 8.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(5.dp).background(color, CircleShape))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = name,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = domain,
                color = TextSecondary,
                fontSize = 9.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
fun FeaturedHeroBanner(
    anime: Anime,
    onWatchClick: () -> Unit,
    onDetailClick: () -> Unit,
    onAddWatchlist: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .aspectRatio(1.6f)
            .background(DarkSurface)
            .clickable(onClick = onDetailClick)
    ) {
        // Hero Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(anime.bannerUrl.ifEmpty { anime.posterUrl }.ifEmpty { R.drawable.hero_banner })
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.hero_banner),
            error = painterResource(R.drawable.hero_banner),
            contentDescription = anime.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0x99000000), Color(0xF20A0910)),
                        startY = 80f
                    )
                )
        )

        // Banner Content
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(14.dp)
        ) {
            // Badges
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(AnimeCyan, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "FEATURED",
                        color = Color.Black,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                SourceBadge(source = AnimeSource.ANIMEPAHE)
                SourceBadge(source = AnimeSource.GOGOANIME)
                SourceBadge(source = AnimeSource.HIANIME)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = anime.title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = anime.genres.joinToString(" • "),
                color = TextSecondary,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onWatchClick,
                    colors = ButtonDefaults.buttonColors(containerColor = AnimeCyan),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Watch",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Watch EP 1", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onAddWatchlist,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Watchlist",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Watchlist", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun ContinueWatchingCard(
    item: com.example.data.local.WatchlistEntity,
    onPlay: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(220.dp)
            .clickable(onClick = onPlay),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
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

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    color = TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Watched ${item.episodesWatched}/${item.totalEpisodes}",
                    color = AnimeCyan,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Mini Progress Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(2.dp))
                ) {
                    val frac = if (item.totalEpisodes > 0) item.episodesWatched.toFloat() / item.totalEpisodes.toFloat() else 0f
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(frac.coerceIn(0f, 1f))
                            .height(3.dp)
                            .background(AnimeCyan, RoundedCornerShape(2.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Quick +1 Episode Increment
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceHighlight)
                    .clickable(onClick = onIncrement),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "+1", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    onSeeAll: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 11.sp
            )
        }
        if (onSeeAll != null) {
            Text(
                text = "Explore >",
                color = AnimeCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable(onClick = onSeeAll)
            )
        }
    }
}
