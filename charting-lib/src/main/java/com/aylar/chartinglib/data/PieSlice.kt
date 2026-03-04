package com.aylar.chartinglib.data

import androidx.compose.runtime.Immutable

/**
 * A single slice in a pie/donut chart.
 */
@Immutable
data class PieSlice(
    val label: String,
    val value: Float
) {
    init {
        require(value >= 0f) { "PieSlice value must be non-negative" }
    }
}
