package com.aylar.chartinglib.components.axis

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import com.aylar.chartinglib.mapper.CoordinateMapper

/**
 * DrawScope extension functions for drawing axes and labels.
 */
object AxisDrawer {

    fun DrawScope.drawXAxis(
        mapper: CoordinateMapper,
        config: AxisConfig
    ) {
        val left = mapper.drawBoundsLeft
        val right = mapper.drawBoundsRight
        val bottom = mapper.drawBoundsBottom
        val dataMinY = mapper.dataMinYValue
        val bottomOffset = mapper.mapToOffset(mapper.dataMinXValue, dataMinY)
        val yLine = bottomOffset.y
        drawLine(
            color = config.lineColor,
            start = Offset(left, yLine),
            end = Offset(right, yLine),
            strokeWidth = config.lineWidth
        )
        val dataMinX = mapper.dataMinXValue
        val dataMaxX = mapper.dataMaxXValue
        val steps = (config.labelCount - 1).coerceAtLeast(1)
        for (i in 0 until config.labelCount) {
            val t = i.toFloat() / steps
            val dataX = dataMinX + t * (dataMaxX - dataMinX)
            val offset = mapper.mapToOffset(dataX, dataMinY)
            val label = config.formatter(dataX)
            drawLabel(label, Offset(offset.x, yLine + config.textSizePx * 0.8f), config, Paint.Align.CENTER)
        }
    }

    fun DrawScope.drawYAxis(
        mapper: CoordinateMapper,
        config: AxisConfig
    ) {
        val left = mapper.drawBoundsLeft
        val top = mapper.drawBoundsTop
        val bottom = mapper.drawBoundsBottom
        val dataMinX = mapper.dataMinXValue
        val leftOffset = mapper.mapToOffset(dataMinX, mapper.dataMinYValue)
        val xLine = leftOffset.x
        drawLine(
            color = config.lineColor,
            start = Offset(xLine, top),
            end = Offset(xLine, bottom),
            strokeWidth = config.lineWidth
        )
        val dataMinY = mapper.dataMinYValue
        val dataMaxY = mapper.dataMaxYValue
        val steps = (config.labelCount - 1).coerceAtLeast(1)
        for (i in 0 until config.labelCount) {
            val t = i.toFloat() / steps
            val dataY = dataMinY + t * (dataMaxY - dataMinY)
            val offset = mapper.mapToOffset(dataMinX, dataY)
            val label = config.formatter(dataY)
            drawLabel(label, Offset((xLine - config.textSizePx * 0.5f).coerceAtLeast(0f), offset.y), config, Paint.Align.RIGHT)
        }
    }

    private fun DrawScope.drawLabel(
        text: String,
        position: Offset,
        config: AxisConfig,
        align: Paint.Align = Paint.Align.CENTER
    ) {
        drawContext.canvas.nativeCanvas.apply {
            val paint = Paint().apply {
                color = config.textColor.toArgb()
                textSize = config.textSizePx
                textAlign = align
                isAntiAlias = true
            }
            drawText(text, position.x, position.y, paint)
        }
    }
}
