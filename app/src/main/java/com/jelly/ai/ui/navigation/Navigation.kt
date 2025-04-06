package com.jelly.ai.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jelly.ai.ui.screen.AIAgentScreen
import com.jelly.ai.ui.screen.ChatDetailScreen
import com.jelly.ai.ui.screen.ConversationScreen
import com.jelly.ai.ui.screen.CreationScreen
import com.jelly.ai.ui.screen.MeScreen
import com.jelly.ai.ui.screen.NotificationScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = NaviItem.Conversation.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) },
    ) {
        composable(NaviItem.Conversation.route) {
            ConversationScreen(paddingValues, navController)
        }

        composable(NaviItem.AIAgent.route) {
            AIAgentScreen(paddingValues)
        }

        composable(NaviItem.Creation.route) {
            CreationScreen(
                paddingValues,
            )
        }

        composable(NaviItem.Notification.route) {
            NotificationScreen(
                paddingValues,
            )
        }

        composable(NaviItem.Me.route) {
            MeScreen(
                paddingValues,
            )
        }

        composable(
            route = "chat_detail",
//            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
        ) { backStackEntry ->
            ChatDetailScreen(
                navController = navController,
                chatId = "id"
            )
        }


//        composable(NaviItem.Repositories.route) {
//            RepositoriesScreen(
//                onBackClick = { navController.popBackStack() }
//            )
//        }
    }
}