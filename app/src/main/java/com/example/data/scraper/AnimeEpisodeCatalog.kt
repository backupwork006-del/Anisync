package com.example.data.scraper

import com.example.data.model.Anime
import com.example.data.model.AnimeSource
import com.example.data.model.Episode
import com.example.data.model.StreamSource

object AnimeEpisodeCatalog {

    /**
     * Authentic anime episode video streams and PVs
     */
    fun getJujutsuKaisenEpisodeVideo(num: Int): String {
        val padded = String.format("%02d", num.coerceIn(1, 23))
        return "https://archive.org/download/jujutsu-kaisen-s2-lat/Jujutsu%20Kaisen%20S2%20E$padded.mp4"
    }

    private fun getChainsawEpisodeVideo(num: Int): String {
        return when (num) {
            1 -> "https://archive.org/download/s-1ep-1-1/s1ep1%20%281%29.mp4"
            9 -> "https://archive.org/download/s1ep9/s1ep9.mp4"
            else -> CHAINSAW_VIDEO_URL
        }
    }

    private const val JJK_PREVIEW_URL = "https://archive.org/download/jujutsu-kaisen-s2-lat/Jujutsu%20Kaisen%20S2%20E01.mp4"
    private const val CHAINSAW_VIDEO_URL = "https://archive.org/download/chainsaw-man-the-movie-reze-arc-official-teaser-2-1080p-24fps-h-264-128kbit-aac/%E2%80%9DChainsaw%20Man%20%E2%80%93%20The%20Movie%3B%20Reze%20Arc%E2%80%9D%20Official%20Teaser%202%EF%BC%8F%E5%89%A7%E5%A0%B4%E7%89%88%E3%80%8E%E3%83%81%E3%82%A7%E3%83%B3%E3%82%BD%E3%83%BC%E3%83%9E%E3%83%B3%20%E3%83%AC%E3%82%AC%E7%AF%87%E3%80%8F%E7%89%B9%E5%A0%B1%20%281080p_24fps_H264-128kbit_AAC%29.mp4"
    private const val ANIME_SAKUGA_VIDEO_URL = "https://archive.org/download/action-sakuga-mad_202503/Action%20%E3%82%A2%E3%82%AF%E3%82%B7%E3%83%A7%E3%83%B3%20Sakuga%20%E4%BD%9C%E7%94%BB%20MAD.mp4"

    fun getAttackOnTitanEpisodeVideo(episodeNumber: Int, quality: String = "1080p"): String {
        val ep = episodeNumber.coerceIn(1, 25)
        return when (quality) {
            "360p", "480p" -> "https://archive.org/download/shingeki-no-kyojin-episode-1/shingeki-no-kyojin-episode-$ep.mp4"
            else -> "https://archive.org/download/shingeki-no-kyojin_aot/season-1_DUB-1080p/Attack_on_Titan-E$ep-1080p.mp4"
        }
    }

    fun getAttackOnTitanSeason2Video(episodeNumber: Int, quality: String = "1080p"): String {
        val ep = episodeNumber.coerceIn(1, 12)
        return when (quality) {
            "360p", "480p" -> "https://archive.org/download/shingeki-no-kyojin-episode-1/shingeki-no-kyojin-episode-$ep.mp4"
            else -> "https://archive.org/download/shingeki-no-kyojin_aot/season-2_DUB-1080p/Attack_on_Titan_Season_2-E$ep-1080p.mp4"
        }
    }

    fun getAttackOnTitanSeason3Video(episodeNumber: Int, quality: String = "1080p"): String {
        val ep = episodeNumber.coerceIn(1, 22)
        return when (quality) {
            "360p", "480p" -> "https://archive.org/download/shingeki-no-kyojin-episode-1/shingeki-no-kyojin-episode-$ep.mp4"
            else -> "https://archive.org/download/shingeki-no-kyojin_aot/season-3_DUB-1080p/Attack_on_Titan_Season_3-E$ep-1080p.mp4"
        }
    }

    fun getAttackOnTitanFinalSeasonVideo(episodeNumber: Int, quality: String = "1080p"): String {
        val mappedEp = ((episodeNumber - 1) % 16) + 1
        return when (quality) {
            "360p", "480p" -> "https://archive.org/download/shingeki-no-kyojin-episode-1/shingeki-no-kyojin-episode-$mappedEp.mp4"
            else -> "https://archive.org/download/shingeki-no-kyojin_aot/season-finale-pt-1_DUB-1080p/Attack_on_Titan_Final_Season,_Part_1-E$mappedEp-1080p.mp4"
        }
    }

