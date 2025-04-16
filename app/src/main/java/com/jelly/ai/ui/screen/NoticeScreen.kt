package com.jelly.ai.ui.screen

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.jelly.ai.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    paddingValues: PaddingValues,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "通知") },
                actions = {
                    // 若需要更多操作按钮，可在右侧放置一个图标或菜单
                    IconButton(onClick = { /* TODO: ... */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More"
                        )
                    }
                }
            )
        },
        // 如果你的项目中已有统一的 BottomBar，可在上层处理，此处可不写
        bottomBar = {
            // 示例：若暂时需要一个占位 BottomBar
            BottomAppBar {
                Text(
                    text = "底部导航占位",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BannerSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                banners = listOf(
                    R.drawable.placeholder_nuts,
                    R.drawable.placeholder_fruit
                )
            )

            EmptyNotificationPlaceholder(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerSection(
    banners: List<Int>,
    modifier: Modifier = Modifier
) {
    val state = rememberPagerState(initialPage = 0) { banners.size }
    val animationScope = rememberCoroutineScope()
    ConstraintLayout(modifier = modifier) {
        val (pager, indicator) = createRefs()
        HorizontalPager(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .constrainAs(pager) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            state = state
        ) { page ->
            AsyncImage(
                model = banners[page % banners.size],
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .constrainAs(indicator) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(state.pageCount) { iteration ->
                val color = if (state.currentPage == iteration) Color.White else Color.Gray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyNotificationPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.placeholder_nuts),
            contentDescription = "No Notifications",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "No notification has been received yet.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
    }
}


@Preview
@Composable
fun RedOutlinedBarPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RedOutlinedBar2(modifier = Modifier.height(30.dp).width(200.dp)) {
            Text("hello")
        }
    }

}

@Composable
fun RedOutlinedBar(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                clip = false,
                ambientColor = Color.Red,
                spotColor = Color.Red
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 0.1.dp,
                color = Color.Red,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        content()
    }
}


@Composable
fun RedOutlinedBar2(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // 创建一个自定义的阴影效果
    Box(
        modifier = modifier
    ) {
        // 阴影层 - 使用更大的红色背景并模糊处理
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = 0.dp) // 不需要偏移，均匀扩散
                .scale(scaleX = 1.05f, scaleY = 1.2f) // 稍微放大以创建阴影效果
                .background(
                    color = Color.Red.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(50)
                )
                .blur(radius = 8.dp) // 应用模糊效果创建阴影的扩散感
        )

        // 主要内容层
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(50)
                )
                .border(
                    width = 1.dp,
                    color = Color.Red,
                    shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            content()
        }
    }
}



