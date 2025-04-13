package com.jelly.ai.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(
    paddingValues: PaddingValues,
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Profile") },
                actions = {
                    IconButton(onClick = { /* TODO: Settings or More actions */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Avatar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))
            // 用户名
            Text(
                text = "Stay Calm",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "ID: user_xxxxxxxx",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = { /* TODO: Edit profile */ }) {
                Text(text = "Edit Personal Info")
            }

            Spacer(modifier = Modifier.height(24.dp))

            val tabs = listOf("Works", "Private", "AI Persona", "Favorites")
            TabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            if (selectedTabIndex == 2) {
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = "Click to create your first AI persona",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { /* TODO: Create AI persona */ }) {
                    Text(text = "Create Now")
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "No content for \"${tabs[selectedTabIndex]}\" tab yet.",
                        color = Color.Gray
                    )
                }
            }
        }
    }
}