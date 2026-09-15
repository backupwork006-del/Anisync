package com.example.ui.components

data class SubtitleEntry(
    val startSec: Int,
    val endSec: Int,
    val textEn: String,
    val textJa: String? = null
)

object AnimeSubtitlesProvider {

    fun getSubtitlesFor(animeIdOrTitle: String, episodeNumber: Int): List<SubtitleEntry> {
        val lower = animeIdOrTitle.lowercase()
        return when {
            lower.contains("titan") || lower.contains("kyojin") || lower.contains("aot") -> {
                if (lower.contains("season-2") || lower.contains("season 2")) {
                    getAttackOnTitanSeason2Subtitles(episodeNumber)
                } else if (lower.contains("season-3") || lower.contains("season 3")) {
                    getAttackOnTitanSeason3Subtitles(episodeNumber)
                } else if (lower.contains("final") || lower.contains("season-4") || lower.contains("season 4")) {
                    getAttackOnTitanFinalSeasonSubtitles(episodeNumber)
                } else {
                    getAttackOnTitanSubtitles(episodeNumber)
                }
            }
            lower.contains("jujutsu") || lower.contains("kaisen") -> getJujutsuKaisenSubtitles(episodeNumber)
            lower.contains("solo leveling") -> getSoloLevelingSubtitles(episodeNumber)
            lower.contains("frieren") -> getFrierenSubtitles(episodeNumber)
            lower.contains("demon slayer") || lower.contains("kimetsu") -> getDemonSlayerSubtitles(episodeNumber)
            lower.contains("kaiju") -> getKaijuSubtitles(episodeNumber)
            lower.contains("chainsaw") -> getChainsawManSubtitles(episodeNumber)
            lower.contains("one piece") -> getOnePieceSubtitles(episodeNumber)
            lower.contains("bleach") -> getBleachSubtitles(episodeNumber)
            else -> getGenericAnimeSubtitles(episodeNumber)
        }
    }

