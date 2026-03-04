package com.aylar.chartinglib.animation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import com.aylar.chartinglib.mapper.PathBuilder

/**
 * Builds a path trimmed to [progress] (0f..1f) by including only the first N points.
 * Used for "draw-on" line animation.
 */
object DrawOnAnimation {

    /**
     * Returns a path through the first (progress * offsets.size) points.
     */
    fun trimmedPath(offsets: List<Offset>, progress: Float): Path {
        if (progress >= 1f || offsets.isEmpty()) return PathBuilder.polyline(offsets)
        if (progress <= 0f) return Path()
        val count = (offsets.size * progress.coerceIn(0f, 1f)).toInt().coerceAtLeast(1)
        val sub = offsets.take(count)
        return PathBuilder.polyline(sub)
    }
}
