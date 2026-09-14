package com.example.data.scraper

import com.example.data.model.Anime
import com.example.data.model.AnimeSource

object CuratedAnimeCatalog {

    fun getAllCuratedAnime(): List<Anime> {
        return listOf(
            Anime(
                id = "attack-on-titan",
                title = "Attack on Titan",
                japaneseTitle = "進撃の巨人 (Shingeki no Kyojin)",
                posterUrl = "https://media.kitsu.app/anime/poster_images/7442/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/7442/large.jpg",
                synopsis = "Centuries ago, mankind was slaughtered to near extinction by monstrous humanoid creatures called Titans, forcing humans to hide behind enormous concentric walls. When a Colossal Titan breaches the outer wall, Eren Yeager vows to eradicate every Titan after witnessing the devastation of his home.",
                genres = listOf("Action", "Dark Fantasy", "Military", "Mystery"),
                totalEpisodes = 25,
                currentEpisodes = 25,
                score = 9.2,
                status = "Finished Airing",
                seasonYear = "2013",
                availableSources = listOf(AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 25
            )
        )
    }

    fun getFeaturedAnime(): Anime {
        return getAllCuratedAnime().first()
    }
}
