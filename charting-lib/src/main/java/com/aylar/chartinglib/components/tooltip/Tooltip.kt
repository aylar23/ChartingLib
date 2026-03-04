package com.aylar.chartinglib.components.tooltip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.aylar.chartinglib.data.DataPoint
import kotlin.math.roundToInt

/**
 * Composable tooltip overlay positioned at [offset] (in pixels from top-start of the chart area).
 */
@Composable
fun Tooltip(
    offset: Offset?,
    point: DataPoint?,
    config: TooltipConfig = TooltipConfig(),
    modifier: Modifier = Modifier
) {
    if (offset == null || point == null) return
    Box(
        modifier = modifier
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .background(config.backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(config.padding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = config.formatter(point.x, point.y),
            color = config.textColor
        )
    }
}
