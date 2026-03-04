package com.aylar.cartinglib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.aylar.cartinglib.ui.theme.ChartingLibTheme
import com.aylar.cartinglib.ui.LineChartScreen
import com.aylar.cartinglib.ui.BarChartScreen
import com.aylar.cartinglib.ui.PieChartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChartingLibTheme {
                var selectedTab by remember { mutableStateOf(0) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TabRow(selectedTabIndex = selectedTab) {
                            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Line") })
                            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Bar") })
                            Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Pie") })
                        }
                    }
                ) { innerPadding ->
                    when (selectedTab) {
                        0 -> LineChartScreen(modifier = Modifier.padding(innerPadding))
                        1 -> BarChartScreen(modifier = Modifier.padding(innerPadding))
                        2 -> PieChartScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}