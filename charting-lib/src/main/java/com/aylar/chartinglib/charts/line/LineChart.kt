package com.aylar.chartinglib.charts.line

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.aylar.chartinglib.animation.AnimationConfig
import com.aylar.chartinglib.animation.DrawOnAnimation
import com.aylar.chartinglib.charts.line.LineChartDrawer.drawAreaFill
import com.aylar.chartinglib.charts.line.LineChartDrawer.drawLinePath
import com.aylar.chartinglib.charts.line.LineChartDrawer.drawLineSeries
import com.aylar.chartinglib.charts.line.LineChartDrawer.drawPointIndicators
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
import com.aylar.chartinglib.theme.ChartDefaults
import com.aylar.chartinglib.touch.chartGestureHandler

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
 *
 * Example:
 * ```
 * LineChart(
 *     modifier = Modifier.fillMaxWidth().height(300.dp),
 *     data = chartData,
 *     style = LineChartStyle(lineColor = MaterialTheme.colorScheme.primary, lineWidth = 3.dp),
 *     xAxis = AxisConfig(labelCount = 6, formatter = { "%.0f".format(it) }),
 *     animation = AnimationConfig(duration = 600, easing = FastOutSlowInEasing),
 *     onPointSelected = { point -> }
 * )
 * ```
 *
 * @param chartState Optional state for scrubber/tooltip; use [rememberChartState].
 * @param onPointSelected Called when user drags and highlights a point.
 */
@Composable
fun LineChart(
    modifier: Modifier = Modifier.fillMaxSize(),
    data: ChartData,
    style: LineChartStyle = LineChartStyle(),
    seriesColors: List<Color> = ChartDefaults.seriesColors,
    padding: PaddingValues = PaddingValues(
        ChartDefaults.paddingLeft,
        ChartDefaults.paddingTop,
        ChartDefaults.paddingRight,
        ChartDefaults.paddingBottom
    ),
    xAxis: AxisConfig = AxisConfig(),
    yAxis: AxisConfig = AxisConfig(),
    gridConfig: GridConfig = GridConfig(),
    chartState: ChartState? = null,
    onPointSelected: ((DataPoint?, Int) -> Unit)? = null,
    tooltipConfig: TooltipConfig? = null,
    animation: AnimationConfig? = null
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val bounds by remember {
        derivedStateOf { data.dataBounds() }
    }
    val drawLeft = padding.left
    val drawTop = padding.top
    val drawWidth = (size.width - padding.left - padding.right).toFloat().coerceAtLeast(0f)
    val drawHeight = (size.height - padding.top - padding.bottom).toFloat().coerceAtLeast(0f)

    val mapper = remember(drawLeft, drawTop, drawWidth, drawHeight, bounds) {
        if (bounds == null || drawWidth <= 0 || drawHeight <= 0) null
        else CoordinateMapper(
            drawLeft, drawTop, drawWidth, drawHeight,
            bounds!!.minX, bounds!!.maxX, bounds!!.minY, bounds!!.maxY
        )
    }
    val seriesOffsets = remember(mapper, data.series) {
        mapper?.let { m ->
            data.series.map { series -> series.points.map { m.mapToOffset(it) } }
        } ?: emptyList()
    }

    var drawOnTarget by remember(animation?.enableDrawOn) {
        mutableStateOf(if (animation?.enableDrawOn == true) 0f else 1f)
    }
    LaunchedEffect(animation?.enableDrawOn) {
        if (animation?.enableDrawOn == true) drawOnTarget = 1f
    }
    val drawOnProgress by animateFloatAsState(
        targetValue = drawOnTarget,
        animationSpec = tween(
            durationMillis = animation?.durationMillis ?: ChartDefaults.animationDurationMillis,
            easing = animation?.easing ?: FastOutSlowInEasing
        ),
        label = "drawOn"
    )

    val hasInteraction = chartState != null && onPointSelected != null
    val gestureModifier = if (hasInteraction && bounds != null && drawWidth > 0 && drawHeight > 0) {
        Modifier.chartGestureHandler(
            chartState!!,
            data,
            drawLeft, drawTop, drawWidth, drawHeight,
            bounds!!.minX, bounds!!.maxX, bounds!!.minY, bounds!!.maxY,
            onPointSelected
        )
    } else Modifier

    Box(
        modifier = modifier
            .onSizeChanged { size = it }
            .then(gestureModifier)
            .semantics { contentDescription = "Line chart with ${data.series.size} series" }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (bounds == null || mapper == null || drawWidth <= 0 || drawHeight <= 0) return@Canvas

            drawGrid(mapper, gridConfig)
            drawXAxis(mapper, xAxis)
            drawYAxis(mapper, yAxis)

            val baselineY = mapper.drawBoundsBottom
            data.series.forEachIndexed { index, series ->
                if (series.points.size < 2) return@forEachIndexed
                val offsets = seriesOffsets.getOrNull(index) ?: return@forEachIndexed
                val color = seriesColors.getOrElse(index) { seriesColors.last() }
                val lineStyle = style.copy(lineColor = color)

                if (style.fillAlpha > 0f) {
                    drawAreaFill(
                        offsets,
                        fillColor = color.copy(alpha = style.fillAlpha),
                        baselineY = baselineY
                    )
                }
                if (animation?.enableDrawOn == true) {
                    val path = DrawOnAnimation.trimmedPath(offsets, drawOnProgress)
                    drawLinePath(path, lineStyle.lineColor, (lineStyle.lineWidth).value * density)
                } else {
                    drawLineSeries(mapper, offsets, lineStyle, density)
                }
                if (style.showPoints) {
                    drawPointIndicators(offsets, color, style.pointRadiusPx)
                }
            }
        }
        if (chartState != null && tooltipConfig != null && chartState.highlightedPoint != null && bounds != null && drawWidth > 0 && drawHeight > 0) {
            val tipOffset = dataPointToOffset(
                chartState.highlightedPoint!!,
                drawLeft, drawTop, drawWidth, drawHeight,
                bounds!!.minX, bounds!!.maxX, bounds!!.minY, bounds!!.maxY
            )
            Tooltip(
                offset = tipOffset,
                point = chartState.highlightedPoint,
                config = tooltipConfig
            )
        }
    }
}
