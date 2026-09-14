package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.notification.AnimeNotificationManager
import com.example.ui.MainViewModel
import com.example.ui.Screen
import com.example.ui.screens.AnimeDetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.PlayerScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.WatchlistScreen
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimePink
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AnimeNotificationManager.createNotificationChannel(this)

        // Handle direct navigation from system notification intent
        val targetAnimeId = intent.getStringExtra("EXTRA_ANIME_ID")
        val targetEpisodeNum = intent.getIntExtra("EXTRA_EPISODE_NUM", 0)
        if (!targetAnimeId.isNullOrEmpty() && targetEpisodeNum > 0) {
            viewModel.navigateTo(Screen.Player(targetAnimeId, targetEpisodeNum))
        }

        setContent {
            MyApplicationTheme {
                AnimeStreamApp(viewModel = viewModel)
            }
        }
    }
}

enum class NavTab(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val targetScreen: Screen
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home, Screen.Home),
    SEARCH("Explore", Icons.Filled.Search, Icons.Outlined.Search, Screen.Search),
    WATCHLIST("Watchlist", Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder, Screen.Watchlist),
    NOTIFICATIONS("Alerts", Icons.Filled.Notifications, Icons.Outlined.Notifications, Screen.Notifications),
    SETTINGS("Settings", Icons.Filled.Settings, Icons.Outlined.Settings, Screen.Settings)
}

@Composable
fun AnimeStreamApp(viewModel: MainViewModel) {
    val context = LocalContext.current
    val currentScreen by viewModel.currentScreen.collectAsState()
    val unreadCount by viewModel.unreadCount.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Request notification permissions for Android 13+
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ -> }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    LaunchedEffect(statusMessage) {
        statusMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearStatusMessage()
        }
    }

    val isPlayerScreen = currentScreen is Screen.Player

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (!isPlayerScreen) {
                NavigationBar(
                    containerColor = DarkSurface,
                    contentColor = Color.White,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("bottom_nav_bar")
                ) {
                    NavTab.values().forEach { tab ->
                        val isSelected = when (tab) {
                            NavTab.HOME -> currentScreen is Screen.Home
                            NavTab.SEARCH -> currentScreen is Screen.Search
                            NavTab.WATCHLIST -> currentScreen is Screen.Watchlist
                            NavTab.NOTIFICATIONS -> currentScreen is Screen.Notifications
                            NavTab.SETTINGS -> currentScreen is Screen.Settings
                        }

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(tab.targetScreen) },
                            icon = {
                                if (tab == NavTab.NOTIFICATIONS && unreadCount > 0) {
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                containerColor = AnimePink,
                                                contentColor = Color.White
                                            ) {
                                                Text("$unreadCount", fontSize = 10.sp)
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                            contentDescription = tab.label
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.label
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Black,
                                unselectedIconColor = TextSecondary,
                                selectedTextColor = AnimeCyan,
                                unselectedTextColor = TextSecondary,
                                indicatorColor = AnimeCyan
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                },
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    is Screen.Home -> HomeScreen(viewModel = viewModel)
                    is Screen.Search -> SearchScreen(viewModel = viewModel)
                    is Screen.Watchlist -> WatchlistScreen(viewModel = viewModel)
                    is Screen.Notifications -> NotificationsScreen(viewModel = viewModel)
                    is Screen.Settings -> SettingsScreen(viewModel = viewModel)
                    is Screen.AnimeDetail -> AnimeDetailScreen(animeId = screen.animeId, viewModel = viewModel)
                    is Screen.Player -> PlayerScreen(
                        animeId = screen.animeId,
                        initialEpisodeNumber = screen.episodeNumber,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