    private fun getAttackOnTitanSubtitles(ep: Int): List<SubtitleEntry> = when (ep) {
        1 -> listOf(
            SubtitleEntry(0, 8, "Year 845, Shiganshina District: On that day, mankind received a grim reminder...", "845年、シガンシナ区――その日、人類は思い出した..."),
            SubtitleEntry(9, 18, "We lived in fear of the Titans, and were disgraced to live in these cages we called walls.", "奴らに支配されていた恐怖を。鳥籠の中に囚われていた屈辱を。"),
            SubtitleEntry(19, 30, "[Eren] 'I want to see the outside world! If nobody goes out, who will fight?'", "[エレン] オレは外の世界を見たい！ 誰も外に行かなかったら、誰が戦うんだ！"),
            SubtitleEntry(31, 45, "[Hannes] 'Peace inside the walls is guaranteed by the 50-meter Wall Maria!'", "[ハンネス] 50メートルの壁がある限り、平和は安泰さ！"),
            SubtitleEntry(46, 58, "Thunder strikes outside the gate... A colossal red hand grips the wall!", "突如、壁の向こうに轟音と雷光... 超巨大な赤い手が壁の縁を掴む！"),
            SubtitleEntry(59, 75, "[Armin] 'That wall is 50 meters tall... It's a Titan taller than the wall!!'", "[アルミン] あの壁は50メートルあるんだぞ... 壁よりデカい巨人だ！！")
        )
        2 -> listOf(
            SubtitleEntry(0, 8, "The Fall of Shiganshina Part 2: Titans flood through the destroyed outer gate...", "シガンシナ陥落 その2――破壊された門から巨人が雪崩れ込む..."),
            SubtitleEntry(9, 20, "[Eren] 'Stop! Carla! Get out from under the roof, please hurry!'", "[エレン] やめろ！母さん！早く瓦礫から出てくれ！"),
            SubtitleEntry(21, 34, "[Carla Yeager] 'Hannes-san! Take the children and run! Live, Eren! Mikasa!'", "[カルラ] ハンネスさん！子供たちを連れて逃げて！生きるのよ、エレン！ミカサ！"),
            SubtitleEntry(35, 50, "[Hannes] 'Eren, you couldn't save your mother because you lacked strength. But I couldn't save her because I lacked courage.'", "[ハンネス] エレン、お前が母さんを救えなかったのは力がなかったからだ。オレは勇気がなかったからだ。"),
            SubtitleEntry(51, 68, "An Armored Titan charges at blinding speed, smashing through Wall Maria's inner gate!", "鎧の巨人が猛スピードで突進し、ウォール・マリアの内門を粉砕する！"),
            SubtitleEntry(69, 85, "[Eren] 'I'll exterminate them! Every single one from this earth... until none are left!'", "[エレン] 駆逐してやる！この世から...一匹残らず！")
        )
        3 -> listOf(
            SubtitleEntry(0, 8, "Year 848: The 104th Training Corps begins under Keith Shadis...", "848年、第104期訓練兵団の過酷な選別が始まる..."),
            SubtitleEntry(9, 20, "[Keith Shadis] 'Who are you, maggot?! What did you come here for?!'", "[キース・シャーディス] 貴様は何者だ！何をしにここへ来た！"),
            SubtitleEntry(21, 32, "[Sasha Braus] 'I... I smelled a steaming potato and took it from the kitchen, sir.'", "[サシャ] 蒸かした芋があまりにも美味しそうだったので...半分どうぞ。"),
            SubtitleEntry(33, 48, "[Eren] 'Why can't I maintain balance on the vertical maneuvering cables?!'", "[エレン] なんでバランスが取れないんだ！オレには素質がないのか？！"),
            SubtitleEntry(49, 65, "[Keith Shadis] 'Change his belt clasp. The equipment was defective from the start.'", "[キース] ベルトの金具を交換しろ。機材が不良品だっただけだ。"),
            SubtitleEntry(66, 80, "[Eren] 'I did it! I can balance now! Look at me, Mikasa!'", "[エレン] できた！浮いたぞ！見たか、ミカサ！")
        )
        4 -> listOf(
            SubtitleEntry(0, 8, "Graduation of the 104th Cadet Corps: Five years after the fall of Wall Maria...", "第104期訓練兵団卒業――ウォール・マリア陥落から5年..."),
            SubtitleEntry(9, 22, "[Cadet Ranks] 'Top 10 Graduates: Mikasa, Reiner, Bertholdt, Annie, Eren, Jean, Connie, Sasha, Krista, Marco.'", "[首席〜上位] ミカサ、ライナー、ベルトルト、アニ、エレン、ジャン、コニー、サシャ、クリスタ、マルコ。"),
            SubtitleEntry(23, 36, "[Jean Kirstein] 'Everyone with brains chooses the Interior Military Police for comfort!'", "[ジャン] 頭のいい奴は内地の憲兵団に行って快適に暮らすのさ！"),
            SubtitleEntry(37, 50, "[Eren] 'I'm joining the Scout Regiment to reclaim the territory mankind lost.'", "[エレン] オレは調査兵団に入って、奪われた人類の領土を取り戻す。"),
            SubtitleEntry(51, 65, "Trost District Wall: Eren and squad clean cannons along the ramparts...", "トロスト区壁上――大砲の手入れをするエレンたち..."),
            SubtitleEntry(66, 80, "Yellow lightning cracks! The Colossal Titan looms face to face with Eren!", "黄金の雷撃！超大型巨人が5年ぶりにエレンの目の前に出現する！")
        )
        5 -> listOf(
            SubtitleEntry(0, 8, "The Defense of Trost District: Wall Rose outer gate smashed open!", "トロスト区攻防戦――ウォール・ローゼの扉が破られる！"),
            SubtitleEntry(9, 22, "[Eren] 'Target the Titan's nape! It's right in front of us!'", "[エレン] 狙うはうなじだ！目の前にいるぞ！"),
            SubtitleEntry(23, 35, "34th Cadet Squad deployed: Abnormal Titan ambush in the city ruins!", "第34班出撃――市街地で奇行種の急襲を受ける！"),
            SubtitleEntry(36, 50, "[Armin] 'Thomas! No! The abnormal Titan swallowed him whole!'", "[アルミン] トーマス！嘘だろ！奇行種に丸呑みされた！"),
            SubtitleEntry(51, 65, "Eren's leg severed by a Titan's bite! Armin frozen in terror inside a Titan's jaws!", "エレンの左足が巨人に食いちぎられる！茫然自失のアルミンが巨人の口の中へ！"),
            SubtitleEntry(66, 82, "[Eren] 'Armin! Grab my hand! We swore to see the sea together!'", "[エレン] アルミン！手を取れ！オレたちは一緒に海を見るんだろ！！")
        )
        6 -> listOf(
            SubtitleEntry(0, 8, "Mikasa Ackerman's childhood memories awaken amidst the despair of Trost...", "ミカサ・アッカーマンの回想――残酷で美しい世界の記憶..."),
            SubtitleEntry(9, 22, "[Mikasa] 'My father and mother were murdered right before my eyes by human traffickers.'", "[ミカサ] お父さんとお母さんは、目の前で人買いに殺された。"),
            SubtitleEntry(23, 38, "[Eren] 'Fight! If you win, you live! If you don't fight, you cannot win!'", "[エレン] 戦え！戦わなければ勝てない！勝てば生きる！"),
            SubtitleEntry(39, 54, "Mikasa grasps the knife handle... Her awakened power shatters the floorboard!", "ナイフの柄を強く握るミカサ...覚醒した力が床板を踏み抜く！"),
            SubtitleEntry(55, 70, "[Mikasa] 'From that moment on, I had absolute control over my body and mind.'", "[ミカサ] その瞬間から、私は自分の体を完璧に支配できた。"),
            SubtitleEntry(71, 85, "[Mikasa] 'Eren wrapped his red scarf around me... It was so warm.'", "[ミカサ] エレンが赤いマフラーを巻いてくれた...とても温かかった。")
        )
        7 -> listOf(
            SubtitleEntry(0, 8, "Armin informs Mikasa that the 34th Squad was wiped out...", "アルミンはミカサに第34班の全滅を涙ながらに告げる..."),
            SubtitleEntry(9, 22, "[Mikasa] 'I am strong. Stronger than any of you. Even alone, I will fight.'", "[ミカサ] 私は強い。あなた達より強い。一人でも巨人を倒せる。"),
            SubtitleEntry(23, 36, "Mikasa runs out of compressed gas and falls into an abandoned alley...", "ガス欠となり人気のない路地裏へ墜落するミカサ..."),
            SubtitleEntry(37, 52, "A 15-meter Titan approaches her... but another mysterious Rogue Titan suddenly roars!", "迫り来る15メートル級の巨人...そこへ漆黒の髪を持つ巨人が猛然と乱入する！"),
            SubtitleEntry(53, 70, "The Rogue Titan brutally punches other Titans, displaying martial arts mastery!", "他の巨人を殴り殺し、格闘技の構えで屠る謎の巨人！"),
            SubtitleEntry(71, 85, "[Armin] 'A Titan... that exclusively attacks other Titans?!'", "[アルミン] 巨人が...巨人を殺しているのか？！")
        )
        8 -> listOf(
            SubtitleEntry(0, 8, "Trapped cadets at the Military HQ Supply Depot formulate a rescue plan...", "補給所に孤立した訓練兵たち、命がけの補給奪還作戦..."),
            SubtitleEntry(9, 24, "[Armin] 'We will blind the seven 4-meter Titans simultaneously, then strike napes!'", "[アルミン] 散弾銃で7体の視界を同時に奪い、背後からうなじを削ぐ！"),
            SubtitleEntry(25, 40, "Sasha and Connie panic, but Mikasa and Annie execute flawless coordinated slashes!", "サシャが仕留め損ねるも、ミカサとアニが瞬時に救援！作戦成功！"),
            SubtitleEntry(41, 56, "Outside, the mysterious Rogue Titan collapses after annihilating 20 Titans...", "外では、20体以上の巨人を殲滅した謎の巨人が力尽きて倒れる..."),
            SubtitleEntry(57, 72, "Steam billows from the nape... Eren Yeager emerges alive with healed limbs!", "うなじの肉が裂け、蒸気の中から手足の再生したエレンが現れる！"),
            SubtitleEntry(73, 88, "[Mikasa] 'Eren! His heartbeat is strong... He is truly alive!'", "[ミカサ] エレン！心臓の音が聞こえる...本当に生きている！")
        )
        9 -> listOf(
            SubtitleEntry(0, 8, "Garrison Commander Kitz Woermann surrounds Eren, Mikasa, and Armin with cannons...", "キッツ・ヴェールマン率いる駐屯兵団守備隊がエレンたちを包囲..."),
            SubtitleEntry(9, 24, "[Kitz] 'Cadet Yeager! Are you human, or a monster wearing human flesh?!'", "[キッツ] 訓練兵エレン・イェーガー！貴様は人間か、怪物か？！"),
            SubtitleEntry(25, 40, "[Eren] 'I don't understand what happened to me! But I am human!'", "[エレン] オレにも何が起きたかわからない！だがオレは人間です！"),
            SubtitleEntry(41, 56, "[Kitz] 'Cannons, fire!! Exterminate the demon!'", "[キッツ] 砲兵隊、撃てーッ！！悪魔を消し去れ！"),
            SubtitleEntry(57, 72, "Eren bites his hand! A massive bone ribcage and muscle shield blocks the cannonball!", "エレンが自分の手を噛む！巨大な骨格と肉の盾が砲弾を弾き飛ばす！"),
            SubtitleEntry(73, 88, "[Eren] 'In the basement of my home in Shiganshina... Dad said the truth is hidden there.'", "[エレン] シガンシナの生家の地下室...父さんはそこに世界の秘密があると言っていた。")
        )
        10 -> listOf(
            SubtitleEntry(0, 8, "Armin steps forward alone to negotiate with the Garrison Regiment...", "アルミンはエレンの巨人の力を人類のために使うべく一人進み出る..."),
            SubtitleEntry(9, 25, "[Armin] 'I am a soldier sworn to devote my heart to the recovery of human territory!'", "[アルミン] 私は人類の復興に心臓を捧げると誓った兵士です！"),
            SubtitleEntry(26, 42, "[Armin] 'If we combine Eren's Titan abilities with the Garrison's soldiers, reclaiming Trost is possible!'", "[アルミン] 彼の巨人の力と駐屯兵団の力を合わせれば、トロスト区奪還も可能です！"),
            SubtitleEntry(43, 58, "Commander Dot Pixis steps in: 'At ease, soldier. Your salute was magnificent.'", "[ドット・ピクシス] これ以上は無用じゃ。見事な敬礼であったぞ、兵士よ。"),
            SubtitleEntry(59, 74, "[Pixis] 'Cadet Yeager, can you carry that giant boulder and plug the hole in Wall Rose?'", "[ピクシス] イェーガー訓練兵、あの巨大な岩を運び、壁の穴を塞ぐことができるか？"),
            SubtitleEntry(75, 90, "[Eren] 'I don't know if I can... but I will do it. I will plug the hole!'", "[エレン] できるかどうかはわかりません...でも、やります！穴を塞ぎます！")
        )
        11 -> listOf(
            SubtitleEntry(0, 8, "Operation to Reclaim Trost District commences under Commander Pixis...", "ピクシス総司令指揮のもと、トロスト区奪還作戦が発動..."),
            SubtitleEntry(9, 24, "[Pixis] 'Do not let humanity die within these walls like cattle!'", "[ピクシス] 人類が家畜のように壁の中で滅びることを許すな！"),
            SubtitleEntry(25, 42, "Elite Garrison Squad assembled: Ian Dietrich, Rico Brzenska, Mitabi Jarnach.", "精鋭護衛部隊集結――イアン、リコ、ミタビ各班長。"),
            SubtitleEntry(43, 58, "Eren sprints toward the giant boulder alongside Mikasa and the elite guard.", "巨岩を目指して全力疾走するエレンとミカサ、精鋭部隊。"),
            SubtitleEntry(59, 75, "Eren bites his thumb! Green lightning erupts as he transforms into the Attack Titan!", "エレンが親指を噛み切る！緑の雷光とともに進撃の巨人へと変身！"),
            SubtitleEntry(76, 90, "Titan Eren roars... but turns around and swings a crushing fist toward Mikasa!", "咆哮を上げる巨人エレン...だが突如振り返り、ミカサに向けて拳を振り下ろす！")
        )
        12 -> listOf(
            SubtitleEntry(0, 8, "Rico fires the red flare: 'Operation has failed! The Titan is out of control!'", "[リコ] 赤い信煙弾を発射！作戦失敗！巨人が暴走した！"),
            SubtitleEntry(9, 24, "[Ian Dietrich] 'Wait! We cannot abandon Yeager! Protect him to the last soldier!'", "[イアン] 待て！イェーガーを見捨てるわけにはいかん！彼を守り抜け！"),
            SubtitleEntry(25, 42, "Titan Eren slumps against the boulder, trapped inside a hallucinatory dream of home...", "巨岩に凭れかかり、温かい生家の幻覚夢に閉じ込められるエレン..."),
            SubtitleEntry(43, 58, "[Armin] 'Eren! Wake up! The Titans are closing in on our comrades!'", "[アルミン] エレン！起きろ！仲間たちが巨人に食われているんだぞ！"),
            SubtitleEntry(59, 74, "Armin drives his blade deep into the Titan's nape, piercing Eren's shoulder!", "アルミンが巨人のうなじに刃を突き立て、エレンの肩を貫く！"),
            SubtitleEntry(75, 90, "[Armin] 'Eren, why do you want to go beyond the walls?! Tell me!'", "[アルミン] エレン、どうしてお前は壁の外に行きたいんだ？！教えてくれ！")
        )
        13 -> listOf(
            SubtitleEntry(0, 8, "Eren opens his eyes inside the Titan's fiery nape...", "エレンが燃える巨人のうなじの中で目覚める..."),
            SubtitleEntry(9, 25, "[Eren] 'Because I was born into this world!!'", "[エレン] オレが、この世に生まれたからだ！！"),
            SubtitleEntry(26, 42, "Titan Eren lifts the colossal boulder onto his shoulders, veins bulging with steam!", "巨岩を両肩に担ぎ上げる巨人エレン、蒸気が凄まじく噴き上がる！"),
            SubtitleEntry(43, 60, "[Ian] 'Protect the Titan! Sacrifice your lives so he can reach the gate!'", "[イアン] 巨人を死守せよ！命を賭して門まで護衛しろ！"),
            SubtitleEntry(61, 78, "With a colossal roar, Eren slams the boulder into the breach, sealing Wall Rose!", "咆哮とともに巨岩を穴へ叩き込み、ウォール・ローゼの扉を完全に塞ぐ！"),
            SubtitleEntry(79, 95, "[Rico] 'Humanity... has won its very first victory against the Titans!!'", "[リコ] 人類が...初めて巨人に勝ったぞ！！")
        )
        14 -> listOf(
            SubtitleEntry(0, 8, "Special Military Tribunal: Premier Darius Zackly presides over Eren's fate...", "特別軍事裁判開廷――総統ダリス・ザックレーがエレンの処遇を決める..."),
            SubtitleEntry(9, 24, "[Nile Dawk] 'The Military Police demands Yeager's immediate dissection and execution.'", "[ナイル・ドーク] 憲兵団はイェーガーの即時解剖と処分を要求する。"),
            SubtitleEntry(25, 42, "[Erwin Smith] 'The Scout Regiment requests custody of Eren to launch a mission to retake Wall Maria.'", "[エルヴィン] 調査兵団はエレンを預かり、ウォール・マリア奪還の遠征を提案します。"),
            SubtitleEntry(43, 60, "[Eren] 'You cowards! If you won't fight, just shut up and invest everything in me!'", "[エレン] 臆病者どもめ！戦えないなら黙ってオレに全部投資しろ！"),
            SubtitleEntry(61, 78, "Captain Levi steps forward and brutally kicks Eren, shattering his tooth to prove control.", "リヴァイ兵長が前に出てエレンを激しく蹴り飛ばし、制圧力を証明する。"),
            SubtitleEntry(79, 95, "[Levi] 'This is my personal opinion: Pain is the most effective tool of discipline.'", "[リヴァイ] これは持論だが、躾に一番効くのは痛みだと思う。")
        )
        15 -> listOf(
            SubtitleEntry(0, 8, "Former Scout Regiment Headquarters: An ancient castle in the wilderness...", "旧調査兵団本部――荒野に佇む古城へ配属されるエレン..."),
            SubtitleEntry(9, 22, "[Levi] 'First task: Clean every speck of dust from the upper floors to the cellar.'", "[リヴァイ] まずは掃除だ。上階から地下室まで埃一つ残すな。"),
            SubtitleEntry(23, 40, "Special Operations Squad (Levi Squad): Eld Jinn, Oluo Bozado, Petra Ral, Gunther Schultz.", "特別作戦班（リヴァイ班）――エルド、オルオ、ペトラ、グンタ。"),
            SubtitleEntry(41, 58, "[Hange Zoe] 'Eren! Let me tell you about the captured Titans Sawney and Beane!'", "[ハンジ・ゾエ] エレン！捕獲した巨人ソニーとビーンについて語り合おう！"),
            SubtitleEntry(59, 75, "Hange talks through the entire night about Titan physiology and lack of digestive organs.", "巨人の生態と消化器官の欠如について夜通し熱弁を振るうハンジ。"),
            SubtitleEntry(76, 92, "Dawn arrives: News breaks that Sawney and Beane were assassinated by an unknown traitor!", "夜明け、捕獲されていた2体の巨人が何者かによって暗殺された！")
        )
        16 -> listOf(
            SubtitleEntry(0, 8, "Commander Erwin delivers a harrowing speech to the remaining 104th recruits...", "エルヴィン団長が第104期兵たちに過酷な調査兵団の実態を語る..."),
            SubtitleEntry(9, 25, "[Erwin] 'In the past 4 years, over 60% of our Scouts have died on missions outside the walls.'", "[エルヴィン] 過去4年で6割以上の兵士が壁外調査で死亡した。"),
            SubtitleEntry(26, 42, "[Erwin] 'Those willing to dedicate their hearts despite knowing this, remain!'", "[エルヴィン] それでも心臓を捧げられる者だけ、ここに残れ！"),
            SubtitleEntry(43, 60, "Many cadets weep and leave, but Jean, Connie, Sasha, Krista, Ymir, and Reiner stand firm.", "多くの兵が去る中、ジャン、コニー、サシャ、クリスタ、ユミル、ライナーが残る。"),
            SubtitleEntry(61, 78, "[Jean] 'I don't want to die with ashes and regrets like Marco did.'", "[ジャン] マルコの死んだ理由もわからないまま、悔いを残したくない。"),
            SubtitleEntry(79, 95, "[Erwin] 'You have brave faces. I welcome you all to the Scout Regiment!'", "[エルヴィン] いい面構えだ。諸君らを調査兵団に心から歓迎しよう！")
        )
        17 -> listOf(
            SubtitleEntry(0, 8, "The 57th Exterior Scouting Mission departs through the Karanes District gate...", "第57回壁外調査――カラネス区の門から大部隊が出撃する..."),
            SubtitleEntry(9, 24, "Erwin's Long-Range Scouting Formation unfolds across the open plains using smoke flares.", "煙弾を用いたエルヴィン考案の長距離索敵陣形が荒野に展開。"),
            SubtitleEntry(25, 42, "Armin's squad on the right wing encounters an agile 14-meter Female Titan!", "右翼側のアルミン班が、驚異的な俊敏さを持つ女型の巨人と遭遇！"),
            SubtitleEntry(43, 60, "[Armin] 'She's not an abnormal... She's an intelligent human Titan, just like Eren!'", "[アルミン] こいつは奇行種じゃない...エレンと同じ、知性を持った人間だ！"),
            SubtitleEntry(61, 78, "Female Titan spares Armin after inspecting his face under his hood.", "フードを剥ぎ、アルミンの顔を確認した女型の巨人は殺さずに去る。"),
            SubtitleEntry(79, 95, "[Armin] 'She's targeting Eren in the central rear squad!'", "[アルミン] あいつの狙いは中央後方にいるエレンだ！")
        )
        18 -> listOf(
            SubtitleEntry(0, 8, "The expedition is led into the massive Forest of Giant Trees...", "索敵部隊は突如、巨木が立ち並ぶ巨大樹の森へと誘導される..."),
            SubtitleEntry(9, 24, "[Cadets] 'Why are we entering the forest where wagon visibility is lost?!'", "[兵士たち] なぜ馬車が通れない森に入るんだ？！"),
            SubtitleEntry(25, 42, "Female Titan charges behind Levi Squad, mercilessly swatting Scout interceptors!", "女型の巨人がリヴァイ班の後方に迫り、迎撃する調査兵を薙ぎ払う！"),
            SubtitleEntry(43, 60, "[Eren] 'Captain! Let me transform! I can kill her right now!'", "[エレン] 兵長！オレに変身させてください！今なら倒せます！"),
            SubtitleEntry(61, 78, "[Levi] 'Choose for yourself, Eren. Whether you trust yourself, or trust your comrades.'", "[リヴァイ] 選べ、エレン。自分を信じるか、仲間を信じるか。"),
            SubtitleEntry(79, 95, "[Petra] 'Eren, please believe in us!'", "[ペトラ] エレン、私たちを信じて！")
        )
        19 -> listOf(
            SubtitleEntry(0, 8, "Eren holds back his bite and chooses to trust the Levi Squad...", "エレンは噛むのを止め、リヴァイ班の仲間を信じる決断を下す..."),
            SubtitleEntry(9, 24, "Levi fires an acoustic bullet straight ahead toward the canopy clearing!", "リヴァイが前方へ音響弾を発射！森の開けた場所へ女型の巨人を誘導！"),
            SubtitleEntry(25, 42, "Commander Erwin's hidden Special Target Restraint Weapon triggers dozens of steel harpoons!", "エルヴィンが仕掛けた拘束兵器から無数の鉄線ハープーンが一斉に発射される！"),
            SubtitleEntry(43, 60, "The Female Titan is completely pinned down under hundreds of heavy wire cables!", "女型の巨人は無数のワイヤーによって完全に身動きを封じられる！"),
            SubtitleEntry(61, 78, "[Levi] 'Good work, everyone. Our primary objective is achieved.'", "[リヴァイ] よくやった。これで作戦目的は達成だ。"),
            SubtitleEntry(79, 95, "[Eren] 'The Commander planned this entire trap without telling ordinary soldiers!'", "[エレン] 団長は一般兵にすら作戦を隠してこの罠を張っていたのか！")
        )
        20 -> listOf(
            SubtitleEntry(0, 8, "Erwin and Levi stand atop the Female Titan's head, attempting to extract the shifter...", "エルヴィンとリヴァイが頭上に降り立ち、うなじの本体を暴こうとする..."),
            SubtitleEntry(9, 24, "The Female Titan hardens her diamond-crystal skin to protect her nape from blades!", "女型の巨人はうなじの皮膚を結晶化させて刃の侵入を阻む！"),
            SubtitleEntry(25, 42, "[Levi] 'Hey, are you enjoying yourself in there? We'll peel you out eventually.'", "[リヴァイ] おい、中で楽しんでるか？ どうせ引きずり出してやるがな。"),
            SubtitleEntry(43, 60, "The Female Titan lets out a deafening high-pitch scream across the entire forest!", "女型の巨人が森中に響き渡る凄まじい絶叫を上げる！"),
            SubtitleEntry(61, 78, "Pure Titans from all directions rush in ignoring humans, swarming and devouring her body!", "四方八方から巨人が群がり、人間を無視して女型の肉体を喰らい尽くす！"),
            SubtitleEntry(79, 95, "[Erwin] 'She summoned them to destroy the evidence! Retreat to horses!'", "[エルヴィン] 証拠隠滅のために巨人を呼んだのか！全員、馬へ戻れ！")
        )
        21 -> listOf(
            SubtitleEntry(0, 8, "A mysterious figure in Scout cloak ambushes Gunther Schultz in the forest...", "森の中、調査兵のマントを羽織った謎の人物がグンタを奇襲殺害..."),
            SubtitleEntry(9, 24, "Lightning strikes! The Female Titan regenerates and reappears in pursuit!", "雷光！女型の巨人が再び姿を現し、リヴァイ班を猛追する！"),
            SubtitleEntry(25, 42, "Eld, Oluo, and Petra fight valiantly, blinding her eyes and severing arm muscles!", "エルド、オルオ、ペトラが果敢に攻め、両目を奪い腕の筋肉を削ぐ！"),
            SubtitleEntry(43, 60, "Female Titan accelerates one-eye regeneration... Eld is bitten in half, Petra crushed against a tree!", "片目の再生を優先させた女型が反撃...エルドが噛み砕かれ、ペトラが無惨に散る！"),
            SubtitleEntry(61, 78, "Seeing his fallen comrades, Eren roars in grief and transforms into the Attack Titan!", "仲間の死を目の当たりにしたエレンが慟哭とともに巨人化！激突する！"),
            SubtitleEntry(79, 95, "Female Titan's hardened roundhouse kick decapitates Eren's Titan, capturing his human body!", "硬質化したハイキックがエレンの巨人の首を刎ね、本体を口内に咥えて逃走！")
        )
        22 -> listOf(
            SubtitleEntry(0, 8, "Mikasa pursues the Female Titan in wild vengeance...", "復讐に燃えるミカサが単身、女型の巨人を追撃する..."),
            SubtitleEntry(9, 24, "Captain Levi arrives: 'Ackerman, maintain distance! We only need to rescue Eren!'", "[リヴァイ] アッカーマン、距離を保て！目的はエレンの奪還だけだ！"),
            SubtitleEntry(25, 42, "Levi unleashes his whirlwind blade dance, slicing the Female Titan's tendons in seconds!", "リヴァイが神速の回転斬撃を繰り出し、女型の全身の腱を一瞬で切断！"),
            SubtitleEntry(43, 60, "Mikasa aims for the nape, Levi injures his ankle protecting her from a counter-swing!", "ミカサがうなじを狙い、リヴァイが彼女を庇って足首を負傷！"),
            SubtitleEntry(61, 78, "Levi slashes open the jaw and retrieves saliva-covered Eren safely.", "リヴァイが女型の顎を切り裂き、エレンを無事救出する。"),
            SubtitleEntry(79, 95, "The defeated Scout Regiment returns to Wall Rose under the silent, grieving gaze of citizens.", "失意の調査兵団は市民の冷たい視線を受けながら壁内へと帰還する。")
        )
        23 -> listOf(
            SubtitleEntry(0, 8, "Stohess District, Wall Sina: The Military Police patrols the cobblestone streets...", "ウォール・シーナ、ストヘス区――憲兵団が街を巡回する..."),
            SubtitleEntry(9, 24, "[Armin] 'Annie, please help Eren escape out of the city through the underground tunnel.'", "[アルミン] アニ、エレンを地下道から逃がすのに協力してほしい。"),
            SubtitleEntry(25, 42, "[Annie Leonhart] 'Why would I help a rebel escape the Military Police?'", "[アニ・レオンハート] なぜ私が反逆者の逃走を手助けしなきゃいけないの？"),
            SubtitleEntry(43, 60, "[Armin] 'Because you didn't kill me back in the forest... Annie, please.'", "[アルミン] あの森で、君は僕を殺さなかったからだ...アニ。"),
            SubtitleEntry(61, 78, "Annie halts at the entrance of the dark underground stairway, refusing to step down.", "薄暗い地下階段の入り口で足を止め、降りることを拒否するアニ。"),
            SubtitleEntry(79, 95, "[Annie] 'I'm glad... that I could be a good person to you, Armin.'", "[アニ] あんたのいい人でいられて...よかったよ、アルミン。")
        )
        24 -> listOf(
            SubtitleEntry(0, 8, "Annie flicks the ring blade on her finger, triggering an enormous lightning blast!", "アニが指輪の隠し刃を弾き、巨大な雷光がストヘス区を揺るがす！"),
            SubtitleEntry(9, 24, "The Female Titan emerges in the middle of the crowded city of Stohess!", "市街地のど真ん中に女型の巨人が出現！瓦礫が降り注ぐ！"),
            SubtitleEntry(25, 42, "Eren is trapped under collapsed tunnel stones, unable to find the resolve to transform.", "地下道の崩落に挟まれ、アニへの疑念から変身できないエレン。"),
            SubtitleEntry(43, 60, "[Mikasa] 'Eren, does some special feeling for Annie prevent you from fighting her?!'", "[ミカサ] エレン、アニに何か特別な感情があって戦えないの？！"),
            SubtitleEntry(61, 78, "[Mikasa] 'The world is cruel! Stand up and fight!'", "[ミカサ] 世界は残酷なんだ！立ち上がって戦いなさい！"),
            SubtitleEntry(79, 95, "[Eren] 'I will destroy it all... I will destroy the whole world!!'", "[エレン] オレが...この世の全てを壊してやる！！")
        )
        25 -> listOf(
            SubtitleEntry(0, 8, "Season 1 Climax: Eren's berserk flaming Titan clashes with Annie atop Stohess!", "第1期クライマックス――炎を纏う暴走エレンとアニが激突！"),
            SubtitleEntry(9, 24, "Titan fists shatter buildings as Eren overwhelms the Female Titan with sheer fury!", "圧倒的な怒りで女型の巨人を叩き伏せ、建物を粉砕するエレン！"),
            SubtitleEntry(25, 42, "Annie attempts to scale the 50-meter Wall Sina to escape...", "逃走を図り、50メートルのウォール・シーナの壁面を登り始めるアニ..."),
            SubtitleEntry(43, 60, "Mikasa slices off Annie's fingers: 'Annie... fall.'", "ミカサが壁上のアニの指を切り落とす――「アニ...落ちて。」"),
            SubtitleEntry(61, 78, "Female Titan crashes to the earth; Annie encases herself in an indestructible crystal cocoon!", "地上へ叩きつけられたアニは、壊せない水晶の繭に身を包み眠りにつく。"),
            SubtitleEntry(79, 95, "Cracks form in the wall where Annie's fingers dug in... A Titan's living eye gazes out from within!", "アニの爪痕から壁の破片が剥がれ落ち...壁の中から生きている巨人の顔が現れる！")
        )
        else -> listOf(
            SubtitleEntry(0, 8, "Attack on Titan Season 1 • Episode $ep: Official Stream", "進撃の巨人 第${ep}話 本編"),
            SubtitleEntry(9, 20, "High Definition dual audio Japanese with English Subtitles.", "HD高精細ストリーミング 日本語音声 / 英語字幕"),
            SubtitleEntry(21, 45, "Watch Eren, Mikasa, and Armin fight for humanity's survival.", "人類の自由と生存をかけた戦い")
        )
    }

