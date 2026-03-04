package com.aylar.chartinglib.components.axis

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Configuration for axis drawing (labels, ticks, style).
 */
@Immutable
data class AxisConfig(
    val labelCount: Int = 6,
    val formatter: (Float) -> String = { "%.1f".format(it) },
    val lineColor: Color = Color.Gray,
    val lineWidth: Float = 2f,
    val textColor: Color = Color.Gray,
    val textSizePx: Float = 28f
)
