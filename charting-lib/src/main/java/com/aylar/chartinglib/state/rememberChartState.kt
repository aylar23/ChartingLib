package com.aylar.chartinglib.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.aylar.chartinglib.data.Bounds
import com.aylar.chartinglib.data.DataPoint

@Stable
internal class MutableChartState(
    initialViewPort: ViewPort,
    initialHighlight: DataPoint? = null,
    initialSeriesIndex: Int = 0
) : ChartState {
    override var viewPort by mutableStateOf(initialViewPort)
    override var highlightedPoint by mutableStateOf(initialHighlight)
    override var highlightedSeriesIndex by mutableStateOf(initialSeriesIndex)
}

/**
 * Creates and remembers a [ChartState] for use with interactive charts.
 */
@Composable
fun rememberChartState(
    initialBounds: Bounds? = null
): ChartState {
    val initialViewPort = remember(initialBounds) {
        when (initialBounds) {
            null -> ViewPort(0f, 1f, 0f, 1f)
            else -> ViewPort(
                minX = initialBounds.minX,
                maxX = initialBounds.maxX,
                minY = initialBounds.minY,
                maxY = initialBounds.maxY
            )
        }
    }
    return remember { MutableChartState(initialViewPort) }
}
