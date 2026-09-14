package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AnimeSource
import com.example.ui.theme.GogoBadgeColor
import com.example.ui.theme.HiAnimeBadgeColor
import com.example.ui.theme.PaheBadgeColor

@Composable
fun SourceBadge(
    source: AnimeSource,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, label) = when (source) {
        AnimeSource.ANIMEPAHE -> Triple(PaheBadgeColor.copy(alpha = 0.2f), PaheBadgeColor, "Pahe")
        AnimeSource.GOGOANIME -> Triple(GogoBadgeColor.copy(alpha = 0.2f), GogoBadgeColor, "Gogo")
        AnimeSource.HIANIME -> Triple(HiAnimeBadgeColor.copy(alpha = 0.2f), HiAnimeBadgeColor, "HiAnime")
        AnimeSource.ALL -> Triple(Color(0xFF7C4DFF).copy(alpha = 0.2f), Color(0xFFB388FF), "All")
    }

    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