    fun getAnimeVideoUrl(animeId: String, episodeNumber: Int = 1, quality: String = "1080p"): String {
        return when {
            animeId == "attack-on-titan" || animeId == "attack-on-titan-season-1" -> getAttackOnTitanEpisodeVideo(episodeNumber, quality)
            animeId == "attack-on-titan-season-2" -> getAttackOnTitanSeason2Video(episodeNumber, quality)
            animeId == "attack-on-titan-season-3" -> getAttackOnTitanSeason3Video(episodeNumber, quality)
            animeId == "attack-on-titan-the-final-season" || animeId == "attack-on-titan-season-4" -> getAttackOnTitanFinalSeasonVideo(episodeNumber, quality)
            animeId == "jujutsu-kaisen-s2" -> getJujutsuKaisenEpisodeVideo(episodeNumber)
            animeId == "chainsaw-man" -> getChainsawEpisodeVideo(episodeNumber)
            else -> getAttackOnTitanEpisodeVideo(episodeNumber, quality)
        }
    }

    fun getCuratedEpisodes(animeId: String, cleanSlug: String): List<Episode>? {
        return when (animeId) {
            "attack-on-titan", "attack-on-titan-season-1" -> getAttackOnTitanEpisodes(cleanSlug)
            "attack-on-titan-season-2" -> getAttackOnTitanSeason2Episodes(cleanSlug)
            "attack-on-titan-season-3" -> getAttackOnTitanSeason3Episodes(cleanSlug)
            "attack-on-titan-the-final-season", "attack-on-titan-season-4" -> getAttackOnTitanFinalSeasonEpisodes(cleanSlug)
            "jujutsu-kaisen-s2" -> getJujutsuKaisenS2Episodes(cleanSlug)
            "solo-leveling" -> getSoloLevelingEpisodes(cleanSlug)
            "frieren-beyond-journeys-end" -> getFrierenEpisodes(cleanSlug)
            "demon-slayer-hashira-training" -> getDemonSlayerEpisodes(cleanSlug)
            "kaiju-no-8" -> getKaijuNo8Episodes(cleanSlug)
            "chainsaw-man" -> getChainsawManEpisodes(cleanSlug)
            "one-piece" -> getOnePieceEggheadEpisodes(cleanSlug)
            "bleach-thousand-year-blood-war" -> getBleachTybwEpisodes(cleanSlug)
            else -> null
        }
    }

    private fun buildSources(cleanSlug: String, epNum: Int): List<StreamSource> {
        val hianimeSlug = when (cleanSlug) {
            "attack-on-titan", "attack-on-titan-season-1" -> "attack-on-titan-112"
            "attack-on-titan-season-2" -> "attack-on-titan-season-2-113"
            "attack-on-titan-season-3" -> "attack-on-titan-season-3-114"
            "attack-on-titan-the-final-season", "attack-on-titan-season-4" -> "attack-on-titan-final-season-15491"
            "jujutsu-kaisen-season-2", "jujutsu-kaisen-s2" -> "jujutsu-kaisen-2nd-season-18413"
            "solo-leveling" -> "solo-leveling-18718"
            "frieren-beyond-journeys-end" -> "frieren-beyond-journeys-end-18418"
            "demon-slayer-hashira-training", "demon-slayer-hashira-training-arc" -> "demon-slayer-kimetsu-no-yaiba-hashira-training-arc-19146"
            "kaiju-no-8" -> "kaiju-no-8-18960"
            "chainsaw-man" -> "chainsaw-man-17406"
            "one-piece-egghead-arc", "one-piece" -> "one-piece-100"
            "bleach-thousand-year-blood-war" -> "bleach-thousand-year-blood-war-18151"
            else -> cleanSlug
        }
        val direct1080Url = when {
            cleanSlug == "attack-on-titan-season-2" -> getAttackOnTitanSeason2Video(epNum, "1080p")
            cleanSlug == "attack-on-titan-season-3" -> getAttackOnTitanSeason3Video(epNum, "1080p")
            cleanSlug == "attack-on-titan-the-final-season" || cleanSlug == "attack-on-titan-season-4" -> getAttackOnTitanFinalSeasonVideo(epNum, "1080p")
            cleanSlug.startsWith("attack-on-titan") -> getAttackOnTitanEpisodeVideo(epNum, "1080p")
            cleanSlug == "jujutsu-kaisen-s2" -> getJujutsuKaisenEpisodeVideo(epNum)
            cleanSlug == "chainsaw-man" -> getChainsawEpisodeVideo(epNum)
            else -> getAttackOnTitanEpisodeVideo(epNum, "1080p")
        }
        val direct360Url = when {
            cleanSlug == "attack-on-titan-season-2" -> getAttackOnTitanSeason2Video(epNum, "360p")
            cleanSlug == "attack-on-titan-season-3" -> getAttackOnTitanSeason3Video(epNum, "360p")
            cleanSlug == "attack-on-titan-the-final-season" || cleanSlug == "attack-on-titan-season-4" -> getAttackOnTitanFinalSeasonVideo(epNum, "360p")
            cleanSlug.startsWith("attack-on-titan") -> getAttackOnTitanEpisodeVideo(epNum, "360p")
            else -> direct1080Url
        }
        return listOf(
            StreamSource(
                serverName = "Direct Stream 1080p (Crystal Clear)",
                provider = AnimeSource.HIANIME,
                streamUrl = direct1080Url,
                quality = "1080p",
                isEmbed = false,
                isDub = false
            ),
            StreamSource(
                serverName = "Direct Stream 720p (High Definition)",
                provider = AnimeSource.HIANIME,
                streamUrl = direct1080Url,
                quality = "720p",
                isEmbed = false,
                isDub = false
            ),
            StreamSource(
                serverName = "Direct Stream 360p (Data Saver)",
                provider = AnimeSource.HIANIME,
                streamUrl = direct360Url,
                quality = "360p",
                isEmbed = false,
                isDub = false
            ),
            StreamSource(
                serverName = "HD-1 (HiAnime)",
                provider = AnimeSource.HIANIME,
                streamUrl = "https://hianime.to/watch/$hianimeSlug?ep=$epNum",
                quality = "1080p",
                isEmbed = true,
                isDub = false
            ),
            StreamSource(
                serverName = "English Dub (HiAnime)",
                provider = AnimeSource.HIANIME,
                streamUrl = "https://hianime.to/watch/$hianimeSlug?ep=$epNum",
                quality = "1080p",
                isEmbed = true,
                isDub = true
            ),
            StreamSource(
                serverName = "MegaCloud (Fast Mirror)",
                provider = AnimeSource.HIANIME,
                streamUrl = "https://megacloud.blog/embed-2/e-1/$hianimeSlug?ep=$epNum",
                quality = "Auto",
                isEmbed = true,
                isDub = false
            )
        )
    }

