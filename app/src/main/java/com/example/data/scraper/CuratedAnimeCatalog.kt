package com.example.data.scraper

import com.example.data.model.Anime
import com.example.data.model.AnimeSource

object CuratedAnimeCatalog {

    fun getAllCuratedAnime(): List<Anime> {
        return listOf(
            Anime(
                id = "attack-on-titan",
                title = "Attack on Titan (Season 1)",
                japaneseTitle = "進撃の巨人 第1期 (Shingeki no Kyojin)",
                posterUrl = "https://media.kitsu.app/anime/poster_images/7442/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/7442/large.jpg",
                synopsis = "Centuries ago, mankind was slaughtered to near extinction by monstrous humanoid creatures called Titans, forcing humans to hide behind enormous concentric walls. When the Colossal and Armored Titans breach Wall Maria, young Eren Yeager vows to eradicate every Titan after witnessing the devastation of his home and mother.",
                genres = listOf("Action", "Dark Fantasy", "Military", "Mystery"),
                totalEpisodes = 25,
                currentEpisodes = 25,
                score = 9.2,
                status = "Finished Airing",
                seasonYear = "2013 (Season 1)",
                availableSources = listOf(AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 25
            ),
            Anime(
                id = "attack-on-titan-season-2",
                title = "Attack on Titan Season 2",
                japaneseTitle = "進撃の巨人 Season 2",
                posterUrl = "https://media.kitsu.app/anime/poster_images/11469/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/11469/large.jpg",
                synopsis = "Eren Yeager and others of the 104th Training Corps have just begun to become full members of the Scout Regiment. Suddenly, Titans appear deep inside Wall Rose without any breach! As the mysterious furry Beast Titan appears, shocking betrayals within the scouts emerge: Reiner and Bertholdt reveal their true identities.",
                genres = listOf("Action", "Suspense", "Dark Fantasy", "Super Power"),
                totalEpisodes = 12,
                currentEpisodes = 12,
                score = 9.3,
                status = "Finished Airing",
                seasonYear = "2017 (Season 2)",
                availableSources = listOf(AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 12
            ),
            Anime(
                id = "attack-on-titan-season-3",
                title = "Attack on Titan Season 3",
                japaneseTitle = "進撃の巨人 Season 3",
                posterUrl = "https://media.kitsu.app/anime/poster_images/13569/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/13569/large.jpg",
                synopsis = "The battle for humanity's freedom shifts inward as the Scout Regiment clashes with the Royal Interior Military Police and Kenny the Ripper. After uncovering the royal Fritz lineage and Historia's coronation, Erwin and Levi lead the fateful expedition to retake Wall Maria, defeat the Colossal & Beast Titans, and unlock the truth of the world inside Grisha's basement.",
                genres = listOf("Action", "Military", "Mystery", "Drama"),
                totalEpisodes = 22,
                currentEpisodes = 22,
                score = 9.5,
                status = "Finished Airing",
                seasonYear = "2018-2019 (Season 3)",
                availableSources = listOf(AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 22
            ),
            Anime(
                id = "attack-on-titan-the-final-season",
                title = "Attack on Titan The Final Season",
                japaneseTitle = "進撃の巨人 The Final Season",
                posterUrl = "https://media.kitsu.app/anime/poster_images/42422/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/cover_images/42422/large.jpg",
                synopsis = "The conflict shifts beyond the sea to the nation of Marley. Four years after the expedition to the ocean, Eren Yeager infiltrates Liberio and strikes a devastating blow during Willy Tybur's declaration of war. As the world unites to annihilate Paradis Island, Eren activates the Founding Titan to initiate the cataclysmic Rumbling.",
                genres = listOf("Action", "Drama", "Psychological", "War"),
                totalEpisodes = 30,
                currentEpisodes = 30,
                score = 9.6,
                status = "Finished Airing",
                seasonYear = "2020-2023 (Final Season)",
                availableSources = listOf(AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 30
            )
        )
    }

    fun getFeaturedAnime(): Anime {
        return getAllCuratedAnime().first()
    }
}
