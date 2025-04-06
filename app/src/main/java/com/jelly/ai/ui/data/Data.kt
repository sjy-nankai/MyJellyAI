package com.jelly.ai.ui.data

data class AIAgentItem(
    val name: String,
    val label: String? = null,       // 例如 "官方"
    val description: String,
    val avatarUrl: String? = null    // 若需要网络图片，可使用 Coil 或 AsyncImage
)