    private fun getAttackOnTitanSeason2Subtitles(ep: Int): List<SubtitleEntry> = when (ep) {
        1 -> listOf(
            SubtitleEntry(0, 8, "Season 2 Premiere: Titans appear deep within Wall Rose without a breach!", "第2期 第1話――ウォール・ローゼ内に巨人の群れが出現！突破口はない！"),
            SubtitleEntry(9, 22, "A massive beast covered in dark fur emerges from the forest: The Beast Titan!", "森の奥から全身を獣の毛で覆われた巨大な影――獣の巨人が姿を現す！"),
            SubtitleEntry(23, 40, "Section Commander Miche Zacharias engages nine Titans alone to buy time for cadets.", "ミケ分隊長が部下を逃がすため、単身で9体の巨人を迎え撃つ。"),
            SubtitleEntry(41, 58, "The Beast Titan grabs Miche's horse and hurls it across the sky with terrifying force!", "獣の巨人が馬を鷲掴みにし、驚異的な投擲力で投げつけてくる！"),
            SubtitleEntry(59, 75, "The Beast Titan speaks with human intellect: 'What is that weapon on your waist?'", "獣の巨人が人間の言葉で喋る――「その腰の武器は何ていうの？」"),
            SubtitleEntry(76, 95, "[Miche] 'Humanity will only be defeated... when they give up the will to fight!'", "[ミケ] 人は戦うことをやめた時、初めて敗北する！")
        )
        6 -> listOf(
            SubtitleEntry(0, 8, "Atop Wall Rose: The Scout Regiment rests in the swirling mist...", "霧煙るウォール・ローゼの壁上――激闘を終えた調査兵団の休息..."),
            SubtitleEntry(9, 25, "[Reiner Braun] 'Eren, five years ago, we breached Wall Maria and attacked mankind.'", "[ライナー] エレン、5年前、俺達が壁を破壊して人類への攻撃を始めた。"),
            SubtitleEntry(26, 42, "[Reiner] 'I am the Armored Titan, and he is the Colossal Titan.'", "[ライナー] 俺が鎧の巨人で、こいつが超大型巨人ってやつだ。"),
            SubtitleEntry(43, 60, "[Eren] 'What are you saying, Reiner?! You're exhausted, you've lost your mind!'", "[エレン] 何言ってんだよライナー？！お前疲れて頭がおかしくなってんだよ！"),
            SubtitleEntry(61, 78, "[Reiner] 'It's true that I lost who I was... but my duty will be fulfilled right here!'", "[ライナー] ここで決着をつける！もう何が正しいかわからん...だが俺の役目を果たす！"),
            SubtitleEntry(79, 95, "Mikasa's blades slash in a blur! Lightning descends and transforms both into Titans!!", "ミカサの刃が一閃！直後、天を突く二条の雷光が落ち、二人が巨人と化す！！")
        )
        12 -> listOf(
            SubtitleEntry(0, 8, "Season 2 Climax: Surrounded by pure Titans, the Scouts are on the brink of slaughter...", "第2期クライマックス――無数の巨人に包囲され、壊滅寸前の調査兵団..."),
            SubtitleEntry(9, 24, "The Smiling Titan that ate Eren's mother reaches out its colossal hand toward them!", "母カルラを食った巨人が、エレンとミカサの前に再び立ちはだかる！"),
            SubtitleEntry(25, 42, "[Hannes] 'Watch me, Carla! I will avenge you and protect these kids with my life!'", "[ハンネス] 見ててください、カルラさん！俺がお前の仇を討ち、二人を護る！"),
            SubtitleEntry(43, 60, "[Mikasa] 'Eren, thank you for wrapping this scarf around me... thank you.'", "[ミカサ] エレン、マフラーを巻いてくれてありがとう...ありがとう。"),
            SubtitleEntry(61, 78, "[Eren] 'I'll wrap it around you as many times as you want! Now and forever!!'", "[エレン] そんなもん、何度でも巻いてやる！これからもずっと、何度でも！！"),
            SubtitleEntry(79, 95, "Eren punches the Smiling Titan's palm! Golden sparks erupt: The Founding Titan awakens!!", "エレンの拳が巨人の掌を殴りつける！黄金の光が炸裂――座標の力が覚醒する！！")
        )
        else -> listOf(
            SubtitleEntry(0, 8, "Attack on Titan Season 2 • Episode $ep: Official Stream", "進撃の巨人 Season 2 第${ep}話 本編"),
            SubtitleEntry(9, 25, "The Scout Regiment uncovers the deepest mysteries of the walls.", "壁の真実と裏切り、巨人の謎に迫る調査兵団"),
            SubtitleEntry(26, 45, "High Definition dual audio stream with English Subtitles.", "フルHD 高画質ストリーミング配信")
        )
    }

