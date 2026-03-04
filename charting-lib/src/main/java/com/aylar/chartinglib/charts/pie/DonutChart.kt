package com.aylar.chartinglib.charts.pie

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import com.aylar.chartinglib.data.PieSlice
import com.aylar.chartinglib.charts.pie.PieChartDrawer.drawDonutSlice

/**
 * Donut chart composable: draws slices with inner cutout and optional center [centerLabel].
 */
@Composable
fun DonutChart(
    modifier: Modifier = Modifier.fillMaxSize(),
    slices: List<PieSlice>,
    style: PieChartStyle = PieChartStyle(),
    innerRadiusRatio: Float = 0.5f,
    centerLabel: String? = null
) {
    if (slices.isEmpty()) return
    val total = slices.sumOf { it.value.toDouble() }.toFloat().coerceAtLeast(1f)
    val colors = style.sliceColors

    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2f
        val innerRadius = radius * innerRadiusRatio.coerceIn(0.1f, 0.9f)
        val center = Offset(size.width / 2f, size.height / 2f)
        var startAngle = 0f
        slices.forEachIndexed { index, slice ->
            val sweep = (slice.value / total * 360f).coerceIn(0f, 360f)
            if (sweep > 0f) {
                val color = colors.getOrElse(index % colors.size) { Color.Gray }
                drawDonutSlice(center, radius, innerRadius, startAngle, sweep, color)
                startAngle += sweep
            }
        }
        centerLabel?.let { text ->
            drawCenterLabel(text, center, style)
        }
    }
}

private fun DrawScope.drawCenterLabel(text: String, center: Offset, style: PieChartStyle) {
    drawContext.canvas.nativeCanvas.apply {
        val paint = Paint().apply {
            color = Color.Black.toArgb()
            textSize = 32f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        val bounds = android.graphics.Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        drawText(text, center.x, center.y + bounds.height() / 2f, paint)
    }
}
