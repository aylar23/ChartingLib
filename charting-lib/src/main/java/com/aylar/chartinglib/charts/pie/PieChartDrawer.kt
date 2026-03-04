package com.aylar.chartinglib.charts.pie

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * DrawScope extension functions for drawing pie/donut slices.
 */
object PieChartDrawer {

    fun DrawScope.drawSlice(
        center: Offset,
        radius: Float,
        startAngleDegrees: Float,
        sweepAngleDegrees: Float,
        color: Color
    ) {
        drawArc(
            color = color,
            startAngle = startAngleDegrees,
            sweepAngle = sweepAngleDegrees,
            useCenter = true,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2)
        )
    }

    fun DrawScope.drawDonutSlice(
        center: Offset,
        outerRadius: Float,
        innerRadius: Float,
        startAngleDegrees: Float,
        sweepAngleDegrees: Float,
        color: Color
    ) {
        val startRad = startAngleDegrees * (PI / 180).toFloat()
        val endRad = (startAngleDegrees + sweepAngleDegrees) * (PI / 180).toFloat()
        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(center.x + innerRadius * cos(startRad), center.y + innerRadius * sin(startRad))
            arcTo(
                rect = Rect(center.x - innerRadius, center.y - innerRadius, center.x + innerRadius, center.y + innerRadius),
                startAngleDegrees = startAngleDegrees,
                sweepAngleDegrees = sweepAngleDegrees,
                forceMoveTo = false
            )
            lineTo(center.x + outerRadius * cos(endRad), center.y + outerRadius * sin(endRad))
            arcTo(
                rect = Rect(center.x - outerRadius, center.y - outerRadius, center.x + outerRadius, center.y + outerRadius),
                startAngleDegrees = startAngleDegrees + sweepAngleDegrees,
                sweepAngleDegrees = -sweepAngleDegrees,
                forceMoveTo = false
            )
            close()
        }
        drawPath(path, color)
    }
}
