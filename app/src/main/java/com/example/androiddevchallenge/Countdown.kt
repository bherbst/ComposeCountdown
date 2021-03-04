package com.example.androiddevchallenge

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import kotlin.time.*

@OptIn(ExperimentalTime::class)
@Composable
fun Countdown(
    duration: Duration,
    remaining: Duration,
    modifier: Modifier = Modifier
) {
    val maxHours = remember { duration.hoursComponent() }
    val maxMinutes = remember { minOf(60, duration.inMinutes.roundToInt()) }
    val maxSeconds = remember { minOf(60, duration.inSeconds.roundToInt()) }

    val strokeWidth = 16.dp
    val ringPadding = 4.dp
    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        var nextRingPadding = 0.dp
        if (duration.inHours >= 1) {
            // Hours
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = remaining.hoursComponent().toFloat() / maxHours,
                color = Color.Red,
                strokeWidth = strokeWidth
            )
            nextRingPadding += strokeWidth + ringPadding
        }

        if (duration.inMinutes >= 1) {
            // Minutes
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(nextRingPadding)
                    .fillMaxSize(),
                progress = remaining.minutesComponent().toFloat() / maxMinutes,
                color = Color.Green,
                strokeWidth = strokeWidth
            )
            nextRingPadding += strokeWidth + ringPadding
        }

        // Seconds
        CircularProgressIndicator(
            modifier = Modifier
                .padding(nextRingPadding)
                .fillMaxSize(),
            progress = remaining.secondsComponent().toFloat() / maxSeconds,
            color = Color.Blue,
            strokeWidth = strokeWidth
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (remaining.inHours > 0) {
                Text(
                    text = "${remaining.hoursComponent()} hours",
                    fontSize = activeFontSize,
                    color = activeFontColor,
                )
            }
            
            if (remaining.inMinutes > 0) {
                Text(
                    text = "${remaining.minutesComponent()} minutes",
                    fontSize = if (remaining.inHours <= 0) activeFontSize else inactiveFontSize,
                    color = if (remaining.inHours <= 0) activeFontColor else inactiveFontColor,
                )
            }

            Text(
                text = "${remaining.secondsComponent()} seconds",
                fontSize = if (remaining.inMinutes <= 0) activeFontSize else inactiveFontSize,
                color = if (remaining.inMinutes <= 0) activeFontColor else inactiveFontColor,
            )
        }
    }
}

val activeFontSize = 20.sp
val inactiveFontSize = 14.sp

val activeFontColor = Color(0xFFFFFFFF)
val inactiveFontColor = Color(0x88FFFFFF)

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