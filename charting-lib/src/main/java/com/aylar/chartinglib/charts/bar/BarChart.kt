package com.aylar.chartinglib.charts.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.aylar.chartinglib.components.PaddingValues
import com.aylar.chartinglib.theme.ChartDefaults
import com.aylar.chartinglib.components.axis.AxisConfig
import com.aylar.chartinglib.components.axis.AxisDrawer.drawXAxis
import com.aylar.chartinglib.components.axis.AxisDrawer.drawYAxis
import com.aylar.chartinglib.components.grid.GridConfig
import com.aylar.chartinglib.components.grid.GridDrawer.drawGrid
import com.aylar.chartinglib.data.ChartData
import com.aylar.chartinglib.mapper.CoordinateMapper
import com.aylar.chartinglib.charts.bar.BarChartDrawer.drawBar


/**
 * Bar chart composable: draws bars from [data] (one or more series), with optional axes and grid.
 */
@Composable
fun BarChart(
    modifier: Modifier = Modifier.fillMaxSize(),
    data: ChartData,
    style: BarChartStyle = BarChartStyle(),
    seriesColors: List<Color> = ChartDefaults.seriesColors,
    padding: PaddingValues = PaddingValues(ChartDefaults.paddingLeft, ChartDefaults.paddingTop, ChartDefaults.paddingRight, ChartDefaults.paddingBottom),
    xAxis: AxisConfig = AxisConfig(),
    yAxis: AxisConfig = AxisConfig(),
    gridConfig: GridConfig = GridConfig()
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val bounds = data.dataBounds()
    val drawLeft = padding.left
    val drawTop = padding.top
    val drawWidth = (size.width - padding.left - padding.right).toFloat().coerceAtLeast(0f)
    val drawHeight = (size.height - padding.top - padding.bottom).toFloat().coerceAtLeast(0f)
    val cornerRadiusPx = with(LocalDensity.current) { style.barCornerRadius.toPx() }

    Box(
        modifier = modifier
            .onSizeChanged { newSize -> size = newSize }
            .semantics { contentDescription = "Bar chart with ${data.series.size} series" }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (bounds == null || drawWidth <= 0 || drawHeight <= 0) return@Canvas

            val mapper = CoordinateMapper(
                drawLeft, drawTop, drawWidth, drawHeight,
                bounds.minX, bounds.maxX, 0f, bounds.maxY.coerceAtLeast(1f)
            )
            drawGrid(mapper, gridConfig)
            drawXAxis(mapper, xAxis)
            drawYAxis(mapper, yAxis)

            val baselineY = mapper.drawBoundsBottom
            val numPoints = data.series.firstOrNull()?.points?.size ?: 0
            if (numPoints == 0) return@Canvas

            val numSeries = data.series.size
            val slotWidth = drawWidth / numPoints
            val barSlotWidth = slotWidth / numSeries
            val barWidth = barSlotWidth * style.barWidthRatio

            for ((seriesIndex, series) in data.series.withIndex()) {
                val color = seriesColors.getOrElse(seriesIndex) { seriesColors.last() }
                for ((pointIndex, point) in series.points.withIndex()) {
                    val centerX = drawLeft + (pointIndex + 0.5f) * slotWidth
                    val barLeft = centerX - barWidth / 2 + (seriesIndex - (numSeries - 1) / 2f) * barSlotWidth
                    val topOffset = mapper.mapToOffset(point.x, point.y)
                    val bottomOffset = mapper.mapToOffset(point.x, 0f)
                    val barHeight = (bottomOffset.y - topOffset.y).coerceAtLeast(0f)
                    drawBar(
                        left = barLeft,
                        top = topOffset.y,
                        width = barWidth,
                        height = barHeight,
                        color = color,
                        cornerRadiusPx = cornerRadiusPx
                    )
                }
            }
        }
    }
}
