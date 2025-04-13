package com.jelly.ai.ui

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import android.graphics.Paint


@Preview
@Composable
fun SampleUsage() {
    UniformShadowBox(
        modifier = Modifier.size(150.dp),
        shadowElevation = 12.dp,
        cornerRadius = 8.dp,
        shadowColor = Color.Red.copy(alpha = 0.5f),
        backgroundColor = Color.White
    ) {
        Text("hello")
    }
}

@Composable
fun UniformShadowBox(
    modifier: Modifier = Modifier,
    shadowElevation: Dp = 12.dp,
    cornerRadius: Dp = 8.dp,
    shadowColor: Color = Color.Red.copy(alpha = 0.5f),
    backgroundColor: Color = Color.White,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    // 将 Dp 转换为像素
    val elevationPx = with(density) { shadowElevation.toPx() }
    val radiusPx = with(density) { cornerRadius.toPx() }

    Box(
        modifier = modifier
            .drawBehind {
                // 创建 Paint 对象，并使用 setShadowLayer 设置阴影颜色和扩散半径
                val paint = Paint().apply {
                    // 这里 color 用背景色，shadowLayer 会在周围绘制阴影
                    color = backgroundColor.toArgb()
                    isAntiAlias = true
                    // 设置均匀阴影：这里 offset 设置为 0，表示四周均匀扩散；阴影颜色由 shadowColor 决定
                    setShadowLayer(elevationPx, 0f, 0f, shadowColor.toArgb())
                }

                // 绘制圆角矩形，利用 nativeCanvas 绘制阴影
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawRoundRect(
                        0f,
                        0f,
                        size.width,
                        size.height,
                        radiusPx,
                        radiusPx,
                        paint
                    )
                }
            }
            // 接着绘制盒子背景（覆盖原来的白色部分），
            // 注意：如果你需要让阴影显示出来，通常盒子需要有一定的 padding 遮挡部分内容。
            .background(backgroundColor, shape = RoundedCornerShape(cornerRadius))
    ) {
        content()
    }
}