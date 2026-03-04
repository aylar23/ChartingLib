package com.aylar.chartinglib.components.tooltip

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class TooltipConfig(
    val backgroundColor: Color = Color.Black.copy(alpha = 0.85f),
    val textColor: Color = Color.White,
    val padding: Dp = 8.dp,
    val formatter: (Float, Float) -> String = { x, y -> "x: %.2f, y: %.2f".format(x, y) }
)
