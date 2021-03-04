package com.example.androiddevchallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.CountdownTheme
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.minutes

@OptIn(ExperimentalTime::class)
@Composable
fun CountdownScreen(
    defaultDuration: Duration = 5.minutes
) {
    var timer  by remember { mutableStateOf(Timer(defaultDuration)) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Countdown(
            duration = timer.duration,
            remaining = timer.remaining,
            modifier = Modifier.padding(16.dp)
        )

        TimerControls(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onPlayPause = {
                if (timer.isRunning) {
                    timer.pause()
                } else {
                    scope.launch { timer.start() }
                }
            },
            isRunning = timer.isRunning,
            onEdit = {
                // TODO real implemenation
                timer = Timer(defaultDuration)
            },
        )
    }
}

@Composable
private fun TimerControls(
    onPlayPause: () -> Unit,
    isRunning: Boolean,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FloatingActionButton(
            onClick = onPlayPause,
            backgroundColor = Color.Blue
        ) {
            Icon(
                imageVector = if (isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (isRunning) "Pause" else "Play"
            )
        }

        FloatingActionButton(
            onClick = onEdit,
            backgroundColor = Color.Blue
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Pause"
            )
        }
    }
}

@Preview
@Composable
private fun CountdownScreenPreview() {
    CountdownTheme {
        CountdownScreen()
    }
}