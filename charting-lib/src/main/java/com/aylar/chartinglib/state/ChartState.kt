package com.aylar.chartinglib.state

import androidx.compose.runtime.Stable
import com.aylar.chartinglib.data.DataPoint

/**
 * Holds chart interaction state: viewport and highlighted point.
 */
@Stable
interface ChartState {
    val viewPort: ViewPort
    val highlightedPoint: DataPoint?
    val highlightedSeriesIndex: Int
}
