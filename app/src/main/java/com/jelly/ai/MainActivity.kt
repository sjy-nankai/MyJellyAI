package com.jelly.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jelly.ai.ui.navigation.NaviItem
import com.jelly.ai.ui.navigation.NavigationGraph
import com.jelly.ai.ui.theme.MyJellyAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyJellyAITheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val currentRoute = navBackStackEntry?.destination?.route
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute in listOf(
                                NaviItem.Conversation.route,
                                NaviItem.AIAgent.route,
                                NaviItem.Creation.route,
                                NaviItem.Notification.route,
                                NaviItem.Me.route
                            )
                        ) {
                            NavigationBar {
                                listOf(
                                    NaviItem.Conversation,
                                    NaviItem.AIAgent,
                                    NaviItem.Creation,
                                    NaviItem.Notification,
                                    NaviItem.Me,
                                ).forEach { bottomNaviItem ->
                                    NavigationBarItem(
                                        selected = bottomNaviItem.route == currentDestination?.route,
                                        label = {
                                            Text(bottomNaviItem.title)
                                        },
                                        icon = {
                                            Icon(
                                                bottomNaviItem.icon,
                                                contentDescription = bottomNaviItem.title
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color.Black,
                                            unselectedIconColor = Color.Gray,
                                            selectedTextColor = Color.Black,
                                            unselectedTextColor = Color.Gray,
                                            indicatorColor = Color.Transparent
                                        ),
                                        onClick = {
                                            navController.navigate(bottomNaviItem.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        paddingValues = innerPadding,
                    )
                }
            }
        }
    }
}