    private fun getAttackOnTitanSeason3Subtitles(ep: Int): List<SubtitleEntry> = when (ep) {
        16 -> listOf(
            SubtitleEntry(0, 8, "Battle of Shiganshina: The Beast Titan unleashes a lethal volley of crushed boulders!", "シガンシナ区決戦――獣の巨人が粉砕岩石の猛射を放ち、調査兵団を薙ぎ払う！"),
            SubtitleEntry(9, 25, "New recruits tremble in terror as comrades are obliterated by rock shrapnel.", "岩の散弾に仲間が次々と粉砕され、絶望に震える新兵たち。"),
            SubtitleEntry(26, 42, "[Erwin Smith] 'It means nothing to die without meaning... but we are the ones who give meaning to our fallen!'", "[エルヴィン] 死んだ仲間たちに意味を与えるのは、生きている我々だ！"),
            SubtitleEntry(43, 60, "[Erwin] 'My soldiers, rage! My soldiers, scream! My soldiers, fight!!'", "[エルヴィン] 兵士よ怒れ！兵士よ叫べ！兵士よ戦え！！"),
            SubtitleEntry(61, 78, "Commander Erwin leads the suicidal charge into the storm of flying stones!", "エルヴィン団長を先頭に、新兵たちが散弾の嵐の中を怒涛の突撃！"),
            SubtitleEntry(79, 95, "Levi flanks along the Titan line in the smoke: 'Erwin... your sacrifice won't be in vain!'", "煙幕の中を疾走するリヴァイ――「エルヴィン...お前の死を無駄にはしない！」")
        )
        17 -> listOf(
            SubtitleEntry(0, 8, "Levi Ackerman strikes the Beast Titan from the blind spot in the mist!", "リヴァイ・アッカーマン、煙幕の死角から獣の巨人を強襲！"),
            SubtitleEntry(9, 25, "Blades spinning like a sonic hurricane, Levi shreds the Beast Titan's arm and eyes in seconds!", "竜巻のような回転刃で獣の巨人の腕と目を瞬時に切り裂くリヴァイ！"),
            SubtitleEntry(26, 45, "[Levi] 'What's the matter? You were having so much fun crushing my soldiers!'", "[リヴァイ] おい...さっきまで楽しそうだったじゃねぇか！"),
            SubtitleEntry(46, 65, "Levi drags Zeke Yeager out of the smoking nape: 'I made a promise to him!'", "うなじからジークを引きずり出す――「俺はあいつに誓ったんだよ！」"),
            SubtitleEntry(66, 80, "Meanwhile, Armin sacrifices his body to the Colossal Titan's blistering steam.", "一方、アルミンは超大型巨人の放つ灼熱蒸気を受け止め、身を挺する。"),
            SubtitleEntry(81, 95, "Eren slices Bertholdt from behind: 'Armin... you really are the bravest of us all.'", "エレンが背後からベルトルトを強襲――「アルミン...お前が一番勇敢だ。」")
        )
        22 -> listOf(
            SubtitleEntry(0, 8, "Season 3 Finale: Beyond the Walls, the Scout Regiment rides across empty plains...", "第3期 最終話――壁の向こう側、白砂の荒野を馬で駆け抜ける調査兵団..."),
            SubtitleEntry(9, 25, "The vast blue ocean stretches infinitely to the horizon: Humanity has reached the sea!", "水平線の彼方まで広がる青い海――人類がついに海へ到達する！"),
            SubtitleEntry(26, 42, "Mikasa, Armin, and Sasha splash joyfully in the salt water with bright smiles.", "塩水をすくい、歓喜の声を上げてはしゃぐミカサ、アルミン、サシャ。"),
            SubtitleEntry(43, 60, "Eren stands alone in the surf, pointing across the distant endless waters.", "ひとり波打ち際に立ち、海の向こうを指差すエレン・イェーガー。"),
            SubtitleEntry(61, 78, "[Eren] 'I thought if we reached the sea, we would be free... but on the other side is an enemy.'", "[エレン] 海の向こうには自由があると思ってた...でも、いたのは敵だ。"),
            SubtitleEntry(79, 95, "[Eren] 'If we kill everyone over there... will we finally be free?'", "[エレン] 向こうにいる敵...全部殺せば、俺達は自由になれるのか？")
        )
        else -> listOf(
            SubtitleEntry(0, 8, "Attack on Titan Season 3 • Episode $ep: Official Stream", "進撃の巨人 Season 3 第${ep}話 本編"),
            SubtitleEntry(9, 25, "Return to Shiganshina and the historic revelation of Grisha's basement.", "ウォール・マリア奪還作戦と地下室の真実"),
            SubtitleEntry(26, 45, "High Definition dual audio stream with English Subtitles.", "フルHD 高画質ストリーミング配信")
        )
    }

