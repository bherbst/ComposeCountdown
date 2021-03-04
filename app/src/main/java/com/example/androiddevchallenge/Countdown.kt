package com.example.androiddevchallenge

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.minutes
import kotlin.time.seconds

@OptIn(ExperimentalTime::class, ExperimentalAnimationApi::class)
@Composable
fun Countdown(
    duration: Duration,
    remaining: Duration,
    modifier: Modifier = Modifier
) {
    val maxMinutes = remember { duration.inMinutes.roundToInt() }
    val maxSeconds = remember { minOf(60, duration.inSeconds.roundToInt()) }

    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        CountdownRing(
            modifier = Modifier.fillMaxSize()
                .padding(ringPadding + ringWidth),
            remaining = (remaining.inSeconds % (maxMinutes * 60)).roundToInt(),
            max = maxMinutes * 60,
            resetAtZero = remaining.minutesComponent() > 1,
            color = MaterialTheme.colors.primary
        )

        // Seconds
        CountdownRing(
            modifier = Modifier.fillMaxSize()
                .padding((ringPadding + ringWidth) * 2),
            remaining = remaining.secondsComponent(),
            max = maxSeconds,
            resetAtZero = remaining.secondsComponent() > 60,
            tickDurationMillis = 1000 * (60 / maxSeconds),
            color = MaterialTheme.colors.secondary
        )

        CountdownText(remaining)
    }
}

@Composable
private fun CountdownRing(
    modifier: Modifier = Modifier,
    remaining: Int,
    max: Int,
    resetAtZero: Boolean,
    tickDurationMillis: Int = 1000,
    color: Color,
) {
    val progress by animateFloatAsState(
        targetValue = progressFor(remaining, max, resetAtZero),
        animationSpec = tween(
            durationMillis = tickDurationMillis,
            easing = LinearEasing
        ),
    )

    CircularProgressIndicator(
        modifier = modifier,
        progress = progress,
        color = color,
        strokeWidth = ringWidth
    )
}

@OptIn(ExperimentalTime::class, ExperimentalAnimationApi::class)
@Composable
private fun CountdownText(remaining: Duration) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = remaining.inMinutes >= 1,
            exit = fadeOut() + slideOutVertically()
        ) {
            val minutesDominant = remaining.inHours < 1
            Text(
                text = "${remaining.minutesComponent()} minutes",
                fontSize = if (minutesDominant) activeFontSize else inactiveFontSize,
                color = MaterialTheme.colors.primary.copy(alpha = if (minutesDominant) 1f else inactiveFontAlpha),
            )
        }

        AnimatedVisibility(
            visible = remaining.inSeconds >= 1,
            exit = fadeOut() + slideOutVertically()
        ) {
            val secondsDominant = remaining.inMinutes < 1
            Text(
                text = "${remaining.secondsComponent()} seconds",
                fontSize = if (secondsDominant) activeFontSize else inactiveFontSize,
                color = MaterialTheme.colors.secondary.copy(alpha = if (secondsDominant) 1f else inactiveFontAlpha),
            )
        }

        AnimatedVisibility(remaining.inSeconds < 1) {
            Text(
                text = "Time is up!",
                fontSize = activeFontSize,
                color = MaterialTheme.colors.secondary,
            )
        }
    }
}

private val activeFontSize = 20.sp
private val inactiveFontSize = 14.sp

private val ringWidth = 16.dp
private val ringPadding = 4.dp

private const val inactiveFontAlpha = .6f


/**
 * Calculate the progress float for a particular unit. This will wrap back to 1f if there are 0 units
 * remaining.
 *
 * @param remaining the amount of the given unit remaining
 * @param max the maximum amount of units for this ring
 */
private fun progressFor(
    remaining: Int,
    max: Int,
    resetAtZero: Boolean
): Float {
    return if (remaining > 0 ) {
        remaining.toFloat() / max
    } else {
        if (resetAtZero) 1f else 0f
    }
}

@Preview
@Composable
@OptIn(ExperimentalTime::class)
private fun CountdownPreview() {
    Countdown(
        duration = 2.hours + 20.minutes + 45.seconds,
        remaining = 1.hours + 15.minutes + 40.seconds,
        modifier = Modifier.padding(16.dp)
    )
}