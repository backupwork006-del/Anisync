package com.example.ui.components

data class SubtitleEntry(
    val startSec: Int,
    val endSec: Int,
    val textEn: String,
    val textJa: String? = null
)

object AnimeSubtitlesProvider {

    fun getSubtitlesFor(animeTitle: String, episodeNumber: Int): List<SubtitleEntry> {
        val lower = animeTitle.lowercase()
        return when {
            lower.contains("jujutsu") -> getJujutsuKaisenSubtitles(episodeNumber)
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
