package com.aylar.chartinglib.components.grid

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect

/**
 * Configuration for grid drawing (dashed lines).
 */
@Immutable
data class GridConfig(
    val horizontalLineCount: Int = 4,
    val verticalLineCount: Int = 4,
    val lineColor: Color = Color.Gray.copy(alpha = 0.3f),
    val lineWidth: Float = 1f,
    val dashPathEffect: PathEffect? = PathEffect.dashPathEffect(
        intervals = floatArrayOf(10f, 10f),
        phase = 0f
    )
)
