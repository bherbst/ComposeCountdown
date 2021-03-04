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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.CountdownTheme
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@OptIn(ExperimentalTime::class)
@Composable
fun CountdownScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Countdown(
            duration = 1.hours,
            remaining = 1.hours,
            modifier = Modifier.padding(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = Color.Blue
            ) {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = "Pause"
                )
            }

            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = Color.Blue
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Pause"
                )
            }
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