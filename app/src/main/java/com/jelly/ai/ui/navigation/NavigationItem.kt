package com.jelly.ai.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NaviItem(val title: String, val icon: ImageVector, val route: String) {
    data object Conversation : NaviItem("Chat", Icons.AutoMirrored.Filled.Chat, "Chat")
    data object AIAgent : NaviItem("Agent", Icons.Default.Search, "Agent")
    data object Creation : NaviItem("Creation", Icons.Default.Create, "Creation")
    data object Notification : NaviItem("Notice", Icons.Default.Notifications, "Notice")
    data object Me : NaviItem("Me", Icons.Default.Person, "Me")
}
