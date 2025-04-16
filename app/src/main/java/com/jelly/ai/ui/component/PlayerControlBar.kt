package com.jelly.ai.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PlayerControlBar() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .height(48.dp)
            .fillMaxWidth()
    ) {
        RedBorderWithShadow(
            cornerRadius = 24.dp,
            shadowColor = Color.Red.copy(alpha = 0.5f),
            shadowRadius = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.95f)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left part with black rectangle (possibly a progress bar or text placeholder)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black)
                )

                // Right side icons
                Row(
                    modifier = Modifier.padding(end = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Volume",
                        tint = Color.DarkGray
                    )

                    // Sound waves icon representation
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        for (height in listOf(8, 12, 16, 12, 8)) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(height.dp)
                                    .background(Color.DarkGray)
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RedOutlinedBarPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RedBorderWithShadow(cornerRadius = 8.dp, shadowColor = Color.Red, shadowRadius = 8.dp) {
            Text(
                modifier = Modifier
                    .width(200.dp)
                    .height(30.dp)
//                    .background(Color.White)
                ,
                text = "hello",
                textAlign = TextAlign.Center,
            )
        }
    }

}

@Composable
fun RedBorderWithShadow(
    cornerRadius: androidx.compose.ui.unit.Dp,
    shadowColor: Color,
    shadowRadius: androidx.compose.ui.unit.Dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .drawBehind {
                drawRedBorderWithShadow(
                    cornerRadius = cornerRadius.toPx(),
                    shadowColor = shadowColor,
                    shadowRadius = shadowRadius.toPx(),
                    borderColor = Color.Red,
                    borderWidth = 2.dp.toPx()
                )
            }
    ) {
        Box(
            modifier = Modifier
//                .padding(2.dp)
                .clip(RoundedCornerShape(cornerRadius))
        ) {
            content()
        }
    }
}

private fun DrawScope.drawRedBorderWithShadow(
    cornerRadius: Float,
    shadowColor: Color,
    shadowRadius: Float,
    borderColor: Color,
    borderWidth: Float
) {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()

        // Set shadow properties
        frameworkPaint.color = borderColor.toArgb()
        frameworkPaint.setShadowLayer(
            shadowRadius,
            0f,
            0f,
            shadowColor.toArgb()
        )

        // Draw rounded rectangle with shadow
        canvas.drawRoundRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height,
            radiusX = cornerRadius,
            radiusY = cornerRadius,
            paint = paint
        )

        // Reset the paint to draw the border
        frameworkPaint.setShadowLayer(0f, 0f, 0f, Color.Transparent.toArgb())
        frameworkPaint.color = borderColor.toArgb()
        frameworkPaint.style = android.graphics.Paint.Style.STROKE
        frameworkPaint.strokeWidth = borderWidth

        // Draw the border
        canvas.drawRoundRect(
            left = borderWidth / 2,
            top = borderWidth / 2,
            right = size.width - borderWidth / 2,
            bottom = size.height - borderWidth / 2,
            radiusX = cornerRadius,
            radiusY = cornerRadius,
            paint = paint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerControlBarPreview() {
    PlayerControlBar()
}




