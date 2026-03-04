package com.aylar.chartinglib.mapper

import androidx.compose.ui.geometry.Offset
import com.aylar.chartinglib.data.DataPoint

/**
 * Maps data coordinates (x, y) to draw coordinates (Offset) within a rectangular area.
 * Pure Kotlin — no Compose types in the mapping logic beyond Offset for output.
 */
class CoordinateMapper(
    drawLeft: Float,
    drawTop: Float,
    drawWidth: Float,
    drawHeight: Float,
    private val dataMinX: Float,
    private val dataMaxX: Float,
    private val dataMinY: Float,
    private val dataMaxY: Float
) {
    val drawBoundsLeft: Float = drawLeft
    val drawBoundsTop: Float = drawTop
    val drawBoundsWidth: Float = drawWidth
    val drawBoundsHeight: Float = drawHeight
    val drawBoundsBottom: Float get() = drawBoundsTop + drawBoundsHeight
    val drawBoundsRight: Float get() = drawBoundsLeft + drawBoundsWidth

    val dataMinXValue: Float get() = dataMinX
    val dataMaxXValue: Float get() = dataMaxX
    val dataMinYValue: Float get() = dataMinY
    val dataMaxYValue: Float get() = dataMaxY

    private val dataRangeX = if (dataMaxX > dataMinX) dataMaxX - dataMinX else 1f
    private val dataRangeY = if (dataMaxY > dataMinY) dataMaxY - dataMinY else 1f

    fun mapToOffset(point: DataPoint): Offset {
        val tX = (point.x - dataMinX) / dataRangeX
        val tY = (point.y - dataMinY) / dataRangeY
        // Y is flipped so that larger values appear higher on screen
        val x = drawBoundsLeft + tX * drawBoundsWidth
        val y = drawBoundsTop + drawBoundsHeight - tY * drawBoundsHeight
        return Offset(x, y)
    }

    fun mapToOffset(x: Float, y: Float): Offset = mapToOffset(DataPoint(x, y))
}
