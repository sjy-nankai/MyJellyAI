package com.jelly.ai.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    paddingValues: PaddingValues,
    navigationController: NavController,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "对话") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                    Box {
                        IconButton(onClick = { menuExpanded = !menuExpanded }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More"
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            modifier = Modifier
                                .width(150.dp)
                                .background(Color.White),
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = { menuExpanded = !menuExpanded },
                                text = { Text(text = "创建新对话") }
                            )
                            DropdownMenuItem(
                                onClick = { menuExpanded = !menuExpanded },
                                text = {
                                    Text(text = "创建AI智能体")
                                }
                            )
                            DropdownMenuItem(
                                onClick = { menuExpanded = !menuExpanded },
                                text = {
                                    Text(text = "扫一扫")
                                }
                            )
                            DropdownMenuItem(
                                onClick = {menuExpanded = !menuExpanded },
                                text = {
                                    Text(text = "Ola Friend 耳机")
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                ConversationItem(
                    avatarRes = null, // 替换成你自己的头像资源或网络图片
                    title = "Jelly",
                    subTitle = "用 Jelly 生图，一键打开",
                    modifier = Modifier.clickable {
                        navigationController.navigate("chat_detail")
                    }
                )
            }
            item {
                ConversationItem(
                    avatarRes = null,
                    title = "Jelly开发者百科",
                    subTitle = "关于Jelly云百科的使用指南"
                )
            }
            item {
                ConversationItem(
                    avatarRes = null,
                    title = "Jelly 图片生成",
                    subTitle = "抱歉，我无法生成你要求的图片"
                )
            }
        }
    }

}

@Composable
fun ConversationItem(
    avatarRes: Int?,       // 如果使用本地资源可以传 @DrawableRes；网络图片可传 URL 并用 AsyncImage
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (avatarRes != null) {
            Image(
                painter = painterResource(id = avatarRes),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
        } else {
            // 如果没有实际图片资源，可放一个默认占位或者初始字母头像
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title.firstOrNull()?.toString() ?: "",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subTitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}