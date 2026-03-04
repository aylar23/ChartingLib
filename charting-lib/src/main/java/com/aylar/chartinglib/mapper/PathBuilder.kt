package com.aylar.chartinglib.mapper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

/**
 * Builds a [Path] from a list of draw-space offsets (e.g. from [CoordinateMapper]).
 */
object PathBuilder {

    /**
     * Creates a polyline path through the given points.
     */
    fun polyline(offsets: List<Offset>): Path {
        val path = Path()
        if (offsets.isEmpty()) return path
        path.moveTo(offsets[0].x, offsets[0].y)
        for (i in 1 until offsets.size) {
            path.lineTo(offsets[i].x, offsets[i].y)
        }
        return path
    }

    /**
     * Creates a closed path that includes the area under the line down to a baseline y.
     */
    fun lineWithArea(offsets: List<Offset>, baselineY: Float): Path {
        val path = Path()
        if (offsets.isEmpty()) return path
        path.moveTo(offsets[0].x, offsets[0].y)
        for (i in 1 until offsets.size) {
            path.lineTo(offsets[i].x, offsets[i].y)
        }
        for (i in offsets.indices.reversed()) {
            path.lineTo(offsets[i].x, baselineY)
        }
        path.close()
        return path
    }
}
