package com.aylar.chartinglib.charts.line

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.aylar.chartinglib.mapper.CoordinateMapper
import com.aylar.chartinglib.mapper.PathBuilder

/**
 * DrawScope extension functions for drawing line chart elements (line, fill, points).
 */
object LineChartDrawer {

    fun DrawScope.drawLineSeries(
        mapper: CoordinateMapper,
        offsets: List<Offset>,
        style: LineChartStyle,
        density: Float
    ) {
        if (offsets.size < 2) return
        val lineWidthPx = (style.lineWidth).value * density
        val path = PathBuilder.polyline(offsets)
        drawPath(path, style.lineColor, style = Stroke(width = lineWidthPx))
    }

    fun DrawScope.drawAreaFill(
        offsets: List<Offset>,
        fillColor: Color,
        baselineY: Float
    ) {
        if (offsets.isEmpty()) return
        val path = PathBuilder.lineWithArea(offsets, baselineY)
        drawPath(path, fillColor)
    }

    fun DrawScope.drawPointIndicators(
        offsets: List<Offset>,
        color: Color,
        radiusPx: Float
    ) {
        for (offset in offsets) {
            drawCircle(color = color, radius = radiusPx, center = offset)
        }
    }
}
