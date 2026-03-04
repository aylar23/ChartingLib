package com.aylar.cartinglib.data

import com.aylar.chartinglib.data.ChartData
import com.aylar.chartinglib.data.DataPoint
import com.aylar.chartinglib.data.DataSeries
import com.aylar.chartinglib.data.PieSlice

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

    fun barChartSample(): ChartData {
        val series1 = DataSeries(
            points = listOf(
                DataPoint(0f, 4f),
                DataPoint(1f, 7f),
                DataPoint(2f, 3f),
                DataPoint(3f, 9f),
                DataPoint(4f, 5f)
            ),
            label = "Q1"
        )
        val series2 = DataSeries(
            points = listOf(
                DataPoint(0f, 3f),
                DataPoint(1f, 5f),
                DataPoint(2f, 6f),
                DataPoint(3f, 4f),
                DataPoint(4f, 8f)
            ),
            label = "Q2"
        )
        return ChartData(listOf(series1, series2))
    }

    fun pieChartSample(): List<PieSlice> = listOf(
        PieSlice("A", 40f),
        PieSlice("B", 25f),
        PieSlice("C", 20f),
        PieSlice("D", 15f)
    )
}
