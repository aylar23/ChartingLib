package com.aylar.chartinglib.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ChartDataTest {

    @Test
    fun dataBounds_emptySeries_returnsNull() {
        val data = ChartData(emptyList())
        assertNull(data.dataBounds())
    }

    @Test
    fun dataBounds_singlePoint_returnsPointAsBounds() {
        val data = ChartData(DataSeries(listOf(DataPoint(10f, 20f)), "A"))
        val bounds = data.dataBounds()
        assertEquals(10f, bounds!!.minX)
        assertEquals(10f, bounds.maxX)
        assertEquals(20f, bounds.minY)
        assertEquals(20f, bounds.maxY)
    }

    @Test
    fun dataBounds_multipleSeries_returnsMinMaxAcrossAllPoints() {
        val series1 = DataSeries(listOf(DataPoint(0f, 1f), DataPoint(2f, 5f)), "A")
        val series2 = DataSeries(listOf(DataPoint(1f, 3f), DataPoint(3f, 2f)), "B")
        val data = ChartData(listOf(series1, series2))
        val bounds = data.dataBounds()
        assertEquals(0f, bounds!!.minX)
        assertEquals(3f, bounds.maxX)
        assertEquals(1f, bounds.minY)
        assertEquals(5f, bounds.maxY)
    }
}
