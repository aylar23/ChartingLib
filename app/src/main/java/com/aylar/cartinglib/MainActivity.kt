package com.aylar.cartinglib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.aylar.cartinglib.ui.theme.ChartingLibTheme
import com.aylar.cartinglib.ui.LineChartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChartingLibTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LineChartScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}