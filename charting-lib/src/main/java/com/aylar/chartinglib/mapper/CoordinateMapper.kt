package com.aylar.chartinglib.mapper

import androidx.compose.ui.geometry.Offset
import com.aylar.chartinglib.data.DataPoint

/**
 * Maps data coordinates (x, y) to draw coordinates (Offset) within a rectangular area.
 * Pure Kotlin — no Compose types in the mapping logic beyond Offset for output.
 */
class CoordinateMapper(
    private val drawLeft: Float,
    private val drawTop: Float,
    private val drawWidth: Float,
    private val drawHeight: Float,
    private val dataMinX: Float,
    private val dataMaxX: Float,
    private val dataMinY: Float,
    private val dataMaxY: Float
) {
    private val dataRangeX = if (dataMaxX > dataMinX) dataMaxX - dataMinX else 1f
    private val dataRangeY = if (dataMaxY > dataMinY) dataMaxY - dataMinY else 1f

    fun mapToOffset(point: DataPoint): Offset {
        val tX = (point.x - dataMinX) / dataRangeX
        val tY = (point.y - dataMinY) / dataRangeY
        // Y is flipped so that larger values appear higher on screen
        val x = drawLeft + tX * drawWidth
        val y = drawTop + drawHeight - tY * drawHeight
        return Offset(x, y)
    }

    fun mapToOffset(x: Float, y: Float): Offset = mapToOffset(DataPoint(x, y))
}