    private fun getAttackOnTitanFinalSeasonSubtitles(ep: Int): List<SubtitleEntry> = when (ep) {
        5 -> listOf(
            SubtitleEntry(0, 8, "Liberio Internment Zone, Marley: Festival and gathering of world ambassadors...", "マーレ・レベリオ収容区――世界各国の要人が集まる演説の舞台..."),
            SubtitleEntry(9, 25, "In the dark basement below, Eren sits across from the trembling Reiner Braun.", "舞台下の暗い地下室で、震えるライナーと向き合うエレン・イェーガー。"),
            SubtitleEntry(26, 42, "[Eren] 'I'm the same as you, Reiner. Across the ocean, inside the walls... we're all the same.'", "[エレン] 俺はお前と同じだ、ライナー。海の向こうも、壁の中も、同じなんだ。"),
            SubtitleEntry(43, 60, "Willy Tybur declares atop the stage: 'To the demons of Paradis Island... I declare war!!'", "壇上のヴィリー・タイバーが叫ぶ――「パラディ島の悪魔たちへ...宣戦布告を！！」"),
            SubtitleEntry(61, 78, "[Eren] 'I will keep moving forward... until my enemies are destroyed.'", "[エレン] 俺は進み続ける...敵を駆逐するまで。"),
            SubtitleEntry(79, 95, "Eren transforms through the building! The Attack Titan obliterates the stage in thunder!", "地下から巨人が炸裂！進撃の巨人がヴィリー・タイバーを握り潰す！！")
        )
        28 -> listOf(
            SubtitleEntry(0, 8, "The Rumbling: Tens of thousands of Colossal Wall Titans march into the ocean...", "地鳴らし発動――無数の超大型巨人が海を渡り、大陸へと上陸する..."),
            SubtitleEntry(9, 25, "The global allied fleet unleashes full artillery, but the colossal heat wave incinerates the warships!", "世界連合艦隊の一斉砲撃も虚しく、圧倒的な熱蒸気が戦艦群を焼き尽くす！"),
            SubtitleEntry(26, 42, "Eren's skeletal Founding Titan towers hundreds of meters high, leading the march.", "数百メートルに及ぶエレンの始祖の巨人が、世界の終焉を導く。"),
            SubtitleEntry(43, 60, "[Eren] 'My name is Eren Yeager. I speak to all Subjects of Ymir...'", "[エレン] 我が名はエレン・イェーガー。全てのユミルの民へ告ぐ..."),
            SubtitleEntry(61, 78, "[Eren] 'My goal is to protect the people of Paradis. I will trample all lands outside the island.'", "[エレン] オレの目的はパラディ島の人々を守ること。島の外にある全ての命を駆逐する。"),
            SubtitleEntry(79, 95, "The world trembles as the colossal footfalls begin to crush the earth.", "地響きが世界を覆い尽くし、人類の歴史が激変する。")
        )
        30 -> listOf(
            SubtitleEntry(0, 8, "The Battle of Heaven and Earth: Climax of Attack on Titan!", "天と地の戦い――進撃の巨人 堂々の完結編クライマックス！"),
            SubtitleEntry(9, 25, "Mikasa, Armin, Levi, and the alliance clash atop the ribs of the Founding Titan.", "ミカサ、アルミン、リヴァイがエレンの始祖の巨人の背上で最後の戦いに挑む。"),
            SubtitleEntry(26, 42, "[Armin] 'Eren, thank you for becoming a monster for our sake...'", "[アルミン] エレン、僕たちのために悪者になってくれて、ありがとう..."),
            SubtitleEntry(43, 60, "[Mikasa] 'See you later, Eren.'", "[ミカサ] いってらっしゃい、エレン。"),
            SubtitleEntry(61, 78, "Mikasa enters the Titan's mouth and delivers the final blow with tears in her eyes.", "巨人の口内に入り、涙とともに最期の一撃を放つミカサ。"),
            SubtitleEntry(79, 95, "Peace returns to the world. A bird tugs at Mikasa's scarf by the lonely tree on the hill.", "戦いが終わり、丘の上の木の下で、鳥がミカサのマフラーを優しく巻き直す。")
        )
        else -> listOf(
            SubtitleEntry(0, 8, "Attack on Titan The Final Season • Episode $ep: Official Stream", "進撃の巨人 The Final Season 第${ep}話 本編"),
            SubtitleEntry(9, 25, "The ultimate conflict between Marley and Paradis Island.", "マーレとパラディ島、世界の命運を分ける最終決戦"),
            SubtitleEntry(26, 45, "High Definition dual audio stream with English Subtitles.", "フルHD 高画質ストリーミング配信")
        )
    }

