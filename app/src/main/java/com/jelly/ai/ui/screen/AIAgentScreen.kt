package com.jelly.ai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jelly.ai.ui.data.AIAgentItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIAgentScreen(
    paddingValues: PaddingValues,
) {
    val tabs = listOf("推荐", "精选", "拍照问", "学习", "品牌专区")
    var selectedTabIndex by remember { mutableStateOf(0) }

    // 示例数据
    val aiItems = listOf(
        AIAgentItem(
            name = "编程助理",
            label = "官方",
            description = "经验丰富的工程师，擅长帮助用户解决编程问题并拓展思路\nby @Jelly官方"
        ),
        AIAgentItem(
            name = "白鹿",
            label = null,
            description = "在抖音、微博上都很火的博主，分享时尚穿搭与日常生活"
        ),
        AIAgentItem(
            name = "鱼圆",
            label = null,
            description = "精通AI生图，拥有丰富的Prompt技巧，为你提供灵感和指导"
        ),
        AIAgentItem(
            name = "秘书",
            label = null,
            description = "帮你管理日程，提醒待办事项，提供个人时间管理方案"
        ),
        AIAgentItem(
            name = "思绪",
            label = null,
            description = "陪你聊天、解压，让你随时找到一个倾听者"
        ),
        AIAgentItem(
            name = "姓名学",
            label = null,
            description = "根据传统文化，帮你解读姓名背后的意义"
        )
    )

    Scaffold(
        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
        topBar = {
            TopAppBar(
                title = {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        edgePadding = 0.dp,
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(title) }
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: 搜索逻辑 */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("创建AI智能体") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "创建AI智能体"
                    )
                },
                onClick = { /* TODO: 执行创建AI智能体的逻辑 */ }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(aiItems.size) { item ->
                AIItemRow(item = aiItems[item])
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun AIItemRow(item: AIAgentItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.name.firstOrNull()?.toString() ?: "",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                if (!item.label.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFEEEEEE)
                    ) {
                        Text(
                            text = item.label,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        IconButton(onClick = {  }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }
    }
}
