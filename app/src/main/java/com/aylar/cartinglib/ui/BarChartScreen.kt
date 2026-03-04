package com.aylar.cartinglib.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aylar.chartinglib.charts.bar.BarChart
import com.aylar.chartinglib.components.PaddingValues
import com.aylar.cartinglib.data.SampleDataProvider

@Composable
fun BarChartScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Bar Chart", modifier = Modifier.padding(bottom = 8.dp))
        BarChart(
            modifier = Modifier.height(280.dp),
            data = SampleDataProvider.barChartSample(),
            padding = PaddingValues(48f, 24f, 24f, 48f)
        )
    }
}