    private fun getJujutsuKaisenSubtitles(ep: Int): List<SubtitleEntry> = when (ep) {
        1 -> listOf(
            SubtitleEntry(0, 8, "Tokyo Metropolitan Jujutsu Technical High School, Spring 2006...", "2006年春、東京都立呪術高等専門学校..."),
            SubtitleEntry(9, 18, "[Gojo] 'Tengen-sama's mission? Guarding the Star Plasma Vessel Riko Amanai.'", "[五条] 天元様の任務？ 星漿体・天内理子の護衛と抹消か。"),
            SubtitleEntry(19, 30, "[Geto] 'Remember Satoru, the strong exist to protect the weak.'", "[夏油] 弱者生存、それが社会のあるべき姿だ。"),
            SubtitleEntry(31, 45, "[Gojo] 'Righteous principles are a headache. We're the strongest anyway.'", "[五条] ポジショントークで気持ちよくなってんじゃねーよ。俺たち最強だし。")
        )
        2 -> listOf(
            SubtitleEntry(0, 8, "Star Plasma Vessel: Infiltrating the curse-user bounty hunter network...", "星漿体を狙う呪詛師集団「Q」と「時のくら」..."),
            SubtitleEntry(9, 18, "[Riko Amanai] 'Insolent fools! I am the Star Plasma Vessel destined to merge with Lord Tengen!'", "[天内理子] 無礼者め！ 妾こそが天元様と同化する星漿体じゃ！"),
            SubtitleEntry(19, 30, "[Gojo] 'She's way noisier than I expected.'", "[五条] 思ってたよりだいぶうるせーガキだな。"),
            SubtitleEntry(31, 45, "[Toji] 'A 30 million yen bounty on the girl? Time to get to work.'", "[伏黒甚爾] 懸賞金3000万か。そろそろ仕事するか。")
        )
        3 -> listOf(
            SubtitleEntry(0, 8, "Okinawa: A brief moment of calm under the southern summer sky...", "沖縄、つかの間の青い海と空..."),
            SubtitleEntry(9, 18, "[Riko] 'I want to stay with Kuroi and everyone just a little longer.'", "[天内理子] もう少しだけ、黒井やみんなと一緒にいたい..."),
            SubtitleEntry(19, 30, "[Geto] 'Satoru hasn't deactivated his Limitless technique for two straight days.'", "[夏油] 悟、まる二日術式を解いていない。少し休め。"),
            SubtitleEntry(31, 45, "[Gojo] 'I'm fine. As long as we make it past the barrier, it's our victory.'", "[五条] 平気平気。高専の結界内に入りさえすれば俺たちの勝ちだ。")
        )
        4 -> listOf(
            SubtitleEntry(0, 8, "The Sanctuary of Tombs: An assassin breaches the sacred barrier...", "薨星宮参道、侵入者を感知する結界..."),
            SubtitleEntry(9, 18, "[Toji Fushiguro] 'Haven't seen you in a while, Satoru Gojo.'", "[伏黒甚爾] 久しぶりだな、五条悟。"),
            SubtitleEntry(19, 30, "[Gojo] 'Zero cursed energy... Heavenly Restriction!'", "[五条] 呪力ゼロ... 天与呪縛か！"),
            SubtitleEntry(31, 45, "[Toji] 'Inverted Spear of Heaven nullifies any cursed technique on contact.'", "[伏黒甚爾] 天逆鉾、発動中の術式を強制解除する。")
        )
        5 -> listOf(
            SubtitleEntry(0, 8, "August 2007: The aftermath of destiny and the awakening of infinity...", "2007年8月、運命の残滓と最強の覚醒..."),
            SubtitleEntry(9, 18, "[Gojo] 'Throughout Heaven and Earth, I alone am the honored one.'", "[五条] 天上天下、唯我独尊。"),
            SubtitleEntry(19, 30, "[Geto] 'Are you the strongest because you're Satoru Gojo? Or are you Gojo because you're the strongest?'", "[夏油] 悟だから最強なのか？ 最強だから悟なのか？"),
            SubtitleEntry(31, 45, "[Geto] 'I will create a world with only jujutsu sorcerers.'", "[夏油] 術師だけの世界を作る。")
        )
        8, 9 -> listOf(
            SubtitleEntry(0, 8, "October 31st, 2018, 7:00 PM: Shibuya Station curtain lowers...", "2018年10月31日 19:00 渋谷駅周辺に「帳」が降ろされる..."),
            SubtitleEntry(9, 18, "[Curtain Announcer] 'Only civilians trapped inside. The only request: Bring Satoru Gojo.'", "[一般人] 五条悟を連れてこい...！"),
            SubtitleEntry(19, 30, "[Gojo] 'Descending to B5 Fukutoshin Line platform alone.'", "[五条] B5F副都心線ホーム、僕一人で行くよ。"),
            SubtitleEntry(31, 45, "[Gojo] 'Domain Expansion: 0.2 Second Infinite Void!'", "[五条] 領域展開――0.2秒の無量空処！")
        )
        16, 17 -> listOf(
            SubtitleEntry(0, 8, "Shibuya scorched in hellfire: The King of Curses awakens...", "業火に包まれる渋谷、呪いの王が目覚める..."),
            SubtitleEntry(9, 18, "[Sukuna] 'Know your place, fool. If you can land a single hit, I'll join your side.'", "[宿儺] 分を弁えろ、痴れ者が。一撃でも当てられたら貴様らの下についてやる。"),
            SubtitleEntry(19, 30, "[Megumi] 'With this treasure I summon: Eight-Handled Sword Divergent Sila Divine General Mahoraga!'", "[伏黒] 布瑠部由良由良、八握剣異戒神将魔虚羅！"),
            SubtitleEntry(31, 45, "[Sukuna] 'Domain Expansion: Malevolent Shrine!'", "[宿儺] 領域展開――伏魔御廚子！")
        )
        23 -> listOf(
            SubtitleEntry(0, 8, "Shibuya Incident Finale: Gate, Close. Darkness settles over Japan...", "渋谷事変終結――閉門。壊滅の東京..."),
            SubtitleEntry(9, 18, "[Kenjaku] 'The Golden Age of Jujutsu, the Heian Era, begins once more!'", "[羂索] 呪術の全盛、平安の世が再び始まる！"),
            SubtitleEntry(19, 30, "[Yuji Itadori] 'I still have a role to fulfill. I will kill curses as long as I breathe.'", "[虎杖悠仁] 俺にはまだ役目がある。呪いを殺し続ける。"),
            SubtitleEntry(31, 45, "[Yuta Okkotsu] 'I don't care who he is. I will personally execute Yuji Itadori.'", "[乙骨憂太] 悠仁くんは僕が殺します。")
        )
        else -> listOf(
            SubtitleEntry(0, 8, "Jujutsu Kaisen Season 2 • Episode $ep: Authentic Broadcast Stream", "呪術廻戦 第2期 第${ep}話 本編"),
            SubtitleEntry(9, 20, "High Definition streaming with dual audio Japanese Sub and English Dub tracks.", "高精細ストリーミング 日本語音声 / 英語字幕対応"),
            SubtitleEntry(21, 45, "Select any streaming mirror below to switch servers.", "ストリーミングサーバー切り替え対応")
        )
    }

