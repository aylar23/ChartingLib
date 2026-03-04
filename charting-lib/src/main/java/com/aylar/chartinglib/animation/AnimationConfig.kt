package com.aylar.chartinglib.animation

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Immutable

/**
 * Configuration for chart animations (duration, easing).
 */
@Immutable
data class AnimationConfig(
    val durationMillis: Int = 600,
    val easing: Easing = FastOutSlowInEasing,
    val enableDrawOn: Boolean = true
)
