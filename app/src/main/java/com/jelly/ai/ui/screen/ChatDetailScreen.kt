package com.jelly.ai.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    navController: androidx.navigation.NavController,
    chatId: String?
) {
    // 此处可根据 chatId 加载数据，此处使用模拟数据
    val messages = listOf(
        ChatMessage(
            text = "快来试试",
            fromSelf = false
        ),
        ChatMessage(
            text = "好的",
            fromSelf = true
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "豆包") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = "通话")
                    }
                }
            )
        },
        bottomBar = {
            BottomToolBar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages.size) { index ->
                ChatBubble(message = messages[index])
            }
        }
    }
}

data class ChatMessage(
    val text: String,
    val fromSelf: Boolean = false
)

@Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.fromSelf) {
        Alignment.End
    } else {
        Alignment.Start
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = if (message.fromSelf) Color(0xFFDCF8C6) else Color.White,
            tonalElevation = 2.dp
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun BottomToolBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        listOf("深度思考", "AI作家", "翻译", "AI画家").forEach { label ->
            ElevatedButton(onClick = { /* TODO */ }) {
                Text(text = label)
            }
        }
    }
}