    private fun getSoloLevelingSubtitles(ep: Int): List<SubtitleEntry> = listOf(
        SubtitleEntry(0, 6, "[System Alert] 'Quest: Survival in the Double Dungeon.'", "[システム警告] 二重ダンジョンでの生存クエスト。"),
        SubtitleEntry(7, 14, "[Sung Jinwoo] 'I was always the weakest E-Rank hunter... until today.'", "[水篠 旬] 俺はずっと最弱のE級ハンターだった...今日までは。"),
        SubtitleEntry(15, 22, "[System] 'Congratulations: You have qualified as a Player.'", "[システム] おめでとうございます。プレイヤーの資格を得ました。"),
        SubtitleEntry(23, 31, "[Sung Jinwoo] 'Level Up. Strength, Agility, Intelligence increasing.'", "[水篠 旬] レベルアップ。ステータスが上昇する。"),
        SubtitleEntry(32, 42, "[Hunters] 'Who is that guy? The mana radiating from him is unbelievable!'", "[ハンター] あいつは何者だ？ 放たれる魔力が規格外だ！"),
        SubtitleEntry(43, 53, "[Sung Jinwoo] 'Arise.'", "[水篠 旬] 起きろ (ARISE)。"),
        SubtitleEntry(54, 66, "Shadow Monarch army rises from the fallen darkness...", "闇の底から影の軍団が立ち上がる..."),
        SubtitleEntry(67, 80, "[Igris] 'My Liege, your will is absolute.'", "[イグリット] 我が王よ、御心のままに。"),
        SubtitleEntry(81, 100, "[Sung Jinwoo] 'From now on, I hunt them down alone.'", "[水篠 旬] これからは俺一人で狩り尽くす。")
    )

