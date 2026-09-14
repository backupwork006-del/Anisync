package com.example.data.scraper

import com.example.data.model.Anime
import com.example.data.model.AnimeSource

object CuratedAnimeCatalog {

    fun getAllCuratedAnime(): List<Anime> {
        return listOf(
            Anime(
                id = "solo-leveling",
                title = "Solo Leveling",
                japaneseTitle = "Ore dake Level Up na Ken",
                posterUrl = "https://media.kitsu.app/anime/46231/poster_image/large-cdadff31f42490b9f48a035939a01a92.jpeg",
                bannerUrl = "https://media.kitsu.app/anime/46231/cover_image/large-33273dc297cdc8b10cc1140de07d3dae.jpeg",
                synopsis = "In a world where hunters must battle deadly monsters to protect mankind, Sung Jinwoo, known as the weakest hunter of all mankind, finds himself in a mysterious double dungeon that grants him a unique ability to level up infinitely.",
                genres = listOf("Action", "Fantasy", "Supernatural", "Adventure"),
                totalEpisodes = 12,
                currentEpisodes = 12,
                score = 8.9,
                status = "Finished Airing",
                seasonYear = "Winter 2024",
                availableSources = listOf(AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME, AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 12
            ),
            Anime(
                id = "jujutsu-kaisen-s2",
                title = "Jujutsu Kaisen Season 2",
                japaneseTitle = "呪術廻戦 懐玉・玉折 / 渋谷事変",
                posterUrl = "https://media.kitsu.app/anime/45857/poster_image/large-1ebb56f346edda6bcde9cdffa9b89316.jpeg",
                bannerUrl = "https://media.kitsu.app/anime/45857/cover_image/large-0fcd119c157177b52ae512052b005a9b.jpeg",
                synopsis = "The past comes to light as Satoru Gojo and Suguru Geto take on a fateful mission to protect the Star Plasma Vessel, leading directly into the catastrophic Shibuya Incident.",
                genres = listOf("Action", "Fantasy", "Dark Fantasy", "Supernatural"),
                totalEpisodes = 23,
                currentEpisodes = 23,
                score = 9.1,
                status = "Finished Airing",
                seasonYear = "Fall 2023",
                availableSources = listOf(AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME, AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 23
            ),
            Anime(
                id = "frieren-beyond-journeys-end",
                title = "Frieren: Beyond Journey's End",
                japaneseTitle = "Sousou no Frieren",
                posterUrl = "https://media.kitsu.app/anime/46474/poster_image/large-ec9b98dd5fbf8f92532d1edb45f9e882.jpeg",
                bannerUrl = "https://media.kitsu.app/anime/46474/cover_image/large-167edf3e01fac59ce6aacfeb47df5634.jpeg",
                synopsis = "The adventure is over, but life goes on for an elf mage just beginning to learn what living is all about. Frieren begins a new journey through the lands her party traveled to reflect on human life and memories.",
                genres = listOf("Adventure", "Drama", "Fantasy"),
                totalEpisodes = 28,
                currentEpisodes = 28,
                score = 9.3,
                status = "Finished Airing",
                seasonYear = "Winter 2024",
                availableSources = listOf(AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME, AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 28
            ),
            Anime(
                id = "demon-slayer-hashira-training",
                title = "Demon Slayer: Hashira Training Arc",
                japaneseTitle = "Kimetsu no Yaiba: Hashira Geiko-hen",
                posterUrl = "https://media.kitsu.app/anime/poster_images/41370/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/41370/cover_image/large-3de3cc6d2b33162c928de10aa201e4ba.jpeg",
                synopsis = "Tanjiro visits the Stone Hashira, Himejima, who intends to prepare him for the battles to come. The training to become a Hashira is rigorous and demanding, earning Himejima's approval seems impossible.",
                genres = listOf("Action", "Fantasy", "Historical"),
                totalEpisodes = 8,
                currentEpisodes = 8,
                score = 8.6,
                status = "Finished Airing",
                seasonYear = "Spring 2024",
                availableSources = listOf(AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME, AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 8
            ),
            Anime(
                id = "kaiju-no-8",
                title = "Kaiju No. 8",
                japaneseTitle = "怪獣8号",
                posterUrl = "https://media.kitsu.app/anime/46300/poster_image/large-31cc66fd5854cc555d496ced7ab40c31.jpeg",
                bannerUrl = "https://media.kitsu.app/anime/46300/cover_image/large-beb78aec0cb2066bc44ea52f66311e04.jpeg",
                synopsis = "In a world plagued by dangerous monsters known as Kaiju, Kafka Hibino aspires to join the Defense Force. After a mysterious small kaiju enters his body, he gains monstrous power himself.",
                genres = listOf("Action", "Sci-Fi", "Military"),
                totalEpisodes = 12,
                currentEpisodes = 12,
                score = 8.4,
                status = "Finished Airing",
                seasonYear = "Spring 2024",
                availableSources = listOf(AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME, AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 12
            ),
            Anime(
                id = "chainsaw-man",
                title = "Chainsaw Man",
                japaneseTitle = "チェンソーマン",
                posterUrl = "https://media.kitsu.app/anime/43806/poster_image/large-815d6008fb3b56f4291b9f0ffa05cd8f.jpeg",
                bannerUrl = "https://media.kitsu.app/anime/43806/cover_image/large-964674a0f11524f62d65dde845ad8e1f.jpeg",
                synopsis = "Denji is a teenage boy living with a Chainsaw Devil named Pochita. Due to the debt his father left behind, he has been living a rock-bottom life while harvesting devil corpses with Pochita.",
                genres = listOf("Action", "Supernatural", "Gore"),
                totalEpisodes = 12,
                currentEpisodes = 12,
                score = 8.8,
                status = "Finished Airing",
                seasonYear = "Fall 2022",
                availableSources = listOf(AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME, AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 12
            ),
            Anime(
                id = "one-piece",
                title = "One Piece: Egghead Arc",
                japaneseTitle = "ワンピース",
                posterUrl = "https://media.kitsu.app/anime/poster_images/12/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/12/cover_image/large-3e72f400a87b5241780c5082f0582611.jpeg",
                synopsis = "Monkey D. Luffy and his Straw Hat pirate crew arrive at the futuristic island of Egghead, home of the genius scientist Dr. Vegapunk, unveiling long-hidden secrets of the world.",
                genres = listOf("Action", "Adventure", "Fantasy"),
                totalEpisodes = 1110,
                currentEpisodes = 1110,
                score = 9.0,
                status = "Currently Airing",
                seasonYear = "Ongoing",
                availableSources = listOf(AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME, AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 1110
            ),
            Anime(
                id = "bleach-thousand-year-blood-war",
                title = "Bleach: Thousand-Year Blood War",
                japaneseTitle = "BLEACH 千年血戦篇",
                posterUrl = "https://media.kitsu.app/anime/poster_images/244/large.jpg",
                bannerUrl = "https://media.kitsu.app/anime/244/cover_image/large-b9e0a3066197f1115c773ff866a60873.jpeg",
                synopsis = "The peace is suddenly broken when warning sirens blare through the Soul Society. A shadow approaches Ichigo and his fellow Soul Reapers in the final battle against the Quincy empire.",
                genres = listOf("Action", "Supernatural", "Shounen"),
                totalEpisodes = 26,
                currentEpisodes = 26,
                score = 9.0,
                status = "Currently Airing",
                seasonYear = "2024",
                availableSources = listOf(AnimeSource.ANIMEPAHE, AnimeSource.GOGOANIME, AnimeSource.HIANIME),
                hasSub = true,
                hasDub = true,
                latestEpisode = 26
            )
        )
    }

    fun getFeaturedAnime(): Anime {
        return getAllCuratedAnime().first()
    }
}
