package com.aylar.chartinglib.data

import androidx.compose.runtime.Immutable

/**
 * A named series of data points (e.g. one line on a line chart).
 */
@Immutable
data class DataSeries(
    val points: List<DataPoint>,
    val label: String = ""
) {
    val isEmpty: Boolean get() = points.isEmpty()
    val size: Int get() = points.size
}
