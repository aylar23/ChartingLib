package com.aylar.chartinglib.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.aylar.chartinglib.data.DataSeries
import com.aylar.chartinglib.mapper.CoordinateMapper
import com.aylar.chartinglib.mapper.PathBuilder

/**
 * A [Composable] that wraps [Canvas] and draws a raw polyline from [data].
 * Used as the foundation for chart drawing.
 */
@Composable
fun ChartCanvas(
    modifier: Modifier = Modifier.fillMaxSize(),
    data: DataSeries,
    lineColor: Color = Color.Blue,
    lineWidth: Float = 4f,
    padding: PaddingValues = PaddingValues(0f, 0f, 0f, 0f)
) {
    Canvas(modifier = modifier) {
        if (data.points.size < 2) return@Canvas

        val drawLeft = padding.left
        val drawTop = padding.top
        val drawWidth = size.width - padding.left - padding.right
        val drawHeight = size.height - padding.top - padding.bottom
        if (drawWidth <= 0 || drawHeight <= 0) return@Canvas

        val xs = data.points.map { it.x }
        val ys = data.points.map { it.y }
        val minX = xs.minOrNull() ?: 0f
        val maxX = xs.maxOrNull() ?: 1f
        val minY = ys.minOrNull() ?: 0f
        val maxY = ys.maxOrNull() ?: 1f

        val mapper = CoordinateMapper(
            drawLeft, drawTop, drawWidth, drawHeight,
            minX, maxX, minY, maxY
        )
        val offsets = data.points.map { mapper.mapToOffset(it) }
        val path = PathBuilder.polyline(offsets)

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = lineWidth)
        )
    }
}

/**
 * Padding for the chart draw area (left, top, right, bottom in pixels).
 */
data class PaddingValues(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
)
