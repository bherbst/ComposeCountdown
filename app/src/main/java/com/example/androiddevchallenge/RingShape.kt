package com.example.androiddevchallenge

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

class RingShape(private val ringWidth: Dp) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val radius = size.minDimension / 2
        val ringWidthPx = with(density) { ringWidth.toPx() }
        val outerCircle = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = size.toRect(),
                    cornerRadius = CornerRadius(radius)
                )
            )
        }
        val innerCircle = Path().apply {
            val innerRect = size.toRect().deflate(ringWidthPx)
            val innerRadius = innerRect.width / 2
            addRoundRect(
                RoundRect(
                    rect = innerRect,
                    cornerRadius = CornerRadius(innerRadius)
                )
            )
        }

        val ring = Path.combine(PathOperation.difference, outerCircle, innerCircle)

        return Outline.Generic(ring)
    }
}