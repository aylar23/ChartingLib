package com.aylar.cartinglib.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aylar.chartinglib.charts.pie.DonutChart
import com.aylar.chartinglib.charts.pie.PieChart
import com.aylar.cartinglib.data.SampleDataProvider

@Composable
fun PieChartScreen(modifier: Modifier = Modifier) {
    val slices = SampleDataProvider.pieChartSample()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Pie & Donut", modifier = Modifier.padding(bottom = 8.dp))
        Row {
            PieChart(
                modifier = Modifier
                    .weight(1f)
                    .height(240.dp),
                slices = slices
            )
            DonutChart(
                modifier = Modifier
                    .weight(1f)
                    .height(240.dp),
                slices = slices,
                centerLabel = "Total"
            )
        }
    }
}
