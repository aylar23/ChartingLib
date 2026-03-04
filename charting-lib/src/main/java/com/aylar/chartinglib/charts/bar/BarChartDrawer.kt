package com.aylar.chartinglib.charts.bar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * DrawScope extension functions for drawing bar chart elements.
 */
object BarChartDrawer {

    fun DrawScope.drawBar(
        left: Float,
        top: Float,
        width: Float,
        height: Float,
        color: Color,
        cornerRadiusPx: Float = 4f
    ) {
        if (width <= 0 || height <= 0) return
        drawRoundRect(
            color = color,
            topLeft = Offset(left, top),
            size = Size(width, height),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadiusPx)
        )
    }

    fun DrawScope.drawBarRect(
        rect: Rect,
        color: Color,
        cornerRadiusPx: Float = 4f
    ) {
        drawBar(
            left = rect.left,
            top = rect.top,
            width = rect.width,
            height = rect.height,
            color = color,
            cornerRadiusPx = cornerRadiusPx
        )
    }
}
