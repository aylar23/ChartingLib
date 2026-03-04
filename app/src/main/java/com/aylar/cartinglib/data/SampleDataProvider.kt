package com.aylar.cartinglib.data

import com.aylar.chartinglib.data.ChartData
import com.aylar.chartinglib.data.DataPoint
import com.aylar.chartinglib.data.DataSeries

object SampleDataProvider {

    fun lineChartSample(): ChartData {
        val series1 = DataSeries(
            points = listOf(
                DataPoint(0f, 1f),
                DataPoint(1f, 3f),
                DataPoint(2f, 2f),
                DataPoint(3f, 5f),
                DataPoint(4f, 4f),
                DataPoint(5f, 6f)
            ),
            label = "Series A"
        )
        val series2 = DataSeries(
            points = listOf(
                DataPoint(0f, 2f),
                DataPoint(1f, 2f),
                DataPoint(2f, 4f),
                DataPoint(3f, 3f),
                DataPoint(4f, 5f),
                DataPoint(5f, 4f)
            ),
            label = "Series B"
        )
        return ChartData(listOf(series1, series2))
    }
}
