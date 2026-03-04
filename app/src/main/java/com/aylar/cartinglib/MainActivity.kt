package com.aylar.cartinglib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aylar.cartinglib.ui.theme.ChartingLibTheme
import com.aylar.chartinglib.components.ChartCanvas
import com.aylar.chartinglib.components.PaddingValues
import com.aylar.chartinglib.data.DataPoint
import com.aylar.chartinglib.data.DataSeries

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChartingLibTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "ChartingLib — Phase 1",
                            modifier = Modifier.padding(16.dp)
                        )
                        ChartCanvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(200.dp)
                                .padding(16.dp),
                            data = DataSeries(
                                points = listOf(
                                    DataPoint(0f, 1f),
                                    DataPoint(1f, 3f),
                                    DataPoint(2f, 2f),
                                    DataPoint(3f, 5f),
                                    DataPoint(4f, 4f)
                                ),
                                label = "Sample"
                            ),
                            padding = PaddingValues(32f, 32f, 32f, 32f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChartCanvasPreview() {
    ChartingLibTheme {
        ChartCanvas(
            modifier = Modifier.height(200.dp),
            data = DataSeries(
                points = listOf(
                    DataPoint(0f, 1f),
                    DataPoint(1f, 3f),
                    DataPoint(2f, 2f)
                )
            )
        )
    }
}