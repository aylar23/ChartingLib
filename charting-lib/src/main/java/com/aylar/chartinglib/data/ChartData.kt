package com.aylar.chartinglib.data

import androidx.compose.runtime.Immutable

/**
 * Container for one or more [DataSeries] to be drawn on a chart.
 */
@Immutable
data class ChartData(
    val series: List<DataSeries>
) {
    constructor(single: DataSeries) : this(listOf(single))

    val allPoints: List<DataPoint>
        get() = series.flatMap { s -> s.points }

    fun dataBounds(): Bounds? {
        val pts = allPoints
        if (pts.isEmpty()) return null
        val xs = pts.map { p -> p.x }
        val ys = pts.map { p -> p.y }
        return Bounds(
            minX = xs.minOrNull()!!,
            maxX = xs.maxOrNull()!!,
            minY = ys.minOrNull()!!,
            maxY = ys.maxOrNull()!!
        )
    }
}

@Immutable
data class Bounds(
    val minX: Float,
    val maxX: Float,
    val minY: Float,
    val maxY: Float
)
