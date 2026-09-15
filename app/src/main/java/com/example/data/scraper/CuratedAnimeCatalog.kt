package com.example.data.scraper

import com.example.data.model.Anime
import com.example.data.model.AnimeSource

data class SeasonInfo(
    val seasonNumber: Int,
    val title: String,
    val subtitle: String,
    val episodeCount: Int,
    val year: String,
    val posterUrl: String,
    val bannerUrl: String,
    val synopsis: String
)

object CuratedAnimeCatalog {

    fun getAttackOnTitanSeasons(): List<SeasonInfo> {
        return listOf(
            SeasonInfo(
                seasonNumber = 1,
                title = "Season 1",
                subtitle = "25 Episodes • 2013",
                episodeCount = 25,
                year = "2013",
                posterUrl = "https://media.kitsu.app/anime/poster_images/7442/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/7442/large.jpg",
                synopsis = "Centuries ago, mankind was slaughtered to near extinction by monstrous humanoid creatures called Titans, forcing humans to hide behind enormous concentric walls. When the Colossal and Armored Titans breach Wall Maria, young Eren Yeager vows to eradicate every Titan after witnessing the devastation of his home and mother."
            ),
            SeasonInfo(
                seasonNumber = 2,
                title = "Season 2",
                subtitle = "12 Episodes • 2017",
                episodeCount = 12,
                year = "2017",
                posterUrl = "https://media.kitsu.app/anime/poster_images/8671/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/8671/large.jpg",
                synopsis = "Eren Yeager and the 104th Training Corps face a sudden crisis: Titans appear inside Wall Rose without any breach! As the mysterious Beast Titan unleashes pure chaos, shocking secrets within the Scout Regiment emerge when Reiner and Bertholdt reveal their true identities."
            ),
            SeasonInfo(
                seasonNumber = 3,
                title = "Season 3",
                subtitle = "22 Episodes • 2018-2019",
                episodeCount = 22,
                year = "2018-2019",
                posterUrl = "https://media.kitsu.app/anime/poster_images/13569/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/13569/large.jpg",
                synopsis = "The struggle for humanity shifts inward as the Scouts clash with the Royal Military Police and Kenny the Ripper. After uncovering the royal Fritz lineage, Erwin and Levi lead the fateful expedition to retake Wall Maria and unlock the truth inside Grisha's basement."
            ),
            SeasonInfo(
                seasonNumber = 4,
                title = "Final Season",
                subtitle = "30 Episodes • 2020-2023",
                episodeCount = 30,
                year = "2020-2023",
                posterUrl = "https://media.kitsu.app/anime/poster_images/42422/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/42422/large.jpg",
                synopsis = "The conflict expands across the sea to Marley. Four years after reaching the ocean, Eren Yeager infiltrates Liberio and strikes during Willy Tybur's declaration of war. As the world mobilizes to destroy Paradis, Eren unleashes the catastrophic Rumbling."
            )
        )
    }

    fun getSeasonInfo(seasonNumber: Int): SeasonInfo {
        val seasons = getAttackOnTitanSeasons()
        return seasons.find { it.seasonNumber == seasonNumber } ?: seasons.first()
    }

    /**
     * Unified single show representation for Explore, Home, and Watchlist tabs.
     * All seasons are consolidated under this single master show.
     */
    fun getAllCuratedAnime(): List<Anime> {
        return listOf(
            Anime(
                id = "attack-on-titan",
                title = "Attack on Titan",
                japaneseTitle = "進撃の巨人 (Shingeki no Kyojin)",
                posterUrl = "https://media.kitsu.app/anime/poster_images/7442/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/7442/large.jpg",
                synopsis = "Centuries ago, mankind was slaughtered to near extinction by monstrous humanoid creatures called Titans, forcing humans to hide behind enormous concentric walls. Eren Yeager, Mikasa Ackerman, and the Scout Regiment wage a desperate war across four seasons to uncover the origin of the Titans, reclaim Wall Maria, and decide the fate of humanity.",
                genres = listOf("Action", "Dark Fantasy", "Military", "Mystery", "Drama"),
                totalEpisodes = 89,
                currentEpisodes = 89,
                score = 9.5,
                status = "Finished Airing",
                seasonYear = "4 Seasons (89 Episodes)",
                availableSources = listOf(AnimeSource.HIANIME, AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 89
            )
        )
    }

    fun getFeaturedAnime(): Anime {
        return getAllCuratedAnime().first()
    }
}
