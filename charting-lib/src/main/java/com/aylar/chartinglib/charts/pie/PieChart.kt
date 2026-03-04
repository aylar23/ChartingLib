package com.aylar.chartinglib.charts.pie

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.aylar.chartinglib.data.PieSlice
import com.aylar.chartinglib.charts.pie.PieChartDrawer.drawSlice

/**
 * Pie chart composable: draws slices from [slices] (label + value). Values are normalized to 360°.
 */
@Composable
fun PieChart(
    modifier: Modifier = Modifier.fillMaxSize(),
    slices: List<PieSlice>,
    style: PieChartStyle = PieChartStyle()
) {
    if (slices.isEmpty()) return
    val total = slices.sumOf { it.value.toDouble() }.toFloat().coerceAtLeast(1f)
    val colors = style.sliceColors

    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2f
        val center = Offset(size.width / 2f, size.height / 2f)
        var startAngle = 0f
        slices.forEachIndexed { index, slice ->
            val sweep = (slice.value / total * 360f).coerceIn(0f, 360f)
            if (sweep > 0f) {
                val color = colors.getOrElse(index % colors.size) { Color.Gray }
                drawSlice(center, radius, startAngle, sweep, color)
                startAngle += sweep
            }
        }
    }
}
