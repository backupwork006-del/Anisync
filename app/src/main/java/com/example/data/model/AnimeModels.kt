package com.example.data.model

enum class WatchStatus(val displayName: String) {
    WATCHING("Watching"),
    PLAN_TO_WATCH("Plan to Watch"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold"),
    DROPPED("Dropped")
}

enum class AnimeSource(val displayName: String, val baseUrl: String, val badgeColorHex: Long) {
    ANIMEPAHE("AnimePahe", "https://animepahe.pw", 0xFFE040FB),
    GOGOANIME("Gogoanime", "https://anitaku.to", 0xFFFF9100),
    HIANIME("HiAnime", "https://hianime.to", 0xFF00E5FF),
    ALL("All Sources", "", 0xFF7C4DFF)
}

data class Anime(
    val id: String,
    val title: String,
    val japaneseTitle: String = "",
    val posterUrl: String = "",
    val bannerUrl: String = "",
    val synopsis: String = "",
    val genres: List<String> = emptyList(),
    val totalEpisodes: Int = 12,
    val currentEpisodes: Int = 12,
    val score: Double = 8.5,
    val status: String = "Ongoing",
    val seasonYear: String = "2024",
    val availableSources: List<AnimeSource> = listOf(AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME, AnimeSource.HIANIME),
    val hasSub: Boolean = true,
    val hasDub: Boolean = true,
    val latestEpisode: Int = 12
)

data class Episode(
    val episodeNumber: Int,
    val title: String,
    val thumbnail: String = "",
    val duration: String = "24m",
    val releaseTime: String = "Recently",
    val sources: List<StreamSource> = emptyList(),
    val videoUrl: String = "",
    val synopsis: String = ""
)

data class StreamSource(
    val serverName: String, // e.g. "MegaCloud", "Kwik", "Vidstream", "StreamWish"
    val provider: AnimeSource,
    val streamUrl: String,
    val quality: String = "1080p",
    val isEmbed: Boolean = false,
    val isDub: Boolean = false
)
