package com.aylar.chartinglib.charts.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.aylar.chartinglib.components.PaddingValues
import com.aylar.chartinglib.components.axis.AxisConfig
import com.aylar.chartinglib.components.axis.AxisDrawer.drawXAxis
import com.aylar.chartinglib.components.axis.AxisDrawer.drawYAxis
import com.aylar.chartinglib.components.grid.GridConfig
import com.aylar.chartinglib.components.grid.GridDrawer.drawGrid
import com.aylar.chartinglib.components.tooltip.Tooltip
import com.aylar.chartinglib.components.tooltip.TooltipConfig
import com.aylar.chartinglib.data.ChartData
import com.aylar.chartinglib.data.DataPoint
import com.aylar.chartinglib.mapper.CoordinateMapper
import com.aylar.chartinglib.state.ChartState
import com.aylar.chartinglib.touch.chartGestureHandler
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

private fun dataPointToOffset(
    point: DataPoint,
    drawLeft: Float, drawTop: Float, drawWidth: Float, drawHeight: Float,
    minX: Float, maxX: Float, minY: Float, maxY: Float
): Offset {
    val rangeX = (maxX - minX).coerceAtLeast(1f)
    val rangeY = (maxY - minY).coerceAtLeast(1f)
    val tX = (point.x - minX) / rangeX
    val tY = (point.y - minY) / rangeY
    val x = drawLeft + tX * drawWidth
    val y = drawTop + drawHeight - tY * drawHeight
    return Offset(x, y)
}

/**
 * Line chart composable: axes, grid, one or more series, optional area fill and point indicators.
 * Optional [chartState] and [onPointSelected] enable scrubber; [tooltipConfig] shows a tooltip overlay.
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
    gridConfig: GridConfig = GridConfig(),
    chartState: ChartState? = null,
    onPointSelected: ((DataPoint?, Int) -> Unit)? = null,
    tooltipConfig: TooltipConfig? = null
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val bounds = data.dataBounds()
    val drawLeft = padding.left
    val drawTop = padding.top
    val drawWidth = (size.width - padding.left - padding.right).toFloat().coerceAtLeast(0f)
    val drawHeight = (size.height - padding.top - padding.bottom).toFloat().coerceAtLeast(0f)
    val hasInteraction = chartState != null && onPointSelected != null
    val gestureModifier = if (hasInteraction && bounds != null && drawWidth > 0 && drawHeight > 0) {
        Modifier.chartGestureHandler(
            chartState!!,
            data,
            drawLeft, drawTop, drawWidth, drawHeight,
            bounds.minX, bounds.maxX, bounds.minY, bounds.maxY,
            onPointSelected
        )
    } else Modifier

    Box(
        modifier = modifier
            .onSizeChanged { size = it }
            .then(gestureModifier)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (bounds == null) return@Canvas
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
        if (chartState != null && tooltipConfig != null && chartState.highlightedPoint != null && bounds != null && drawWidth > 0 && drawHeight > 0) {
            val tipOffset = dataPointToOffset(
                chartState.highlightedPoint!!,
                drawLeft, drawTop, drawWidth, drawHeight,
                bounds.minX, bounds.maxX, bounds.minY, bounds.maxY
            )
            Tooltip(
                offset = tipOffset,
                point = chartState.highlightedPoint,
                config = tooltipConfig
            )
        }
    }
}
