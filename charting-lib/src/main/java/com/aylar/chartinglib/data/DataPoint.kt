package com.aylar.chartinglib.data

import androidx.compose.runtime.Immutable

/**
 * A single data point in chart space (x, y).
 */
@Immutable
data class DataPoint(
    val x: Float,
    val y: Float
)