    private fun getFrierenSubtitles(ep: Int): List<SubtitleEntry> = listOf(
        SubtitleEntry(0, 6, "Ten years. It was only a mere tenth of an elf's hundred-year span...", "たったの10年。エルフにとってはほんの僅かな時間だった..."),
        SubtitleEntry(7, 15, "[Himmel] 'Frieren, look at the stars tonight. Aren't they beautiful?'", "[ヒンメル] フリーレン、今夜の星を見てごらん。綺麗だろう？"),
        SubtitleEntry(16, 25, "[Frieren] 'I barely knew anything about him... Why didn't I try to learn more?'", "[フリーレン] 私は彼のことを何も知らなかった... なぜ知ろうとしなかったんだろう。"),
        SubtitleEntry(26, 36, "[Fern] 'Frieren-sama, let's proceed to the next town to study spells.'", "[フェルン] フリーレン様、次の街へ魔法の収集に向かいましょう。"),
        SubtitleEntry(37, 48, "[Frieren] 'Zoltraak was once called the ultimate killing magic... now it's standard.'", "[フリーレン] ゾルトラークはかつて人を殺す魔法と呼ばれていた。"),
        SubtitleEntry(49, 62, "[Aura] 'How can your mana be greater than mine?!'", "[アウラ] なぜ貴様の魔力が私を上回っているのよ？！"),
        SubtitleEntry(63, 76, "[Frieren] 'Aura, kill yourself.'", "[フリーレン] アウラ、自害しろ。"),
        SubtitleEntry(77, 95, "The spell takes hold without hesitation as the scales tip completely.", "服従の天秤が傾き、魔法が絶対の命令を下す。")
    )

    private fun getDemonSlayerSubtitles(ep: Int): List<SubtitleEntry> = listOf(
        SubtitleEntry(0, 7, "[Tanjiro] 'Total Concentration... Water Breathing, Tenth Form: Constant Flux!'", "[炭治郎] 全集中・水の呼吸 拾ノ型 生生流転！"),
        SubtitleEntry(8, 16, "[Nezuko] *Blood Demon Art: Exploding Blood!*", "[禰豆子] 血鬼術・爆血！"),
        SubtitleEntry(17, 26, "[Rengoku] 'Set your heart ablaze! Go beyond your limits!'", "[煉獄] 心を燃やせ！ 限界を超えろ！"),
        SubtitleEntry(27, 38, "[Tanjiro] 'Hinokami Kagura: Clear Blue Sky!'", "[炭治郎] ヒノカミ神楽・碧羅の天！"),
        SubtitleEntry(39, 52, "[Muzan] 'Do not dare look down upon me, weaklings.'", "[無惨] 私を見下すな、弱者どもが。")
    )

    private fun getKaijuSubtitles(ep: Int): List<SubtitleEntry> = listOf(
        SubtitleEntry(0, 7, "[Kafka] 'Defense Force cleanup crew, reporting for duty!'", "[カフカ] 防衛隊清掃部隊、作業開始！"),
        SubtitleEntry(8, 17, "[Kafka Hibino] 'Wait... what just crawled into my mouth?!'", "[カフカ] おい...今、何が口に入った？！"),
        SubtitleEntry(18, 28, "[Reno] 'Senpai... your face! You've turned into a Kaiju!!'", "[レノ] 先輩...顔が！ 怪獣になってますよ！！"),
        SubtitleEntry(29, 42, "[Kafka] 'I swore to Mina that I would stand by her side!'", "[カフカ] ミナの隣に立つって、約束したんだ！")
    )

    private fun getChainsawManSubtitles(ep: Int): List<SubtitleEntry> = listOf(
        SubtitleEntry(0, 7, "[Denji] 'All I wanted was jam on my toast and a normal life.'", "[デンジ] 食パンにジャム塗って、普通の暮らしがしたかった。"),
        SubtitleEntry(8, 16, "[Pochita] 'Denji... show me your dreams.'", "[ポチタ] デンジ...君の夢を見せてくれ。"),
        SubtitleEntry(17, 28, "[Denji] 'Vrrrrooooom! Rip and tear, Chainsaw Man!!'", "[デンジ] ブオオオオン！ ぶっ殺してやるぜ！"),
        SubtitleEntry(29, 40, "[Makima] 'Good boy. I'll take care of you from now on.'", "[マキマ] いい子ね。これからは私が面倒を見てあげる。")
    )

    private fun getOnePieceSubtitles(ep: Int): List<SubtitleEntry> = listOf(
        SubtitleEntry(0, 8, "[Luffy] 'I'm Monkey D. Luffy! The man who will become King of the Pirates!'", "[ルフィ] 海賊王に、おれはなる！"),
        SubtitleEntry(9, 18, "[Vegapunk] 'Welcome to Egghead, the Island of the Future from 900 years ago!'", "[ベガパンク] 未来島エッグヘッドへようこそ！"),
        SubtitleEntry(19, 30, "[Luffy] 'Gear 5th! The Drums of Liberation sound loud and clear!'", "[ルフィ] ギア5！ 解放のドラムが鳴り響く！")
    )

    private fun getBleachSubtitles(ep: Int): List<SubtitleEntry> = listOf(
        SubtitleEntry(0, 8, "Warning sirens echo through the Seireitei... The Quincy empire emerges.", "警報が瀞霊廷に鳴り響く...見えざる帝国が姿を現す。"),
        SubtitleEntry(9, 19, "[Ichigo] 'Getsuga Tenshou!'", "[一護] 月牙天衝！"),
        SubtitleEntry(20, 32, "[Yhwach] 'My son born in the dark... the true battle begins.'", "[ユーハバッハ] 闇に生まれし我が子よ...真の戦いが始まる。")
    )

    private fun getGenericAnimeSubtitles(ep: Int): List<SubtitleEntry> = listOf(
        SubtitleEntry(0, 6, "Episode $ep begins as the tension mounts across the horizon...", "第${ep}話、地平線の彼方に緊張が高まる..."),
        SubtitleEntry(7, 15, "[Protagonist] 'No matter how many times I fall, I will stand back up!'", "[主人公] 何度倒れようとも、立ち上がってみせる！"),
        SubtitleEntry(16, 25, "[Rival] 'You haven't seen my true power yet!'", "[ライバル] お前はまだ、俺の真の力を知らない！"),
        SubtitleEntry(26, 36, "A surge of overwhelming energy radiates across the battlefield...", "圧倒的なエネルギーの奔流が戦場を包み込む..."),
        SubtitleEntry(37, 48, "[Ally] 'Believe in yourself! We are right here behind you!'", "[仲間] 自分を信じろ！ 俺たちが後ろについている！"),
        SubtitleEntry(49, 62, "The final clash draws near as the theme song reaches its crescendo...", "主題歌が高揚し、決着の時が近づく...")
    )

    fun findActiveSubtitle(subtitles: List<SubtitleEntry>, currentSec: Int): SubtitleEntry? {
        if (subtitles.isEmpty()) return null
        val loopSec = currentSec % (subtitles.last().endSec + 15)
        return subtitles.find { loopSec in it.startSec..it.endSec }
    }
}
