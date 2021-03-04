package com.example.androiddevchallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun CountdownWheel(seconds: Int) {
    NumberWheel(
        min = 0,
        max = 60,
        current = seconds,
        color = Color.Blue
    )
}

@Composable
private fun NumberWheel(
    min: Int,
    max: Int,
    current: Int,
    color: Color
) {
    RingLayout(color = color) {
        (min..max).forEach {
            Text(
                it.toString(),
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

@Composable
private fun RingLayout(
    modifier: Modifier = Modifier,
    color: Color,
    content: @Composable () -> Unit
) {
    val ringWidth = 24.dp
    Layout(
        content = content,
        modifier = modifier
            .clip(RingShape(ringWidth))
            .background(color)
            .aspectRatio(1f)
            .fillMaxWidth()
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }

        val radiansPerItem = 2 * PI / measurables.size
        val ringCenterRadius = ((constraints.maxWidth - ringWidth.roundToPx()) / 2)

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEachIndexed { index, placeable ->
                val offset = angleToXY(radiansPerItem * index, ringCenterRadius)
                placeable.place(offset)
            }
        }
    }
}

private fun angleToXY(angleRadians: Double, radius: Int) = IntOffset(
    x = (radius * cos(angleRadians)).roundToInt(),
    y = (radius * sin(angleRadians)).roundToInt()
)

@Preview
@Composable
fun NumberWheelPreview() {
    NumberWheel(min = 0, max = 60, current = 30, color = Color.Blue)
}