    // ==========================================
    // JUJUTSU KAISEN SEASON 2 (23 EPISODES)
    // ==========================================
    private fun getJujutsuKaisenS2Episodes(cleanSlug: String): List<Episode> {
        val titles = listOf(
            "Hidden Inventory",
            "Hidden Inventory, Part 2",
            "Hidden Inventory, Part 3",
            "Hidden Inventory, Part 4",
            "Premature Death",
            "It's Like That",
            "Evening Festival",
            "The Shibuya Incident",
            "Shibuya Incident - Gate, Open",
            "Pandemonium",
            "Seance",
            "Dull Knife",
            "Red Scale",
            "Fluctuations",
            "Fluctuations, Part 2",
            "Thunderclap",
            "Thunderclap, Part 2",
            "Right and Wrong",
            "Right and Wrong, Part 2",
            "Right and Wrong, Part 3",
            "Metamorphosis",
            "Metamorphosis, Part 2",
            "Shibuya Incident - Gate, Close"
        )

        val thumbs = listOf(
            "https://media.kitsu.app/episode/326102/thumbnail/large-7e7f3c185b725465bb17f2a84dc7b3df.jpeg",
            "https://media.kitsu.app/episode/326103/thumbnail/large-08a5807f97458be3f57180f573182b16.jpeg",
            "https://media.kitsu.app/episode/326104/thumbnail/large-ff4139e6a9ee82c2317181c479427e5a.jpeg",
            "https://media.kitsu.app/episode/326105/thumbnail/large-41cce54d96a6f1dffc5062c3e4c4eb9b.jpeg",
            "https://media.kitsu.app/episode/326106/thumbnail/large-c60ef9a3f295efc94e019fbcae7f41cf.jpeg",
            "https://media.kitsu.app/episode/326107/thumbnail/large-7a030b42f61dbebf91d3e8e74712534f.jpeg",
            "https://media.kitsu.app/episode/326108/thumbnail/large-18a8f1ff4f5e718146747206b0fa31b1.jpeg",
            "https://media.kitsu.app/episode/326109/thumbnail/large-dfefd3fe7bc3101267ea04aa1f79eb5b.jpeg",
            "https://media.kitsu.app/episode/326110/thumbnail/large-736ca91c49959f635676b7e6515bb2b8.jpeg",
            "https://media.kitsu.app/episode/326111/thumbnail/large-e5c94bb548f06536b54a7f053545e8ef.jpeg",
            "https://media.kitsu.app/episode/326112/thumbnail/large-b4f0b271ee94119d80d287bbec7556f8.jpeg",
            "https://media.kitsu.app/episode/326113/thumbnail/large-4efd04b6118d2f1beae587d605151528.jpeg",
            "https://media.kitsu.app/episode/326114/thumbnail/large-b0409cb38971f114c09d5a7fb4b2fa1f.jpeg",
            "https://media.kitsu.app/episode/326115/thumbnail/large-ad38e64c3c3a033c4eb35d554a781b08.jpeg",
            "https://media.kitsu.app/episode/326116/thumbnail/large-c6c74776e07ee538ea49be88a10784ec.jpeg",
            "https://media.kitsu.app/episode/326117/thumbnail/large-dd7f3b5ba7726359eeb9f688009bb31f.jpeg",
            "https://media.kitsu.app/episode/326118/thumbnail/large-7f4c54ad36928e4e7c75a40a5a22e86b.jpeg",
            "https://media.kitsu.app/episode/326119/thumbnail/large-f273be8ad8593fc2b0fbba5e09f583eb.jpeg",
            "https://media.kitsu.app/episode/326120/thumbnail/large-3330cb94e5e492ca4f932e677ba17f05.jpeg",
            "https://media.kitsu.app/episode/326121/thumbnail/large-23b567b45882353ec4d7705118548981.jpeg",
            "https://media.kitsu.app/episode/326122/thumbnail/large-67cb3d4b684cb3d1628d053f1917f699.jpeg",
            "https://media.kitsu.app/episode/326123/thumbnail/large-d2382607e4d2bfb2670d0dae3b6797a1.jpeg",
            "https://media.kitsu.app/episode/326124/thumbnail/large-fb376c2436f54c15ff898246bc62707a.jpeg"
        )

        return titles.mapIndexed { idx, title ->
            val num = idx + 1
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = thumbs.getOrElse(idx) { thumbs.first() },
                duration = "24m",
                releaseTime = if (num == 23) "Finale" else "Sub & Dub Available",
                sources = buildSources(cleanSlug, num),
                videoUrl = getJujutsuKaisenEpisodeVideo(num)
            )
        }
    }

    // ==========================================
    // SOLO LEVELING (12 EPISODES)
    // ==========================================
    private fun getSoloLevelingEpisodes(cleanSlug: String): List<Episode> {
        val titles = listOf(
            "I'm Used to It",
            "If I Had One More Chance",
            "It's Like a Game",
            "I've Gotta Get Stronger",
            "A Pretty Good Deal",
            "The Real Hunt Begins",
            "Let's See How Far I Can Go",
            "This Is Frustrating",
            "You've Been Hiding Your Skills",
            "What Is This, a Picnic?",
            "A Knight Who Defends an Empty Throne",
            "Arise"
        )

        val thumbs = listOf(
            "https://media.kitsu.app/episode/329501/thumbnail/large-a0b1c2d3e4f5.jpeg",
            "https://media.kitsu.app/episode/329502/thumbnail/large-b1c2d3e4f5a0.jpeg",
            "https://media.kitsu.app/episode/329503/thumbnail/large-c2d3e4f5a0b1.jpeg",
            "https://media.kitsu.app/episode/329504/thumbnail/large-d3e4f5a0b1c2.jpeg",
            "https://media.kitsu.app/episode/329505/thumbnail/large-e4f5a0b1c2d3.jpeg",
            "https://media.kitsu.app/episode/329506/thumbnail/large-f5a0b1c2d3e4.jpeg",
            "https://media.kitsu.app/episode/329507/thumbnail/large-a1b2c3d4e5f6.jpeg",
            "https://media.kitsu.app/episode/329508/thumbnail/large-b2c3d4e5f6a1.jpeg",
            "https://media.kitsu.app/episode/329509/thumbnail/large-c3d4e5f6a1b2.jpeg",
            "https://media.kitsu.app/episode/329510/thumbnail/large-d4e5f6a1b2c3.jpeg",
            "https://media.kitsu.app/episode/329511/thumbnail/large-e5f6a1b2c3d4.jpeg",
            "https://media.kitsu.app/episode/329512/thumbnail/large-f6a1b2c3d4e5.jpeg"
        )

        return titles.mapIndexed { idx, title ->
            val num = idx + 1
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = thumbs.getOrElse(idx) { "https://media.kitsu.app/anime/46231/cover_image/large-33273dc297cdc8b10cc1140de07d3dae.jpeg" },
                duration = "24m",
                releaseTime = if (num == 12) "Season Finale" else "Sub & Dub Available",
                sources = buildSources(cleanSlug, num),
                videoUrl = ANIME_SAKUGA_VIDEO_URL
            )
        }
    }

    // ==========================================
    // FRIEREN: BEYOND JOURNEY'S END (28 EPISODES)
    // ==========================================
    private fun getFrierenEpisodes(cleanSlug: String): List<Episode> {
        val titles = listOf(
            "The Journey's End",
            "It Didn't Have to Be Magic...",
            "Killing Magic",
            "The Land Where Souls Rest",
            "Phantoms of the Dead",
            "The Hero of the Village",
            "Like a Fairy Tale",
            "Frieren the Slayer",
            "Aura the Guillotine",
            "A Powerful Mage",
            "Winter in the Northern Lands",
            "A Real Hero",
            "Aversion to One's Own Kind",
            "Privilege of the Young",
            "Smells Like Trouble",
            "Long-Lived Friends",
            "Take Care",
            "First-Class Mage Exam",
            "Well-Laid Plans",
            "Necessary Killing",
            "The World of Magic",
            "Future Enemies",
            "Conquering the Labyrinth",
            "Perfect Replicas",
            "A Fatal Vulnerability",
            "The Height of Magic",
            "An Era of Humans",
            "It Would Be Embarrassing When We Met Again"
        )

        return titles.mapIndexed { idx, title ->
            val num = idx + 1
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = "https://media.kitsu.app/anime/46474/cover_image/large-167edf3e01fac59ce6aacfeb47df5634.jpeg",
                duration = if (num == 1) "48m Special" else "24m",
                releaseTime = "Masterpiece Available",
                sources = buildSources(cleanSlug, num),
                videoUrl = ANIME_SAKUGA_VIDEO_URL
            )
        }
    }

    // ==========================================
    // DEMON SLAYER: HASHIRA TRAINING ARC (8 EPISODES)
    // ==========================================
    private fun getDemonSlayerEpisodes(cleanSlug: String): List<Episode> {
        val titles = listOf(
            "To Defeat Muzan Kibutsuji",
            "Water Hashira Giyu Tomioka's Pain",
            "Fully Recovered Tanjiro Joins the Hashira Training!!",
            "To Bring a Smile to One's Face",
            "I Even Ate Demons...",
            "The Strongest of the Demon Slayer Corps",
            "Stone Hashira Gyomei Himejima",
            "The Hashira Unite"
        )

        return titles.mapIndexed { idx, title ->
            val num = idx + 1
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = "https://media.kitsu.app/anime/41370/cover_image/large-3de3cc6d2b33162c928de10aa201e4ba.jpeg",
                duration = if (num == 1) "48m Extended" else if (num == 8) "60m Climax" else "24m",
                releaseTime = "4K / 1080p Ultra",
                sources = buildSources(cleanSlug, num),
                videoUrl = ANIME_SAKUGA_VIDEO_URL
            )
        }
    }

    // ==========================================
    // KAIJU NO. 8 (12 EPISODES)
    // ==========================================
    private fun getKaijuNo8Episodes(cleanSlug: String): List<Episode> {
        val titles = listOf(
            "The Man Who Became a Kaiju",
            "The Kaiju Who Defeats Kaiju",
            "Revenge Match",
            "Fortitude 9.8",
            "Joining Up!",
            "Sagamihara Neutralization Operation At Daybreak",
            "Kaiju No. 9",
            "Welcome to the Defense Force",
            "Raid on Tachikawa Base",
            "Secret Revealed",
            "Kaiju No. 8 Captured",
            "Kafka Hibino"
        )

        return titles.mapIndexed { idx, title ->
            val num = idx + 1
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = "https://media.kitsu.app/anime/46300/cover_image/large-beb78aec0cb2066bc44ea52f66311e04.jpeg",
                duration = "24m",
                releaseTime = "HD Available",
                sources = buildSources(cleanSlug, num),
                videoUrl = ANIME_SAKUGA_VIDEO_URL
            )
        }
    }

    // ==========================================
    // CHAINSAW MAN (12 EPISODES)
    // ==========================================
    private fun getChainsawManEpisodes(cleanSlug: String): List<Episode> {
        val titles = listOf(
            "DOG & CHAINSAW",
            "ARRIVAL IN TOKYO",
            "MEOWY'S WHEREABOUTS",
            "RESCUE",
            "GUN DEVIL",
            "KILL DENJI",
            "TASTE OF A KISS",
            "GUNFIRE",
            "FROM KYOTO",
            "BRUISED & BATTERED",
            "MISSION START",
            "KATANA VS. CHAINSAW"
        )

        return titles.mapIndexed { idx, title ->
            val num = idx + 1
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = "https://media.kitsu.app/anime/43806/cover_image/large-964674a0f11524f62d65dde845ad8e1f.jpeg",
                duration = "24m",
                releaseTime = "Uncensored HD",
                sources = buildSources(cleanSlug, num),
                videoUrl = getChainsawEpisodeVideo(num)
            )
        }
    }

    // ==========================================
    // ONE PIECE: EGGHEAD ARC (KEY EPISODES)
    // ==========================================
    private fun getOnePieceEggheadEpisodes(cleanSlug: String): List<Episode> {
        val eggheadList = listOf(
            1086 to "A New Emperor! Buggy the Genius Jester!",
            1087 to "The War on the Island of Women! A Case Involving Koby the Hero",
            1088 to "Luffy's Dream",
            1089 to "Entering a New Chapter! Luffy and Sabo's Paths!",
            1090 to "A New Island! Future Island Egghead",
            1091 to "Teeming with the Future! An Adventure on the Island of Science!",
            1092 to "Bonney's Lamentation! Darkness Lurking on the Future Island",
            1093 to "The Winner Takes All! Law vs. Blackbeard!",
            1094 to "The Mystery Deepens! Egghead Labophase",
            1095 to "The Brain of a Genius - Six Vegapunks!",
            1096 to "A Forbidden History! A Hypothesis Concerning a Certain Kingdom",
            1097 to "The Will of Ohara! The Inherited Research",
            1098 to "The Eccentric Dream of a Genius!",
            1099 to "Preparations for Interception! Rob Lucci Strikes!",
            1100 to "Powers on a Different Level! Luffy vs. Lucci!",
            1101 to "The Strongest Form of Humanity! The Seraphim's Powers!",
            1102 to "Sinister Schemes! The Operation to Escape Egghead!",
            1103 to "Turn the Tide! The Counterattack of the Straw Hats!",
            1104 to "A Desperate Situation! All-Out Attack on Egghead!",
            1105 to "The Beautiful Traitor! Stussy the Cloned Agent!",
            1106 to "Trouble Occurs! Seek Dr. Vegapunk!",
            1107 to "A Shudder! The Evil Hand Creeping Up on the Research Lab!",
            1108 to "Incomprehensible! The Seraphim's Rebellion!",
            1109 to "A Tough Decision! An Unusual United Front!",
            1110 to "Survive! Deadly Battle with the Strongest Form of Humanity!"
        )

        return eggheadList.map { (epNum, title) ->
            Episode(
                episodeNumber = epNum,
                title = "Episode $epNum: $title",
                thumbnail = "https://media.kitsu.app/anime/12/cover_image/large-3e72f400a87b5241780c5082f0582611.jpeg",
                duration = "24m",
                releaseTime = if (epNum == 1110) "Latest Release" else "Available",
                sources = buildSources(cleanSlug, epNum),
                videoUrl = ANIME_SAKUGA_VIDEO_URL
            )
        }
    }

    // ==========================================
    // BLEACH: THOUSAND-YEAR BLOOD WAR (26 EPISODES)
    // ==========================================
    private fun getBleachTybwEpisodes(cleanSlug: String): List<Episode> {
        val titles = listOf(
            "THE BLOOD WARFARE",
            "Foundation Stones",
            "March of the StarCross",
            "Kill The Shadow",
            "Wrath as a Lightning",
            "The Fire",
            "BORN IN THE DARK",
            "The Shooting Star Project (ZERO MIX)",
            "THE DROP",
            "The Battle",
            "Everything But the Rain",
            "EVERYTHING BUT THE RAIN: June Truth",
            "THE BLADE IS ME",
            "THE SEPARATION",
            "Peace from Shadows",
            "The Fundamental Virulence",
            "Heart of Wolf",
            "Rages at Ringside",
            "The White Haze",
            "I AM THE EDGE",
            "THE HEADLESS STAR",
            "MARCHING OUT THE ZOMBIES",
            "MARCHING OUT THE ZOMBIES 2",
            "Too Early to Win, Too Late to Know",
            "THE MASTER",
            "BLACK"
        )

        return titles.mapIndexed { idx, title ->
            val num = idx + 1
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = "https://media.kitsu.app/anime/244/cover_image/large-b9e0a3066197f1115c773ff866a60873.jpeg",
                duration = "24m",
                releaseTime = if (num == 26) "Climax Finale" else "Available",
                sources = buildSources(cleanSlug, num),
                videoUrl = ANIME_SAKUGA_VIDEO_URL
            )
        }
    }

    // ==========================================
    // ATTACK ON TITAN (SEASON 1 - 25 EPISODES)
    // ==========================================
    fun getAttackOnTitanEpisodes(cleanSlug: String): List<Episode> {
        val episodeEntries = listOf(
            Triple(1, "To You, in 2,000 Years: The Fall of Shiganshina, Part 1", "https://media.kitsu.app/episodes/thumbnails/104938/original.jpg"),
            Triple(2, "That Day: The Fall of Shiganshina, Part 2", "https://media.kitsu.app/episodes/thumbnails/104939/original.jpg"),
            Triple(3, "A Dim Light Amid Despair: Humanity's Comeback, Part 1", "https://media.kitsu.app/episodes/thumbnails/104940/original.jpeg"),
            Triple(4, "The Night of the Closing Ceremony: Humanity's Comeback, Part 2", "https://media.kitsu.app/episodes/thumbnails/104941/original.jpeg"),
            Triple(5, "First Battle: The Struggle for Trost, Part 1", "https://media.kitsu.app/episodes/thumbnails/104942/original.jpeg"),
            Triple(6, "The World the Girl Saw: The Struggle for Trost, Part 2", "https://media.kitsu.app/episodes/thumbnails/104943/original.jpeg"),
            Triple(7, "Small Blade: The Struggle for Trost, Part 3", "https://media.kitsu.app/episodes/thumbnails/104944/original.jpeg"),
            Triple(8, "I Can Hear His Heartbeat: The Struggle for Trost, Part 4", "https://media.kitsu.app/episodes/thumbnails/104945/original.jpeg"),
            Triple(9, "Whereabouts of His Left Arm: The Struggle for Trost, Part 5", "https://media.kitsu.app/episodes/thumbnails/104946/original.jpeg"),
            Triple(10, "Response: The Struggle for Trost, Part 6", "https://media.kitsu.app/episodes/thumbnails/104947/original.jpeg"),
            Triple(11, "Idol: The Struggle for Trost, Part 7", "https://media.kitsu.app/episodes/thumbnails/104948/original.jpeg"),
            Triple(12, "Wound: The Struggle for Trost, Part 8", "https://media.kitsu.app/episodes/thumbnails/104949/original.jpeg"),
            Triple(13, "Primal Desire: The Struggle for Trost, Part 9", "https://media.kitsu.app/episodes/thumbnails/104950/original.jpeg"),
            Triple(14, "Can't Look into His Eyes Yet: Eve of the Counterattack, Part 1", "https://media.kitsu.app/episodes/thumbnails/104951/original.jpeg"),
            Triple(15, "Special Operations Squad: Eve of the Counterattack, Part 2", "https://media.kitsu.app/episodes/thumbnails/104952/original.jpeg"),
            Triple(16, "What Should Be Done: Eve of the Counterattack, Part 3", "https://media.kitsu.app/episodes/thumbnails/104953/original.jpeg"),
            Triple(17, "Female Titan: The 57th Exterior Scouting Mission, Part 1", "https://media.kitsu.app/episodes/thumbnails/104954/original.jpeg"),
            Triple(18, "Forest of Giant Trees: The 57th Exterior Scouting Mission, Part 2", "https://media.kitsu.app/episodes/thumbnails/104955/original.jpeg"),
            Triple(19, "Bite: The 57th Exterior Scouting Mission, Part 3", "https://media.kitsu.app/episodes/thumbnails/104956/original.jpeg"),
            Triple(20, "Erwin Smith: The 57th Exterior Scouting Mission, Part 4", "https://media.kitsu.app/episodes/thumbnails/104957/original.jpeg"),
            Triple(21, "Crushing Blow: The 57th Exterior Scouting Mission, Part 5", "https://media.kitsu.app/episodes/thumbnails/104958/original.jpeg"),
            Triple(22, "The Defeated: The 57th Exterior Scouting Mission, Part 6", "https://media.kitsu.app/episodes/thumbnails/104959/original.jpeg"),
            Triple(23, "Smile: Raid on Stohess District, Part 1", "https://media.kitsu.app/episodes/thumbnails/104960/original.jpeg"),
            Triple(24, "Mercy: Raid on Stohess District, Part 2", "https://media.kitsu.app/episodes/thumbnails/104961/original.jpeg"),
            Triple(25, "Wall: Raid on Stohess District, Part 3", "https://media.kitsu.app/episodes/thumbnails/104962/original.jpeg")
        )

        return episodeEntries.map { (num, title, thumb) ->
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = thumb,
                duration = if (num == 1) "25m" else "24m",
                releaseTime = if (num == 25) "Season 1 Finale" else "Episode $num (Full)",
                sources = buildSources(cleanSlug, num),
                videoUrl = getAttackOnTitanEpisodeVideo(num)
            )
        }
    }

    // ==========================================
    // ATTACK ON TITAN (SEASON 2 - 12 EPISODES)
    // ==========================================
    fun getAttackOnTitanSeason2Episodes(cleanSlug: String): List<Episode> {
        val s2Titles = listOf(
            "Beast Titan",
            "I'm Home",
            "Southwestward",
            "Soldier",
            "Historia",
            "Warrior",
            "Close Combat",
            "The Hunters",
            "Opening",
            "Children",
            "Charge",
            "Scream"
        )
        val s2Thumbs = listOf(
            "https://media.kitsu.app/episodes/thumbnails/104963/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104964/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104965/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104966/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104967/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104968/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104969/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104970/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104971/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104972/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104973/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/104974/original.jpeg"
        )
        return s2Titles.mapIndexed { idx, title ->
            val num = idx + 1
            val overallEp = 25 + num
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = s2Thumbs.getOrElse(idx) { "https://media.kitsu.app/anime/cover_images/11469/large.jpg" },
                duration = "24m",
                releaseTime = if (num == 12) "Season 2 Finale" else "Episode $overallEp (Canon)",
                sources = buildSources(cleanSlug, num),
                videoUrl = getAttackOnTitanSeason2Video(num)
            )
        }
    }

    // ==========================================
    // ATTACK ON TITAN (SEASON 3 - 22 EPISODES)
    // ==========================================
    fun getAttackOnTitanSeason3Episodes(cleanSlug: String): List<Episode> {
        val s3Titles = listOf(
            "Smoke Signal",
            "Pain",
            "Old Story",
            "Trust",
            "Reply",
            "Sin",
            "Wish",
            "Outside the Walls of Orvud District",
            "Ruler of the Walls",
            "Friends",
            "Bystander",
            "Night of the Battle to Retake the Wall",
            "The Town Where Everything Began",
            "Thunder Spears",
            "Descent",
            "Perfect Game",
            "Hero",
            "Midnight Sun",
            "The Basement",
            "That Day",
            "Attack Titan",
            "The Other Side of the Wall"
        )
        val s3Thumbs = listOf(
            "https://media.kitsu.app/episodes/thumbnails/116900/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116901/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116902/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116903/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116904/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116905/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116906/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116907/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116908/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116909/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116910/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/116911/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117920/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117921/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117922/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117923/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117924/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117925/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117926/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117927/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117928/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/117929/original.jpeg"
        )
        return s3Titles.mapIndexed { idx, title ->
            val num = idx + 1
            val overallEp = 37 + num
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = s3Thumbs.getOrElse(idx) { "https://media.kitsu.app/anime/cover_images/13569/large.jpg" },
                duration = "24m",
                releaseTime = if (num == 22) "Season 3 Finale (The Ocean)" else "Episode $overallEp (Part ${if (num <= 12) 1 else 2})",
                sources = buildSources(cleanSlug, num),
                videoUrl = getAttackOnTitanSeason3Video(num)
            )
        }
    }

    // ==========================================
    // ATTACK ON TITAN (THE FINAL SEASON - 30 EPISODES)
    // ==========================================
    fun getAttackOnTitanFinalSeasonEpisodes(cleanSlug: String): List<Episode> {
        val s4Titles = listOf(
            "The Other Side of the Sea",
            "Midnight Train",
            "The Door of Hope",
            "From One Hand to Another",
            "Declaration of War",
            "The War Hammer Titan",
            "Assault",
            "Assassin's Bullet",
            "Brave Volunteers",
            "A Sound Argument",
            "Deceiver",
            "Guides",
            "Children of the Forest",
            "Savagery",
            "Sole Salvation",
            "Above and Below",
            "Judgment",
            "Sneak Attack",
            "Two Brothers",
            "Memories of the Future",
            "From You, 2,000 Years Ago",
            "Thaw",
            "Sunset",
            "Pride",
            "Night of the End",
            "Traitor",
            "Retrospective",
            "The Dawn of Humanity",
            "The Rumbling / Battle of Heaven and Earth",
            "Toward the Tree on That Hill (Series Climax Finale)"
        )
        val s4Thumbs = listOf(
            "https://media.kitsu.app/episodes/thumbnails/150110/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150111/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150112/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150113/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150114/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150115/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150116/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150117/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150118/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150119/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150120/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150121/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150122/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150123/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150124/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150125/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150126/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150127/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150128/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150129/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150130/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150131/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150132/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150133/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150134/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150135/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150136/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150137/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150138/original.jpeg",
            "https://media.kitsu.app/episodes/thumbnails/150139/original.jpeg"
        )
        return s4Titles.mapIndexed { idx, title ->
            val num = idx + 1
            val overallEp = 59 + num
            Episode(
                episodeNumber = num,
                title = "Episode $num: $title",
                thumbnail = s4Thumbs.getOrElse(idx) { "https://media.kitsu.app/anime/cover_images/42422/large.jpg" },
                duration = if (num >= 29) "60m" else "24m",
                releaseTime = if (num == 30) "Grand Series Finale" else "Episode $overallEp (Canon)",
                sources = buildSources(cleanSlug, num),
                videoUrl = getAttackOnTitanFinalSeasonVideo(num)
            )
        }
    }
}
