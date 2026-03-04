package com.aylar.chartinglib.state

import androidx.compose.runtime.Immutable

/**
 * Defines the visible data window (for zoom/pan).
 */
@Immutable
data class ViewPort(
    val minX: Float,
    val maxX: Float,
    val minY: Float,
    val maxY: Float,
    val panOffsetX: Float = 0f,
    val panOffsetY: Float = 0f,
    val scaleX: Float = 1f,
    val scaleY: Float = 1f
) {
    val rangeX: Float get() = (maxX - minX).coerceAtLeast(1f)
    val rangeY: Float get() = (maxY - minY).coerceAtLeast(1f)

    fun withPan(dx: Float, dy: Float): ViewPort = copy(
        panOffsetX = panOffsetX + dx,
        panOffsetY = panOffsetY + dy
    )

    fun withZoom(factor: Float, pivotX: Float, pivotY: Float): ViewPort {
        val newScaleX = (scaleX * factor).coerceIn(0.5f, 10f)
        val newScaleY = (scaleY * factor).coerceIn(0.5f, 10f)
        return copy(scaleX = newScaleX, scaleY = newScaleY)
    }
}
