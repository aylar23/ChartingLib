package com.aylar.chartinglib.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Default values for chart styling (colors, dimensions).
 * Use with chart style and axis/grid configs.
 */
@Immutable
object ChartDefaults {

    val seriesColors: List<Color> = listOf(
        Color(0xFF6200EE),
        Color(0xFF03DAC6),
        Color(0xFFFF5722),
        Color(0xFFE91E63),
        Color(0xFF4CAF50)
    )

    val paddingLeft = 48f
    val paddingTop = 24f
    val paddingRight = 24f
    val paddingBottom = 48f

    val lineWidth = 3.dp
    val fillAlpha = 0.2f
    val pointRadiusPx = 6f

    val animationDurationMillis = 600
}
