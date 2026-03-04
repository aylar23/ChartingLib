package com.aylar.chartinglib.charts.pie

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Style configuration for a pie/donut chart.
 */
@Immutable
data class PieChartStyle(
    val sliceColors: List<Color> = listOf(
        Color(0xFF6200EE),
        Color(0xFF03DAC6),
        Color(0xFFFF5722),
        Color(0xFFE91E63),
        Color(0xFF4CAF50)
    ),
    val strokeWidth: Float = 2f,
    val strokeColor: Color = Color.White
)
