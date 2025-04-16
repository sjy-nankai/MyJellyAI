package com.jelly.ai.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PlayerControlBar(
    backgroundColor: Color = Color.LightGray.copy(alpha = 0.95f),
    borderColor: Color = Color.Red,
    shadowColor: Color = Color.Red.copy(alpha = 0.5f),
    borderWidth: androidx.compose.ui.unit.Dp = 1.dp,
    shadowRadius: androidx.compose.ui.unit.Dp = 10.dp,
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .height(48.dp)
            .fillMaxWidth()
    ) {
        CustomBorderWithShadow(
            cornerRadius = 24.dp,
            shadowColor = shadowColor,
            shadowRadius = shadowRadius,
            borderColor = borderColor,
            backgroundColor = backgroundColor,
            borderWidth = borderWidth
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
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

@Composable
fun CustomBorderWithShadow(
    cornerRadius: androidx.compose.ui.unit.Dp,
    shadowColor: Color,
    shadowRadius: androidx.compose.ui.unit.Dp,
    borderColor: Color,
    backgroundColor: Color,
    borderWidth: androidx.compose.ui.unit.Dp = 1.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .drawBehind {
                drawCustomBorderWithShadow(
                    cornerRadius = cornerRadius.toPx(),
                    shadowColor = shadowColor,
                    shadowRadius = shadowRadius.toPx(),
                    borderColor = borderColor,
                    borderWidth = borderWidth.toPx()
                )
            }
    ) {
        Box(
            modifier = Modifier
                .padding(borderWidth)
                .clip(RoundedCornerShape(cornerRadius))
                .background(backgroundColor)
        ) {
            content()
        }
    }
}

private fun DrawScope.drawCustomBorderWithShadow(
    cornerRadius: Float,
    shadowColor: Color,
    shadowRadius: Float,
    borderColor: Color,
    borderWidth: Float
) {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()

        // 为边框和阴影创建更柔和的效果
        frameworkPaint.isAntiAlias = true

        // 先绘制阴影
        frameworkPaint.color = borderColor.toArgb()
        frameworkPaint.setShadowLayer(
            shadowRadius,
            0f,
            0f,
            shadowColor.toArgb()
        )

        // 使用稍微小一点的区域绘制阴影，使阴影看起来更自然
        val shadowPadding = borderWidth / 2
        canvas.drawRoundRect(
            left = shadowPadding,
            top = shadowPadding,
            right = size.width - shadowPadding,
            bottom = size.height - shadowPadding,
            radiusX = cornerRadius - shadowPadding,
            radiusY = cornerRadius - shadowPadding,
            paint = paint
        )

        // 重置画笔以绘制实际边框
        frameworkPaint.setShadowLayer(0f, 0f, 0f, Color.Transparent.toArgb())
        frameworkPaint.color = borderColor.toArgb()
        frameworkPaint.style = android.graphics.Paint.Style.STROKE
        frameworkPaint.strokeWidth = borderWidth

        // 绘制边框
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
fun PlayerControlBarPreview3() {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 默认样式，更细的边框
        PlayerControlBar(
            borderWidth = 0.5.dp,
            shadowRadius = 12.dp
        )

        // 白色背景，更自然的阴影
        PlayerControlBar(
            backgroundColor = Color.White,
            borderWidth = 0.5.dp,
            shadowRadius = 12.dp
        )

        // 深色背景，蓝色边框和阴影
        PlayerControlBar(
            backgroundColor = Color(0xFF333333),
            borderColor = Color.Blue,
            shadowColor = Color.Blue.copy(alpha = 0.4f),
            borderWidth = 0.5.dp,
            shadowRadius = 12.dp
        )
    }
}