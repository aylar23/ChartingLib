package com.aylar.chartinglib.touch

import androidx.compose.ui.geometry.Offset
import com.aylar.chartinglib.data.ChartData
import com.aylar.chartinglib.data.DataPoint
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Finds the data point nearest to a touch position (in draw-space pixels).
 */
object HighlightResolver {

    data class HighlightResult(
        val point: DataPoint,
        val seriesIndex: Int,
        val pointIndex: Int
    )

    /**
     * Converts draw-space (px, py) to data (x,y) using the same mapping as CoordinateMapper.
     */
    fun touchToData(
        touchPx: Float, touchPy: Float,
        drawLeft: Float, drawTop: Float, drawWidth: Float, drawHeight: Float,
        dataMinX: Float, dataMaxX: Float, dataMinY: Float, dataMaxY: Float
    ): Pair<Float, Float> {
        if (drawWidth <= 0 || drawHeight <= 0) return 0f to 0f
        val rangeX = (dataMaxX - dataMinX).coerceAtLeast(1f)
        val rangeY = (dataMaxY - dataMinY).coerceAtLeast(1f)
        val tX = (touchPx - drawLeft) / drawWidth
        val tY = 1f - (touchPy - drawTop) / drawHeight
        return (dataMinX + tX * rangeX) to (dataMinY + tY * rangeY)
    }

    /**
     * Finds nearest point in [chartData] to (touchPx, touchPy) by mapping touch to data space
     * and comparing to each point (mapping point to draw space for distance).
     */
    fun findNearest(
        touchPx: Float, touchPy: Float,
        drawLeft: Float, drawTop: Float, drawWidth: Float, drawHeight: Float,
        dataMinX: Float, dataMaxX: Float, dataMinY: Float, dataMaxY: Float,
        chartData: ChartData,
        maxDistancePx: Float = 72f
    ): HighlightResult? {
        if (drawWidth <= 0 || drawHeight <= 0) return null
        val rangeX = (dataMaxX - dataMinX).coerceAtLeast(1f)
        val rangeY = (dataMaxY - dataMinY).coerceAtLeast(1f)
        var best: HighlightResult? = null
        var bestDistSq = maxDistancePx * maxDistancePx
        chartData.series.forEachIndexed { seriesIndex, series ->
            series.points.forEachIndexed { pointIndex, point ->
                val tX = (point.x - dataMinX) / rangeX
                val tY = (point.y - dataMinY) / rangeY
                val drawX = drawLeft + tX * drawWidth
                val drawY = drawTop + drawHeight - tY * drawHeight
                val dx = touchPx - drawX
                val dy = touchPy - drawY
                val distSq = dx * dx + dy * dy
                if (distSq < bestDistSq) {
                    bestDistSq = distSq
                    best = HighlightResult(point, seriesIndex, pointIndex)
                }
            }
        }
        return best
    }
}
