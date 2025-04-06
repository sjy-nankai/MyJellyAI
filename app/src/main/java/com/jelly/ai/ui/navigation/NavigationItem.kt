package com.jelly.ai.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NaviItem(val title: String, val icon: ImageVector, val route: String) {
    data object Conversation : NaviItem("对话", Icons.AutoMirrored.Filled.Chat, "Conversation")
    data object AIAgent : NaviItem("智能体", Icons.Default.Search, "AIAgent")
    data object Creation : NaviItem("创作", Icons.Default.Create, "Creation")
    data object Notification : NaviItem("通知", Icons.Default.Notifications, "Notification")
    data object Me : NaviItem("我的", Icons.Default.Person, "Me")
}
