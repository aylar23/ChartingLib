# ChartingLib

A **Jetpack Compose** charting library for Android: line charts, bar charts, pie/donut charts with axes, grid, tooltips, and animations.

## Features

- **Line chart** — Multiple series, area fill, point indicators, draw-on animation, scrubber + tooltip
- **Bar chart** — Grouped bars, rounded corners, axes & grid
- **Pie chart** — Proportional slices with configurable colors
- **Donut chart** — Inner cutout with optional center label
- **Compose-native** — `Canvas` + `DrawScope`, `State`, `Animatable`, `pointerInput` gestures
- **Accessibility** — Semantics for content description

## Installation

### JitPack

1. Add the JitPack repository (e.g. in `settings.gradle.kts`):

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

2. Add the dependency:

```kotlin
dependencies {
    implementation("com.github.aylar:ChartingLib:VERSION")
}
```

Replace `VERSION` with a release tag (e.g. `1.0.0`) or commit hash.

### Local (development)

Include the `charting-lib` module and depend on it from your app:

```kotlin
implementation(project(":charting-lib"))
```

## Quick start

### Line chart

```kotlin
import com.aylar.chartinglib.charts.line.LineChart
import com.aylar.chartinglib.charts.line.LineChartStyle
import com.aylar.chartinglib.state.rememberChartState

val data = ChartData(
    listOf(
        DataSeries(
            points = listOf(
                DataPoint(0f, 1f),
                DataPoint(1f, 3f),
                DataPoint(2f, 2f),
                DataPoint(3f, 5f)
            ),
            label = "Series A"
        )
    )
)

LineChart(
    modifier = Modifier.fillMaxWidth().height(300.dp),
    data = data,
    style = LineChartStyle(
        lineColor = MaterialTheme.colorScheme.primary,
        lineWidth = 3.dp,
        fillAlpha = 0.2f,
        showPoints = true
    ),
    xAxis = AxisConfig(labelCount = 6, formatter = { "%.1f".format(it) }),
    animation = AnimationConfig(durationMillis = 600, enableDrawOn = true),
    chartState = rememberChartState(data.dataBounds()),
    onPointSelected = { point, _ -> /* scrubber */ },
    tooltipConfig = TooltipConfig()
)
```

### Bar chart

```kotlin
BarChart(
    modifier = Modifier.height(280.dp),
    data = barChartData,
    style = BarChartStyle(barCornerRadius = 4.dp),
    padding = PaddingValues(48f, 24f, 24f, 48f)
)
```

### Pie / Donut

```kotlin
val slices = listOf(
    PieSlice("A", 40f),
    PieSlice("B", 25f),
    PieSlice("C", 20f),
    PieSlice("D", 15f)
)

PieChart(modifier = Modifier.size(200.dp), slices = slices)
DonutChart(slices = slices, innerRadiusRatio = 0.5f, centerLabel = "Total")
```

## API overview

| Chart      | Composable   | Data / config |
|-----------|--------------|----------------|
| Line      | `LineChart`  | `ChartData`, `LineChartStyle`, `AxisConfig`, `AnimationConfig`, `TooltipConfig` |
| Bar       | `BarChart`   | `ChartData`, `BarChartStyle`, `AxisConfig`, `GridConfig` |
| Pie       | `PieChart`   | `List<PieSlice>`, `PieChartStyle` |
| Donut     | `DonutChart` | `List<PieSlice>`, `PieChartStyle`, `innerRadiusRatio`, `centerLabel` |

Shared building blocks: `DataPoint`, `DataSeries`, `ChartData`, `CoordinateMapper`, `PaddingValues`, `ChartDefaults`.

## Compose & SDK

- **Min SDK:** 24  
- **Compile SDK:** 36  
- **Compose BOM:** 2024.09.00 (see project `gradle/libs.versions.toml`)  
- **Kotlin:** 2.0.x  

## License

Apache 2.0. See [LICENSE](LICENSE) if present.
