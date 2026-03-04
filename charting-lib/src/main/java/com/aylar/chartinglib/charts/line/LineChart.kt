package com.aylar.chartinglib.charts.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aylar.chartinglib.components.PaddingValues
import com.aylar.chartinglib.components.axis.AxisConfig
import com.aylar.chartinglib.components.axis.AxisDrawer.drawXAxis
import com.aylar.chartinglib.components.axis.AxisDrawer.drawYAxis
import com.aylar.chartinglib.components.grid.GridConfig
import com.aylar.chartinglib.components.grid.GridDrawer.drawGrid
import com.aylar.chartinglib.data.ChartData
import com.aylar.chartinglib.mapper.CoordinateMapper
import com.aylar.chartinglib.charts.line.LineChartDrawer.drawAreaFill
import com.aylar.chartinglib.charts.line.LineChartDrawer.drawLineSeries
import com.aylar.chartinglib.charts.line.LineChartDrawer.drawPointIndicators

private val DEFAULT_SERIES_COLORS = listOf(
    Color(0xFF6200EE),
    Color(0xFF03DAC6),
    Color(0xFFFF5722),
    Color(0xFFE91E63),
    Color(0xFF4CAF50)
)

/**
 * Line chart composable: axes, grid, one or more series, optional area fill and point indicators.
 */
@Composable
fun LineChart(
    modifier: Modifier = Modifier.fillMaxSize(),
    data: ChartData,
    style: LineChartStyle = LineChartStyle(),
    seriesColors: List<Color> = DEFAULT_SERIES_COLORS,
    padding: PaddingValues = PaddingValues(48f, 24f, 24f, 48f),
    xAxis: AxisConfig = AxisConfig(),
    yAxis: AxisConfig = AxisConfig(),
    gridConfig: GridConfig = GridConfig()
) {
    Canvas(modifier = modifier) {
        val bounds = data.dataBounds() ?: return@Canvas
        val drawLeft = padding.left
        val drawTop = padding.top
        val drawWidth = size.width - padding.left - padding.right
        val drawHeight = size.height - padding.top - padding.bottom
        if (drawWidth <= 0 || drawHeight <= 0) return@Canvas

        val mapper = CoordinateMapper(
            drawLeft, drawTop, drawWidth, drawHeight,
            bounds.minX, bounds.maxX, bounds.minY, bounds.maxY
        )

        drawGrid(mapper, gridConfig)
        drawXAxis(mapper, xAxis)
        drawYAxis(mapper, yAxis)

        val baselineY = mapper.drawBoundsBottom
        for ((index, series) in data.series.withIndex()) {
            if (series.points.size < 2) continue
            val color = seriesColors.getOrElse(index) { seriesColors.last() }
            val lineStyle = style.copy(lineColor = color)
            val offsets = series.points.map { mapper.mapToOffset(it) }

            if (style.fillAlpha > 0f) {
                drawAreaFill(
                    offsets,
                    fillColor = color.copy(alpha = style.fillAlpha),
                    baselineY = baselineY
                )
            }
            drawLineSeries(mapper, offsets, lineStyle, density)
            if (style.showPoints) {
                drawPointIndicators(offsets, color, style.pointRadiusPx)
            }
        }
    }
}
