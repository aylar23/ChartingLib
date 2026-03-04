package com.aylar.cartinglib.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aylar.chartinglib.charts.line.LineChart
import com.aylar.chartinglib.charts.line.LineChartStyle
import com.aylar.chartinglib.animation.AnimationConfig
import com.aylar.chartinglib.components.PaddingValues
import com.aylar.chartinglib.components.tooltip.TooltipConfig
import com.aylar.chartinglib.state.rememberChartState
import com.aylar.cartinglib.data.SampleDataProvider

@Composable
fun LineChartScreen(modifier: Modifier = Modifier) {
    val data = remember { SampleDataProvider.lineChartSample() }
    val bounds = data.dataBounds()
    val chartState = rememberChartState(initialBounds = bounds)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Line Chart — drag to scrub, tooltip", modifier = Modifier.padding(bottom = 8.dp))
        LineChart(
            modifier = Modifier.height(280.dp),
            data = data,
            style = LineChartStyle(
                lineWidth = 3.dp,
                fillAlpha = 0.2f,
                showPoints = true
            ),
            padding = PaddingValues(48f, 24f, 24f, 48f),
            chartState = chartState,
            onPointSelected = { _, _ -> },
            tooltipConfig = TooltipConfig(),
            animationConfig = AnimationConfig(enableDrawOn = true, durationMillis = 800)
        )
    }
}
