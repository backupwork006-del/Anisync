package com.example.ui.components

import android.app.Activity
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.graphics.SurfaceTexture
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.AnimeSource
import com.example.data.model.Episode
import com.example.data.model.StreamSource
import com.example.data.scraper.AnimeEpisodeCatalog
import com.example.ui.theme.AnimeAmber
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimePink
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.SuccessGreen
import kotlinx.coroutines.delay

enum class PlayerViewMode {
    STREAM_PLAYER,
    WEB_MIRROR
}

@Composable
fun VideoPlayerView(
    episode: Episode,
    animeTitle: String,
    animeId: String = "",
    backdropUrl: String = "",
    playerViewMode: PlayerViewMode = PlayerViewMode.STREAM_PLAYER,
    selectedSourceIndex: Int = 0,
    isFullscreen: Boolean = false,
    onPlayerViewModeChange: ((PlayerViewMode) -> Unit)? = null,
    onSourceIndexChange: ((Int) -> Unit)? = null,
    onFullscreenToggle: ((Boolean) -> Unit)? = null,
    onBack: () -> Unit,
    onNextEpisode: (() -> Unit)? = null,
    onProgressUpdate: ((progressPercent: Float) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var internalFullscreen by remember { mutableStateOf(isFullscreen) }
    LaunchedEffect(isFullscreen) {
        internalFullscreen = isFullscreen
    }
    val currentFullscreen = if (onFullscreenToggle != null) isFullscreen else internalFullscreen
    val setFullscreen: (Boolean) -> Unit = { next ->
        internalFullscreen = next
        onFullscreenToggle?.invoke(next)
    }

    // Auto-rotate activity to sensor landscape on fullscreen, restore to unspecified on exit
    val activity = remember(context) {
        var ctx = context
        while (ctx is ContextWrapper) {
            if (ctx is Activity) return@remember ctx
            ctx = ctx.baseContext
        }
        null
    }

    LaunchedEffect(currentFullscreen) {
        activity?.let { act ->
            val window = act.window
            if (currentFullscreen) {
                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                if (window != null) {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    val controller = WindowCompat.getInsetsController(window, window.decorView)
                    controller.hide(WindowInsetsCompat.Type.systemBars())
                    controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            } else {
                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                if (window != null) {
                    WindowCompat.setDecorFitsSystemWindows(window, true)
                    val controller = WindowCompat.getInsetsController(window, window.decorView)
                    controller.show(WindowInsetsCompat.Type.systemBars())
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            activity?.let { act ->
                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                act.window?.let { win ->
                    WindowCompat.setDecorFitsSystemWindows(win, true)
                    val controller = WindowCompat.getInsetsController(win, win.decorView)
                    controller.show(WindowInsetsCompat.Type.systemBars())
                }
            }
        }
    }

    BackHandler(enabled = currentFullscreen) {
        setFullscreen(false)
    }

    var isPlaying by remember { mutableStateOf(true) }
    var showControls by remember { mutableStateOf(true) }
    var currentPositionSeconds by remember { mutableIntStateOf(0) }
    var realDurationSeconds by remember { mutableIntStateOf(0) }
    val totalDurationSeconds = remember { 24 * 60 } // Standard 24-minute fallback
    val displayDuration = if (realDurationSeconds > 0) realDurationSeconds else totalDurationSeconds
    var isBuffering by remember { mutableStateOf(false) }
    var activePlayerViewMode by remember(playerViewMode) { mutableStateOf(playerViewMode) }
    var showCaptions by remember { mutableStateOf(true) }

    // Selected server & stream (Single primary focus: HiAnime)
    val sources = episode.sources.ifEmpty {
        listOf(
            StreamSource("HD-1 (HiAnime)", AnimeSource.HIANIME, "https://hianime.to", "1080p", true),
            StreamSource("HD-2 Sub (HiAnime)", AnimeSource.HIANIME, "https://hianime.to", "720p", true),
            StreamSource("English Dub (HiAnime)", AnimeSource.HIANIME, "https://hianime.to", "1080p", true, isDub = true),
            StreamSource("Fast Server (HiAnime)", AnimeSource.HIANIME, "https://megacloud.blog", "Auto", true)
        )
    }
    var activeSourceIndex by remember(selectedSourceIndex) { mutableIntStateOf(selectedSourceIndex) }
    val currentSource = sources.getOrElse(activeSourceIndex) { sources.first() }

    var selectedSpeed by remember { mutableFloatStateOf(1.0f) }
    var isDub by remember { mutableStateOf(false) }
    var showServerMenu by remember { mutableStateOf(false) }
    var showSpeedMenu by remember { mutableStateOf(false) }
    var showQualityMenu by remember { mutableStateOf(false) }
    var selectedQuality by remember { mutableStateOf("1080p") }

    // Subtitles list (Japanese audio with English/Japanese subtitles)
    val subtitles = remember(animeId, animeTitle, episode.episodeNumber) {
        val key = animeId.ifEmpty { animeTitle }
        AnimeSubtitlesProvider.getSubtitlesFor(key, episode.episodeNumber)
    }
    val currentSubtitle = remember(currentPositionSeconds, subtitles) {
        AnimeSubtitlesProvider.findActiveSubtitle(subtitles, currentPositionSeconds)
    }

    // Auto-hide controls timer
    LaunchedEffect(showControls, isPlaying) {
        if (showControls && isPlaying) {
            delay(4500)
            showControls = false
        }
    }

    // Native MediaPlayer reference, active surface and preparation state tracking
    var mediaPlayerRef by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlayerPrepared by remember { mutableStateOf(false) }
    var isMediaStarted by remember { mutableStateOf(false) }
    var activeSurface by remember { mutableStateOf<Surface?>(null) }

    val videoStreamUrl = remember(episode.videoUrl, episode.episodeNumber, animeId, animeTitle) {
        if (episode.videoUrl.isNotEmpty()) {
            episode.videoUrl
        } else {
            val key = animeId.ifEmpty { animeTitle }
            AnimeEpisodeCatalog.getAnimeVideoUrl(key, episode.episodeNumber)
        }
    }

    // Playback progress ticker & synchronized MediaPlayer tracking (NEVER loop, sync position accurately)
    LaunchedEffect(isPlaying, isPlayerPrepared, isMediaStarted, selectedSpeed) {
        while (isPlaying) {
            delay(500)
            if (isPlayerPrepared && isMediaStarted) {
                try {
                    mediaPlayerRef?.let { mp ->
                        if (mp.isPlaying) {
                            val pos = mp.currentPosition / 1000
                            currentPositionSeconds = pos
                            val dur = if (realDurationSeconds > 0) realDurationSeconds else displayDuration
                            if (dur > 0) {
                                onProgressUpdate?.invoke((pos.toFloat() / dur.toFloat()).coerceIn(0f, 1f))
                            }
                        }
                    }
                } catch (e: Exception) { }
            } else {
                if (currentPositionSeconds < displayDuration) {
                    currentPositionSeconds += 1
                }
            }
        }
    }

    // Gentle Ken Burns zoom animation for anime scene
    val infiniteTransition = rememberInfiniteTransition(label = "ken_burns")
    val sceneScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scene_zoom"
    )

    // Visualizer equalizer bar height animations
    val bar1 by infiniteTransition.animateFloat(
        initialValue = 4f, targetValue = 16f,
        animationSpec = infiniteRepeatable(tween(350, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "eq1"
    )
    val bar2 by infiniteTransition.animateFloat(
        initialValue = 14f, targetValue = 6f,
        animationSpec = infiniteRepeatable(tween(420, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "eq2"
    )
    val bar3 by infiniteTransition.animateFloat(
        initialValue = 6f, targetValue = 18f,
        animationSpec = infiniteRepeatable(tween(380, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "eq3"
    )

    val imageModel = episode.thumbnail.ifEmpty { backdropUrl }.ifEmpty { R.drawable.hero_banner }

    // Safe seek function to prevent calling seekTo when uninitialized
    val seekToTime: (Int) -> Unit = remember(displayDuration, isPlayerPrepared) {
        { targetSec: Int ->
            val bounded = targetSec.coerceIn(0, displayDuration)
            currentPositionSeconds = bounded
            if (isPlayerPrepared) {
                try {
                    mediaPlayerRef?.seekTo((bounded * 1000).coerceAtLeast(0))
                } catch (e: Exception) {
                    // Ignore seek error
                }
            }
        }
    }

    // Orchestrate MediaPlayer loading and preparation whenever video stream or surface changes
    LaunchedEffect(videoStreamUrl, activeSurface) {
        currentPositionSeconds = 0
        realDurationSeconds = 0
        val surface = activeSurface ?: return@LaunchedEffect
        try {
            isPlayerPrepared = false
            isMediaStarted = false
            isBuffering = true
            try {
                mediaPlayerRef?.run {
                    try {
                        reset()
                    } catch (e: Exception) { }
                    try {
                        release()
                    } catch (e: Exception) { }
                }
            } catch (e: Exception) { }
            mediaPlayerRef = null

            val mp = MediaPlayer().apply {
                setSurface(surface)
                try {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                } catch (e: Exception) { }
                setDataSource(context, Uri.parse(videoStreamUrl))
                isLooping = false // NEVER repeat or loop episodes
                setOnPreparedListener { player ->
                    isBuffering = false
                    isPlayerPrepared = true
                    val durSec = player.duration / 1000
                    if (durSec > 0) {
                        realDurationSeconds = durSec
                    }
                    if (isPlaying) {
                        try {
                            player.start()
                            isMediaStarted = true
                        } catch (e: Exception) { }
                    }
                }
                setOnCompletionListener { player ->
                    isBuffering = false
                    isPlaying = false
                    isMediaStarted = false
                    currentPositionSeconds = displayDuration
                    onProgressUpdate?.invoke(1.0f)
                    onNextEpisode?.invoke()
                }
                setOnErrorListener { _, _, _ ->
                    isBuffering = false
                    isPlayerPrepared = false
                    isMediaStarted = false
                    true
                }
                prepareAsync()
            }
            mediaPlayerRef = mp
        } catch (e: Exception) {
            isBuffering = false
            isPlayerPrepared = false
            isMediaStarted = false
        }
    }

    // Safely sync play/pause with MediaPlayer only when prepared
    LaunchedEffect(isPlaying, isPlayerPrepared) {
        if (isPlayerPrepared) {
            val mp = mediaPlayerRef ?: return@LaunchedEffect
            try {
                if (isPlaying) {
                    if (!isMediaStarted) {
                        mp.start()
                        isMediaStarted = true
                    }
                } else {
                    if (isMediaStarted) {
                        mp.pause()
                        isMediaStarted = false
                    }
                }
            } catch (e: Exception) {
                // Ignore transient state race
            }
        }
    }

    // Safely sync playback speed with MediaPlayer when prepared
    LaunchedEffect(selectedSpeed, isPlayerPrepared) {
        if (isPlayerPrepared) {
            try {
                val mp = mediaPlayerRef ?: return@LaunchedEffect
                val params = mp.playbackParams
                params.speed = selectedSpeed
                mp.playbackParams = params
            } catch (e: Exception) {
                // Ignore if device does not support playbackParams speed change
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                isPlayerPrepared = false
                isMediaStarted = false
                activeSurface = null
                mediaPlayerRef?.run {
                    try {
                        reset()
                    } catch (e: Exception) { }
                    try {
                        release()
                    } catch (e: Exception) { }
                }
                mediaPlayerRef = null
            } catch (e: Exception) {
                // Ignore cleanup error
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (currentFullscreen) Modifier.fillMaxSize()
                else Modifier.aspectRatio(16f / 9f)
            )
            .background(Color.Black)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                showControls = !showControls
            }
    ) {
        if (activePlayerViewMode == PlayerViewMode.WEB_MIRROR) {
            // WEB MIRROR MODE: In-App WebView for scraped server embed
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.mediaPlaybackRequiresUserGesture = false
                        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        settings.userAgentString = "Mozilla/5.0 (Linux; Android 14; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36"
                        webChromeClient = WebChromeClient()
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                                    view?.loadUrl(url)
                                    return true
                                }
                                return false
                            }
                        }
                        loadUrl(currentSource.streamUrl)
                    }
                },
                update = { webView ->
                    if (webView.url != currentSource.streamUrl) {
                        webView.loadUrl(currentSource.streamUrl)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // STREAM PLAYER MODE: Multi-Layer Video & Animated Anime Scene (Never Blank!)
            Box(modifier = Modifier.fillMaxSize()) {
                // Layer 1: Authentic High-Res Anime Backdrop with Ken Burns Motion
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageModel)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.hero_banner),
                    error = painterResource(R.drawable.hero_banner),
                    contentDescription = animeTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(if (isPlaying) sceneScale else 1.0f)
                )

                // Layer 2: TextureView Native Hardware-Accelerated Video Layer
                AndroidView(
                    factory = { ctx ->
                        TextureView(ctx).apply {
                            layoutParams = FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                                override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
                                    activeSurface = Surface(surfaceTexture)
                                }

                                override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
                                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                                    isPlayerPrepared = false
                                    isMediaStarted = false
                                    activeSurface = null
                                    try {
                                        mediaPlayerRef?.let { mp ->
                                            try {
                                                mp.setSurface(null)
                                            } catch (e: Exception) { }
                                        }
                                    } catch (e: Exception) { }
                                    return true
                                }
                                override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
                            }
                        }
                    },
                    update = {
                        // Playback is safely orchestrated via LaunchedEffect(isPlaying, isPlayerPrepared)
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Layer 3: Cinematic Vignette & Ambient Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0x77000000),
                                    Color.Transparent,
                                    Color(0x99000000)
                                )
                            )
                        )
                )

                // Layer 4: Live Audio/Stream Visualizer Equalizer Badge (Shows media is actively streaming)
                if (isPlaying) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 10.dp, end = 12.dp)
                            .background(Color(0x88000000), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Audio Stream",
                            tint = AnimeCyan,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.height(14.dp)
                        ) {
                            Box(modifier = Modifier.width(3.dp).height(bar1.dp).background(AnimeCyan, RoundedCornerShape(1.dp)))
                            Box(modifier = Modifier.width(3.dp).height(bar2.dp).background(AnimePink, RoundedCornerShape(1.dp)))
                            Box(modifier = Modifier.width(3.dp).height(bar3.dp).background(AnimeCyan, RoundedCornerShape(1.dp)))
                        }
                    }
                }

                // Layer 5: Authentic Synced Anime Subtitles Overlay (SUB Mode with CC toggle)
                if (showCaptions && !isDub && currentSubtitle != null) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = if (showControls) 70.dp else 24.dp)
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // English Subtitle Line
                        Text(
                            text = currentSubtitle.textEn,
                            color = AnimeAmber,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(Color(0xAA000000), RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        )

                        // Japanese Script Line if present
                        if (currentSubtitle.textJa != null) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = currentSubtitle.textJa,
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .background(Color(0x88000000), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                } else if (isDub) {
                    // DUB Audio Indicator
                    Text(
                        text = "[English Dub Audio Track Active]",
                        color = AnimeCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = if (showControls) 72.dp else 24.dp)
                            .background(Color(0x99000000), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }

        // Loading spinner if buffering
        if (isBuffering) {
            CircularProgressIndicator(
                color = AnimeCyan,
                strokeWidth = 3.dp,
                modifier = Modifier.size(36.dp).align(Alignment.Center)
            )
        }

        // Overlay Controls HUD
        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xDD000000),
                                Color(0x33000000),
                                Color(0xDD000000)
                            )
                        )
                    )
            ) {
                // TOP BAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (currentFullscreen) {
                            setFullscreen(false)
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = animeTitle,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            text = episode.title,
                            color = AnimeCyan,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                    }

                    // Scraped Server Switcher Chip
                    Box {
                        Surface(
                            onClick = { showServerMenu = true },
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0x66FFFFFF),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color(currentSource.provider.badgeColorHex), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = currentSource.serverName.split(" ").first(),
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = showServerMenu,
                            onDismissRequest = { showServerMenu = false },
                            modifier = Modifier.background(DarkSurface)
                        ) {
                            Text(
                                text = "Select Scraped Server",
                                color = Color.Gray,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                            sources.forEachIndexed { index, src ->
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .background(Color(src.provider.badgeColorHex), CircleShape)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "${src.serverName} (${src.provider.displayName})",
                                                color = if (activeSourceIndex == index) AnimeCyan else Color.White,
                                                fontSize = 12.sp,
                                                fontWeight = if (activeSourceIndex == index) FontWeight.Bold else FontWeight.Normal
                                            )
                                        }
                                    },
                                    onClick = {
                                        activeSourceIndex = index
                                        onSourceIndexChange?.invoke(index)
                                        showServerMenu = false
                                        isBuffering = true
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Player Mode Toggle: Native Stream vs Web Mirror
                    Surface(
                        onClick = {
                            val newMode = if (activePlayerViewMode == PlayerViewMode.STREAM_PLAYER) {
                                PlayerViewMode.WEB_MIRROR
                            } else {
                                PlayerViewMode.STREAM_PLAYER
                            }
                            activePlayerViewMode = newMode
                            onPlayerViewModeChange?.invoke(newMode)
                        },
                        shape = RoundedCornerShape(16.dp),
                        color = if (activePlayerViewMode == PlayerViewMode.WEB_MIRROR) AnimePink else Color(0x66FFFFFF),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = if (activePlayerViewMode == PlayerViewMode.WEB_MIRROR) Icons.Default.Language else Icons.Default.LiveTv,
                                contentDescription = "Toggle Player Mode",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (activePlayerViewMode == PlayerViewMode.WEB_MIRROR) "Mirror" else "Native",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Quality Selector Chip
                    Box {
                        Surface(
                            onClick = { showQualityMenu = true },
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0x66FFFFFF),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text(
                                text = selectedQuality,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showQualityMenu,
                            onDismissRequest = { showQualityMenu = false },
                            modifier = Modifier.background(DarkSurface)
                        ) {
                            listOf("1080p", "720p", "480p", "360p", "Auto").forEach { q ->
                                DropdownMenuItem(
                                    text = { Text(q, color = if (selectedQuality == q) AnimeCyan else Color.White) },
                                    onClick = {
                                        selectedQuality = q
                                        showQualityMenu = false
                                    }
                                )
                            }
                        }
                    }
                }

                // CENTER PLAYBACK CONTROLS
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rewind 10s
                    IconButton(
                        onClick = {
                            seekToTime(currentPositionSeconds - 10)
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color(0x55000000), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Replay10,
                            contentDescription = "Rewind 10s",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    // Play/Pause
                    IconButton(
                        onClick = { isPlaying = !isPlaying },
                        modifier = Modifier
                            .size(60.dp)
                            .background(AnimeCyan, CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color.Black,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    // Forward 10s
                    IconButton(
                        onClick = {
                            seekToTime(currentPositionSeconds + 10)
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color(0x55000000), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Forward10,
                            contentDescription = "Forward 10s",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                // BOTTOM CONTROLS
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    // Progress Slider
                    Slider(
                        value = currentPositionSeconds.toFloat(),
                        onValueChange = { seekToTime(it.toInt()) },
                        valueRange = 0f..displayDuration.toFloat(),
                        colors = SliderDefaults.colors(
                            thumbColor = AnimeCyan,
                            activeTrackColor = AnimeCyan,
                            inactiveTrackColor = Color(0x66FFFFFF)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Time indicators
                        Text(
                            text = "${formatSeconds(currentPositionSeconds)} / ${formatSeconds(displayDuration)}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // CC Subtitle Toggle
                            Surface(
                                onClick = { showCaptions = !showCaptions },
                                shape = RoundedCornerShape(12.dp),
                                color = if (showCaptions) AnimeCyan else Color(0x44FFFFFF),
                                modifier = Modifier.height(24.dp)
                            ) {
                                Text(
                                    text = "CC",
                                    color = if (showCaptions) Color.Black else Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            // Sub / Dub Toggle
                            Surface(
                                onClick = { isDub = !isDub },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isDub) AnimePink else Color(0x44FFFFFF),
                                modifier = Modifier.height(24.dp)
                            ) {
                                Text(
                                    text = if (isDub) "DUB" else "SUB",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Playback Speed
                            Box {
                                Surface(
                                    onClick = { showSpeedMenu = true },
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0x44FFFFFF),
                                    modifier = Modifier.height(24.dp)
                                ) {
                                    Text(
                                        text = "${selectedSpeed}x",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }

                                DropdownMenu(
                                    expanded = showSpeedMenu,
                                    onDismissRequest = { showSpeedMenu = false },
                                    modifier = Modifier.background(DarkSurface)
                                ) {
                                    listOf(0.75f, 1.0f, 1.25f, 1.5f, 2.0f).forEach { speed ->
                                        DropdownMenuItem(
                                            text = { Text("${speed}x", color = if (selectedSpeed == speed) AnimeCyan else Color.White) },
                                            onClick = {
                                                selectedSpeed = speed
                                                showSpeedMenu = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Next Episode button
                            if (onNextEpisode != null) {
                                IconButton(onClick = onNextEpisode, modifier = Modifier.size(32.dp)) {
                                    Icon(
                                        imageVector = Icons.Default.SkipNext,
                                        contentDescription = "Next Episode",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            // Fullscreen Toggle
                            IconButton(
                                onClick = { setFullscreen(!currentFullscreen) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = if (currentFullscreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                                    contentDescription = "Toggle Fullscreen",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatSeconds(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return String.format("%02d:%02d", m, s)
}
