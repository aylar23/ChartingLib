package com.aylar.chartinglib.components.grid

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.aylar.chartinglib.mapper.CoordinateMapper

/**
 * DrawScope extension for drawing dashed grid lines.
 */
object GridDrawer {

    fun DrawScope.drawGrid(
        mapper: CoordinateMapper,
        config: GridConfig
    ) {
        val dataMinX = mapper.dataMinXValue
        val dataMaxX = mapper.dataMaxXValue
        val dataMinY = mapper.dataMinYValue
        val dataMaxY = mapper.dataMaxYValue

        val stroke = Stroke(
            width = config.lineWidth,
            pathEffect = config.dashPathEffect
        )
        for (i in 1 until config.horizontalLineCount) {
            val t = i.toFloat() / config.horizontalLineCount
            val dataY = dataMinY + t * (dataMaxY - dataMinY)
            val start = mapper.mapToOffset(dataMinX, dataY)
            val end = mapper.mapToOffset(dataMaxX, dataY)
            val path = Path().apply {
                moveTo(start.x, start.y)
                lineTo(end.x, end.y)
            }
            drawPath(path, config.lineColor, style = stroke)
        }
        for (i in 1 until config.verticalLineCount) {
            val t = i.toFloat() / config.verticalLineCount
            val dataX = dataMinX + t * (dataMaxX - dataMinX)
            val start = mapper.mapToOffset(dataX, dataMinY)
            val end = mapper.mapToOffset(dataX, dataMaxY)
            val path = Path().apply {
                moveTo(start.x, start.y)
                lineTo(end.x, end.y)
            }
            drawPath(path, config.lineColor, style = stroke)
        }
    }
}
