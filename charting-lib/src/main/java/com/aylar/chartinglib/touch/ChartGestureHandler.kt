package com.aylar.chartinglib.touch

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.aylar.chartinglib.data.ChartData
import com.aylar.chartinglib.data.DataPoint
import com.aylar.chartinglib.state.ChartState
import com.aylar.chartinglib.state.MutableChartState

/**
 * Modifier that adds drag (scrubber) handling: updates [chartState] highlight and [onPointSelected].
 */
fun Modifier.chartGestureHandler(
    chartState: ChartState,
    chartData: ChartData,
    drawLeft: Float,
    drawTop: Float,
    drawWidth: Float,
    drawHeight: Float,
    dataMinX: Float,
    dataMaxX: Float,
    dataMinY: Float,
    dataMaxY: Float,
    onPointSelected: ((DataPoint?, Int) -> Unit)?
): Modifier = this.then(
    pointerInput(
        chartState, chartData,
        drawLeft, drawTop, drawWidth, drawHeight,
        dataMinX, dataMaxX, dataMinY, dataMaxY
    ) {
        val mutableState = chartState as? MutableChartState ?: return@pointerInput
        if (onPointSelected == null) return@pointerInput
        detectDragGestures { change, _ ->
            change.consume()
            val pos = change.position
            val result = HighlightResolver.findNearest(
                pos.x, pos.y,
                drawLeft, drawTop, drawWidth, drawHeight,
                dataMinX, dataMaxX, dataMinY, dataMaxY,
                chartData,
                maxDistancePx = 72f
            )
            if (result != null) {
                mutableState.highlightedPoint = result.point
                mutableState.highlightedSeriesIndex = result.seriesIndex
                onPointSelected(result.point, result.seriesIndex)
            } else {
                mutableState.highlightedPoint = null
                onPointSelected(null, -1)
            }
        }
    }
)
