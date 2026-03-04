package com.aylar.chartinglib.charts.line

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Style configuration for a line chart.
 */
@Immutable
data class LineChartStyle(
    val lineColor: Color = Color.Blue,
    val lineWidth: Dp = 3.dp,
    val fillAlpha: Float = 0.2f,
    val smoothCurve: Boolean = false,
    val showPoints: Boolean = true,
    val pointRadiusPx: Float = 6f
)
