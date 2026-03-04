package com.aylar.chartinglib.charts.bar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Style configuration for a bar chart.
 */
@Immutable
data class BarChartStyle(
    val barColor: Color = Color(0xFF6200EE),
    val barCornerRadius: Dp = 4.dp,
    val barWidthRatio: Float = 0.7f, // fraction of slot width per bar (for grouped)
    val stacked: Boolean = false
)
